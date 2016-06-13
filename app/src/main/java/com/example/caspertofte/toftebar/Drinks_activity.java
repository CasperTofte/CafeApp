package com.example.caspertofte.toftebar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Drinks_activity extends AppCompatActivity {
    private static int hasPopulatedDrinks = 0;
    TextView drinkName;
    TextView drinkPrice;
    ImageView drinkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);


/*  //1.0 Using a simple ArrayAdapter
        String[] drinksList = {"Cola", "Orange", "Lemon Lime", "Ginger Ale", "Tonic", "Lemon Lime Sugarfree", "Cola Sugarfree", "Orange Sugarfree"};
        Float[] priceList = {45.00f, 36.25f, 30.75f, 40.99f, 15.00f};
        String[] priceList = {"45.00", "36.25", "30.75", "40.99", "15.00"};
        Integer[] imageList={
                R.drawable.classics_cola,
                R.drawable.classics_orange,
                R.drawable.classics_lemon_lime,
                R.drawable.classics_ginger_ale,
                R.drawable.classics_tonic,
                R.drawable.classics_sugar_free_lemon_lime,
                R.drawable.classics_sugar_free_cola,
                R.drawable.classics_sugar_free_orange,
      };
        // Single item list with text
        ArrayAdapter<String> DrinksAdaptor= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DrinksList);
        ListView ListOfDrinks = (ListView) findViewById(R.id.myDrinkList);
        ListOfDrinks.setAdapter(DrinksAdaptor);
*/

/*     2.0 Using a Custom ArrayAdapter
        populateDrinksListDb();
        // Add item to adapter
        Drinks_method newDrink = new Drinks_method("Cola", "45.00", R.drawable.classics_cola);
        adapter.add(newDrink);
*/

    // 3.0 Using a Custom CurserAdapter with a database
        // Insert drinks into table
        //TODO: Data should only be inserted once
        DbHelper myhelper = new DbHelper(this);
        SQLiteDatabase db = myhelper.getWritableDatabase();

        if (hasPopulatedDrinks == 0) {
            insertDrinksDB (db, "Cola", 45.00, R.drawable.classics_cola);
            insertDrinksDB (db, "Orange", 36.25, R.drawable.classics_orange);
            insertDrinksDB (db, "Lemon Lime", 30.75, R.drawable.classics_lemon_lime);
            insertDrinksDB (db, "Ginger Ale", 40.99, R.drawable.classics_ginger_ale);
            insertDrinksDB (db, "Tonic", 15.00, R.drawable.classics_tonic);
            insertDrinksDB (db, "Lemon Lime Sugarfree", 21.99, R.drawable.classics_sugar_free_lemon_lime);
            insertDrinksDB (db, "Cola Sugarfree", 17.50, R.drawable.classics_sugar_free_cola);
            insertDrinksDB (db, "Orange Sugarfree", 19.50, R.drawable.classics_sugar_free_orange);

            hasPopulatedDrinks++;
            Log.d("Database", "Drinks are inserted into table");
        }
        populateDrinksListDb();
    }


/*      public void populateDrinksList() {
        // Construct the data source
        ArrayList<Drinks_method> arrayOfDrinks = Drinks_method.getDrinks();
        // Create the adapter to convert the array to views
        CustomAdapter_Drinklist adapter = new CustomAdapter_Drinklist(this, arrayOfDrinks);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.myCustomDrinkList);
        listView.setAdapter(adapter);
        }
*/

        public void populateDrinksListDb() {
            DbHelper myhelper = new DbHelper(this);
            SQLiteDatabase db = myhelper.getWritableDatabase();

        // Retrieve all information from the database table
            Cursor drinkCursor = db.rawQuery("Select * FROM " + DbHelper.DRINK_TABLE_NAME, null);
            // Store the desired colum values
            String[] PresentedData = new String[] {DbHelper.DRINK_NAME, DbHelper.DRINK_PRICE, DbHelper.DRINK_IMAGE};
            // Store the views to be used for the ListView
            int[] PresentedViews = new int[] {R.id.drinkName, R.id.drinkPrice, R.id.drinkImage};
            // Create the adapter to convert the array to views
            SimpleCursorAdapter drinkCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_drinks, drinkCursor, PresentedData, PresentedViews,0);
            // Attach the adapter to a ListView
            ListView drinkListview = (ListView) findViewById(R.id.myCustomDrinkList);
            drinkListview.setAdapter(drinkCursorAdapter);
    }

        public void insertDrinksDB (SQLiteDatabase db,  String name, double price, int imageRessource) {

            DbHelper myhelper = new DbHelper(this);
            db = myhelper.getWritableDatabase();

            ContentValues drinkValues = new ContentValues();
            drinkValues.put(myhelper.DRINK_NAME, name);
            drinkValues.put(myhelper.DRINK_PRICE, price);
            drinkValues.put(myhelper.DRINK_IMAGE, imageRessource);
            db.insert(myhelper.DRINK_TABLE_NAME, null, drinkValues);

        }

}