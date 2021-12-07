package com.example.tulsi.familytrackingapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference latLong  = database.getReference("Location");
        final Double[] newLat = new Double[1];
        final Double[] newLong = new Double[1];
        latLong.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newLat[0] = Double.parseDouble(dataSnapshot.child("lat").getValue().toString());
                newLong[0] = Double.parseDouble(dataSnapshot.child("long").getValue().toString());
                LatLng markerPoint = new LatLng(newLat[0], newLong[0]);
                //LatLng sydney = new LatLng(37.391219,-121.930);
                MarkerOptions marker = new MarkerOptions().position(markerPoint).title("Sanskriti Agrawal");
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.userplaceholder));
                mMap.addMarker(marker);
                // mMap.addMarker(new MarkerOptions().position(sydney).title("Anuradha's location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPoint));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

      /*  LatLng location2 = new LatLng(37.402276,-121.942161);
        mMap.addMarker(new MarkerOptions().position(location2).title("Anuradha's new location"));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(location2));*/

                //zoom into a particular position
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
                mMap.moveCamera(zoom);
                mMap.animateCamera(zoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
