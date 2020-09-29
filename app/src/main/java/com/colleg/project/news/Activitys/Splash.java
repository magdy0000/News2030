package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.R;

public class Splash extends AppCompatActivity {
   ImageView imageView  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.img) ;



        mySharedPreference.init(this);



    }


    public void Timer() {
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                   checkLoggedInUser();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timer();
    }

    private void checkLoggedInUser() {
        if(!mySharedPreference.getUserAdmin().equals("")){


            startActivity(new Intent(Splash.this, Admin.class));
            finish();


        }else {


            if (mySharedPreference.getUserOBJ().equals("")) {
                //user obj not available
                startActivity(new Intent(Splash.this, Login.class));
                finish();
            } else {
                //user obj available
                startActivity(new Intent(Splash.this, Home.class));
                finish();
            }

        }
    }
    }

