package com.example.mohamedsobhy.musicalstructureapp;

/**
 * Created by Mohamed Sobhy on 23/11/2017.
 */

public class Song {


    private String songName;
    private String artistName;
    private long songId;

    public Song( String songName , String artistName , long songId) {
        this.songName = songName;
        this.artistName = artistName;
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public long getSongId() {
        return songId;
    }
}
