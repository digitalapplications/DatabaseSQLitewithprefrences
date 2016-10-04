package com.example.hp.databasesqlite;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP on 10/3/2016.
 */
public class customArrayAdapter extends ArrayAdapter<Flower> {

    Context context;
    List<Flower> objects;
    public customArrayAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Flower flower = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.mylayout,null);

        ImageView iv = (ImageView) view.findViewById(R.id.custom_iv);
        int res = context.getResources().getIdentifier(flower.getName()+"", "drawable", context.getPackageName());
        iv.setImageResource(res);

        TextView tv = (TextView) view.findViewById(R.id.custom_tv);
        tv.setText(flower.getName());
        return view;
    }
}
