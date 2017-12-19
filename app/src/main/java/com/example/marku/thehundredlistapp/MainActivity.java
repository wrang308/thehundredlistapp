package com.example.marku.thehundredlistapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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


        Cursor result = Mydb.getAllData();
        String contentString = "";
        while(result.moveToNext()){
            contentString = contentString  + result.getString(1) + "¤";
        }
        String[] mainTextArray = ArrayifyString(contentString);

        time.getDays();
        System.out.println(contentString);
        System.out.println("TIME" + time.getDays() + " Length " + mainTextArray.length);

        mainText.setText(mainTextArray[time.getDays() % mainTextArray.length]);
    }


    public String[] ArrayifyString(String string){
        String[] array;
        array = string.split("¤");
        return array;
    }

}


