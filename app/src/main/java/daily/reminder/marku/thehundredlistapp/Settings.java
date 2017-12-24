package daily.reminder.marku.thehundredlistapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by marku on 2017-12-01.
 */

public class Settings extends AppCompatActivity implements DeleteTablePopup.Communicator{

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
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addData();
        viewAll();
        updateData();
        deleteRow();
    }

    public void addData(){
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (textInput.getText().toString().length() < 1) {
                            Toast.makeText(Settings.this, "No Text. Please insert text before submitting", Toast.LENGTH_LONG).show();
                        } else {
                            boolean isInserted = Mydb.insertData(textInput.getText().toString());
                            if (isInserted == true) {
                                Mydb.updateIds();
                                Toast.makeText(Settings.this, "Data inserted", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(Settings.this, "Data not inserted", Toast.LENGTH_LONG).show();

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
                    buffer.append("ROW " + result.getString(0) + "\n");
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
                    Toast.makeText(Settings.this, "No number. Please insert a number", Toast.LENGTH_LONG).show();
                }
                else{
                    if (textInput.getText().toString().length() < 1) {
                        Toast.makeText(Settings.this, "No Text. Please insert text before updating", Toast.LENGTH_LONG).show();
                    }
                    else {
                        updateInt = Integer.parseInt(updateNumberText.getText().toString());

                        if (updateInt >= 0) {
                            boolean isupdate = Mydb.updateData(Integer.toString(updateInt), textInput.getText().toString());
                            if (isupdate == true) {
                                Toast.makeText(Settings.this, "Data is updated", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(Settings.this, "Data is not updated", Toast.LENGTH_LONG).show();

                            updateNumberText.setText("");
                        }
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
                    Toast.makeText(Settings.this, "No number. Please insert a number", Toast.LENGTH_LONG).show();
                }
                else{
                    deleteInt = Integer.parseInt(updateNumberText.getText().toString());

                    boolean isdeleted = Mydb.dropRow(Integer.toString(deleteInt));
                    if (isdeleted == true) {
                        Mydb.updateIds();
                        Toast.makeText(Settings.this, "Data is deleted", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(Settings.this, "Data is not deleted", Toast.LENGTH_LONG).show();

                    updateNumberText.setText("");
                }

            }
        });
    }

    public void deleteTable() {

        Toast.makeText(Settings.this, "Table deleted", Toast.LENGTH_LONG).show();
        Mydb.dropTable();

    }

    public void deleteTablePopupClick(View view) {

        FragmentManager manager = getFragmentManager();
        DeleteTablePopup deleteTablePopup = new DeleteTablePopup();
        deleteTablePopup.show(manager, "deleteTablePopup");

    }

    @Override
    public void onDialogMessage(String message) {
        System.out.println("SEttings yes clicked");
        deleteTable();
    }

}



