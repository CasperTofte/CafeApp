package com.example.caspertofte.toftebar;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Casper on 06-04-2016.
 */
public class Frag_dessertDescription extends Fragment {

    String[] description = {"Nutella", "Chocolate", "Strawberry", "Pineapple" };
    TextView tv_dessertDescription;
    String activeDescription;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dessertdescription, container, false);

        // If the screen has been rotated / if the fragment is not created for the first time
        if(savedInstanceState !=null){
            activeDescription = savedInstanceState.getString("description");
            tv_dessertDescription = (TextView) view.findViewById(R.id.fragment_dessertDescription);
            tv_dessertDescription.setText(activeDescription);
        }
        return view;
    }

    // Returned when "onCreate" is finished executing in the Dessert_activity
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Reference to the TextView
        tv_dessertDescription = (TextView) getActivity().findViewById(R.id.fragment_dessertDescription);
    }

    // Save the fragments state when Frag_dessertDescription is about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("description", activeDescription);
    }

    // Update the TextView when to the matching dessert description
    public void setDescription(int position){
        this.activeDescription = description[position];
        tv_dessertDescription.setText(activeDescription);
        //tv_dessertDescription.setText(description[position]);
        Log.d("Dessert description", String.valueOf(description[position]));
    }
}
