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

public class AdapterListViewHome extends ArrayAdapter {



        ArrayList<ModelListViewHome> mlist;

        public AdapterListViewHome(@NonNull Context context, int resource, @NonNull ArrayList objects) {
            super(context, resource, objects);

            mlist = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_listview_home, parent,false);

            TextView textdisc = convertView.findViewById(R.id.text_listview);
            TextView textTime = convertView.findViewById(R.id.time_listview);
            ImageView images = convertView.findViewById(R.id.image_listview);


            textdisc.setText(mlist.get(position).textdis);
            textTime.setText(mlist.get(position).texttime);
            images.setImageResource(mlist.get(position).image);


            return convertView;
        }

}
