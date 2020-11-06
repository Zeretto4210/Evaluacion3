package com.zeretto4210.evaluacion3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MarkerRegister extends AppCompatActivity {
    TextView nombre, latitud, longitud;
    String user;
    BDConnection data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_register);
        nombre = (TextView) findViewById(R.id.i_nombre);
        latitud = (TextView) findViewById(R.id.i_latitud);
        longitud = (TextView) findViewById(R.id.i_longitud);
        user = getIntent().getStringExtra("user");
        data = new BDConnection(this, "systemBD", null, 1);
    }
    public void add(View view){
        String table = "markers";
        SQLiteDatabase base = data.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",getCountDB(table)+1);
        values.put("user",user);
        values.put("name",nombre.getText()+"");
        values.put("latitude",Double.parseDouble(latitud.getText()+""));
        values.put("longitude",Double.parseDouble(longitud.getText()+""));
        long result = base.insert(table,"id",values);
        nombre.setText("");
        latitud.setText("");
        longitud.setText("");
    }
    public void showAll(View view){
        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        i.putExtra("user",user);
        startActivity(i);
    }
    private int getCountDB(String table){
        SQLiteDatabase dbcant = data.getReadableDatabase();
        return ((int) android.database.DatabaseUtils.queryNumEntries(dbcant,table));
    }
}