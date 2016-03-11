package com.example.caspertofte.toftebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Drinks_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

//        String[] drinksList = {"Cola", "Orange", "Lemon Lime", "Ginger Ale", "Tonic", "Lemon Lime Sugarfree", "Cola Sugarfree", "Orange Sugarfree"};
//        Float[] priceList = {45.00f, 36.25f, 30.75f, 40.99f, 15.00f};
//        String[] priceList = {"45.00", "36.25", "30.75", "40.99", "15.00"};
//        Integer[] imageList={
//                R.drawable.classics_cola,
//                R.drawable.classics_orange,
//                R.drawable.classics_lemon_lime,
//                R.drawable.classics_ginger_ale,
//                R.drawable.classics_tonic,
//                R.drawable.classics_sugar_free_lemon_lime,
//                R.drawable.classics_sugar_free_cola,
//                R.drawable.classics_sugar_free_orange,
//        };

        populateDrinksList();

        // Single item list with text
        // ArrayAdapter<String> DrinksAdaptor= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DrinksList);
        // ListView ListOfDrinks = (ListView) findViewById(R.id.myDrinkList);
        // ListOfDrinks.setAdapter(DrinksAdaptor);

        // Add item to adapter
        //Drinks_method newDrink = new Drinks_method("Cola", "45.00", R.drawable.classics_cola);
        //adapter.add(newDrink);



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



}

