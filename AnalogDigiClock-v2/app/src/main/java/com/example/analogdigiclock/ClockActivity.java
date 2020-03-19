package com.example.analogdigiclock;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.IOException;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {

    ToggleButton tbtn_slovensko;
    ToggleButton tbtn_jemne;
    ToggleButton tbtn_vlna;
    ToggleButton tbtn_regina;
    SeekBar mediaPlayer;
    private MediaPlayer player;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_clock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initControls();
        mediaPlayer = (SeekBar)findViewById(R.id.seekBar1);
        final DigitalClock digitalClock = findViewById(R.id.digitalClock);
        final TextClock textClock = findViewById(R.id.textClock);

        tbtn_slovensko = (ToggleButton) findViewById(R.id.tbtn_slovensko);
        tbtn_slovensko.setBackgroundDrawable(getResources().getDrawable(R.drawable.slovensko3));
        tbtn_slovensko.setOnCheckedChangeListener(changeChecker);

        tbtn_jemne = (ToggleButton) findViewById(R.id.tbtn_jemne);
        tbtn_jemne.setBackgroundDrawable(getResources().getDrawable(R.drawable.jemne1));
        tbtn_jemne.setOnCheckedChangeListener(changeChecker);

        tbtn_vlna = (ToggleButton) findViewById(R.id.tbtn_vlna);
        tbtn_vlna.setBackgroundDrawable(getResources().getDrawable(R.drawable.vlna2));
        tbtn_vlna.setOnCheckedChangeListener(changeChecker);

        tbtn_regina = (ToggleButton) findViewById(R.id.tbtn_regina);
        tbtn_regina.setBackgroundDrawable(getResources().getDrawable(R.drawable.regina));
        tbtn_regina.setOnCheckedChangeListener(changeChecker);
        //mediaPlayer.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        initializeUIElements();

    }
    OnCheckedChangeListener changeChecker = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                if (buttonView != tbtn_slovensko) {
                    tbtn_slovensko.setChecked(false);
                    tbtn_slovensko.setBackgroundDrawable(getResources().getDrawable(R.drawable.slovensko3));
                }
                if (buttonView != tbtn_jemne) {
                    tbtn_jemne.setChecked(false);
                    tbtn_jemne.setBackgroundDrawable(getResources().getDrawable(R.drawable.jemne1));
                }
                if (buttonView != tbtn_vlna) {
                    tbtn_vlna.setChecked(false);
                    tbtn_vlna.setBackgroundDrawable(getResources().getDrawable(R.drawable.vlna2));
                }
                if (buttonView != tbtn_regina) {
                    tbtn_regina.setChecked(false);
                    tbtn_regina.setBackgroundDrawable(getResources().getDrawable(R.drawable.regina));
                }
            }
        }
    };

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
                    startPlaying("http://icecast.stv.livebox.sk/slovensko_128.mp3");
                    tbtn_slovensko.setBackgroundDrawable(getResources().getDrawable(R.drawable.slovensko4));
                    //calculate the result
                } else {
                    stopPlaying();
                    tbtn_slovensko.setBackgroundDrawable(getResources().getDrawable(R.drawable.slovensko3));
                    //Reset your global calculation variable;
                }
            }
        });
        tbtn_jemne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_jemne.isChecked()) {
                    startPlaying("https://stream.radioservices.sk/jemne-hi.mp3");
                    tbtn_jemne.setBackgroundDrawable(getResources().getDrawable(R.drawable.jemne2));
                    //calculate the result
                } else {
                    stopPlaying();
                    tbtn_jemne.setBackgroundDrawable(getResources().getDrawable(R.drawable.jemne1));
                    //Reset your global calculation variable;
                }
            }
        });
        tbtn_vlna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_vlna.isChecked()) {
                    tbtn_vlna.setBackgroundDrawable(getResources().getDrawable(R.drawable.vlna3));
                    startPlaying("https://stream.radioservices.sk/vlna-hi.mp3");
                    //calculate the result
                } else {
                    stopPlaying();
                    tbtn_vlna.setBackgroundDrawable(getResources().getDrawable(R.drawable.vlna2));
                    //Reset your global calculation variable;
                }
            }
        });
        tbtn_regina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tbtn_regina.isChecked()) {
                    startPlaying("https://stream.radioservices.sk/vlna-hi.mp3");
                    tbtn_regina.setBackgroundDrawable(getResources().getDrawable(R.drawable.regina2));
                    //calculate the result
                } else {
                    stopPlaying();
                    tbtn_regina.setBackgroundDrawable(getResources().getDrawable(R.drawable.regina));
                    //Reset your global calculation variable;
                }
            }
        });
    }

        private void startPlaying(String source) {
            initializeMediaPlayer(source);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void (MediaPlayer mp) {
                    player.start();
                }
            });
        }

        private void stopPlaying() {
            if (player.isPlaying()) {
                player.stop();
                player.release();
            }

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
