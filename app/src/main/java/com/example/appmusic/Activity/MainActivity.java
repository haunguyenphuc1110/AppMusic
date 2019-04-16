package com.example.appmusic.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appmusic.Adapter.MainViewPagerAdapter;
import com.example.appmusic.Fragment.Fragment_Music_Library;
import com.example.appmusic.Fragment.Fragment_Main_Page;
import com.example.appmusic.Model.LoginModel;
import com.example.appmusic.Model.Profile;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imgRefresh;
    CircleImageView imgAccount;
    MainViewPagerAdapter mainViewPagerAdapter;
    Button btnSearch;
    String link_pic;//Link picture profile

    public static AccessToken accessToken;//If Logged then not null. It's like login state

    LoginModel loginModel;//Model that has some function used for login

    GoogleApiClient googleApiClient;//If Logged then not null. It's like login state
    GoogleSignInResult googleSignInResult;//If Logged then not null. It's like login state

    public static Profile profile;//Hold data and send it to new ProfileManagement Activity

    ProgressDialog progressDialog;

    Intent intent;//Store key that ProfileManagement Activirty send to this Activity through Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();

        getFacebookProfile();

        getGoogleProfile();

        if (accessToken == null && googleSignInResult == null)
            init();

        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    private void addEvents() {
        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accessToken == null && googleSignInResult == null) {
                    showProgressDialog();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                else if (accessToken != null && googleSignInResult == null) {
                    showProgressDialog();
                    Intent intent = new Intent(MainActivity.this, ProfileManagementActivity.class);
                    intent.putExtra("facebook", profile);
                    startActivity(intent);
                }
                else if (accessToken == null && googleSignInResult != null){
                    showProgressDialog();
                    Intent intent = new Intent(MainActivity.this, ProfileManagementActivity.class);
                    intent.putExtra("google", profile);
                    startActivity(intent);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }

    private void addControls() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        imgAccount = findViewById(R.id.imgaccount);
        btnSearch = findViewById(R.id.btnsearch);
        imgRefresh = findViewById(R.id.imgrefresh);

        //Init Login Model
        loginModel = new LoginModel();

        //Init model Profile
        profile = new Profile();

        intent = getIntent();
    }

    private void init() {
        //Set up adapter
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Main_Page(), "Trang chủ");
        mainViewPagerAdapter.addFragment(new Fragment_Music_Library(), "Cá nhân");
        viewPager.setAdapter(mainViewPagerAdapter);

        //Set up Tab Layout
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_music_library);
    }

    private void getFacebookProfile(){
        accessToken = loginModel.getCurrentFacebookToken();
        if(accessToken != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        profile.setId(object.getString("id"));
                        link_pic = object.getJSONObject("picture").getJSONObject("data").getString("url");
                        profile.setName(object.getString("name"));
                        profile.setAvatar(link_pic);
                        profile.setEmail("");

                        insertUser();//Insert user into database if it is not exist

                        new PictureProfile().execute(link_pic);//Set avatar

                        Toast.makeText(MainActivity.this,profile.getName() + " đã đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        init();
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

    private void getGoogleProfile(){
        googleApiClient = loginModel.getGoogleApiClient(this, this);
        googleSignInResult = loginModel.getGoogleProfileInformation(googleApiClient);

        if (googleSignInResult != null){

            //Check if user click button ĐĂNG XUẤT
            if (intent.hasExtra("logout google")) {
                Toast.makeText(this, "Bạn đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                signOutGoogle();
                finish();
                startActivity(getIntent());
            }
            else {
                link_pic = googleSignInResult.getSignInAccount().getPhotoUrl().toString();
                profile.setId(googleSignInResult.getSignInAccount().getId());
                profile.setName(googleSignInResult.getSignInAccount().getDisplayName());
                profile.setAvatar(link_pic);
                profile.setEmail(googleSignInResult.getSignInAccount().getEmail());

                insertUser();//Insert user into database if it is not exist

                new PictureProfile().execute(link_pic);//Set avatar
                Toast.makeText(MainActivity.this, profile.getName() + " đã đăng nhập thành công", Toast.LENGTH_SHORT).show();

                init();
            }
        }
    }

    private void signOutGoogle(){
        try {
            Auth.GoogleSignInApi.signOut(googleApiClient);
        }
        catch (Exception ex){
        }
    }

    private void insertUser(){
        DataService dataService = APIService.getService();
        Call<String> callback =dataService.insertUser(profile.getId(), profile.getName(), profile.getEmail(),profile.getAvatar());
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.equals("Success")){
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }else{
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private void hideProgressDialog() {
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    //Using AsyncTask for loading picture profile from Facebook
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
