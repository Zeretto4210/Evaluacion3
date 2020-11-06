package com.zeretto4210.evaluacion3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText user, password;
    BDConnection data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        data = new BDConnection(this, "systemBD", null, 1);
        user = (EditText) findViewById(R.id.u_user);
        password = (EditText) findViewById(R.id.u_password);
    }
    public void login(View view){
        String sUser = ""+user.getText();
        String sPassword = ""+password.getText();
        User u = retrieveUser(sUser);
        if(user != null){
            ContentValues userdata = new ContentValues();
            userdata.put("id",1);
            userdata.put("user",sUser);
            SQLiteDatabase base = data.getWritableDatabase();
            long result = base.insert("logged","id",userdata);
            Toast.makeText(this, "¡Sesión iniciada correctamente!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public User retrieveUser(String user) {
        SQLiteDatabase base = data.getReadableDatabase();
        String[] columns = {"id", "user", "password", "name", "lastname", "gender"};
        Cursor c = base.query("usuario", columns, "user =?", new String[]{user}, null, null, null);
        try {
            c.moveToFirst();
            if (c.getString(1) == user) {
                return (new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            }
        } catch (Exception e) {
        }
        return null;
    }
}