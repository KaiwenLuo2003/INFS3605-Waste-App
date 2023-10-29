package com.example.infs3605wasteapplicationt13a_04;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.ReceiptResultActivity;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;
import com.google.android.material.textfield.TextInputEditText;

public class EditItemActivity extends AppCompatActivity{
    private DBHandler dbHandler = new DBHandler(EditItemActivity.this);

    private ImageView itemImg;
    private TextInputEditText editItemName;
    private TextInputEditText editItemQuantity;
    private TextView editExpiryDate;

    private IngredientItem item;
    private String itemName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Init. item from intent
        Intent intent = getIntent();
        itemName = intent.getStringExtra(ReceiptResultActivity.ITEM_TAG);
        item = dbHandler.getItemByName(itemName);

        //Init. display objects
        itemImg = findViewById(R.id.itemImage);
        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        editExpiryDate = findViewById(R.id.editItemExpiryDate);

        //set data
        itemImg.setImageResource(item.getIcon());
        editItemName.setText(item.getItemName());
        editItemQuantity.setText(item.getQuantity());
        editExpiryDate.setText(item.getExpiryDate());

        /*
        TODO: figure out how to have multiple intents from multiple screens
        TODO: figure out how to update/set the text in the database (save button?)
         */


    }
}
