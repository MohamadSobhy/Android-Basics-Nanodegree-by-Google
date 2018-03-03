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
public class AlbumsFragment extends Fragment {
    public static ArrayList<Song> albumSongs;

    public AlbumsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_artists, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.artists_list_view);

        ArrayAdapter adapter = new ArrayAdapter(getContext() , android.R.layout.simple_list_item_1 , MainActivity.songsArtists.keySet().toArray());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnAlbumClicked());

        albumSongs = new ArrayList<>();

        return rootView;
    }

    /**
     * puts all songs of the artist in artistSong List
     * @param albumName name of the album
     */
    private void getSongsOfAlbum(String albumName){
        ArrayList<Song> songs = MainActivity.songs;

        for(Song song : songs){
            if(song.getArtistName().equals(albumName)){
                albumSongs.add(song);
            }
        }
    }


    private class OnAlbumClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            String albumName = MainActivity.songsArtists.keySet().toArray()[position].toString();

            getSongsOfAlbum(albumName);

            Intent showAlbumSongs = new Intent(getActivity() , ArtistOrAlbumSongs.class);

            showAlbumSongs.putExtra("caller" , "AlbumsFragment");

            startActivity(showAlbumSongs);

        }
    }

}
