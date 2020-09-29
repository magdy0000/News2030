package com.colleg.project.news.Fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.colleg.project.news.Activitys.Details;
import com.colleg.project.news.Adapters.AdapterListViewHome;
import com.colleg.project.news.Adapters.CustomPagerAdapterAcc;
import com.colleg.project.news.Adapters.CustomPagerAdapterSports;
import com.colleg.project.news.Adapters.CustomPagerAdapterTran;
import com.colleg.project.news.Adapters.CustomPagerAdapterfifth;
import com.colleg.project.news.Adapters.CustomPagerAdapterfourth;
import com.colleg.project.news.Adapters.CustomPagerAdapterseventh;
import com.colleg.project.news.Adapters.CustomPagerAdaptersixith;
import com.colleg.project.news.Models.GsonForHome;
import com.colleg.project.news.Models.ModelListViewHome;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


public class HomeFragment extends Fragment {

    String []dis = {"الطيب : اطلس الاوقاف يضم 25 مليون مستند","الطيب : اطلس الاوقاف يضم 25 مليون مستند","الطيب : اطلس الاوقاف يضم 25 مليون مستند"};
    String []time = {"2019 jun 7 09:12" , "2019 jun 7 09:12", "2019 jun 7 09:12"};
//    int []image = {R.drawable.sheekh,R.drawable.tramp,R.drawable.news};
    ListView listView ;
    ArrayList<ModelListViewHome> list = new ArrayList<>();
    List<GsonForHome.NewsBean> listGson =new ArrayList<>();

    ViewPager viewPagerAcc, viewPagerSports ,viewPagerTran , viewPagerfourth , viewPagerfifth , viewPagersexith , viewPagerseventh ;
    AdapterListViewHome adapter ;


    public HomeFragment() {
           }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);

        Get_Data();


        viewPagerAcc = v.findViewById(R.id.viewpager_accidents);
        TabLayout tabLayoutacc = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_accidents);
        tabLayoutacc.setupWithViewPager(viewPagerAcc, true);




        viewPagerTran = v.findViewById(R.id.viewpager_Investigations);
        TabLayout tabLayoutTran = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_Investigations);
        tabLayoutTran.setupWithViewPager(viewPagerTran, true);


        viewPagerSports = v.findViewById(R.id.viewpager_sports);
        TabLayout tabLayoutsport = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_sports);
        tabLayoutsport.setupWithViewPager(viewPagerSports, true);


        viewPagerfourth= v.findViewById(R.id.viewpager_fourth);
        TabLayout tabLayout4 = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_fourth);
        tabLayout4.setupWithViewPager(viewPagerfourth, true);


        viewPagerfifth = v.findViewById(R.id.viewpager_fifth);
        TabLayout tabLayout5 = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_fifth);
        tabLayout5.setupWithViewPager(viewPagerfifth, true);


        viewPagersexith = v.findViewById(R.id.viewpager_sixith);
        TabLayout tabLayout6 = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_sixith);
        tabLayout6.setupWithViewPager(viewPagersexith, true);


        viewPagerseventh = v.findViewById(R.id.viewpager_seventh);
        TabLayout tabLayout7 = (TabLayout) v.findViewById(R.id.tab_layout_viewpager_seventh);
        tabLayout7.setupWithViewPager(viewPagerseventh, true);

//......................  List view ................................................................


        listView = v.findViewById(R.id.homeListview);
        adapter = new AdapterListViewHome(getContext(), R.layout.item_listview_home, list);
        listView.setAdapter(adapter);
        ListViewClick();
        setListViewHeightBasedOnChildren(listView);


        return v;
    }


    private void ListViewClick() {

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent go=new Intent(getContext(), Details.class);
                    startActivity(go);
                }

                else if (position == 1) {
                    Intent go=new Intent(getContext(), Details.class);
                    startActivity(go);
                }
            }
        });

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;



        View.MeasureSpec m = new View.MeasureSpec();


        int desiredWidth = m.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    private void Get_Data() {

        AndroidNetworking.get(Url.home)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        GsonForHome array = gson.fromJson(response.toString(), GsonForHome.class);

                           listGson = array.getNews();


                       viewPagerAcc.setAdapter(new CustomPagerAdapterAcc(getContext(),listGson));
                       viewPagerSports.setAdapter(new CustomPagerAdapterSports(getContext(),listGson));
                       viewPagerTran.setAdapter(new CustomPagerAdapterTran(getContext(),listGson));
                       viewPagerfourth.setAdapter(new CustomPagerAdapterfourth(getContext(),listGson));
                       viewPagerfifth.setAdapter(new CustomPagerAdapterfifth(getContext(),listGson));
                       viewPagersexith.setAdapter(new CustomPagerAdaptersixith(getContext(),listGson));
                       viewPagerseventh.setAdapter(new CustomPagerAdapterseventh(getContext(),listGson));



                    }

                    @Override
                    public void onError(ANError anError) {

                     MyUtils.handleError(getActivity() , anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }


}
