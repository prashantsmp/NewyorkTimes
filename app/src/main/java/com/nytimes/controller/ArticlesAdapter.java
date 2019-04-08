package com.nytimes.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<Article> articleList;
    private ItemClickListener itemClickListener;

    public ArticlesAdapter(List<Article> articles, ItemClickListener clickListener) {
        this.articleList = articles;
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.article_item, viewGroup, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position) {

        Article article = articleList.get(position);
        if (article != null) {
            if (!isValidString(article.getTitle()))
                articleViewHolder.title.setText(article.getTitle());

            if (!isValidString(article.getByLine()))
                articleViewHolder.byLine.setText(article.getByLine());

            if (!isValidString(article.getPublishedDate()))
                articleViewHolder.date.setText(article.getPublishedDate());

            RequestOptions requestOptions = new RequestOptions().
                    diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true);

            if (article.getMediaList() != null) {
                if (article.getMediaList().get(0) != null) {
                    Media media = article.getMediaList().get(0);
                    if (media != null) {
                        List<MediaMetadata> mediaMetadata = media.getMetadataList();
                        if (mediaMetadata != null) {
                            if (mediaMetadata.get(0) != null) {
                                if (mediaMetadata.get(0).getImageUrl() != null) {
                                    Glide.with(articleViewHolder.image.getContext()).load(mediaMetadata.get(0).getImageUrl()).apply(requestOptions).into(articleViewHolder.image);
                                }
                            }
                        }
                    }
                }
            }


        }

    }

    private boolean isValidString(String title) {
        return title == null || title.length() <= 0;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void swap(List<Article> articleList) {
        this.articleList = articleList;
        this.notifyDataSetChanged();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView byLine;
        private TextView date;
        private CircleImageView image;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            byLine = itemView.findViewById(R.id.by_line);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(articleList.get(getAdapterPosition()));
                }
            });
        }
    }
}
