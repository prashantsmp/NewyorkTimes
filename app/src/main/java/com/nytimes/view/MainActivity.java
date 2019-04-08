package com.nytimes.view;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nytimes.R;
import com.nytimes.controller.ArticlesAdapter;
import com.nytimes.controller.ItemClickListener;
import com.nytimes.model.Article;
import com.nytimes.model.ArticleResponse;
import com.nytimes.model.Constant;
import com.nytimes.network.NetworkUtils;
import com.nytimes.network.RetrofitApiClient;
import com.nytimes.network.RetrofitApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView empty;
    private List<Article> articleList = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setAdapter();
        fetchArticles();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        empty = findViewById(R.id.empty);
    }

    private void setAdapter() {
        articlesAdapter = new ArticlesAdapter(articleList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(articlesAdapter);
    }

    private void fetchArticles() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (NetworkUtils.isConnected(MainActivity.this))
                    getArticles();
                else
                    showError(getString(R.string.internet_error));
            }
        });
    }

    private void getArticles() {
        RetrofitApiInterface apiService = RetrofitApiClient.getRetrofitApiClient().create(RetrofitApiInterface.class);
        Call<ArticleResponse> call = apiService.getArticles("all-sections", "7", Constant.API_KEY);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponse> call, @NonNull Response<ArticleResponse> response) {

                int statusCode = response.code();

                if (statusCode == 200) {
                    List<Article> articles;
                    if (response.body() != null) {
                        articles = response.body().getResults();
                        parseResponse(articles);
                    }
                } else {
                    showError(getString(R.string.server_error));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponse> call, @NonNull Throwable t) {
                showError(t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void parseResponse(final List<Article> articles) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (articles != null && !articles.isEmpty()) {
                    articleList.addAll(articles);
                    articlesAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void showError(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Article article) {
        if (article != null) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("article", article);
            startActivity(intent);
        }
    }
}
