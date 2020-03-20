package com.example.analogdigiclock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {
    MediaPlayer player;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Budik")
                .setPositiveButton("Vypni", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.release();
                        ((Activity)getContext()).finish();
                    }

                });
        if (player == null) {
            player = MediaPlayer.create(getContext(), R.raw.gitara);
        }
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (player != null){
                    player.release();
                    player = null;
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        player.release();
        ((Activity)getContext()).finish();
        super.onCancel(dialog);
    }
}
