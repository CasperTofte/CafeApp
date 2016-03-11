package com.example.caspertofte.toftebar;

import java.util.ArrayList;
/**
 * Created by Casper Tofte on 08-03-2016.
 */
public class Drinks_method {

        private String name;
        private String price;
        private Integer image;

        // Constructor
        public Drinks_method(String name, String price, Integer image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }

    public static ArrayList<Drinks_method> getDrinks() {
        ArrayList<Drinks_method> users = new ArrayList<>();
        users.add(new Drinks_method("Cola", "45.00", R.drawable.classics_cola));
        users.add(new Drinks_method("Orange", "36.25", R.drawable.classics_orange));
        users.add(new Drinks_method("Lemon Lime", "30.75", R.drawable.classics_lemon_lime));
        users.add(new Drinks_method("Ginger Ale", "40.99", R.drawable.classics_ginger_ale));
        users.add(new Drinks_method("Tonic", "15.00", R.drawable.classics_tonic));
        users.add(new Drinks_method("Lemon Lime Sugarfree", "21.99", R.drawable.classics_sugar_free_lemon_lime));
        users.add(new Drinks_method("Cola Sugarfree", "17.50", R.drawable.classics_sugar_free_cola));
        users.add(new Drinks_method("Orange Sugarfree", "19.50", R.drawable.classics_sugar_free_orange));
        return users;
    }

        // Set and get
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getImage() {
            return image;
        }

        public void setImage(Integer image) {
            this.image = image;
        }
}

