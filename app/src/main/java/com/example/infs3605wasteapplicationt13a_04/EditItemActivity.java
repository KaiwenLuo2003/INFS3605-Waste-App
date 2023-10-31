package com.example.infs3605wasteapplicationt13a_04;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI.ReceiptResultActivity;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.google.android.material.textfield.TextInputEditText;

public class EditItemActivity extends AppCompatActivity{

    private static final String TAG = "EditItemActivity";
    private final DBHandler dbHandler = new DBHandler(EditItemActivity.this);

    private ImageView itemImg;
    private TextInputEditText editItemName;
    private TextInputEditText editItemQuantity;
    private TextView editExpiryDate;
    private Button saveButton;
    private Button eatenButton;
    private Button disposalButton;

    private ImageView binIcon;
    private ImageView eatenIcon;

    private IngredientItem item;
    private String itemName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Init. display objects
        itemImg = findViewById(R.id.itemImage);
        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        editExpiryDate = findViewById(R.id.editItemExpiryDate);
        saveButton = findViewById(R.id.saveButton);
        eatenButton = findViewById(R.id.eatenButton);
        disposalButton = findViewById(R.id.disposeButton);
        eatenIcon = findViewById(R.id.eatenIcon);
        binIcon = findViewById(R.id.binIcon);

        //Init. item from intent
        //if intExtra = 1 --> from ReceiptResultActivity
        //if inExtra = 2 --> from PantryActivity
        Intent intent = getIntent();

        if(intent.getIntExtra(ReceiptResultActivity.ACTIVITY_INDICATOR, 1) == 1){
            itemName = intent.getStringExtra(ReceiptResultActivity.ITEM_TAG);
            eatenButton.setVisibility(View.GONE);
            disposalButton.setVisibility(View.GONE);
            eatenIcon.setVisibility(View.GONE);
            binIcon.setVisibility(View.GONE);

        } else if(intent.getIntExtra(PantryActivity.ACTIVITY_INDICATOR, 2) == 2){
            itemName = intent.getStringExtra(PantryActivity.ITEM_TAG);
        }
        item = dbHandler.getItemByName(itemName);
        setTitle("Edit " + itemName);



        //set data
        itemImg.setImageResource(item.getIcon());
        editItemName.setText(item.getItemName());
        editItemQuantity.setText(item.getQuantity());
        editExpiryDate.setText(item.getExpiryDate());

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //update IngredientItem object data
                item.setItemName(String.valueOf(editItemName.getText()));
                item.setQuantity(String.valueOf(editItemQuantity.getText()));
                item.setExpiryDate(String.valueOf(editExpiryDate.getText()));

                //update database
                dbHandler.updateIngredientItem(item);
                Log.d(TAG, "Ingredient details updated");

                if(intent.getIntExtra(ReceiptResultActivity.ACTIVITY_INDICATOR, 1) == 1){
                    ReceiptResultActivity.updateRecyclerView(dbHandler);
                    Intent returnToReceiptResult = new Intent(EditItemActivity.this, ReceiptResultActivity.class);
                    startActivity(returnToReceiptResult);
                }
                else if(intent.getIntExtra(PantryActivity.ACTIVITY_INDICATOR, 2) == 2){
                    PantryActivity.updateRecyclerView(dbHandler);
                    Intent returnToPantry = new Intent(EditItemActivity.this, PantryActivity.class);
                    startActivity(returnToPantry);
                }

            }
        });

        eatenButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dbHandler.deleteItem(item);
                Log.d(TAG, "Ingredient details deleted");

                PantryActivity.updateRecyclerView(dbHandler);
                Intent returnToPantry = new Intent(EditItemActivity.this, PantryActivity.class);
                startActivity(returnToPantry);
            }
        });

        disposalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dbHandler.deleteItem(item);
                Log.d(TAG, "Ingredient details deleted");

                PantryActivity.updateRecyclerView(dbHandler);
                Intent returnToPantry = new Intent(EditItemActivity.this, PantryActivity.class);
                startActivity(returnToPantry);
            }
        });
    }
}
