package com.example.marku.thehundredlistapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


import static com.example.marku.thehundredlistapp.R.id.submitButton;
import static com.example.marku.thehundredlistapp.R.id.textInput;
import static com.example.marku.thehundredlistapp.R.id.up;

/**
 * Created by marku on 2017-12-01.
 */

public class settings extends AppCompatActivity {


    DataBaseHelper Mydb;
    EditText textInput, updateNumberText;
    Button submitButton, showTableButton, updateButton,settingsBackButton, dropRowButton, dropTableButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        textInput = (EditText)findViewById(R.id.textInput);
        dropRowButton = (Button)findViewById(R.id.dropRowButton);
        dropTableButton = (Button)findViewById(R.id.dropTableButton);
        updateNumberText = (EditText)findViewById(R.id.updateNumberText);
        submitButton = (Button)findViewById(R.id.submitButton);
        showTableButton = (Button)findViewById(R.id.showTable);
        updateButton = (Button)findViewById(R.id.updateTable);
        Mydb = new DataBaseHelper(this);


        settingsBackButton = (Button)findViewById(R.id.backButton);

        settingsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings.this, MainActivity.class);
                startActivity(intent);
            }
        });


        addData();
        viewAll();
        updateData();
        deleteRow();
       // deleteTable();
    }

    public void addData(){
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (textInput.getText().toString().length() < 1) {
                            Toast.makeText(settings.this, "No Text. Please insert text before submitting", Toast.LENGTH_LONG).show();
                        } else {
                            boolean isInserted = Mydb.insertData(textInput.getText().toString());
                            if (isInserted == true) {
                                Mydb.updateIds();
                                Toast.makeText(settings.this, "Data inserted", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(settings.this, "Data not inserted", Toast.LENGTH_LONG).show();

                            textInput.setText("");
                        }

                    }
                }

        );
    }

    public void viewAll(){
        showTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = Mydb.getAllData();


                if(result.getCount() == 0) {
                    showMessage("Error", "Nothing Found");
                    //show message
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(result.moveToNext()){
                    buffer.append("ID " + result.getString(0) + "\n");
                    buffer.append(result.getString(1) + "\n");
                }

                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void updateData(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int updateInt = 0;
               Cursor result = Mydb.getAllData();

                if(updateNumberText.getText().toString().length() < 1){
                    Toast.makeText(settings.this, "No number. Please insert a number", Toast.LENGTH_LONG).show();
                }
                else{
                    updateInt = Integer.parseInt(updateNumberText.getText().toString());

                    if (updateInt >= 0) {
                        boolean isupdate = Mydb.updateData(Integer.toString(updateInt), textInput.getText().toString());
                        if (isupdate == true) {
                            Toast.makeText(settings.this, "Data is updated", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(settings.this, "Data is not updated", Toast.LENGTH_LONG).show();

                        updateNumberText.setText("");
                    }
                }
            }
        });
    }

    public void deleteRow(){
        dropRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deleteInt = 0;

                if(updateNumberText.getText().toString().length() < 1){
                    Toast.makeText(settings.this, "No number. Please insert a number", Toast.LENGTH_LONG).show();
                }
                else{
                    deleteInt = Integer.parseInt(updateNumberText.getText().toString());

                    boolean isdeleted = Mydb.dropRow(Integer.toString(deleteInt));
                    if (isdeleted == true) {
                        Mydb.updateIds();
                        Toast.makeText(settings.this, "Data is deleted", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(settings.this, "Data is not deleted", Toast.LENGTH_LONG).show();

                    updateNumberText.setText("");
                }

            }
        });
    }


    public void deleteTable(View view) {

    Mydb.dropTable();
    }

    public void removePopup(View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.dismiss();
    }


    public void onButtonShowPopupWindowClick(View view) {

        // get a reference to the already created main layout
        LinearLayout settingsLayout = (LinearLayout) findViewById(R.id.settings_layout);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(settingsLayout, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
       // popupView.setOnTouchListener(new View.OnTouchListener() {
       //     @Override
       //     public boolean onTouch(View v, MotionEvent event) {
       //         //popupWindow.dismiss();
       //         return true;
       //     }
       // });
    }


}



