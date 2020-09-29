package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.ModelOfRejestraion;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class Rejester extends AppCompatActivity {


    EditText userName , email  , password , repassword  ;

    String sUserName   ,sEmail , sPassword , sRepassword ;
    final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejester);
        MyUtils.setLocale(this);
         mySharedPreference.init(this);
        userName = findViewById(R.id.userName);
        email  =findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);











    }


    private void  validationRegisterData (String userName1  , String email1  , String password1  , String repassword1 ){

        if (userName1.equals("")){
            userName.setError("Required");

        }else if (email1.equals("")){
            email.setError("Required");

        }else if(!email1.matches(EMAIL_PATTERN)) {
            email.setError("example@example.com" );
        }

        else if(password1.equals("")){
            password.setError("Required");

        }else if (password1.length() < 8 ){

            password.setError("Password shouldn't less than 8 characters");
        }

        else if(!repassword1.equals(password1)){
            repassword.setError("Not Matched");
        }else {
            onRegisterData(userName1 ,password1  , email1 );

        }


    }


    private void onRegisterData(String name, String password,  String email) {
        JSONObject object = new JSONObject();
        try {
            object.put("user_name", name);
            object.put("email", email);
            object.put("password", password);


        } catch (JSONException e) {
            e.getStackTrace();
        }



        AndroidNetworking.post(Url.rejestration)
                .addJSONObjectBody(object)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {



                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        ModelOfRejestraion resPOJO = gson.fromJson(response.toString(), ModelOfRejestraion.class);


                        String userOBJSTR = gson.toJson(resPOJO.getUser_info());



                        mySharedPreference.setUserOBJ(userOBJSTR);
                        Toast.makeText(Rejester.this, mySharedPreference.getUserOBJ()+"", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Rejester.this  , Home.class));
                        finish();

                    }

                    @Override
                    public void onError(ANError anError) {

                        MyUtils.handleError(Rejester.this , anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }

    public void rejest(View view) {
        sUserName = userName.getText().toString();
        sEmail = email.getText().toString();
        sPassword = password.getText().toString();
        sRepassword = repassword.getText().toString();
        validationRegisterData(sUserName , sEmail , sPassword , sRepassword);
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Rejester.this  , Login.class));
        finish();
    }
}
