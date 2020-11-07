package com.zeretto4210.evaluacion3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {
    TextView name, user, gender;
    BDConnection data;
    User userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.p_name);
        user = (TextView) findViewById(R.id.p_user);
        gender = (TextView) findViewById(R.id.p_gender);
        data = new BDConnection(this, "systemBD", null, 1);
        Intent i = getIntent();
        userdata = retrieveLoggedUser();
        name.setText(userdata.getName()+" "+userdata.getLastname());
        user.setText(userdata.getUser());
        gender.setText(userdata.getGender());
    }


    public User retrieveLoggedUser() { //Obtiene el usuario logueado anteriormente, a través de su usuario obtenemos sus datos.
        SQLiteDatabase b = data.getWritableDatabase();
        try {
            SQLiteDatabase base = data.getReadableDatabase();
            String[] columns = {"id", "user"};
            Cursor c = base.query("logged", columns, null, null, null, null, null);
            c.moveToFirst();
            if (c.getInt(0) == 1) {
                return (retrieveUser(c.getString(1)));
            }
        } catch (Exception e) {
        }
        return null;
    }


    public User retrieveUser(String user) { //Obtiene los datos del usuario enviandole su usuario.
        SQLiteDatabase base = data.getReadableDatabase();
        String[] columns = {"id", "user", "password", "name", "lastname", "gender"};
        Cursor c = base.query("users", columns, "user =?", new String[]{user}, null, null, null);
        try {
            c.moveToFirst();
            if (c.getString(1).equals(user)) {
                return (new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            }
        } catch (Exception e) {
        }
        return null;
    }
}