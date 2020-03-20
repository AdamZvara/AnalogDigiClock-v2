package com.example.analogdigiclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //What will happend, when the timer goes off - play music and add stop button
        Intent intent2 = new Intent(context, AlarmActivity.class);
        context.startActivity(intent2);
    }
}
