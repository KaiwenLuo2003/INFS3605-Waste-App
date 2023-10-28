package com.example.infs3605wasteapplicationt13a_04.pantry;

import android.graphics.drawable.Drawable;

import com.example.infs3605wasteapplicationt13a_04.R;

import java.util.ArrayList;

public class Pantry {
    //Declarations
    private String mItem;
    private String mQuantity;
    private String mExpiry;
    private int mImage;

    public Pantry(String item, String quantity, String expiry, int image) {
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
    public int getImage() {
        return mImage;
    }


    public static ArrayList<Pantry> getPantry() {
        ArrayList<Pantry> pantry = new ArrayList<Pantry>();
        pantry.add(new Pantry("Chocolate","250g", "20/12/2023", R.drawable.chocolate_icon));
        return pantry;
    }
}


