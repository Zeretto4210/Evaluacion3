package com.zeretto4210.evaluacion3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MarkerRegister extends AppCompatActivity {
    TextView nombre, latitud, longitud;
    String user;
    BDConnection data;
    int drawId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_register);
        nombre = (TextView) findViewById(R.id.i_nombre);
        latitud = (TextView) findViewById(R.id.i_latitud);
        longitud = (TextView) findViewById(R.id.i_longitud);
        user = getIntent().getStringExtra("user");
        drawId = R.drawable.defaultmarker;
        data = new BDConnection(this, "systemBD", null, 1);
    }
    public void newIcon(View view){
        switch(view.getId()){
            case R.id.imageButton2:
                drawId = R.drawable.dollar;
                break;
            case R.id.imageButton3:
                drawId = R.drawable.fire;
                break;
            case R.id.imageButton4:
                drawId = R.drawable.happyface;
                break;
            case R.id.imageButton5:
                drawId = R.drawable.music;
                break;
            default:
                drawId = R.drawable.defaultmarker;
        }
    }
    public void addMarker(View view){
        String table = "markers";
        SQLiteDatabase base = data.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",getCountDB(table)+1);
        values.put("user",user);
        values.put("name",nombre.getText()+"");
        values.put("latitude",Double.parseDouble(latitud.getText()+""));
        values.put("longitude",Double.parseDouble(longitud.getText()+""));
        values.put("icon",drawId);
        long result = base.insert(table,"id",values);
        nombre.setText("");
        latitud.setText("");
        longitud.setText("");
        Toast.makeText(this, "¡Marcador Agregado!", Toast.LENGTH_SHORT).show();
    }
    public void showAllMarkers(View view){
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("user",user);
        startActivity(i);
    }
    private int getCountDB(String table){
        SQLiteDatabase dbcant = data.getReadableDatabase();
        return ((int) android.database.DatabaseUtils.queryNumEntries(dbcant,table));
    }
}