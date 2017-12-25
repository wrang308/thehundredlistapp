package daily.reminder.marku.thehundredlistapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

public class MainActivity extends AppCompatActivity {

    ImageButton settingsButton;
    TextView mainText;
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainText = (TextView)findViewById(R.id.mainText);
        mainText.setText(getDailyText());

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
    }

    public String[] ArrayifyString(String string){
        String[] array;
        array = string.split("¤");
        return array;
    }

}


