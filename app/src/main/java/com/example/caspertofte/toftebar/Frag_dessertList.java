package com.example.caspertofte.toftebar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Casper on 06-04-2016.
 */
public class Frag_dessertList extends Fragment implements AdapterView.OnItemClickListener{

    ListView list;

    String[] desserts = {"Toblerone", "Sukker", "Havregryn", "Lotte"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dessertlist, container, false);
    }

    // Returned when "onCreate" is finished executing in the Dessert_activity
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        //Reference to the ListView
        list = (ListView) getActivity().findViewById(R.id.fragment_dessertList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,desserts);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    // When the user clicks on an item in the ListView, this method is called
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        // Retrieve position of the item clicked upon
        Dessert_interface listener = (Dessert_interface) getActivity();
        listener.getPosition(position);
    }

}
