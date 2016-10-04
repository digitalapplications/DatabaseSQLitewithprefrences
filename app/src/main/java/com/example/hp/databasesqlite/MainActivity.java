package com.example.hp.databasesqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    flowerDataSource dataSource;
    FlowerDbOpenHelper dbOpenHelper;
    List<Flower> data;
    ListView lv;
    Flower flower1;
    ArrayAdapter<Flower> adapter;

    private SharedPreferences settings;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                MainActivity.this.refreshData();
            }
        };
        settings.registerOnSharedPreferenceChangeListener(listener);

        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);
        registerForContextMenu(lv);

        dbOpenHelper = new FlowerDbOpenHelper(MainActivity.this);
        dataSource = new flowerDataSource(this);
        dataSource.open();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void createData()
    {
//        Flower flower = new Flower();
//        flower.setName("Rose");
//        flower.setDescription("Beautiful flower");
//        dataSource.create(flower);
//        Log.d("zma","Data inserted");

        //retreiving data from XML FILE
        FlowerXMLParser parser = new FlowerXMLParser();
        List<Flower> list = parser.parseFeed(MainActivity.this);
        for (Flower flower : list) {
            dataSource.create(flower);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()){
            case R.id.delete:
                adapter.remove(data.get(position));
                refreshmyData();
                break;

        }
        return super.onContextItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.rose:
                data = dataSource.retreiveData("rose");
                refreshData("rose");
                break;

            case R.id.lilly:

                data = dataSource.retreiveData("lilly");
                refreshData("lilly");

                break;

            case R.id.chrysanthemum:
                data = dataSource.retreiveData("chrysanthemum");
                refreshData("chrysanthemum");
                break;

            case R.id.setting:
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
                refreshData();
                break;
            case R.id.show:
//                data = dataSource.showMyflower();
//                refreshmyData();
                Intent intent2 = new Intent(MainActivity.this,myList.class);
                startActivity(intent2);
            default:
                break;
        }

        return false;
    }

    public void refreshData() {
        boolean viewImages = settings.getBoolean("pref_image",false);
        if(viewImages){
            data = dataSource.retreiveData();
            if(data.size()==0){
                createData();
                ArrayAdapter<Flower> adapter = new customArrayAdapter(this,android.R.layout.simple_list_item_1,data);
                lv.setAdapter(adapter);
            }else{
                ArrayAdapter<Flower> adapter = new customArrayAdapter(this,android.R.layout.simple_list_item_1,data);
                lv.setAdapter(adapter);
            }
        }
        else{
            ArrayAdapter<Flower> adapter = new ArrayAdapter<Flower>(this,android.R.layout.simple_list_item_1,data);
            lv.setAdapter(adapter);
        }
    }

    public void refreshData(String type) {
        boolean viewImages = settings.getBoolean("pref_image",false);
        if(viewImages){
            data = dataSource.retreiveData(type);
          adapter   = new customArrayAdapter(this,android.R.layout.simple_list_item_1,data);
            lv.setAdapter(adapter);
        }
        else{
            adapter = new ArrayAdapter<Flower>(this,android.R.layout.simple_list_item_1,data);
            lv.setAdapter(adapter);
        }
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
         flower1= data.get(position);
        Intent intent = new Intent(MainActivity.this,Detail.class);
        intent.putExtra("name",flower1.getName());
        intent.putExtra("detail",flower1.getDescription());
        intent.putExtra("photo",flower1.getName());
        startActivity(intent);
    }


}
