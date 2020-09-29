package com.colleg.project.news.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.Activitys.AboutUs;
import com.colleg.project.news.Activitys.CategoryFilter;
import com.colleg.project.news.Activitys.Details;
import com.colleg.project.news.Activitys.Home;
import com.colleg.project.news.Activitys.Search;
import com.colleg.project.news.Adapters.AdapterListViewPhotos;
import com.colleg.project.news.Adapters.AdapterListViewVideos;
import com.colleg.project.news.Models.ModelMediaPhotos;
import com.colleg.project.news.Models.ModelMediaVideos;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {

    List<ModelMediaVideos.AllVideosBean> listGson =new ArrayList<>();

    ListView listVideo;


    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v=  inflater.inflate(R.layout.fragment_videos, container, false);

      listVideo=v.findViewById(R.id.VideosListView);

      Get_Data();

      return v;
    }

    private void Get_Data() {

        AndroidNetworking.get(Url.videos)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelMediaVideos array = gson.fromJson(response.toString(), ModelMediaVideos.class);

                        listGson=array.getAll_videos();

                        listVideo.setAdapter(new AdapterListViewVideos(getContext(),R.layout.item_listview_videos,listGson));
                    }

                    @Override
                    public void onError(ANError anError) {

                        MyUtils.handleError(getContext(), anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }

}


