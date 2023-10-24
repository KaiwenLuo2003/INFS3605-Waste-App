package com.example.infs3605wasteapplicationt13a_04.ImgToTxtAPI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ReceiptResultActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private RecyclerViewAdapterReceiptResultView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_result);

        ArrayList<String> receiptResultItems = new ArrayList<>();

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvReceiptResultList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cameraPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterReceiptResultView(this, receiptResultItems);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        /*
        TODO: finish setting up the result list screen
        TODO: create intent to go to result list screen
        TODO: hardcode in items from receipt
        TODO: figure out way to add those items into the pantry/fridge
         */

    }

}
