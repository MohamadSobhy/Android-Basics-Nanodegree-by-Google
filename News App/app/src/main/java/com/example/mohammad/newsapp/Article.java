package com.example.mohammad.newsapp;

/**
 * Created by mohammad on 06/02/18.
 */

public class Article {

    private String mArticleTitle;
    private String mArticleDescription;
    private String mArticleDate;
    private String mArticleTime;
    private String mArticleAuthor;

    public Article(String articleTitle , String articleDescription , String articleDate , String articleTime , String articleAuthor){
        mArticleTitle = articleTitle;
        mArticleDescription = articleDescription;
        mArticleDate = articleDate;
        mArticleTime = articleTime;
        mArticleAuthor = articleAuthor;
    }

    public String getArticleTitle(){
        return mArticleTitle;
    }

    public String getArticleDescription(){
        return mArticleDescription;
    }

    public String getArticleDate(){
        return mArticleDate;
    }

    public String getArticleTime(){
        return mArticleTime;
    }

    public String getArticleAuthor(){
        return mArticleAuthor;
    }

}
