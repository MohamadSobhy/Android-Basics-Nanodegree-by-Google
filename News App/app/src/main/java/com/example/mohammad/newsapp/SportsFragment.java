package com.example.mohammad.newsapp;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SportsFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private final String GUARDIAN_API = "http://content.guardianapis.com/search";
    private final String SECTION_NAME = "sport";
    private final String SHOW_TAGS_VALUE = "contributor";
    private static final int ARTICLE_LOADER_ID = 1;

    private ArticleAdapter adapter;
    private ArrayList<Article> mArticles;
    private ListView articlesListView ;
    private View rootView;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;
    private TextView noConnectionView;

    public SportsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sports, container, false);

        progressBar = rootView.findViewById(R.id.loading_bar);

        articlesListView = (ListView) rootView.findViewById(R.id.sports_fragment_list_view);

        noConnectionView = (TextView) rootView.findViewById(R.id.no_connection_text_view);

        emptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        articlesListView.setEmptyView(emptyStateTextView);

        if(checkConnectivity()){

            LoaderManager loaderManager = getActivity().getLoaderManager();
            loaderManager.initLoader(ARTICLE_LOADER_ID , null ,this);

            noConnectionView.setVisibility(View.INVISIBLE);
        }
        else {
            noConnectionView.setText(getContext().getString(R.string.no_connection));
            noConnectionView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            emptyStateTextView.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    /**
     * this method updates the UI after getting data from API
     */
    private void updateUI(){

        adapter = new ArticleAdapter(getContext() , mArticles);

        articlesListView.setAdapter(adapter);

        articlesListView.setOnItemClickListener(new OnListItemClicked());
    }

    /**
     * checks the Internet Connection of the device.
     * @return true if there is an Internet Connection.
     */
    public boolean checkConnectivity(){

        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String numberOfArticles = sharedPrefs.getString(
                getString(R.string.settings_article_num_key),
                getString(R.string.settings_article_num_default));

        String orderBy  = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_API);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("section" , SECTION_NAME);
        uriBuilder.appendQueryParameter("show-tags" , SHOW_TAGS_VALUE);
        uriBuilder.appendQueryParameter("page-size", numberOfArticles);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key"  , "test");

        progressBar.setVisibility(View.VISIBLE);
        return new ArticleLoader(getContext() , uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader loader, Object articles) {

        emptyStateTextView.setText(getContext().getString(R.string.no_articles));
        if ( articles != null && articles instanceof ArrayList){
            mArticles = (ArrayList) articles;
        }
        progressBar.setVisibility(View.INVISIBLE);
        updateUI();
        articlesListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }

    private class OnListItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Article currentArticle = mArticles.get(position);
            String urlString = currentArticle.getArticleDescription().substring(31).trim();

            Uri url = Uri.parse(urlString);

            Intent i = new Intent(Intent.ACTION_VIEW , url);
            if (i.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(i);
            }

        }
    }
}
