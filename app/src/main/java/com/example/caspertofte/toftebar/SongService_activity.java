package com.example.caspertofte.toftebar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SongService_activity extends AppCompatActivity implements SensorEventListener {

// TODO Play music from a service. Bind the service to the activity to react to gestures.

    // mediaplayer
    MediaPlayer mp;
    int songList[];
    int numberOfSongs;
    int songNumber = 0;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_service_activity);
        songList = new int[] {R.raw.arcofthesun1, R.raw.coalminingblues2, R.raw.yellowmoon3};
        numberOfSongs = songList.length;
        Log.d("Number of songs",String.valueOf(numberOfSongs));
        tv_songTitle = (TextView) findViewById(R.id.songTitle);
        tv_playPause = (TextView) findViewById(R.id.btn_playPause);
        mp = MediaPlayer.create(this, songList[songNumber]);


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
            public void onOrientationChanged(int arg0) {
                // get the orientation value between 0 and 359
                orientationValue = arg0;
                orientationText = "Orientation: " + String.valueOf(arg0);
                //Log.d("Orientation", orientationText);

                if ((orientationValue> 80 && orientationValue<100) || (orientationValue>260 && orientationValue<280)){
                    if(mp!=null && !mp.isPlaying()){playSong(songNumber);}
                }
                /*if((orientationValue < 0) || (orientationValue>0 && orientationValue<79)){
                    if(mp!=null && mp.isPlaying()){ playPause(); }
                }*/
                if(orientationValue < 0){
                    if(mp!=null && mp.isPlaying()){ playPause(); }
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

        stopMusic();
        smanager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        smanager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event) {
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
                nextSong();
            }

            last_x = x;
            last_y = y;
            last_z = z;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        myOrientationEventListener.disable();

        stopMusic();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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

    public void playSong(int index){
        stopMusic();

        switch (index){
            case 0:
                tv_songTitle.setText("Arc of the sun");
                Toast.makeText(this, "Song 0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                tv_songTitle.setText("Coalmining Blues");
                Toast.makeText(this, "Song 1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                tv_songTitle.setText("Yellow moon");
                Toast.makeText(this, "Song 2", Toast.LENGTH_SHORT).show();
        }

        tv_playPause.setText("Pause");
        mp = MediaPlayer.create(this,songList[index]);
        mp.start();
        //playPause();
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
                tv_playPause.setText("Pause");
            } else{
                mp.pause();
                tv_playPause.setText("Play");
            }
        }
    }

    public void nextSong_btn(View view){
        nextSong();
    }
    public void previousSong_btn(View view){
        previousSong();
    }
    public void playPause_btn(View view){
        playPause();
    }

}