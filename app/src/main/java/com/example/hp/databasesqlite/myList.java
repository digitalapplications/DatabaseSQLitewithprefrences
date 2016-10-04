package com.example.hp.databasesqlite;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class myList extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    ListView lv;
    List<Flower> data;
    Flower flower1;
    ArrayAdapter<Flower> adapter;
    private SharedPreferences settings;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    flowerDataSource dataSource;
    FlowerDbOpenHelper dbOpenHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        lv = (ListView) findViewById(R.id.mylist);
        lv.setOnItemClickListener(this);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                myList.this.refreshmyData();
            }
        };
        settings.registerOnSharedPreferenceChangeListener(listener);

        dbOpenHelper = new FlowerDbOpenHelper(myList.this);
        dataSource = new flowerDataSource(this);
        dataSource.open();
        refreshmyData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thirdmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete:
                dataSource.deleteRow(flower1);
                refreshmyData();
                break;


        }
        return false;
    }

    public void refreshmyData() {
        boolean viewImages = settings.getBoolean("pref_image",false);
        if(viewImages){
            data = dataSource.showMyflower();
            adapter = new customArrayAdapter(this,android.R.layout.simple_list_item_1,data);
            lv.setAdapter(adapter);
        }
        else{
            adapter = new ArrayAdapter<Flower>(this,android.R.layout.simple_list_item_1,data);
            lv.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        flower1 = data.get(position);
        openOptionsMenu();
    }
}
