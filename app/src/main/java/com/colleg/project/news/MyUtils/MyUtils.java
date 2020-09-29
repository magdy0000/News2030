package com.colleg.project.news.MyUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.ErrorModel;
import com.colleg.project.news.Models.ModelOfRejestraion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

/**
 * Created by aswany on 3/3/19.
 */

public class MyUtils {

    public static String PostID;
    public static String CategoryTittle;


    public static void handleError(Context context, String errorRes, int errorStatusCode) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ErrorModel error = gson.fromJson(errorRes, ErrorModel.class);


        Toast.makeText(context, error.getMsg(), Toast.LENGTH_SHORT).show();


    }

    public static int userId() {
        Gson gson = new Gson();
        ModelOfRejestraion.UserInfoBean id = gson.fromJson( mySharedPreference.getUserOBJ() , ModelOfRejestraion.UserInfoBean.class);




      return  id.getUserId();
    }

    public static String userName() {
        Gson gson = new Gson();
        ModelOfRejestraion.UserInfoBean id = gson.fromJson( mySharedPreference.getUserOBJ() , ModelOfRejestraion.UserInfoBean.class);

        return  id.getUsername();
    }

    public static String userMail(){

        Gson gson = new Gson();
        ModelOfRejestraion.UserInfoBean mail = gson.fromJson( mySharedPreference.getUserOBJ() , ModelOfRejestraion.UserInfoBean.class);



     return mail.getEmail();
    }

    public static Boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }
    public static void setLocale(Context context) {

        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());


    }
//    public static Dialog LoadingDialog(final Context myContext) {
//        final Dialog dialog = new Dialog(myContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setContentView(R.layout.dialog_loading);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
////        final RelativeLayout loadingDialog_rl = dialog.findViewById(R.id.loadingDialog_rl);
////
////        loadingDialog_rl.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dialog.dismiss();
////            }
////        });
//
//
//        return dialog;
//    }
}
