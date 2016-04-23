package com.example.caspertofte.toftebar;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Casper on 23-04-2016.
 */
public class songService extends Service {

    // Mediaplayer
    MediaPlayer mp = null;
    int songList[];
    int numberOfSongs;
    int songNumber = 0;
    String songNames[];


    // Creating service and bindings
    private final IBinder songBinder = new LocalBinder();

    @Override
    public void onCreate(){
        super.onCreate();

        songList = new int[] {R.raw.arcofthesun1, R.raw.coalminingblues2, R.raw.yellowmoon3};
        songNames = new String[] {"Song 0", "Song 1", "Song 2"};
        numberOfSongs = songList.length;
        Log.d("Number of songs", String.valueOf(numberOfSongs));

        mp = MediaPlayer.create(this, songList[songNumber]);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "songService is destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        Toast.makeText(this,"songService is started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return songBinder;
    }

    public class LocalBinder extends Binder {
        songService getService(){
            return songService.this;
        }
    }



    public void playSong(int index){
        stopMusic();

        //tv_playPause.setText("Pause");
        mp = MediaPlayer.create(this, songList[index]);
        mp.start();
    }

    // Clear the mediaplayer
    public void stopMusic(){
        if (mp!=null)
        {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    public void nextSong() {
        songNumber++;
        if (songNumber<3){
            playSong(songNumber);
        } else{
            songNumber = 0;
            playSong(songNumber);
        }
    }

    public void previousSong() {
        songNumber--;
        if(songNumber>-1){
            playSong(songNumber);
        } else{
            songNumber=numberOfSongs-1;
            playSong(songNumber);
        }
    }

    public void playPause(){
        if(mp!=null){
            if (!mp.isPlaying()){
                playSong(songNumber);
                //tv_playPause.setText("Pause");
            } else{
                mp.pause();
                //tv_playPause.setText("Play");
            }
        }
    }

    public int getSongNumber(){
        return songNumber;
    }

    public String getSongName(){
        String songName = songNames[songNumber];
        return songName;
    }

    public boolean getMPstate(){
        if(mp !=null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean getMPplay() {
        if (mp.isPlaying()) {
            return true;
        }
        else{
            return false;
        }
    }




}
