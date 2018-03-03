package com.example.mohamedsobhy.musicalstructureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ArtistOrAlbumSongs extends AppCompatActivity {

    private ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_or_album_songs);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        String activityCaller = intent.getStringExtra("caller");
        songs = new ArrayList<>();

        ListView listView = (ListView) findViewById(R.id.artists_album_songs_list_view);

        SongsAdapter adapter = validateCallerFragment(activityCaller);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnSongClicked());


    }

    /**
     * validate which fragment call the activity
     * @param activityCaller  the name of the fragment which called the activity
     * @return song adapter for the songs of this artist or album or playlist
     */
    private SongsAdapter validateCallerFragment(String activityCaller){
        SongsAdapter adapter = null;

        switch(activityCaller){

            case "ArtistsFragment":{
                adapter = new SongsAdapter(this , ArtistsFragment.artistSongs );
                this.songs = ArtistsFragment.artistSongs;
                break;
            }
            case "PlaylistsFragment":{
                adapter = new SongsAdapter(this , PlaylistsFragment.playlistSongs);
                this.songs = PlaylistsFragment.playlistSongs;
                break;
            }
            case "AlbumsFragment":{
                adapter = new SongsAdapter(this , AlbumsFragment.albumSongs );
                this.songs = AlbumsFragment.albumSongs;
                break;
            }

        }
        return adapter;
    }

    private class OnSongClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            ArrayList<String> songsList = new ArrayList<>();
            Song artistSong = songs.get(position);

            int i = 0;
            for(Song song : MainActivity.songs){
                songsList.add(song.getSongName());
                MainActivity.songsIDs[i] = song.getSongId();

                if(song == artistSong){
                    position = i;
                }
                i++;
            }

            Intent sendToPlayer = new Intent(ArtistOrAlbumSongs.this , SongPlayer.class);

            sendToPlayer.putExtra("pos" , position);
            sendToPlayer.putExtra("songsList", songsList);
            sendToPlayer.putExtra("IDs" , MainActivity.songsIDs);


            startActivity(sendToPlayer);

        }
    }
}
