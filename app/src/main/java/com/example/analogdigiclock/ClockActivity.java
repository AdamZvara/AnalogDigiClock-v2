package com.example.analogdigiclock;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DigitalClock;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.io.IOException;


import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {

    public ToggleButton tbtn_slovensko;
    public ToggleButton tbtn_jemne;
    public ToggleButton tbtn_vlna;
    public ToggleButton tbtn_regina;
    public ToggleButton tbtn_mod;
    public SeekBar seekBar;
    private MediaPlayer player;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    private TextView batteryLevel;
    public LinearLayout LL_radia_seekbar;
    TextClock t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_clock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        volumeSeekbar = (SeekBar)findViewById(R.id.seekBar1);
        final DigitalClock digitalClock = findViewById(R.id.digitalClock);
        final TextClock textClock = findViewById(R.id.textClock);
        LL_radia_seekbar = (LinearLayout)findViewById(R.id.LL_radia_seekbar);
        batteryLevel = (TextView)findViewById(R.id.battery_level);
        tbtn_slovensko = (ToggleButton) findViewById(R.id.tbtn_slovensko);
        tbtn_jemne = (ToggleButton) findViewById(R.id.tbtn_jemne);
        tbtn_vlna = (ToggleButton) findViewById(R.id.tbtn_vlna);
        tbtn_regina = (ToggleButton) findViewById(R.id.tbtn_regina);
        tbtn_mod = (ToggleButton) findViewById(R.id.mod) ;
        player = new MediaPlayer();
        t1 = (TextClock) findViewById(R.id.textClock);

        initControls();
        initializeUIElements();
        change_mod();
        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        //mediaPlayer.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryLevel.setText(String.valueOf(level) + "%");
        }
    };

    private void change_mod(){
        tbtn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_mod.isChecked()) {
                    LL_radia_seekbar.setVisibility(View.INVISIBLE);
                    t1.setTextSize(45f);
                } else {
                    LL_radia_seekbar.setVisibility(View.VISIBLE);
                    t1.setTextSize(65f);
                }
            }
        });
    }


    private void initControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar1);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));

            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeUIElements() {
        tbtn_slovensko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_slovensko.isChecked()) {
                    tbtn_jemne.setChecked(false);
                    tbtn_vlna.setChecked(false);
                    tbtn_regina.setChecked(false);
                    startPlayer(Radios.RADIO_SLOVENSKO_URL);
                } else {
                    stopPlaying();
                }
            }
        });
        tbtn_jemne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_jemne.isChecked()) {
                    tbtn_vlna.setChecked(false);
                    tbtn_regina.setChecked(false);
                    tbtn_slovensko.setChecked(false);
                    startPlayer(Radios.RADIO_JEMNE_URL);
                } else {
                    stopPlaying();
                }
            }
        });
        tbtn_vlna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_vlna.isChecked()) {
                    tbtn_regina.setChecked(false);
                    tbtn_slovensko.setChecked(false);
                    tbtn_jemne.setChecked(false);
                    startPlayer(Radios.RADIO_VLNA_URL);
                } else {
                    stopPlaying();
                }
            }
        });
        tbtn_regina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_regina.isChecked()) {
                    tbtn_jemne.setChecked(false);
                    tbtn_slovensko.setChecked(false);
                    tbtn_vlna.setChecked(false);
                    startPlayer(Radios.RADIO_REGINA_URL);
                } else {
                    stopPlaying();
                }
            }
        });
    }

    private void startPlayer(String source){
        try{
            stopPlaying();
        }catch(IllegalStateException e){
            Log.v("Warning", "startPlayer: Player was stopped - in idle state");
        }
        startPlaying(source);
    }

    private void startPlaying(String source) {
        initializeMediaPlayer(source);
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
    }

    private void stopPlaying() {
            player.stop();
            player.reset();
    }

    private void initializeMediaPlayer(String source) {
        player = new MediaPlayer();
        try {
            player.setDataSource(source);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    Log.i("Buffering", "" + percent);
                }
            });
        }
    }
