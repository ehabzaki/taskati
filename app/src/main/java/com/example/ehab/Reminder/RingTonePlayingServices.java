package com.example.ehab.Reminder;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ehab on 2/19/2016.
 */
public class RingTonePlayingServices extends Service {
    MediaPlayer song;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        song= MediaPlayer.create(this, R.raw.song);
        song.start();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this,AddNewRecord.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Horoscope reminder")
                .setContentText("Check your daily horoscope")
                .setSmallIcon(R.drawable.clock)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(startId, notification);

        return START_NOT_STICKY;


    }

    @Override
    public void onDestroy() {

        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }


}
