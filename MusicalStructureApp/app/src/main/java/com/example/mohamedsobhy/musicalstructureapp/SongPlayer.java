package com.example.mohamedsobhy.musicalstructureapp;

import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class SongPlayer extends AppCompatActivity {

    static MediaPlayer player;
    ArrayList<String> songsList;
    int position;
    long [] songsIDs;
    Uri contentUri;

    SeekBar progressBar;
    ImageButton playButton, skipNext, skipPrevious, seekForward, seekBack;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        getSupportActionBar().hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarId);
        TextView titleName = (TextView)findViewById(R.id.song_name_activity_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SongPlayer.this , MainActivity.class);

                startActivity(i);
            }
        });

        //assign references to seek bar and all button  ..
        assignReferences();


        Intent i = getIntent();
        songsList = i.getStringArrayListExtra("songsList");
        position = i.getExtras().getInt("pos");
        songsIDs = i.getLongArrayExtra("IDs");

        if(player != null){
            releaseResources();
            try {
                if(updateSeekBar != null)
                    updateSeekBar.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        player = new MediaPlayer();

        long songId = songsIDs[position];

        titleName.setText(songsList.get(position));

        contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);

        try {
            player.setDataSource(getApplicationContext(), contentUri);
            player.prepare();
        } catch (IOException e) {
            Log.v("Player" , "Error Creating player");
        }

        player.start();
        progressBar.setMax(player.getDuration());

        updateSeekBar.start();

        player.setOnCompletionListener(new OnPlayerCompletion());


    }

    private void assignReferences(){

        progressBar = (SeekBar) findViewById(R.id.progress_bar);
        skipPrevious = (ImageButton) findViewById(R.id.skip_previous);
        skipNext = (ImageButton) findViewById(R.id.skip_next);
        playButton = (ImageButton) findViewById(R.id.play_button);
        seekBack = (ImageButton) findViewById(R.id.seek_back);
        seekForward = (ImageButton) findViewById(R.id.seek_forward);

        playButton.setOnClickListener(new OnButtonClicked());
        skipNext.setOnClickListener(new OnButtonClicked());
        skipPrevious.setOnClickListener(new OnButtonClicked());
        seekForward.setOnClickListener(new OnButtonClicked());
        seekBack.setOnClickListener(new OnButtonClicked());

        progressBar.setOnSeekBarChangeListener(new OnSeekBarChange());

        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuartion = player.getDuration();
                int currentPosition = 0;

                while (currentPosition < totalDuartion){
                    try {
                        sleep(500);
                        currentPosition = player.getCurrentPosition();
                        progressBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

    }

    private void releaseResources(){

        player.stop();
        //player.release();
    }

    private class OnButtonClicked implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            int id = view.getId();

            switch (id){

                case R.id.play_button:{
                    if(player.isPlaying()){
                        player.pause();
                        playButton.setImageResource(R.drawable.play_icon);
                    }
                    else {
                        playButton.setImageResource(R.drawable.pause_icon);
                        player.start();
                    }

                    break;
                }
                case R.id.skip_previous:{

                    releaseResources();
                    player = new MediaPlayer();
                    position = (position - 1) < 0 ? songsList.size() - 1 : position - 1 ;
                    long songId = songsIDs[position];
                    getSupportActionBar().setTitle(songsList.get(position));
                    contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
                    try {
                        player.setDataSource(getApplicationContext(), contentUri);
                        player.prepare();
                    } catch (IOException e) {
                        Log.v("Player" , "Error Creating player");
                    }
                    player.start();
                    progressBar.setMax(player.getDuration());

                    break;
                }
                case R.id.seek_back:{
                    player.seekTo(player.getCurrentPosition() - 5000);
                    progressBar.setProgress(player.getCurrentPosition());
                    break;
                }
                case R.id.seek_forward:{
                    player.seekTo(player.getCurrentPosition() + 5000);
                    progressBar.setProgress(player.getCurrentPosition());
                    break;
                }
                case R.id.skip_next:{

                    releaseResources();
                    player = new MediaPlayer();
                    position = (position + 1) % songsList.size();
                    long songId = songsIDs[position];
                    getSupportActionBar().setTitle(songsList.get(position));
                    contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
                    try {
                        //shof 7l fe om el player ..
                        player.setDataSource(getApplicationContext(), contentUri);
                        player.prepare();
                    } catch (IOException e) {
                        Log.v("Player" , "Error Creating player");
                    }
                    player.start();
                    progressBar.setMax(player.getDuration());

                    break;
                }

            }

        }
    }

    private class OnSeekBarChange implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.seekTo(progressBar.getProgress());
        }
    }

    private class OnPlayerCompletion implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer player) {

            skipNext.callOnClick();

        }
    }
}
