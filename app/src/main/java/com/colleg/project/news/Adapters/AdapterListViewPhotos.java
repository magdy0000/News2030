package com.colleg.project.news.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.colleg.project.news.Models.ModelMediaPhotos;
import com.colleg.project.news.R;
import java.util.List;

public class AdapterListViewPhotos extends ArrayAdapter {



       List<ModelMediaPhotos.AllPhotosBean> mlist;
       Context mContext ;

        public AdapterListViewPhotos(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);

            mlist = objects;
            mContext = context ;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_listview_photos, parent,false);


            ImageView images = convertView.findViewById(R.id.picture);

            Glide.with(mContext).load(mlist.get(position).getPhoto()).into(images);




            return convertView;
        }

}
