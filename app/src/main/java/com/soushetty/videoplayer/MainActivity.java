package com.soushetty.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    //MediaPlayer class is the primary API for playing sound and video.
    public MediaPlayer mediaPlayer;
    //Provides a dedicated drawing surface embedded inside of a view hierarchy
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing media player and passing the path where video is present
        mediaPlayer=MediaPlayer.create(this,R.raw.marriage_song);

        surfaceView=findViewById(R.id.surface_view);
        surfaceView.setKeepScreenOn(true);


        /*Access to the underlying surface is provided via SurfaceHolder interface, which can be retrieved by calling getHolder() method*/
        //holder is to play the video on the top of the surface
        SurfaceHolder holder=surfaceView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(400,300);

        //when user clicks PLAY button
        Button play=findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        //when user clicks PAUSE button
        Button pause=findViewById(R.id.pause_button);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        //when user clicks SKIP button
        //here randomly moving it to a value when the time would be duration/2
        Button skip=findViewById(R.id.skip_button);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getDuration() / 2);

            }
        });

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //once the holder is created, we need to attach it to the media player
        mediaPlayer.setDisplay(holder);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    //Surface view will be running in the main thread of the application.hence should be destroyed  as that last activity state is reached
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer=null;
        }

    }


    //when the application/activity reaches the Pause state of activity life cycle, releasing it from memory to avoid memory leaking or any other issues
    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
