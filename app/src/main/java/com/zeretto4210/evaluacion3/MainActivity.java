package com.zeretto4210.evaluacion3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BDConnection data;
    User user;
    TextView userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userdata = (TextView) findViewById(R.id.menulabel);
        data = new BDConnection(this, "systemBD", null, 1);
        user = retrieveLoggedUser();
        if (user != null){
            Toast.makeText(this, "¡Bienvenido, "+user.getName()+"!", Toast.LENGTH_SHORT).show();
            userdata.setText("USUARIO: "+user.getUser());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch(item.getItemId()){
            case R.id.m_login:{
                if (user != null){
                    logout();
                }
                else{
                    i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                }
                return true;
            }
            case R.id.m_user: {
                i = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(i);
                return true;
            }
            case R.id.m_localization: {
                if(user==null){
                    Toast.makeText(this, "¡Primero inicia sesión!", Toast.LENGTH_SHORT).show();
                }
                else{
                    i = new Intent(getApplicationContext(), MarkerRegister.class);
                    i.putExtra("user",user.getUser());
                    startActivity(i);
                }
                return true;
            }
            case R.id.m_show: {
                if(user==null){
                    Toast.makeText(this, "¡Primero inicia sesión!", Toast.LENGTH_SHORT).show();
                }
                else{
                    i = new Intent(getApplicationContext(), MapsActivity.class);
                    i.putExtra("user",user.getUser());
                    startActivity(i);
                }
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    public void logout(){
        SQLiteDatabase base = data.getWritableDatabase();
        base.delete("logged",null, null);
        finish();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public User retrieveLoggedUser() {
        SQLiteDatabase base = data.getReadableDatabase();
        String[] columns = {"id", "user"};
        Cursor c = base.query("logged", columns, null, null, null, null, null);
        try {
            c.moveToFirst();
            if (c.getInt(0) == 1) {
                return (retrieveUser(c.getString(1)));
            }
        } catch (Exception e) {
        }
        return null;
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