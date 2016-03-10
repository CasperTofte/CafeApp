package com.example.caspertofte.toftebar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;




/**
 * Created by Casper Tofte on 08-03-2016.
 */
public class CustomAdapter_Drinklist extends ArrayAdapter<Drinks_method> {

    // declaring the ArrayList of drink items
    private ArrayList<Drinks_method> objects;

    // override the constructor of the ArrayAdapter, to be able to parse the list of drink objects
    public CustomAdapter_Drinklist(Context context, ArrayList<Drinks_method> objects){
        super(context, R.layout.list_drinks, objects);
        this.objects = objects;
    }

    //Override getView method - defines how the list of drinks will be displayed
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        //View v = convertView;

        // Check if the view is not defined. if so, we have to inflate (render/create) the view.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.list_drinks, null);
        }

        /*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        // Get the data item for this position
        Drinks_method drinks = objects.get(position);


        // Lookup view for data population
        TextView drinkName = (TextView) convertView.findViewById(R.id.drinkName);
        TextView drinkPrice = (TextView) convertView.findViewById(R.id.drinkPrice);
        ImageView drinkImage = (ImageView) convertView.findViewById(R.id.drinkImage);  //Should be ImageView

        // Populate the data into the template view using the data object
        drinkName.setText(drinks.getName());
        drinkPrice.setText(drinks.getPrice());
        drinkImage.setImageResource(drinks.getImage());     // Should be get Ressource


        // print the data
        return convertView;
    }



}
