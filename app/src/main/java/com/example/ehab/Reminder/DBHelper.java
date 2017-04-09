package com.example.ehab.Reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	//define all the constants
    static String DATABASE_NAME="Ehab";
    public static final String TABLE_NAME="Reminder";
    //these are the lit of fields in the table
    public static final String  TaskId="taskid";
    public static final String TaskData="Task";
    public static final String TimeData="Time";
    public static final String DateData="Date";
    public DBHelper(Context context) {
    	//create the database
        super(context, DATABASE_NAME, null, 2);
       
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	//create the table
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+TaskId+" INTEGER PRIMARY KEY, "+TaskData+" TEXT, "+TimeData+" TEXT, "+DateData+" TEXT)";
        db.execSQL(CREATE_TABLE);
        //populate dummy data


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	//onUpgrade remove the existing table, and recreate and populate new data
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}


