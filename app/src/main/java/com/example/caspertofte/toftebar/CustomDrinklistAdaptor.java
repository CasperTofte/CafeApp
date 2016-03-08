package com.example.caspertofte.toftebar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Casper Tofte on 08-03-2016.
 */
public class CustomDrinklistAdaptor extends BaseAdapter{

    private ArrayList<DrinkList> listData;
    private LayoutInflater layoutInflater;

    public CustomDrinklistAdaptor(Context aContext, ArrayList<DrinkList> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_drinks, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.drinkName);
            holder.priceView = (TextView) convertView.findViewById(R.id.drinkPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).getName());
        holder.priceView.setText("Price, " + listData.get(position).getPrice());
        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView priceView;
    }
}
