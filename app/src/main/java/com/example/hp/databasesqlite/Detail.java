package com.example.hp.databasesqlite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {

    flowerDataSource dataSource;
    TextView tv_name,tv_detail;
    ImageView iv;
    String id;
    String detail;
    String name;

    Flower flower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dataSource = new flowerDataSource(Detail.this);
        dataSource.open();
         name = getIntent().getStringExtra("name");
        detail = getIntent().getStringExtra("detail");
        id= getIntent().getStringExtra("photo");

        flower = new Flower();
        flower.setName(name);
        flower.setDescription(detail);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        iv = (ImageView) findViewById(R.id.iv_student);


        tv_name.setText(name);
        tv_detail.setText(detail);
        int res = getApplication().getResources().getIdentifier(id, "drawable", getApplication().getPackageName());
        iv.setImageResource(res);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.secondmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:

               if(dataSource.addToMytable(flower)){
                   Toast.makeText(Detail.this,"Data inserted",Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(Detail.this,"Data not inserted",Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.show:
        }
        return false;
    }
}
