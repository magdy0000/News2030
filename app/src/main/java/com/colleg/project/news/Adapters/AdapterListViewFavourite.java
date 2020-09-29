package com.colleg.project.news.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.ModelListViewFavourite;
import com.colleg.project.news.Models.ModelOfAllFavourite;
import com.colleg.project.news.Models.ModelOfRejestraion;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdapterListViewFavourite extends ArrayAdapter {



    List<ModelOfAllFavourite.FavoritesBean>list = new ArrayList<>();

    Context context;
    boolean c=false;
    int resource;

    public AdapterListViewFavourite(Context context, int resource, List objects) {
        super(context , resource , objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_listview_favourite, null);

        ImageView imageView = view.findViewById(R.id.listviewFavPic);
        TextView textViewtitle = view.findViewById(R.id.title_listview);
        TextView textViewsubject = view.findViewById(R.id.subject_list_fav);
        ImageView buttonDelete = view.findViewById(R.id.unsave_in_listview);



        Glide.with(context).load(list.get(position).getPost_img()).into(imageView);
        textViewsubject.setText(list.get(position).getCategoty_name());

        textViewtitle.setText(list.get(position).getPost_title());





        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeHero(position);
            }
        });


        return view;
    }

    private void removeHero(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeFormFavourite(MyUtils.userId() , list.get(position).getPost_id());
                list.remove(position);
                notifyDataSetChanged();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void removeFormFavourite(int userId  , int postId) {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userId);
            object.put("post_id" , postId);



        } catch (JSONException e) {
            e.getStackTrace();
        }



        AndroidNetworking.post("https://cizaro.net/2030/api/favorite")
                .addJSONObjectBody(object)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                        MyUtils.handleError(getContext() , anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }

}