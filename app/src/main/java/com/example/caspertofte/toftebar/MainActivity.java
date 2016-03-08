package com.example.caspertofte.toftebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.app.Activity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Switch activity
    public void drinksActivity(View v)
    {
        Intent intent = new Intent(this, Drinks.class);
        startActivity(intent);
    }
}
