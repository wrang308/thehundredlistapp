package com.example.marku.thehundredlistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marku on 2017-11-16.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME = "databaseTable";
    public static final String COL_ID = "id";
    public static final String COL_TEXT = "maintext";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Maintext TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TEXT, text);

        long result = db.insert(TABLE_NAME, null, contentValues);


        //checks if its inserted in the table or not
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

    public boolean updateData(String id, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_TEXT, text);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[] { id });

        return true;

    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null,null);
    }

    public boolean dropRow(String rowNumber){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, COL_ID + "=" + rowNumber, null) > 0;
    }

    public void updateIds(){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + COL_ID + " = (" +
                "SELECT COUNT(*) + 1 FROM " + TABLE_NAME + " r " +
                "WHERE " + TABLE_NAME + "." + COL_ID + "> r." + COL_ID + ")";

        db.execSQL(updateQuery);
        db.close();

    }
}
