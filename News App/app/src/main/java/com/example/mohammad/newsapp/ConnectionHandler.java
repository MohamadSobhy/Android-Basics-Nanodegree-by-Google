package com.example.mohammad.newsapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by mohammad on 06/02/18.
 */

public final class ConnectionHandler {

    private static String LOG_TAG = ConnectionHandler.class.getName();
    private static final String AUTHOR_NOT_FOUND = "Author Not Available.";


    public static ArrayList<Article> extractArticlesFromURL(Context context , String API_URL){

        if(API_URL.isEmpty()){
            return null;
        }

        ArrayList<Article> articles = new ArrayList<>();
        String jsonResponse = "";

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            URL url = createURL(API_URL);
            jsonResponse = makeHttpConnection(url);

            JSONObject rootJSONResponse = new JSONObject(jsonResponse);
            JSONObject baseJSONResponse = rootJSONResponse.getJSONObject("response");
            JSONArray results = baseJSONResponse.getJSONArray("results");

            for (int i = 0 ; i < results.length() ; i++){

                JSONObject currentArticle = results.getJSONObject(i);

                String articleTitle = currentArticle.getString("webTitle");
                String articleDescription = context.getResources().getString(R.string.article_desc , currentArticle.getString("webUrl"));
                String articlePublicationDate[] = currentArticle.getString("webPublicationDate").split("T");

                String articleDate = context.getResources().getString(R.string.article_date , articlePublicationDate[0]);
                String articleTime = context.getResources().getString(R.string.article_time , articlePublicationDate[1].replace("Z" , ""));

                JSONArray tagsArray = currentArticle.getJSONArray("tags");
                JSONObject firstObject = tagsArray.optJSONObject(0);
                String articleAuthor = "";
                if(firstObject == null){
                    articleAuthor = AUTHOR_NOT_FOUND;
                }else{
                    articleAuthor = context.getResources().getString(R.string.article_author_name , firstObject.getString("webTitle"));
                }

                articles.add(new Article(articleTitle , articleDescription , articleDate , articleTime , articleAuthor));
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error Closing InputStream");
        }catch (JSONException jsonException){
            Log.e(LOG_TAG, "Error Extracting JSON Response.");
        }

        return articles;
    }

    private static URL createURL(String urlString){
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private static String makeHttpConnection(URL url) throws IOException {
        if (url == null){
            return "";
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = "";

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = getDataFromInputStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG , "Error: Can't Connect to URL");
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String getDataFromInputStream(InputStream inputStream) throws IOException {

        StringBuilder jsonResponse = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                jsonResponse.append(line);
                line = reader.readLine();

            }
        }

        return jsonResponse.toString();
    }

}
