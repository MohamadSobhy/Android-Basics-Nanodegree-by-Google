package com.example.mohamedsobhy.musicalstructureapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private int tabIcons[] = {R.drawable.playlists_icon
                             ,R.drawable.songs_icon
                             ,R.drawable.albums_icon
                             ,R.drawable.artists_icon };

    public static ArrayList<Song> songs;
    public static long[] songsIDs;
    public static HashMap<String , String> songsArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager() , this);

        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();


        songs = new ArrayList<Song>();
        songsArtists = new HashMap<String , String>();

        //check if the permission of reading data from external storage accepted or not
        //use if because of the API is less than 23 then it will return true so i need to get the data from the external storage
        //it wil return false if the permission denied and the API is greater then or equal 23
        if(isPermissionGrantedForStorage()){
            getSongs();
        }

    }

    public void getSongs(){

        ContentResolver contentResolver = this.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri , null , null , null ,null);

        if(songCursor != null && songCursor.moveToFirst()){
            int songNameIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtistNameIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songIdIndex = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);

            do{
                String songName = songCursor.getString(songNameIndex);
                String artistName = songCursor.getString(songArtistNameIndex);
                long songId = songCursor.getLong(songIdIndex);

                songsArtists.put(artistName , null);
                songs.add(new Song(songName , artistName , songId));

            }while (songCursor.moveToNext());

            songsIDs = new long[songs.size()];

        }

    }

    /**
     * check if the permission of reading data from external storage accepted or not
     * use if because of the API is less than 23 then it will return true so i need to get the data from the external storage
     * it wil return false if the permission denied and the API is greater then or equal 23
     * @return true if the API is less than 23
     * @return false if the permission denied and the API is greater then or equal 23
     */
    public  boolean isPermissionGrantedForStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Song","Permission is granted");
                return true;
            } else {

                Log.v("Song","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("Song","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            getSongs();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SongPlayer.player.stop();
        SongPlayer.player.release();
    }

    private void setupTabIcons() {

        for (int i = 0 ; i < tabLayout.getTabCount(); i++)
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);

    }


}
