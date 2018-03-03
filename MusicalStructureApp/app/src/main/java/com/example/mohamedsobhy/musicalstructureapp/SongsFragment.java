package com.example.mohamedsobhy.musicalstructureapp;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class SongsFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    public SongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_songs, container, false);

        ListView listView =(ListView) rootView.findViewById(R.id.songs_list_view);

        SongsAdapter adapter = new SongsAdapter(getActivity() , MainActivity.songs);

        listView.setAdapter(adapter);

        //set OcItemClickListener ..
        listView.setOnItemClickListener(new OnItemClicked());

        return rootView;
    }


    private class OnItemClicked implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            //Song song = songs.get(position);
            ArrayList<String> songsList = new ArrayList<>();

            for(int i = 0 ; i < MainActivity.songs.size() ; i++){
                songsList.add(MainActivity.songs.get(i).getSongName());
                MainActivity.songsIDs[i] = MainActivity.songs.get(i).getSongId();
            }

            Intent sendToPlayer = new Intent(getActivity() , SongPlayer.class);

            sendToPlayer.putExtra("pos" , position);
            sendToPlayer.putExtra("songsList", songsList);
            sendToPlayer.putExtra("IDs" , MainActivity.songsIDs);


            startActivity(sendToPlayer);

        }
    }

}
