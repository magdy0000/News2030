package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.Adapters.AdapterOfSearch;
import com.colleg.project.news.Models.ModelOfSearchResult;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
   EditText searchEdit  ;
   ListView resultList;
   List<ModelOfSearchResult.PostsBean> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEdit = findViewById(R.id.searchEditText);
        resultList = findViewById(R.id.searchResult);

        MyUtils.setLocale(this);


        searchListener();
        onClick();




    }


    private void onClick(){

        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyUtils.CategoryTittle = list.get(position).getCategory_post();
                MyUtils.PostID  = String.valueOf(list.get(position).getPost_id());
                Intent i = new Intent(Search.this,Details.class);
                startActivity(i);
                finish();

            }
        });

    }


   private  void  searchListener (){

       searchEdit.addTextChangedListener(new TextWatcher() {

           public void afterTextChanged(Editable s) {



               getDataResult(searchEdit.getText().toString().trim());




           }

           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

           public void onTextChanged(CharSequence s, int start, int before, int count) {}
       });

   }
    

    private void getDataResult(String keyword) {
        JSONObject object = new JSONObject();
        try {
            object.put("keyword", keyword);



        } catch (JSONException e) {
            e.getStackTrace();
        }



        AndroidNetworking.post(Url.search)
                .addJSONObjectBody(object)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                         ModelOfSearchResult data = gson.fromJson(response.toString(), ModelOfSearchResult.class);
                         list  = data.getPosts();

                        resultList.setAdapter(new AdapterOfSearch(Search.this,R.layout.item_of_searh,list));






                    }

                    @Override
                    public void onError(ANError anError) {


                        MyUtils.handleError(Search.this , anError.getErrorBody() , anError.getErrorCode());
                        list.clear();
                        resultList.setAdapter(new AdapterOfSearch(Search.this,R.layout.item_of_searh,list));

                    }
                });
    }
}
