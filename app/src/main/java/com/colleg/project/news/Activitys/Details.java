package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.colleg.project.news.Adapters.CustomPagerAdapterAcc;
import com.colleg.project.news.Models.GsonForDetails;
import com.colleg.project.news.Models.GsonForHome;
import com.colleg.project.news.Models.ModelHandleFavourite;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {

    ImageView image;
    TextView TitleNews , Ldis , SDis, tittle;
    ImageView more_acc;
    LinearLayout save;
    boolean c=false ;

    ScrollView parent  ;
    CircleImageView shareButton ;

    ProgressBar progressBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MyUtils.setLocale(this);
        image=findViewById(R.id.image_details);
        TitleNews=findViewById(R.id.title_details);
        Ldis=findViewById(R.id.dis_details);
        SDis=findViewById(R.id.more_details);
        tittle =findViewById(R.id.tittle);
        parent = findViewById(R.id.parent);
        progressBar = findViewById(R.id.progress_bar);
        shareButton=findViewById(R.id.ShareButton);




        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        save = findViewById(R.id.save_Btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavourite(MyUtils.userId(),MyUtils.PostID);
            }
        });


        initiateData();
    }



    private void initiateData() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", MyUtils.PostID);
            object.put("user_id", MyUtils.userId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(Url.details)
                .addJSONObjectBody(object)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        shareButton.setVisibility(View.VISIBLE);

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        GsonForDetails array = gson.fromJson(response.toString(), GsonForDetails.class);

                        Glide.with(Details.this).load(array.getPost_img()).into(image);

                        TitleNews.setText(array.getPost_title());
                        Ldis.setText(array.getLong_description());
                        SDis.setText(array.getShort_description());

                        tittle.setText(MyUtils.CategoryTittle);


                        if (array.getFavorite().equals("false")){
                             save.setBackgroundColor(getResources().getColor(R.color.textColorOfLogin));

                        }else {
                            save.setBackgroundColor(getResources().getColor(R.color.Blue));


                        }




                    }

                    @Override
                    public void onError(ANError anError) {
                       MyUtils.handleError(Details.this , anError.getErrorBody() , anError.getErrorCode());
                       progressBar.setVisibility(View.GONE);

                    }
                });

    }

    private void addToFavourite(int userId  , String postId){

        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userId);
            object.put("post_id" , postId);



        } catch (JSONException e) {
            e.getStackTrace();
        }



        AndroidNetworking.post(Url.addToFavourite)
                .addJSONObjectBody(object)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        ModelHandleFavourite array = gson.fromJson(response.toString(), ModelHandleFavourite.class);


                      char x = array.getMsg().charAt(1);
                      if (x == 'e'){


                          save.setBackgroundColor(getResources().getColor(R.color.textColorOfLogin));

                      }else {

                          save.setBackgroundColor(getResources().getColor(R.color.Blue));


                      }



                    }

                    @Override
                    public void onError(ANError anError) {

                        MyUtils.handleError(Details.this , anError.getErrorBody() , anError.getErrorCode());
                    }
                });


    }



}
