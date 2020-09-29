package com.colleg.project.news.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colleg.project.news.Models.ModelListViewHome;
import com.colleg.project.news.R;

import java.util.ArrayList;

public class AdapterOfNavList extends ArrayAdapter {


   String [] text  ;
    public AdapterOfNavList(@NonNull Context context, int resource, @NonNull String [] objects) {
        super(context, resource, objects);

        text = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.item_nav_list, parent,false);

        TextView textView = convertView.findViewById(R.id.mtext);

        textView.setText(text[position]);








        return convertView;
    }

}
