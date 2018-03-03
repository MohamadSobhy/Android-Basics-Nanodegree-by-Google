package com.example.mohamedsobhy.musicalstructureapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by Mohamed Sobhy on 24/11/2017.
 */

public class SongsAdapter extends ArrayAdapter<Song> {

    public SongsAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0 , songs);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_view , parent , false);
        }

        Song song = getItem(position);

        TextView songNameTextView = (TextView) listItemView.findViewById(R.id.song_name_view);
        songNameTextView.setText(song.getSongName());

        TextView artistTextView = (TextView) listItemView.findViewById(R.id.artist_name_view);
        artistTextView.setText(song.getArtistName());


        return listItemView;
    }
}
