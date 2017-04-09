package com.example.ehab.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ehab on 2/19/2016.
 */
public class AlarmReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        Intent ringServiceIntent=new Intent(context,RingTonePlayingServices.class);
        context.startService(ringServiceIntent);
    }
}
