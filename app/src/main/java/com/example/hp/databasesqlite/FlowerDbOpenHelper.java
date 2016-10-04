package com.example.hp.databasesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HP on 9/30/2016.
 */
public class FlowerDbOpenHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG= "zma";
    private static final String DATABASE_NAME = "flowers";
    private static final int DATABASE_VERSION = 3;


    private static final String TABLE_CREATE =  "create table if not exists flower (name varchar(20),description varchar(20))";
    private static final String MYTABLE = "create table if not exists mytable(name varchar(20) primary key,description varchar(20))";

    public FlowerDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        db.execSQL(MYTABLE);
        Log.d(LOG_TAG,"Table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS flower");
        db.execSQL("DROP TABLE IF EXISTS mytable");
        onCreate(db);
    }
}
