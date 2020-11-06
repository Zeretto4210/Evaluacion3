package com.zeretto4210.evaluacion3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDConnection extends SQLiteOpenHelper {
    private final String usersDB = "CREATE TABLE users (id INTEGER, user TEXT, password TEXT, name TEXT, lastname TEXT, gender TEXT)";
    private final String loggedDB = "CREATE TABLE logged (id INTEGER, user TEXT)";
    private final String markersDB = "CREATE TABLE markers (id INTEGER, user TEXT, name TEXT, latitude REAL, longitude REAL)";

    public BDConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(usersDB);
        db.execSQL(loggedDB);
        db.execSQL(markersDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS logged");
        db.execSQL("DROP TABLE IF EXISTS markers");
        onCreate(db);
    }
}