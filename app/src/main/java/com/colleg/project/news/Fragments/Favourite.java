package com.colleg.project.news.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.Activitys.Details;
import com.colleg.project.news.Activitys.Home;
import com.colleg.project.news.Activitys.Rejester;
import com.colleg.project.news.Adapters.AdapterListViewFavourite;
import com.colleg.project.news.Adapters.AdapterListViewHome;
import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.ModelListViewFavourite;
import com.colleg.project.news.Models.ModelListViewHome;
import com.colleg.project.news.Models.ModelOfAllFavourite;
import com.colleg.project.news.Models.ModelOfRejestraion;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favourite extends Fragment {



    ListView listView ;
    List<ModelOfAllFavourite.FavoritesBean> list = new ArrayList<>();


    public Favourite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);




        listView=view.findViewById(R.id.FavouriteListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyUtils.PostID  = String.valueOf(list.get(position).getPost_id());
                MyUtils.CategoryTittle = list.get(position).getCategoty_name();
                Intent i = new Intent(getActivity(),Details.class);
                startActivity(i);

            }
        });



        getData(MyUtils.userId());


        return view;
    }


    private void getData(int id ) {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", id);



        } catch (JSONException e) {
            e.getStackTrace();
        }



        AndroidNetworking.post(Url.favourite)
                .addJSONObjectBody(object)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelOfAllFavourite data = gson.fromJson(response.toString(), ModelOfAllFavourite.class);

                        list = data.getFavorites();



                        listView.setAdapter(new AdapterListViewFavourite(getContext(),R.layout.item_listview_favourite,list));




                    }

                    @Override
                    public void onError(ANError anError) {

                        MyUtils.handleError(getActivity() , anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }
//kjn

}
