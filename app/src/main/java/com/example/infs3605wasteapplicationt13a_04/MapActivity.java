package com.example.infs3605wasteapplicationt13a_04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.infs3605wasteapplicationt13a_04.pantry.PantryActivity;
import com.example.infs3605wasteapplicationt13a_04.recipe.RecipeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng matravilleRecyclingCentre = new LatLng(-33.959420, 151.226710);
    LatLng randwickRecyclingCentre = new LatLng(-33.911200, 151.243180);
    LatLng banksmeadowRecycling = new LatLng(-33.958660, 151.220460);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);


    private ArrayList<LatLng> locationArrayList;
    private ArrayList<String> nameArrayList;
    private BottomNavigationView bottomNavigationView;
    private ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Get handle for view elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        home = findViewById(R.id.backHome);
        bottomNavigationView.setSelectedItemId(R.id.recyclePage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomePageActivity("Message from HomeActivity");
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationArrayList = new ArrayList<>();
        //Worst hardcoding ever look away
        nameArrayList = new ArrayList<>();


        locationArrayList.add(matravilleRecyclingCentre);
        locationArrayList.add(randwickRecyclingCentre);
        locationArrayList.add(banksmeadowRecycling);
        nameArrayList.add("Matraville Recycling Centre");
        nameArrayList.add("Randwick Recycling Centre");
        nameArrayList.add("BM Recycling Banksmeadow");
    }




    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePage:
                        launchHomePageActivity("Message from MainActivity");
                        return true;
                    case R.id.pantryPage:
                        launchPantryActivity("Message from MainActivity");
                        return true;
                    case R.id.cameraPage:
                        launchAddItemActivity("Message from MainActivity");
                        return true;
                    case R.id.recipesPage:
                        launchRecipeActivity("Message from HomeActivity");
                        return true;
                    case R.id.recyclePage:
                        launchRecycleActivity("Message from MainActivity");
                        return true;
                }

                return false;
            }
        });
        return false;
    }


    //Methods to open new activities for navigation bar functionalities

    public void launchAddItemActivity(String msg) {
        Intent intent = new Intent(MapActivity.this, AddItemActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchPantryActivity(String msg) {
        Intent intent = new Intent(MapActivity.this, PantryActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecipeActivity(String msg) {
        Intent intent = new Intent(MapActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchRecycleActivity(String msg) {
        Intent intent = new Intent(MapActivity.this, MapActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    public void launchHomePageActivity(String msg) {
        Intent intent = new Intent(MapActivity.this, MainActivity.class);
        intent.putExtra(AddItemActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }
    public void returnToLogIn(String msg) {
        Intent intent = new Intent(MapActivity.this, LoginActivity.class);
        intent.putExtra(LoginActivity.INTENT_MESSAGE, msg);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < locationArrayList.size(); i++) {

            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(nameArrayList.get(i)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationArrayList.get(i), 15));
        }
    }
}
