package com.colleg.project.news.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.Adapters.AdapterOfNavList;
import com.colleg.project.news.Fragments.Favourite;
import com.colleg.project.news.Fragments.HomeFragment;
import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.GsonForHome;
import com.colleg.project.news.Models.ModelOfSurvey;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private   Fragment fragment;
    private   FragmentTransaction transaction;
    private   ListView listView  ;
    private   List<GsonForHome.NewsBean> listGson =new ArrayList<>();
    private   String [] arrayOfnav ;
    private   Button search ;
    private   ImageView HomeIcon , FavIcon , GallaryIcon;
    private   TextView userNameOfNav ;
    private   TextView goAbout ;
    private   DatabaseReference ref  = FirebaseDatabase.getInstance().getReference() ;
    public static int categoryId ;
    private   boolean home = false , gallary = true , favourite = true ;
    private   long backPressedTime  ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_in_our_app);

        mySharedPreference.init(this);
          showDialogOfSurvy();
         definitions();

         navFunction();
         Get_Data();
         firstFragmentRun();
         onClickOnItemsForNavList();






    }

    private void definitions (){
        goAbout = findViewById(R.id.goAboutUs);
        listView  =findViewById(R.id.items_listView_for_navigation);
        search = findViewById(R.id.searchBtn);
        HomeIcon=findViewById(R.id.home_icon_id);
        FavIcon=findViewById(R.id.favert_icon_id);
        GallaryIcon=findViewById(R.id.Media_icon_id);
        userNameOfNav = findViewById(R.id.profile_name1);

    }

    private void onClickOnItemsForNavList(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyUtils.CategoryTittle = listGson.get(position).getCategory_title() ;
                categoryId   = listGson.get(position).getCategory_id() ;

                startActivity(new Intent(Home.this , CategoryFilter.class));



            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Search.class));

            }
        });


        goAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(Home.this, AboutUs.class);
                startActivity(go);
            }
        });




    }

    private void firstFragmentRun(){
        fragment = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, fragment, "Home_Fragment");
        transaction.commitNow();

        GallaryIcon.setImageResource(R.drawable.gallary_black);

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



                        dataForNav();


                    }

                    @Override
                    public void onError(ANError anError) {


                        MyUtils.handleError(Home.this , anError.getErrorBody() , anError.getErrorCode());

                    }
                });
    }

    private void dataForNav(){
        arrayOfnav = new String[listGson.size()];

        for(int x = 0 ; x <listGson.size() ; x++){



            arrayOfnav [x]  = listGson.get(x).getCategory_title() ;
        }


        AdapterOfNavList arrayAdapter  = new AdapterOfNavList(this ,R.layout.item_nav_list,arrayOfnav);

        listView.setAdapter(arrayAdapter);
        setListViewHeightBasedOnChildren(listView);

        userNameOfNav.setText(MyUtils.userName());



    }

    public void favourite(View view) {

        if(favourite){




        fragment = new Favourite();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, fragment, "favourite");
        transaction.commitNow();

        HomeIcon.setImageResource(R.drawable.home_black);
        FavIcon.setImageResource(R.drawable.love_blue);
        GallaryIcon.setImageResource(R.drawable.gallary_black);

        home = true ;
        gallary=true;
        favourite = false;

        }
    }

    public void Gallary(View view) {

        if(gallary){

        Intent i =new Intent(Home.this,MediaActivity.class);
        startActivity(i);

        }
    }

    public void logout(View view) {



        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle("Are you sure you want to logout?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mySharedPreference.setUserOBJ("");

                Intent go =new Intent(Home.this,Login.class);
                startActivity(go);
                finish();
            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fragment = new HomeFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.FragmentLayout, fragment, "Home_Fragment");
                transaction.commitNow();
                HomeIcon.setImageResource(R.drawable.home_blue);
                FavIcon.setImageResource(R.drawable.love_black);
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void home(View view) {

        if (home){

        fragment = new HomeFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FragmentLayout, fragment, "Home_Fragment");
        transaction.commitNow();

        HomeIcon.setImageResource(R.drawable.home_blue);
        FavIcon.setImageResource(R.drawable.love_black);
        GallaryIcon.setImageResource(R.drawable.gallary_black);

        home= false ;
        favourite =true  ;
        gallary=true;

        }

    }

    private void navFunction(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.nav);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { drawer.openDrawer(Gravity.RIGHT); } });



    }

    @Override
    public void onBackPressed() {

        if (!fragment.getTag().equals("Home_Fragment")) {
            fragment = new HomeFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.FragmentLayout, fragment, "Home_Fragment");
            transaction.commitNow();
            HomeIcon.setImageResource(R.drawable.home_blue);
            FavIcon.setImageResource(R.drawable.love_black);
            home= false ;
            favourite =true  ;
        }else {


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {

                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    finishAffinity();
                } else {
                    Toast.makeText(this, "press again to exit ", Toast.LENGTH_SHORT).show();
                }

                backPressedTime = System.currentTimeMillis();
            }

        }
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

    private void showDialogOfSurvy(){

        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View view = layoutInflater.inflate(R.layout.customdialoge, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        final EditText editText = view.findViewById(R.id.editTextOfAnswer);
        final LinearLayout parent = view.findViewById(R.id.parentOfDialog);
        Button submit = view.findViewById(R.id.submit_btn);
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar_dialog);
        

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                parent.setVisibility(View.GONE);

                ModelOfSurvey model = new ModelOfSurvey();
                model.setAnswer(editText.getText().toString().trim());
                model.setUserEmail(MyUtils.userMail());
                model.setUserName(MyUtils.userName());

                ref.child("survey").child(String.valueOf(MyUtils.userId())).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        alertD.cancel();
                        
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        progressBar.setVisibility(View.GONE);
                        parent.setVisibility(View.VISIBLE);
                        Toast.makeText(Home.this, "Connection Error", Toast.LENGTH_SHORT).show();
                        
                    }
                });

            }
        });

        alertD.setCancelable(true);

        alertD.setView(view);

        alertD.show();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();






        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
