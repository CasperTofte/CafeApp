package com.example.caspertofte.toftebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class Drinks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        String[] DrinksList = {"Cola", "Orange", "Lemon Lime", "Xtream energy", "Raspberry"};
        String[] PriceList = {"50", "10", "30", "15", "45"};

        // Single item list
        // ArrayAdapter<String> DrinksAdaptor= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DrinksList);
        // ListView ListOfDrinks = (ListView) findViewById(R.id.myDrinkList);
        // ListOfDrinks.setAdapter(DrinksAdaptor);


        //Custom list
        ArrayList image_details = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.customDrinkList);
        lv1.setAdapter(new CustomDrinklistAdaptor(this, image_details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                DrinkList drinkData = (DrinkList) o;
                Toast.makeText(Drinks.this, "Selected :" + " " + drinkData, Toast.LENGTH_LONG).show();
            }
        });

    }

    private ArrayList getListData() {
        ArrayList<DrinkList> results = new ArrayList<DrinkList>();
        DrinkList drinkData = new DrinkList();
        drinkData.setName("LemonLime");
        drinkData.setPrice("7");
        results.add(drinkData);

        // Add some more dummy data for testing
        return results;
    }

}

