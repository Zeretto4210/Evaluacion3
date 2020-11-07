package com.zeretto4210.evaluacion3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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
        user = (EditText) findViewById(R.id.l_user);
        password = (EditText) findViewById(R.id.l_password);
    }
    public void loginUser(View view){
        String sUser = ""+user.getText();
        String sPassword = ""+password.getText();
        User u = retrieveUser(sUser);
        if(u != null){
            if (sPassword.equals(u.getPassword())){
                ContentValues userdata = new ContentValues();
                userdata.put("id",1);
                userdata.put("user",sUser);
                SQLiteDatabase base = data.getWritableDatabase();
                long result = base.insert("logged","id",userdata);
                Toast.makeText(this, "¡Sesión iniciada correctamente!", Toast.LENGTH_SHORT).show();
                finish();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            else{
                Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show();
        }
    }
    public User retrieveUser(String user) {
        SQLiteDatabase base = data.getReadableDatabase();
        String[] columns = {"id", "user", "password", "name", "lastname", "gender"};
        Cursor c = base.query("users", columns, "user=?", new String[]{user}, null, null, null);
        while(c.moveToNext()){
            if (c.getString(1).equals(user)) {
                return (new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));
            }
        }
        return null;
    }
}