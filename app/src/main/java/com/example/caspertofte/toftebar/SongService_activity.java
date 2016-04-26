package com.example.caspertofte.toftebar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SongService_activity extends AppCompatActivity implements SensorEventListener {

// TODO Play music from a service. Bind the service to the activity to react to gestures.

    TextView tv_songTitle;
    TextView tv_playPause;

    // Orientation
    OrientationEventListener myOrientationEventListener;
    String orientationText;
    int orientationValue;

    // accelerometer
    SensorManager smanager;
    Sensor accelerometer;
    TextView xAxis;
    TextView yAxis;
    TextView zAxis;
    long lastUpdate;
    int SHAKE_THRESHOLD = 1000;
    float last_x;
    float last_y;
    float last_z;

    // Service
    songService s;
    Boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_service_activity);


        tv_songTitle = (TextView) findViewById(R.id.songTitle);
        tv_playPause = (TextView) findViewById(R.id.btn_playPause);

        // reference to the axes views
        /*xAxis = (TextView) findViewById(R.id.xaxis);
        yAxis = (TextView) findViewById(R.id.yaxis);
        zAxis = (TextView) findViewById(R.id.zaxis);*/

        // Retrieve the default accelerometer
        smanager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Listen for changes in the orientation
        // If rotated to landscape, play song. If put on table, pause song.
        myOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL){
            @Override
            public void onOrientationChanged(int arg0) {        // TODO: The service should react to gestures / changes in orientation
                // get the orientation value between 0 and 359
                orientationValue = arg0;
                orientationText = "Orientation: " + String.valueOf(arg0);
                //Log.d("Orientation", orientationText);

                if ((orientationValue> 80 && orientationValue<100) || (orientationValue>260 && orientationValue<280)){
                    if(s.getMPstate() && !s.getMPplay() ){s.playSong(s.getSongNumber());}        // TODO: Retrieve mp status and mp.isPlaying + change play/pause if playing
                }
                if(orientationValue < 0){   // The phone lies on the table
                    if(s.getMPstate() && !s.getMPplay()){s.playPause(); displaySongName(); }
                }
            }
        };

        if (myOrientationEventListener.canDetectOrientation()){
            Log.d("Orientation", "Can detect orientation");
            myOrientationEventListener.enable();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        //s.stopMusic();
        smanager.unregisterListener(this);

        // service unbind
        unbindService(songConnection);
    }

    @Override
    protected void onResume(){
        super.onResume();
        smanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        // service binding
        Intent intent = new Intent(this,songService.class);
        bindService(intent, songConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myOrientationEventListener.disable();

        //s.stopMusic();
    }

    public void onSensorChanged(SensorEvent event) {            //TODO: Move shake to service
        /*xAxis.setText(String.format("%f", event.values[0]));
        yAxis.setText(String.format("%f", event.values[1]));
        zAxis.setText(String.format("%f", event.values[2]));*/

        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
            if (speed > SHAKE_THRESHOLD) {
                Log.d("sensor", "shake detected w/ speed: " + speed);
                Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                s.nextSong();
                displaySongName();
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void nextSong_btn(View view){
        s.nextSong();
        displaySongName();
        displayPlayPause();
    }
    public void previousSong_btn(View view){
        s.previousSong();
        displaySongName();
        displayPlayPause();
    }
    public void playPause_btn(View view){
        s.playPause();
        displaySongName();
        displayPlayPause();
    }

    public void displaySongName(){
        String songName = s.getSongName();
        tv_songTitle.setText(songName);
    }

    public void displayPlayPause() {
        if (s.getMPplay()) {
            tv_playPause.setText("Pause");
        }
        else{
            tv_playPause.setText("Play");
        }
    }



    // Service

    @Override
    public void onStart(){
        // service
        super.onStart();
        songServiceExplicitStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        songServiceExplicitStop();
    }

    private void songServiceExplicitStart(){
        Intent intent = new Intent(SongService_activity.this, songService.class);
        startService(intent);
    }
    private void songServiceExplicitStop(){
        stopService(new Intent(SongService_activity.this, songService.class));
    }

    private ServiceConnection songConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            songService.LocalBinder b = (songService.LocalBinder) service;
            s = b.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            s = null;
            isBound = false;
        }
    };

}