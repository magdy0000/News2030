package com.colleg.project.news.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.Adapters.AdapterListViewPhotos;
import com.colleg.project.news.Models.ModelMediaPhotos;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;



/**
 * A simple {@link Fragment} subclass.
 */
public class GallaryFragment extends Fragment {

    List<ModelMediaPhotos.AllPhotosBean> listGson =new ArrayList<>();

    ListView listphotos;

    public GallaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_gallary, container, false);

        listphotos=v.findViewById(R.id.GalleryListView);
        Get_Data();
       return v;
    }

    private void Get_Data() {

        AndroidNetworking.get(Url.photos)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelMediaPhotos array = gson.fromJson(response.toString(), ModelMediaPhotos.class);

                        listGson=array.getAll_photos();

                        listphotos.setAdapter(new AdapterListViewPhotos(getContext(),R.layout.item_listview_photos,listGson));

                    }

                    @Override
                    public void onError(ANError anError) {

                        MyUtils.handleError(getContext(), anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }

}
