package daily.reminder.marku.thehundredlistapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


public class NotifyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        DataBaseHelper Mydb;

        int MID = 333;

        Mydb = new DataBaseHelper(this);

        Cursor result = Mydb.getAllData();
        String contentString = "";
        while(result.moveToNext()){
            contentString = contentString  + result.getString(1) + "¤";
        }
        String[] mainTextArray = ArrayifyString(contentString);

        String[] titleArray = {"Go to the app to see your daily reminder", "Have you seen your daily" +
                " reminder? Go to the app to see it", "A new reminder for you! Go to the app to see it!",
        "New day new things, don't forget your reminder!", "Have a great day! Here is your daily reminder :)",
        "Heve you seen your daily reminder? :)", "Hello human, app says use app!", "Woop woop! A new day with" +
                "new opportunities, make the most of your day!", "Don't forget to see your daily reminder!",
        "Glöm inte bort att kolla in dagens påminelse!", "If you don't have anything to do you can go and see your reminder"};


        long when = System.currentTimeMillis();

        NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this.getApplicationContext() , MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent ,PendingIntent.FLAG_UPDATE_CURRENT );

        Notification mNotify = new Notification.Builder(this)
                .setContentTitle(titleArray[time.getDays()%titleArray.length])
                .setContentText(mainTextArray[time.getDays() % mainTextArray.length])
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true).setWhen(when)
                .build();

        mNM.notify( MID , mNotify);
        MID++;

    }

//
//    DataBaseHelper Mydb;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // TODO Auto-generated method stub
//        Mydb = new DataBaseHelper(context);
//
//        Cursor result = Mydb.getAllData();
//        String contentString = "";
//        while(result.moveToNext()){
//            contentString = contentString  + result.getString(1) + "¤";
//        }
//        String[] mainTextArray = ArrayifyString(contentString);
//
//        int MID = 333;
//
//        long when = System.currentTimeMillis();
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                context).setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Go to the app to see your daily reminder")
//                .setContentText(mainTextArray[time.getDays() % mainTextArray.length])
//                .setAutoCancel(true).setWhen(when)
//                .setContentIntent(pendingIntent)
//                .setVibrate(new long[]{1000, 1000});
//        notificationManager.notify(MID, mNotifyBuilder.build());
//        MID++;
//
//    }

    public String[] ArrayifyString(String string){
        String[] array;
        array = string.split("¤");
        return array;
    }

}