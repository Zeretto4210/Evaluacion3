package com.zeretto4210.evaluacion3;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    BDConnection data;
    String user;
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
        user = i.getStringExtra("user");
        ArrayList<Marker> markerList = retrieveMarkers(mMap,user);

        if (!markerList.isEmpty()){
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker m : markerList){
                builder.include(m.getPosition());
            }
            CameraUpdate cu;
            int padding = 50;
            LatLngBounds bounds = builder.build();
            cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    /**set animated zoom camera into map*/
                    mMap.animateCamera(cu);
                }
            });
        }
        else{
            Toast.makeText(this, "Pero "+user+"... ¡No tienes ningún marcador!", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Marker> retrieveMarkers(GoogleMap map, String user){
        ArrayList<Marker> tempList = new ArrayList<>();
        SQLiteDatabase base = data.getReadableDatabase();
        String[] columns = {"id", "user","name","latitude","longitude"};
        Cursor c = base.query("markers", columns, "user =?", new String[]{user}, null, null, null);
        while(c.moveToNext()){
            LatLng position = new LatLng(c.getDouble(3), c.getDouble(4));
            Marker m = mMap.addMarker(new MarkerOptions().position(position).title(c.getString(2)));
            tempList.add(m);
        }
        return tempList;
    }

}