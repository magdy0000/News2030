package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import com.colleg.project.news.Adapters.AdapterOfListOfAdminPage;
import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.ModelOfSurvey;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    ListView listView  ;
    ArrayList<ModelOfSurvey> list = new ArrayList<>();

     AdapterOfListOfAdminPage adapter  ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listView = findViewById(R.id.adminList);

        MyUtils.setLocale(this);

        getData();

        adapter= new AdapterOfListOfAdminPage(this , 0  , list);
        listView.setAdapter(adapter);

    }

    private void getData (){


        ref.child("survey").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    list.add(dataSnapshot1.getValue(ModelOfSurvey.class));

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    public void log(View view) {

        mySharedPreference.setUserAdmin("");
        startActivity(new Intent(Admin.this, Login.class));
        finish();
    }
}
