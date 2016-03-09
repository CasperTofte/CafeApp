package com.example.caspertofte.toftebar;

import java.util.ArrayList;
/**
 * Created by Casper Tofte on 08-03-2016.
 */
public class Drinks_method {
                                // Should be private
        private String name;
        private String price;
        private String image;

        // Constructor
        public Drinks_method(String name, String price, String image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }

    public static ArrayList<Drinks_method> getDrinks() {
        ArrayList<Drinks_method> users = new ArrayList<Drinks_method>();
        users.add(new Drinks_method("Harry", "San Diego", "1"));
        users.add(new Drinks_method("Marla", "San Francisco", "2"));
        users.add(new Drinks_method("Sarah", "San Marco", "3"));
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
}

