package com.example.mohamedsobhy.musicalstructureapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistsFragment extends Fragment {


    public static ArrayList<Song> playlistSongs;

    public PlaylistsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_artists, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.artists_list_view);

        ArrayAdapter adapter = new ArrayAdapter(getContext() , android.R.layout.simple_list_item_1 , MainActivity.songsArtists.keySet().toArray());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnPlaylistClicked());

        playlistSongs = new ArrayList<>();

        return rootView;
    }

    /**
     * puts all songs of the artist in artistSong List
     * @param playlistName name of the playlist
     */
    private void getSongsOfArtist(String playlistName){
        ArrayList<Song> songs = MainActivity.songs;

        for(Song song : songs){
            if(song.getArtistName().equals(playlistName)){
                playlistSongs.add(song);
            }
        }
    }


    private class OnPlaylistClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            String playlistName = MainActivity.songsArtists.keySet().toArray()[position].toString();

            getSongsOfArtist(playlistName);

            Intent showPlaylistSongs = new Intent(getActivity() , ArtistOrAlbumSongs.class);

            showPlaylistSongs.putExtra("caller" , "PlaylistsFragment");

            startActivity(showPlaylistSongs);

        }
    }

}
