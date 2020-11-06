package com.zeretto4210.evaluacion3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class UserRegister extends AppCompatActivity {

    BDConnection data;
    EditText user, password, name, lastname;
    RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        data = new BDConnection(this, "systemBD", null, 1);
        user = (EditText) findViewById(R.id.u_user);
        password = (EditText) findViewById(R.id.u_password);
        name = (EditText) findViewById(R.id.u_name);
        lastname = (EditText) findViewById(R.id.u_lastname);
        gender = (RadioGroup) findViewById(R.id.r_group);
    }

    public void registrarUsuario(View view) {
        String sUser, sPassword, sName, sLastName, sGender;
        sUser = "" + user.getText();
        sPassword = "" + password.getText();
        sName = "" + name.getText();
        sLastName = "" + lastname.getText();
        sGender = "";
        switch (gender.getCheckedRadioButtonId()) {
            case (R.id.r_male): {
                sGender += "Hombre";
                break;
            }
            case (R.id.r_female): {
                sGender += "Mujer";
                break;
            }
            case (R.id.r_any): {
                sGender += "Prefiero no decir";
                break;
            }
        }
        //(id INTEGER, nombre TEXT, apellido TEXT, genero TEXT, fecha TEXT)";
        ContentValues userdata = new ContentValues();
        userdata.put("id", getCountDB("users")+1);
        userdata.put("user", sUser);
        userdata.put("password", sPassword);
        userdata.put("name", sName);
        userdata.put("lastname", sLastName);
        userdata.put("gender", sGender);

        SQLiteDatabase base = data.getWritableDatabase();
        long result = base.insert("users", "id", userdata);
        Toast.makeText(this, "¡Te has registrado correctamente ("+sPassword+")!", Toast.LENGTH_SHORT).show();
        finish();
    }
    private int getCountDB(String table){
        SQLiteDatabase dbcant = data.getReadableDatabase();
        return ((int) android.database.DatabaseUtils.queryNumEntries(dbcant,table));
    }
}