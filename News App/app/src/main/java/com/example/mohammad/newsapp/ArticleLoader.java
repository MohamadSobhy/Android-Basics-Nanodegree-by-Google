package com.example.mohammad.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by mohammad on 06/02/18.
 */

public class ArticleLoader extends AsyncTaskLoader< ArrayList<Article> > {

    private static String LOG_TAG = ArticleLoader.class.getName();
    private String mUrl;
    private Context mContext;

    public ArticleLoader(Context context , String url) {
        super(context);
        mUrl = url;
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        ArrayList<Article> articles = ConnectionHandler.extractArticlesFromURL(mContext , mUrl);

        return articles;
    }
}
