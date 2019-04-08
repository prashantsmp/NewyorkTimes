package com.nytimes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nytimes.R;
import com.nytimes.model.Article;
import com.nytimes.model.Media;
import com.nytimes.model.MediaMetadata;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView byLine;
    private TextView date;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolbar();
        initViews();

        if (getIntent() != null) {
            Article article = (Article) getIntent().getSerializableExtra("article");
            if (article != null) {
                if (!isValidString(article.getTitle()))
                    title.setText(article.getTitle());

                if (!isValidString(article.getByLine()))
                    byLine.setText(article.getByLine());

                if (!isValidString(article.getPublishedDate()))
                    date.setText(article.getPublishedDate());

                RequestOptions requestOptions = new RequestOptions().fitCenter().
                        diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

                if (article.getMediaList() != null) {
                    if (article.getMediaList().get(0) != null) {
                        Media media = article.getMediaList().get(0);
                        if (media != null) {
                            List<MediaMetadata> mediaMetadata = media.getMetadataList();
                            if (mediaMetadata != null) {
                                if (mediaMetadata.get(0) != null) {
                                    if (mediaMetadata.get(0).getImageUrl() != null) {
                                        Glide.with(image.getContext()).load(mediaMetadata.get(0).getImageUrl()).apply(requestOptions).into(image);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }

    private void initToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        title = findViewById(R.id.title);
        byLine = findViewById(R.id.by_line);
        date = findViewById(R.id.date);
        image = findViewById(R.id.image);
    }

    private boolean isValidString(String title) {
        return title == null || title.length() <= 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
