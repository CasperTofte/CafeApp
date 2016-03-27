package com.example.caspertofte.toftebar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Casper Tofte on 22-03-2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    // dbhelper is only responsible for the DB structure

    //TODO: All variables should be private. Methods to change these values should be implemented.
    public static final String DATABASE_NAME = "ToftebarDB";
    public static final int DATABASE_VERSION = '1';

    // Drink table names
    public static final String DRINK_TABLE_NAME = "Drinks";
    public static final String DRINK_ID = "_id";
    public static final String DRINK_NAME = "Name";
    public static final String DRINK_IMAGE = "Image";
    public static final String DRINK_PRICE = "Price";

    // Event_activity table names
    public static final String EVENT_TABLE_NAME = "Events";
    public static final String EVENT_ID = "_id";
    public static final String EVENT_DATE = "Date";
    public static final String EVENT_TIME = "Time";
    public static final String EVENT_PERSON = "Person";
    public static final String EVENT_DRINK = "Drink";

    // Constructor of the DbHelper
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Drinks table
        String queryDrinks = "CREATE TABLE " + DRINK_TABLE_NAME + " (" + DRINK_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + DRINK_NAME + " TEXT, " + DRINK_IMAGE + " TEXT, " + DRINK_PRICE + " REAL);";
        db.execSQL(queryDrinks);
        Log.d("Database", "Drinks table is created");

        // Create Events table
        String queryEvents = "CREATE TABLE " + EVENT_TABLE_NAME + " (" + EVENT_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENT_DATE + " TEXT, " + EVENT_TIME + " TEXT, " + EVENT_PERSON + " TEXT, " + EVENT_DRINK + " TEXT);";
        db.execSQL(queryEvents);
        Log.d("Database", "Events table is created");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // TODO: Table data should be migrated to newer version
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DRINK_TABLE_NAME);
            onCreate(db);

            db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
            onCreate(db);
            Log.d("Database","Tables are dropped");
        }

    }
}