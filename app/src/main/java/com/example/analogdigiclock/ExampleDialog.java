package com.example.analogdigiclock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.io.IOException;


public class ExampleDialog extends AppCompatDialogFragment {
    MediaPlayer mp;
    CountDownTimer countdown;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AudioManager audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 20, 0);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_ALARM);
        mp.setLooping(true);
        try {
            mp.setDataSource(getContext(), notification);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();

        countdown = new CountDownTimer(60000, 1000) { //Stop the alarm after 60 seconds

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                stopPlayer();
            }
        }.start();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Budik")
                .setPositiveButton("Vypni", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopPlayer();
                        countdown.cancel();
                    }

                });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        stopPlayer();
        countdown.cancel();
        super.onCancel(dialog);
    }


    public void stopPlayer(){
        mp.stop();
        mp.release();
        ((Activity)getContext()).finish();
    }
}
