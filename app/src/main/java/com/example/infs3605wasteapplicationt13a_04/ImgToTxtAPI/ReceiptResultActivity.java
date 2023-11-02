package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.DBHandler;
import com.example.infs3605wasteapplicationt13a_04.EditItemActivity;
import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.objects.IngredientItem;
import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.ui.SpacingItemDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReceiptResultActivity extends AppCompatActivity {
    private static RecyclerViewAdapterReceiptResultView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button confirmButton;
    private final DBHandler dbHandler = new DBHandler(ReceiptResultActivity.this);
    private SQLiteDatabase db;
    private static List<IngredientItem> receiptResultItems = new ArrayList<IngredientItem>();


    public static String ITEM_TAG = "Receipt Item";
    public static String ACTIVITY_INDICATOR = "Activity Indicator";
    public static int activityIndicator = 1;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_result);
        setTitle("Results");

        Intent receiveIntent = getIntent();
        if(receiveIntent.getStringExtra(EditItemActivity.EDIT_TAG) == null){
            db = dbHandler.getWritableDatabase();
            receiptResultItems = createReceiptItemsList();
        }

        //To editable detail screen
        RecyclerViewAdapterReceiptResultView.ItemClickListener listener = new RecyclerViewAdapterReceiptResultView.ItemClickListener() {
            @Override
            public void onItemClick(View view, String itemName) {
                Intent intent = new Intent(ReceiptResultActivity.this, EditItemActivity.class);
                intent.putExtra(ITEM_TAG, itemName);
                intent.putExtra(ACTIVITY_INDICATOR, activityIndicator);
                startActivity(intent);
            }
        };

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvReceiptResultList);
        confirmButton = findViewById(R.id.confirmButton);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterReceiptResultView(this, receiptResultItems, listener);
        recyclerView.setAdapter(adapter);

        //Format the recycler view for readability and aesthetics
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(60, 50);
        recyclerView.addItemDecoration(itemDecorator);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent seePantry = new Intent(ReceiptResultActivity.this, PantryActivity.class);
                startActivity(seePantry);
            }
        });

    }

    public ArrayList<IngredientItem> createReceiptItemsList(){
        ArrayList<IngredientItem> receiptItemsList = new ArrayList<>();

        //for dates, get today's date and add a certain amount of days/weeks
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String today = df.format(date);


        try{
            cal.setTime(df.parse(today));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //1 week expiry stuff
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String lettuceDate = df.format(cal.getTime());

        //2 week expiry stuff
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String chickenDate = df.format(cal.getTime());

        //multi-month expiry stuff
        cal.add(Calendar.DAY_OF_MONTH, 30);
        String pankoDate = df.format(cal.getTime());

        dbHandler.addIngredientItem(db, "Iceberg Lettuce", today, lettuceDate, R.drawable.lettuce, "1");
        dbHandler.addIngredientItem(db, "WW RSPCA Chicken Mince 500g", today, chickenDate, R.drawable.chicken_leg, "1");
        dbHandler.addIngredientItem(db, "Mr Chens Pantry Panko Brd Crumb 250g", today, pankoDate, R.drawable.bread, "1");
        dbHandler.addIngredientItem(db, "Brioche Gourmet Burger Buns 4pk 250g", today, lettuceDate, R.drawable.bread, "1");

        receiptItemsList = dbHandler.getItemsByDate(today);

        return receiptItemsList;
    }

    public static void updateRecyclerView(DBHandler dbHandler){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String today = df.format(date);

        receiptResultItems.clear();
        receiptResultItems = dbHandler.getItemsByDate(today);
        adapter.updateList(receiptResultItems);

    }


}
