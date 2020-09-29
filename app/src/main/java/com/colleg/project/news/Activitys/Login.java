package com.colleg.project.news.Activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.colleg.project.news.InternalStorage.mySharedPreference;
import com.colleg.project.news.Models.ErrorModel;
import com.colleg.project.news.Models.ModelOfRejestraion;
import com.colleg.project.news.MyUtils.MyUtils;
import com.colleg.project.news.MyUtils.Url;
import com.colleg.project.news.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Login extends AppCompatActivity {
    private Button loginWithGoogle ;
    private  Button loginWithFaceBook ;
    private int RC_SIGN_IN = 123;
    private ProgressBar progressBar;
    private LinearLayout parent ;
    private CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String sEmail , sPassword ;

    private EditText editEmail , editpassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         mySharedPreference.init(this);

        MyUtils.setLocale(this);

        printKeyHash();
        definitions();
        googleToken();
        facebookToken();
        onClick();






    }

    private void onClick(){
        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        loginWithFaceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "email"));
            }
        });
    }


    private void definitions(){
        parent = findViewById(R.id.parentLogin);
        progressBar = findViewById(R.id.progress_bar_login);
        editEmail = findViewById(R.id.editTextEmailForLogin);
        editpassword = findViewById(R.id.editTextPassword) ;
        loginWithGoogle = findViewById(R.id.login_with_google);
        loginWithFaceBook = findViewById(R.id.login_with_faceBook);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            //Google response

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            int statusCode = result.getStatus().getStatusCode();

        } else {
            //facebook response
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            parent.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            onLoginWithGoogle(account.getEmail() , account.getDisplayName() , account.getId());


        } catch (ApiException e) {


            parent.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            Toast.makeText(this, "Failed to do Sign In", Toast.LENGTH_SHORT).show();
        }
    }
    private void googleToken() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }




    private void facebookToken() {

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        Log.d("facebookData", object.toString());
                        try {
                            String name = object.getString("name");
                            String id  = object.getString("id");


                            parent.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            onLoginWithFaceBook(id  , name , id);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();

                LoginManager.getInstance().logOut();

            }

            @Override
            public void onCancel() {
                Log.d("FacebookData", "facebook cancelled");
            }

            @Override
            public void onError(FacebookException error) {


                parent.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, "Failed to do Sign In", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.colleg.project.news", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    private void onLoginData( String email,  String password) {
        JSONObject object = new JSONObject();
        try {

            object.put("email", email);
            object.put("password", password);


        } catch (JSONException e) {
            e.getStackTrace();
        }


        AndroidNetworking.post(Url.login)
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


                        startActivity(new Intent(Login.this, Home.class));
                        finish();


                    }

                    @Override
                    public void onError(ANError anError) {

                        parent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                     MyUtils.handleError(Login.this , anError.getErrorBody() , anError.getErrorCode());
                    }
                });
    }
    private void  validationRegisterData ( String email1  , String password1  ){

        if (email1.equals("")){
            editEmail.setError("Required");



        }else if(password1.equals("")){
            editpassword.setError("Required");

        }else {

            parent.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            onLoginData(email1 ,password1  );

        }


    }



    public void login(View view) {


        sEmail = editEmail.getText().toString();
        sPassword = editpassword.getText().toString();


        if(sEmail.equals("admin12") && sPassword.equals("1")){

            mySharedPreference.setUserAdmin("true");
         startActivity(new Intent(Login.this  , Admin.class));
         finish();


        }else {

            validationRegisterData(sEmail, sPassword);

        }
    }

    private void onLoginWithGoogle (final String email  , String userName  , final String password ){

        JSONObject object = new JSONObject();
        try {
            object.put("user_name", userName);
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




                         onLoginWithGoogleAfterRejested(password , email);



                    }

                    @Override
                    public void onError(ANError anError) {



                        if (anError.getErrorCode() == 401){


                            onLoginWithGoogleAfterRejested(password , email);



                        }else {

                            parent.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            MyUtils.handleError(Login.this,anError.getErrorBody(),anError.getErrorCode());
                        }


                    }
                });





    }
    private void onLoginWithGoogleAfterRejested( String password,  String email) {
        JSONObject object = new JSONObject();
        try {

            object.put("email", email);
            object.put("password", password);


        } catch (JSONException e) {
            e.getStackTrace();
        }


        AndroidNetworking.post(Url.login)
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


                        startActivity(new Intent(Login.this, Home.class));
                        finish();


                    }

                    @Override
                    public void onError(ANError anError) {

                        parent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        MyUtils.handleError(Login.this,anError.getErrorBody(),anError.getErrorCode());
                    }
                });
    }

    private void onLoginWithFaceBook (final String email  , String userName  , final String password ){


        JSONObject object = new JSONObject();
        try {
            object.put("user_name", userName);
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




                     afterLoginWithFaceBook(password,email);



                    }

                    @Override
                    public void onError(ANError anError) {



                        if (anError.getErrorCode() == 401){

                            afterLoginWithFaceBook(password,email);

                        }else {

                            parent.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            MyUtils.handleError(Login.this,anError.getErrorBody(),anError.getErrorCode());

                        }
                    }
                });





    }
    private void  afterLoginWithFaceBook ( String password,  String email){



        JSONObject object = new JSONObject();
        try {

            object.put("email", email);
            object.put("password", password);


        } catch (JSONException e) {
            e.getStackTrace();
        }


        AndroidNetworking.post(Url.login)
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


                        startActivity(new Intent(Login.this, Home.class));
                        finish();

                    }

                    @Override
                    public void onError(ANError anError) {
                        parent.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        MyUtils.handleError(Login.this,anError.getErrorBody(),anError.getErrorCode());
                    }
                });





    }


    public void signUp(View view) {
        startActivity(new Intent(Login.this , Rejester.class));
        finish();
    }


}
