package com.example.infs3605wasteapplicationt13a_04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng matravilleRecyclingCentre = new LatLng(-33.959420, 151.226710);
    LatLng randwickRecyclingCentre = new LatLng(-33.911200, 151.243180);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);

    private ArrayList<LatLng> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationArrayList = new ArrayList<>();


        locationArrayList.add(matravilleRecyclingCentre);
        locationArrayList.add(randwickRecyclingCentre);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < locationArrayList.size(); i++) {

            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));

             mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

             mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationArrayList.get(i), 16));
        }
    }
}
