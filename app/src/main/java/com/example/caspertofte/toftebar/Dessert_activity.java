package com.example.caspertofte.toftebar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Dessert_activity extends AppCompatActivity implements Dessert_interface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dessert);
    }

    @Override
    public void getPosition (int p) {
        // When the user clicks on an item: get the position of the clicked item and retrieve the matching description.
        Log.d("Dessert list position", String.valueOf(p));

        //FragmentManager dessertDescription = getFragmentManager();
        //Frag_dessertDescription description = dessertDescription.findFragmentById(R.id.fragment_dessertDescription);
        Frag_dessertDescription fragmentDescription = (Frag_dessertDescription) getFragmentManager().findFragmentById(R.id.fragment2);
        fragmentDescription.setDescription(p);  // Pass the position of the clicked item and set the matching description in Frag_dessertDescription
    }
}
