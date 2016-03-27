package com.example.caspertofte.toftebar;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Casper Tofte on 22-03-2016.
 */
public class CustomCursorAdapter_Drinklist extends SimpleCursorAdapter {

    // Default constructor
    public CustomCursorAdapter_Drinklist(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }
}
