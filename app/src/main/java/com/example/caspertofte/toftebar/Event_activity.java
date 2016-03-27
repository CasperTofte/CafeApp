package com.example.caspertofte.toftebar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Event_activity extends AppCompatActivity {

    EditText et_eventDate;
    EditText et_eventTime;
    Spinner s_eventDrink;
    EditText et_eventPerson;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // references to the views
        et_eventDate = (EditText) findViewById(R.id.eventDate);
        et_eventTime = (EditText) findViewById(R.id.eventTime);
        s_eventDrink = (Spinner) findViewById(R.id.eventDrinks);
        et_eventPerson = (EditText) findViewById(R.id.eventPerson);

        // Populate Spinner from database
        ArrayList<String> allDrinks = getAllDrinks();
        Spinner drinksSpinner = (Spinner) findViewById(R.id.eventDrinks);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,allDrinks);
        drinksSpinner.setAdapter(adapter);
    }

    public void eventSave (View view) {
        String date = et_eventDate.getText().toString();
        String time = et_eventTime.getText().toString();
        String drink = s_eventDrink.toString();
        String person = et_eventPerson.getText().toString();

        //TODO Save event into database table
        DbHelper myhelper = new DbHelper(this);
        db = myhelper.getWritableDatabase();

        if (!date.equals("") && !time.equals("") && !drink.equals("") && !person.equals("")) {
            ContentValues event = new ContentValues();
            event.put(myhelper.EVENT_DATE, date);
            event.put(myhelper.EVENT_TIME, time);
            event.put(myhelper.EVENT_DRINK, drink);
            event.put(myhelper.EVENT_PERSON, person);
            db.insert(myhelper.EVENT_TABLE_NAME, null, event);
            Log.d("Event:", "Event is saved into database table");
            Toast.makeText(this, R.string.event_saved, Toast.LENGTH_SHORT).show();

            //Clear fields TODO: Spinner is not reset
            et_eventDate.setText("");
            et_eventTime.setText("");
            et_eventPerson.setText("");
        }
        else {
            Toast.makeText(this, R.string.event_err_emptyFields, Toast.LENGTH_SHORT).show();
        }
    }

    // Populate spinner from database
    public ArrayList<String> getAllDrinks(){
        ArrayList<String> drinks = new ArrayList<>();
        DbHelper myhelper = new DbHelper(this);
        db = myhelper.getReadableDatabase();
        Cursor drinkCursor = db.rawQuery("Select * FROM " + DbHelper.DRINK_TABLE_NAME, null);
        if(drinkCursor.getCount()>0){
            Log.d("Spinner","Database check OK - at least one drink");
            drinkCursor.moveToFirst();
            while(drinkCursor.moveToNext()){
                Log.d("Spinner", "More drinks");
                String drinkName = drinkCursor.getString(drinkCursor.getColumnIndex(DbHelper.DRINK_NAME));
                drinks.add(drinkName);
            }
        }
        return drinks;
    }

}
