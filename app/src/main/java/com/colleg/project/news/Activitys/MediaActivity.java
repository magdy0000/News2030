package com.colleg.project.news.Activitys;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.colleg.project.news.Fragments.GallaryFragment;
import com.colleg.project.news.Fragments.VideosFragment;
import com.colleg.project.news.R;


public class MediaActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentTransaction transaction;
    Button photos,videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        fragment = new VideosFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MediaFragment, fragment, "Home_Fragment");
        transaction.commitNow();

        photos=findViewById(R.id.Photos);
        videos=findViewById(R.id.Videos);

        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photos.setBackgroundResource(R.color.Blue);
                photos.setTextColor(MediaActivity.this.getResources().getColor(R.color.white));

                videos.setBackgroundResource(R.color.white);
                videos.setTextColor(MediaActivity.this.getResources().getColor(R.color.black));

                fragment = new GallaryFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.MediaFragment, fragment, "Home_Fragment");
                transaction.commitNow();
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videos.setBackgroundResource(R.color.Blue);
                videos.setTextColor(MediaActivity.this.getResources().getColor(R.color.white));

                photos.setBackgroundResource(R.color.white);
                photos.setTextColor(MediaActivity.this.getResources().getColor(R.color.black));

                fragment = new VideosFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.MediaFragment, fragment, "Home_Fragment");
                transaction.commitNow();
            }
        });

    }

}
