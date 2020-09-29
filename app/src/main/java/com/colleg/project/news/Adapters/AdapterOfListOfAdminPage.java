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

import com.bumptech.glide.Glide;
import com.colleg.project.news.Models.ModelOfSearchResult;
import com.colleg.project.news.Models.ModelOfSurvey;
import com.colleg.project.news.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterOfListOfAdminPage extends ArrayAdapter {



    ArrayList<ModelOfSurvey> mlist  ;
    public AdapterOfListOfAdminPage(@NonNull Context context, int resource, @NonNull ArrayList list) {
        super(context, resource, list);

        mlist = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = layoutInflater.inflate(R.layout.item_of_admin_list, parent,false);

        TextView name = convertView.findViewById(R.id.userName1);
        TextView mail = convertView.findViewById(R.id.mail1);
        TextView answer = convertView.findViewById(R.id.answer);


        name.setText(mlist.get(position).getUserName());
        mail.setText(mlist.get(position).getUserEmail());
        answer.setText(mlist.get(position).getAnswer());



        return convertView;
    }
}
