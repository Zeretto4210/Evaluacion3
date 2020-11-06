package com.zeretto4210.evaluacion3;

import androidx.fragment.app.FragmentActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    BDConnection data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        data = new BDConnection(this, "systemBD", null, 1);
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

        Intent i = getIntent();
        String user = i.getStringExtra("user");
        ArrayList<Marker> markerList = retrieveMarkers(user);


        for(Marker m : markerList){
            LatLng marker = new LatLng(m.getLatitude(), m.getLongitude());
            mMap.addMarker(new MarkerOptions().position(marker).title(m.getName()));
            //quedé aca
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,18));
        }
    }


    public ArrayList<Marker> retrieveMarkers(String user){
        ArrayList<Marker> tempList = new ArrayList<>();
        SQLiteDatabase base = data.getReadableDatabase();
        String[] columns = {"id", "user","name","latitude","longitude"};
        Cursor c = base.query("markers", columns, "user =?", new String[]{user}, null, null, null);
        while(c.moveToNext()){
            Marker b = new Marker(c.getInt(0), c.getString(1),c.getString(2),c.getDouble(3),c.getDouble(4));
            tempList.add(b);
        }
        return tempList;
    }

}