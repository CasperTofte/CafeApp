package com.example.caspertofte.toftebar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;



/**
 * Created by Casper Tofte on 08-03-2016.
 */
public class CustomAdapter_Drinklist extends ArrayAdapter<String>{

    CustomAdapter_Drinklist(Context context, String[] itemname, String[] price){
    // price should be Float
    // Not passing second array
        super(context, R.layout.list_drinks, itemname);
    }

    //The definition of how the data should be displayed
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater drinksInflater = LayoutInflater.from(getContext());
        View customView = drinksInflater.inflate(R.layout.list_drinks, parent, false);

        // references to the data
        // getItem(position): the placement of the data in the array


        TextView drinkName = (TextView) customView.findViewById(R.id.drinkName);
        String singleDrinkItem = getItem(position);

        TextView drinkPrice = (TextView) customView.findViewById(R.id.drinkPrice);
        String singleDrinkPrice = getItem(position);

        ImageView drinkImage = (ImageView) customView.findViewById(R.id.drinkImage);

        // References to replace the data
        drinkName.setText(singleDrinkItem);
        drinkPrice.setText(singleDrinkPrice);
        drinkImage.setImageResource(R.drawable.classics_cola);  // Needs update to be generic

        // print the data
        return customView;
    }



}
