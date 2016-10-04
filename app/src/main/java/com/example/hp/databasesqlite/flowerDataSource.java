package com.example.hp.databasesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 9/30/2016.
 */
public class flowerDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    Context context;

    public flowerDataSource(Context context){

        this.context = context;
        dbhelper = new FlowerDbOpenHelper(context);
    }

    public void open() {
        Log.d("zma","Database opend");
        database  = dbhelper.getWritableDatabase();
    }
    public void close(){

        Log.d("zma", "Database closed");
        dbhelper.close();
    }

    public void create(Flower flower){
        ContentValues cv = new ContentValues();
        cv.put("name", flower.getName());
        cv.put("description", flower.getDescription());
        long insert = database.insert("flower",null,cv);
        Log.d("data", "Data inserted" + flower.getName() + " " + flower.getDescription());

    }

    public List<Flower> retreiveData()
    {

        Cursor cursor = database.rawQuery("select * from flower",null);
        List<Flower> flowerList = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                Flower flower = new Flower();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description =cursor.getString(cursor.getColumnIndex("description"));
                Log.d("name", name + " " + description);
                flower.setName(name);
                flower.setDescription(description);
                flowerList.add(flower);
            }
        }
        return flowerList;
    }

    public List<Flower> retreiveData(String condition)
    {
        Cursor cursor = database.rawQuery("select * from flower where name = '"+condition+"'",null);
        List<Flower> flowerList = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                Flower flower = new Flower();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description =cursor.getString(cursor.getColumnIndex("description"));
                Log.d("name", name + " " + description);
                flower.setName(name);
                flower.setDescription(description);
                flowerList.add(flower);
            }
        }
        return flowerList;
    }

    public boolean addToMytable(Flower flower){
        ContentValues cv = new ContentValues();
        cv.put("name",flower.getName());
        cv.put("description", flower.getDescription());
        long inset = database.insert("mytable", null, cv);
       Log.d("insert tag ",inset+"");
       return (inset != -1);
    }

    public List<Flower> showMyflower(){

        Cursor cursor = database.rawQuery("select * from mytable",null);
        List<Flower> flowerList = new ArrayList<>();
        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()){
                Flower flower = new Flower();
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description =cursor.getString(cursor.getColumnIndex("description"));
                Log.d("name", name + " " + description);
                flower.setName(name);
                flower.setDescription(description);
                flowerList.add(flower);
            }
        }
        return flowerList;
    }

    public void deleteRow(Flower flower){
        String sql = "delete from mytable where name = '"+flower.getName()+"'";
        database.execSQL(sql);
    }

}
