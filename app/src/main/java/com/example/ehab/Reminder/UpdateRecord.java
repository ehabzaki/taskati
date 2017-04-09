package com.example.ehab.Reminder;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UpdateRecord extends AppCompatActivity {
    private EditText taskupdate, timeupdate, dateupdate;
    private Button updatebtn;
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    private String id,task,time,date;//to hold the data strings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        mHelper=new DBHelper(this);
        dataBase = mHelper.getWritableDatabase();

        //capture sent parameter from previous screen
        id=getIntent().getExtras().getString("taskid");

        //initiate all textbox container to hold the data for the staff
        taskupdate=(EditText)findViewById(R.id.tasktextupdate);
        timeupdate=(EditText)findViewById(R.id.timeTextupdate);
        dateupdate=(EditText)findViewById(R.id.dateTextupdate);


        //display field record inside the textboxes
        displayData();

        updatebtn=(Button)findViewById(R.id.Alarmedit);
        updatebtn.setOnClickListener( new OnClickListener(){
            public void onClick(View v){
                //capture amendment
                task=taskupdate.getText().toString();
                time=timeupdate.getText().toString();
                date=dateupdate.getText().toString();
                saveData();
            }
        });//end btnsave setOnCLickCListener
    }

    //display single record of data from stafid
    private void displayData() {

        //the SQL command to fetched all records from the table
        String sql="SELECT * FROM "
                + DBHelper.TABLE_NAME +" WHERE taskid='"+id+"';";
        Cursor mCursor = dataBase.rawQuery(sql, null);

        //fetch the record
        if (mCursor.moveToFirst()) {
            //fetch each field and transfer to textbox
            task=mCursor.getString(mCursor.getColumnIndex(DBHelper.TaskData));
            time=mCursor.getString(mCursor.getColumnIndex(DBHelper.TimeData));
            date=mCursor.getString(mCursor.getColumnIndex(DBHelper.DateData));
            //get data from field and transfer to EditText
            taskupdate.setText(task);

            timeupdate.setText(time);
            dateupdate.setText(date);

        }
        else{
            //do something here if no record fetched from database
            taskupdate.setText(sql);
        }
    }//end displayData


    //save updated data
    private void saveData(){
        //dataBase=mHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DBHelper.TaskData,task);
        values.put(DBHelper.TimeData,time );
        values.put(DBHelper.DateData,date );
        System.out.println("");
        //update database with new data
        dataBase.update(DBHelper.TABLE_NAME, values, DBHelper.TaskId+"="+id, null);
        //close database
        dataBase.close();
        finish();


    }

}
