package com.example.appmusic.Activity;

import android.app.AlertDialog;
import dmax.dialog.SpotsDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appmusic.Adapter.MainViewPagerAdapter;
import com.example.appmusic.Common.Common;
import com.example.appmusic.Fragment.Fragment_Music_Library;
import com.example.appmusic.Fragment.Fragment_Main_Page;
import com.example.appmusic.Model.LoginModel;
import com.example.appmusic.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imgAccount;
    Button btnSearch;
    String link_pic;//Link picture profile
    AccessToken accessToken;//If Logged then not null. It's like login state
    LoginModel loginModel;
    GoogleApiClient googleApiClient;
    GoogleSignInResult googleSignInResult;//If Logged then not null. It's like login state


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        init();
        getFacebookProfilePicture();
        getGoogleProfilePicture();
    }

    private void addEvents() {

        if (accessToken == null && googleSignInResult == null) {
            imgAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }
        else{

        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });


    }

    private void addControls() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        imgAccount = findViewById(R.id.imgaccount);
        btnSearch = findViewById(R.id.btnsearch);
    }

    private void init() {
        //Set up adapter
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Main_Page(), "Trang chủ");
        mainViewPagerAdapter.addFragment(new Fragment_Music_Library(), "Cá nhân");
        viewPager.setAdapter(mainViewPagerAdapter);

        //Set up Tab Layout
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_music_library);

        //Init Login Model
        loginModel = new LoginModel();

    }

    private void getFacebookProfilePicture(){
        accessToken = loginModel.getCurrentFacebookToken();
        if(accessToken != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        link_pic = object.getJSONObject("picture").getJSONObject("data").getString("url");
                        new PictureProfile().execute(link_pic);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameter = new Bundle();
            parameter.putString("fields", "id,name,picture.type(normal)");
            graphRequest.setParameters(parameter);
            graphRequest.executeAsync();

        }

    }

    private void getGoogleProfilePicture(){
        googleApiClient = loginModel.getGoogleApiClient(this, this);
        googleSignInResult = loginModel.getGoogleProfileInformation(googleApiClient);
        if (googleSignInResult != null){
            link_pic = googleSignInResult.getSignInAccount().getPhotoUrl().toString();
            new PictureProfile().execute(link_pic);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class PictureProfile extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgAccount.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            Bitmap resized = null;
            try{
                String link = strings[0];
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(link).getContent());
                resized = Bitmap.createScaledBitmap(bitmap, 150,150,true);
                return resized;
            }
            catch(Exception ex){
                Log.e("LOI",ex.toString());
            }
            return null;
        }
    }
}
