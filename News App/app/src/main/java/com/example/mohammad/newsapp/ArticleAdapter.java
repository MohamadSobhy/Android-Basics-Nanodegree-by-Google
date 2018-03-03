package com.example.mohammad.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohammad on 06/02/18.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0 , articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view , parent , false);
        }

        Article currentArticle = getItem(position);

        TextView articleTitleView = (TextView) listItemView.findViewById(R.id.article_title_text_view);
        articleTitleView.setText(currentArticle.getArticleTitle());

        TextView articleDescriptionView = (TextView) listItemView.findViewById(R.id.article_desc_text_view);
        articleDescriptionView.setText(currentArticle.getArticleDescription());

        TextView articleDateView = (TextView) listItemView.findViewById(R.id.article_date_text_view);
        articleDateView.setText(currentArticle.getArticleDate());

        TextView articleTimeView = (TextView) listItemView.findViewById(R.id.article_time_text_view);
        articleTimeView.setText(currentArticle.getArticleTime());

        TextView articleAuthorView = (TextView) listItemView.findViewById(R.id.article_author_text_view);
        articleAuthorView.setText(currentArticle.getArticleAuthor());

        return listItemView;
    }
}
