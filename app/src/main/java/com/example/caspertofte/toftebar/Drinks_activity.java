package com.example.caspertofte.toftebar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Drinks_activity extends AppCompatActivity {
    private static boolean hasPopulatedDrinks = false;

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

    // 2.0 Using a Custom ArrayAdapter
        populateDrinksList();
        // Add item to adapter
        //Drinks_method newDrink = new Drinks_method("Cola", "45.00", R.drawable.classics_cola);
        //adapter.add(newDrink);

    // 3.0 Using a Custom CurserAdapter with a database
        // Populate drinks table
        DbHelper myhelper = new DbHelper(this);
        SQLiteDatabase db = myhelper.getWritableDatabase();

        if (!hasPopulatedDrinks) {
            insertDrinksDB (db, "Cola", 45.00, R.drawable.classics_cola);
            insertDrinksDB (db, "Orange", 36.25, R.drawable.classics_orange);
            insertDrinksDB (db, "Lemon Lime", 30.75, R.drawable.classics_lemon_lime);
            insertDrinksDB (db, "Ginger Ale", 40.99, R.drawable.classics_ginger_ale);
            insertDrinksDB (db, "Tonic", 15.00, R.drawable.classics_tonic);
            insertDrinksDB (db, "Lemon Lime Sugarfree", 21.99, R.drawable.classics_sugar_free_lemon_lime);
            insertDrinksDB (db, "Cola Sugarfree", 17.50, R.drawable.classics_sugar_free_cola);
            insertDrinksDB (db, "Orange Sugarfree", 19.50, R.drawable.classics_sugar_free_orange);

            hasPopulatedDrinks = true;
            Log.d("Database", "Drinks are populated into table");
        }


    }

        public void populateDrinksList() {
        // Construct the data source
        ArrayList<Drinks_method> arrayOfDrinks = Drinks_method.getDrinks();
        // Create the adapter to convert the array to views
        CustomAdapter_Drinklist adapter = new CustomAdapter_Drinklist(this, arrayOfDrinks);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.myCustomDrinkList);
        listView.setAdapter(adapter);
        }

       /* public void populateDrinksDB() {
        // Construct the data source
        ArrayList<Drinks_method> arrayOfDrinks = Drinks_method.getDrinks();
        // Create the adapter to convert the array to views
        CustomCursorAdapter_Drinklist curseradapter = new CustomCursorAdapter_Drinklist(this, arrayOfDrinks);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.myCustomDrinkList);
        listView.setAdapter(curseradapter);
    }*/

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