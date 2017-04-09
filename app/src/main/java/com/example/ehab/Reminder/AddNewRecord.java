package com.example.ehab.Reminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddNewRecord extends AppCompatActivity {
    private EditText tasktext, timeText, dateText;
    private Button btnsave;
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;
    private String id,task,time,date;//to hold the data strings
    AlarmManager alarmManager;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    PendingIntent pendingIntent;

    Calendar timeCalendar = Calendar.getInstance();
    Intent myIntent;
    Context context;
    private Button AlarmAddbutton;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        mHelper=new DBHelper(this);
        dataBase = mHelper.getWritableDatabase();
        //initiate all textbox container to hold the data for the staff
        tasktext=(EditText)findViewById(R.id.tasktext);
        timeText=(EditText)findViewById(R.id.timeText);
        dateText=(EditText)findViewById(R.id.dateText);
        btnsave=(Button)findViewById(R.id.AlarmSetter);
        AlarmAddbutton = (Button) findViewById(R.id.AlarmSetter);
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        this.context=this;
        myIntent=new Intent(this.context,AlarmReciver.class);
        timeText.setInputType(InputType.TYPE_NULL);
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(AddNewRecord.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        timeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        timeCalendar.set(Calendar.MINUTE, minute);


                        String timestring = DateUtils.formatDateTime(AddNewRecord.this, timeCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
                        timeText.setText("time: " + timestring);

                    }
                },timeCalendar.get(timeCalendar.HOUR_OF_DAY), timeCalendar.get(timeCalendar.MINUTE), android.text.format.DateFormat.is24HourFormat(AddNewRecord.this));

                timePickerDialog.show();

            }
        });
        timeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    timePickerDialog = new TimePickerDialog(AddNewRecord.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                            timeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            timeCalendar.set(Calendar.MINUTE, minute);


                            String timestring = DateUtils.formatDateTime(AddNewRecord.this, timeCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
                            timeText.setText("time: " + timestring);

                        }
                    },timeCalendar.get(timeCalendar.HOUR_OF_DAY), timeCalendar.get(timeCalendar.MINUTE), android.text.format.DateFormat.is24HourFormat(AddNewRecord.this));

                    timePickerDialog.show();

                }
            }
        });

        dateText.setInputType(InputType.TYPE_NULL);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(AddNewRecord.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        timeCalendar.set(Calendar.YEAR, year);
                        timeCalendar.set(Calendar.MONTH, monthOfYear);
                        timeCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String dateString = DateUtils.formatDateTime(AddNewRecord.this, timeCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
                        dateText.setText("date: " + dateString);

                    }
                }, timeCalendar.get(timeCalendar.YEAR), timeCalendar.get(timeCalendar.MONTH), timeCalendar.get(timeCalendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });
        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    datePickerDialog = new DatePickerDialog(AddNewRecord.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                            timeCalendar.set(Calendar.YEAR, year);
                            timeCalendar.set(Calendar.MONTH, monthOfYear);
                            timeCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            String dateString = DateUtils.formatDateTime(AddNewRecord.this, timeCalendar.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE);
                            dateText.setText("date: " + dateString);

                        }
                    }, timeCalendar.get(timeCalendar.YEAR), timeCalendar.get(timeCalendar.MONTH), timeCalendar.get(timeCalendar.DAY_OF_MONTH));

                    datePickerDialog.show();
                }
            }
        });
        btnsave.setOnClickListener( new OnClickListener(){
            public void onClick(View v){
                //capture amendment
                task=tasktext.getText().toString();
                date=dateText.getText().toString();
                time=timeText.getText().toString();

                saveData();
                pendingIntent=pendingIntent.getBroadcast(AddNewRecord.this, 0, myIntent, pendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeCalendar.getTimeInMillis(), pendingIntent);
            }
        });//end btnsave setOnCLickCListener
    }

    //save new record
    private void saveData(){
        ContentValues values=new ContentValues();

        values.put(DBHelper.TaskData,task);
        values.put(DBHelper.TimeData,time);
        values.put(DBHelper.DateData,date );
        System.out.println("");

        //save new record to the database into database
        dataBase.insert(DBHelper.TABLE_NAME, null, values);

        //close database
        dataBase.close();
        finish();


    }

}

