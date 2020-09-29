package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.colleg.project.news.R;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }


    public void openTwitter(View view) {


        Intent intent = null;
        try {
            // get the Twitter app if possible
            AboutUs.this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/2030News2"));

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/2030News2"));
        }
        AboutUs.this.startActivity(intent);
    }

    public void openfacebook(View view) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/2030-492918314787192/?modal=admin_todo_tour"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/2030-492918314787192/?modal=admin_todo_tour")));
        }

    }

    public void openinsta(View view) {
        try {


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.instagram.com/newssite2030_/"));
            startActivity(intent);
        } catch (Exception o) {

        }
    }

    public void openyoutube(View view) {

        try {


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://m.youtube.com/channel/UCsJyL-UJkUyBeT1VAjjyAAQ"));
            startActivity(intent);
        } catch (Exception o) {
//            Toast.makeText(this,youtubeUrl+ "", Toast.LENGTH_SHORT).show();
        }
    }

    public void opengoogle(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + ""));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "Newssite2030@gmail.com");
        startActivity(emailIntent);
    }

}
