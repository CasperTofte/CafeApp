package com.example.caspertofte.toftebar;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Casper on 06-04-2016.
 */
public class Frag_dessertDescription extends Fragment {

    String[] description = {"Nutella", "Chocolate", "Strawberry", "Pineapple" };
    TextView tv_dessertDescription;
    String activeDescription;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_dessertDescription = (TextView) getActivity().findViewById(R.id.fragment_dessertDescription);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dessertdescription, container, false);

        Log.d("Desserts", String.valueOf(description));

        if(savedInstanceState !=null){
            activeDescription = savedInstanceState.getString("text");
            tv_dessertDescription = (TextView) view.findViewById(R.id.fragment_dessertDescription);
            tv_dessertDescription.setText(activeDescription);
        }


        return view;
    }

    // Save the fragments state
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("text", activeDescription);
    }

    public void setDescription(int position){
        tv_dessertDescription.setText(description[position]);
        Log.d("Dessert description", String.valueOf(description[position]));
    }


    public void newText (String newtext){
        this.activeDescription = newtext;
        tv_dessertDescription.setText(newtext);
    }

}
