package com.example.marku.thehundredlistapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    ImageButton settingsButton;
    TextView mainText;

    public PendingIntent pendingIntent;



    DataBaseHelper Mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mydb = new DataBaseHelper(this);

        settingsButton = (ImageButton)findViewById(R.id.settingsButton);
        mainText = (TextView)findViewById(R.id.mainText);


        //mainText.setText(content[1]);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });


        //System.out.println(contentString);
        //System.out.println("TIME" + time.getDays() + " Length " + mainTextArray.length);

        mainText.setText(getDailyText());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        int i = preferences.getInt("Numberoflaunches", 1);

        // initialize the alarm
        if (i < 2) {
            i++;
            alarmMethod();
            editor.putInt("Numberoflaunches", i);
            editor.commit();
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
        }

        //setAlarm();
    }

    public String getDailyText(){

        String[] mainTextArray = getContentStringArr();

        return mainTextArray[time.getDays()%mainTextArray.length];
    }

    public String[] getContentStringArr(){
        Cursor result = Mydb.getAllData();
        String contentString = "";
        while(result.moveToNext()){
            contentString = contentString  + result.getString(1) + "¤";
        }
        String[] mainTextArray = ArrayifyString(contentString);
        return mainTextArray;
    }


    public  void alarmMethod(){




        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime());

        Intent intent1 = new Intent(MainActivity.this, NotifyService.class);
        intent1.putExtra("String", getContentStringArr());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//        Intent myIntent = new Intent(this , NotifyService.class);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);
//
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 120, pendingIntent);
//
//        System.out.println(calendar.getTimeInMillis());
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  1000 * 60 * 60 * 24,
          //      PendingIntent.getBroadcast(this, 2, myIntent,
            //            PendingIntent.FLAG_UPDATE_CURRENT));


    }

    public void setAlarm() {
        System.out.println("setAlarm Derek Banas");
        // Define a time value of 5 seconds
        //Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        // Define our intention of executing AlertReceiver
        Intent alertIntent = new Intent(this, NotifyService.class);

        // Allows you to schedule for your application to do something at a later date
        // even if it is in he background or isn't active
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // set() schedules an alarm to trigger
        // Trigger for alertIntent to fire in 5 seconds
        // FLAG_UPDATE_CURRENT : Update the Intent if active
        System.out.println(calendar.getTimeInMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  1000 * 60 * 60 *24,
                PendingIntent.getBroadcast(this, 1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));

    }

    public String[] ArrayifyString(String string){
        String[] array;
        array = string.split("¤");
        return array;
    }

}


