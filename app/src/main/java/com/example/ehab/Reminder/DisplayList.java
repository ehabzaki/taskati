package com.example.ehab.Reminder;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayList extends AppCompatActivity {

    private String id;//to hold selected stafid
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;

    //variables to hold staff records
    private ArrayList<String> taskid = new ArrayList<String>();
    private ArrayList<String> taskname = new ArrayList<String>();
    private ArrayList<String> tasktime = new ArrayList<String>();
    private ArrayList<String> taskdate = new ArrayList<String>();
    private ListView userList;
    private AlertDialog.Builder build;
public  int item;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        userList = (ListView) findViewById(R.id.List);

        mHelper = new DBHelper(this);

        //click to update data
        userList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2,long arg3  ) {
                item=arg2;
                //Update record selected
                Intent i = new Intent(getApplicationContext(),UpdateRecord.class);
                i.putExtra("taskid", taskid.get(arg2));
                startActivity(i);

            }
        });//end setOnItemClickListener

        //long-press to update data


    }// end onCreate method

    @Override
    protected void onResume() {
        //refresh data for screen is invoked/displayed
        displayData();
        Toast.makeText(getApplicationContext(), "TAP to view complete info, \n LONG-TAP to update/delete...", Toast.LENGTH_LONG).show();
        super.onResume();
    }

    /**
     * displays data from SQLite
     */
    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        //the SQL command to fetched all records from the table
        Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DBHelper.TABLE_NAME, null);

        //reset variables
        taskid.clear();
        taskname.clear();
        tasktime.clear();

        //fetch each record
        if (mCursor.moveToFirst()) {
            do {
                //get data from field
                taskid.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.TaskId)));
                taskname.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.TaskData)));
                tasktime.add(mCursor.getString(mCursor.getColumnIndex(DBHelper.TimeData)));

            } while (mCursor.moveToNext());
            //do above till data exhausted
        }

        //display to screen
        DisplayAdapter disadpt = new DisplayAdapter(DisplayList.this, taskid, taskname, tasktime);
        userList.setAdapter(disadpt);
        mCursor.close();
    }//end displayData


    public void adding(View view )
    {
        Intent i = new Intent(getApplicationContext(),
                AddNewRecord.class);
        startActivity(i);

    }
    public void completedtask(View view )
    {
        dataBase.delete(DBHelper.TABLE_NAME,DBHelper.TaskId + "="+ taskid.get(item), null);
        Toast.makeText(getApplicationContext(), taskname.get(item) + " is deleted.", Toast.LENGTH_SHORT).show();
        displayData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId()==R.id.m_add_record) {

            //invoke the AddNewRecord screen using Intent
            Intent i = new Intent(getApplicationContext(),
                    AddNewRecord.class);
            startActivity(i);
        }
        return true;
    }

}//end DisplayList class
