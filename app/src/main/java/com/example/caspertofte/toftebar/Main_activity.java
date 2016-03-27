package com.example.caspertofte.toftebar;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;


public class Main_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Switch activities
    public void aboutActivity(View v)
    {
        Intent intent = new Intent(this, About_activity.class);
        startActivity(intent);
    }

    public void contactActivity(View v)
    {
        Intent intent = new Intent(this, Contact_activity.class);
        startActivity(intent);
    }

    public void drinksActivity(View v)
    {
        Intent intent = new Intent(this, Drinks_activity.class);
        startActivity(intent);
    }

    public void mapActivity(View v)
    {
        Intent intent = new Intent(this, Map_activity.class);
        startActivity(intent);
    }

    public void profileActivity(View v)
    {
        Intent intent = new Intent(this, Profile_activity.class);
        startActivity(intent);
    }
}
