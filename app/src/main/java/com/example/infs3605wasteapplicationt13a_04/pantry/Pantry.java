package com.example.infs3605wasteapplicationt13a_04.pantry;

import android.widget.ImageView;

import java.util.ArrayList;

public class Pantry {
    //Declarations
    private String mItem;
    private String mQuantity;
    private String mExpiry;
    private ImageView mImage;

    public Pantry(String item, String quantity, String expiry, ImageView image) {
        mItem = item;
        mQuantity = quantity;
        mExpiry = expiry;
        image = mImage;
    }

    public String getItem() {
        return mItem;
    }
    public String getQuantity() {
        return mQuantity;
    }

    public String getExpiry() {
        return mExpiry;
    }
    public ImageView getImage() {
        return mImage;
    }


    public static ArrayList<Pantry> getPantry() {
        ArrayList<Pantry> pantry = new ArrayList<Pantry>();
        pantry.add(new Pantry("Chocolate","250g", "20/12/2023", );
        pantry.add(new Pantry("Exercise", "Dogs need exercise to burn calories, stimulate their minds, and stay healthy. Individual exercise needs vary based on breed or breed mix, sex, age and level of health. Exercise also tends to help dogs avoid boredom, which can lead to destructive behaviors. Supervised fun and games will satisfy many of your pet's instinctual urges to dig, herd, chew, retrieve and chase."));
        pantry.add(new Pantry("Grooming", "Help keep your dog clean and reduce shedding with frequent brushing. Check for fleas and ticks daily during warm weather. Most dogs don't need to be bathed more than a few times a year. Before bathing, comb or cut out all mats from the coat. Carefully rinse all soap out of the coat, or the dirt will stick to soap residue."));

        return categories;
    }

    public static Categories findCategories(String symbol) {
        ArrayList<Categories> categories = Categories.getCategories();
        for (final Categories categories1 : categories) {
            if (categories1.getName().equalsIgnoreCase(symbol)) {
                return categories1;
            }
        }
        return null;
    }

}


}
