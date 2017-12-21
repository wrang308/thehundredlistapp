package com.example.marku.thehundredlistapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotifyService extends BroadcastReceiver {

    DataBaseHelper Mydb;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Mydb = new DataBaseHelper(context);

        Cursor result = Mydb.getAllData();
        String contentString = "";
        while(result.moveToNext()){
            contentString = contentString  + result.getString(1) + "¤";
        }
        String[] mainTextArray = ArrayifyString(contentString);

        int MID = 33;

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, NotifyService.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Go to the app to see your daily reminder")
                .setContentText(mainTextArray[time.getDays() % mainTextArray.length])
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});
        notificationManager.notify(MID, mNotifyBuilder.build());
        MID++;

    }

    public String[] ArrayifyString(String string){
        String[] array;
        array = string.split("¤");
        return array;
    }

}