package com.example.mohamedsobhy.musicalstructureapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Mohamed Sobhy on 23/11/2017.
 */

public class ArtistsFragment extends Fragment {

    public static ArrayList<Song> artistSongs;

    public ArtistsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_artists, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.artists_list_view);

        ArrayAdapter adapter = new ArrayAdapter(getContext() , android.R.layout.simple_list_item_1 , MainActivity.songsArtists.keySet().toArray());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnArtistClicked());

        artistSongs = new ArrayList<>();

        return rootView;
    }

    /**
     * puts all songs of the artist in artistSong List
     * @param artistName name of the artist
     */
    private void getSongsOfArtist(String artistName){
        ArrayList<Song> songs = MainActivity.songs;

        for(Song song : songs){
            if(song.getArtistName().equals(artistName)){
                artistSongs.add(song);
            }
        }
    }


    private class OnArtistClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            String artistName =  MainActivity.songsArtists.keySet().toArray()[position].toString();

            getSongsOfArtist(artistName);

            Intent showArtistSongs = new Intent(getActivity() , ArtistOrAlbumSongs.class);

            showArtistSongs.putExtra("caller" , "ArtistsFragment");

            startActivity(showArtistSongs);

        }
    }




}
