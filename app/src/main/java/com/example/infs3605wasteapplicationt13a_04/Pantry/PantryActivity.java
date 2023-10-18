package com.example.infs3605wasteapplicationt13a_04.Pantry;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605wasteapplicationt13a_04.R;
import com.example.infs3605wasteapplicationt13a_04.ui.RecyclerViewInterface;
import com.example.infs3605wasteapplicationt13a_04.ui.SpacingItemDecorator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity implements RecyclerViewAdapterPantryView.ItemClickListener{
    public static final String INTENT_MESSAGE = "intent_message";
    private BottomNavigationView bottomNavigationView;

    private RecyclerViewAdapterPantryView adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        //Temporary data to populate recyclerview
        ArrayList<String> pantryItemNames = new ArrayList<>();
        pantryItemNames.add("Chocolate");
        pantryItemNames.add("Cheese");
        pantryItemNames.add("Lemon");

        //Get handle for view elements
        recyclerView = findViewById(R.id.rvList);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.pantryPage);

        //Instantiate a linear recycler view layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterPantryView(this, pantryItemNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        //Format the recycler view for readibility and aesthetics
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(60, 50);
        recyclerView.addItemDecoration(itemDecorator);


        //firebase documentation: https://firebase.google.com/docs/firestore/quickstart#java
        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}