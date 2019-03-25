package com.example.appmusic.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmusic.Model.Profile;
import com.example.appmusic.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileManagementActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    CollapsingToolbarLayout collapsingToolbarProfile;
    Toolbar toolBarProfile;
    CircleImageView circleImageViewProfile;
    TextView txtIdUser, txtNameUser, txtEmailUser, txtUrlUser;
    Button btnLogOut;
    Intent intent;
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        addControls();

        getDataProfile();

        addEvents();

    }

    private void addEvents() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.hasExtra("facebook")) {
                    LoginManager.getInstance().logOut();
                    showAlertDialogLogoutFacebook();
                }

                if (intent.hasExtra("google")){
                    showAlertDialogLogoutGoogle();
                }
            }
        });
    }

    private void getDataProfile() {
        intent = getIntent();

        if (intent.hasExtra("facebook")) {
            profile = (Profile) intent.getSerializableExtra("facebook");
        }

        if (intent.hasExtra("google")) {
            profile = (Profile) intent.getSerializableExtra("google");
        }

        //Load Default Background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            collapsingToolbarProfile.setBackgroundResource(R.drawable.ic_collapsingtoolbar_background);
        }


        collapsingToolbarProfile.setTitle(profile.getName());
        Picasso.with(this).load(profile.getAvatar()).into(circleImageViewProfile);
        txtIdUser.setText(profile.getId());
        txtNameUser.setText(profile.getName());
        txtEmailUser.setText(profile.getEmail());
        txtUrlUser.setText(profile.getAvatar());
    }

    private void addControls() {
        toolBarProfile = findViewById(R.id.toolbarprofile);
        collapsingToolbarProfile = findViewById(R.id.collapsingtoolbarprofile);
        circleImageViewProfile = findViewById(R.id.imgprofilepicture);
        txtIdUser = findViewById(R.id.txtiduser);
        txtNameUser = findViewById(R.id.txtnameuser);
        txtEmailUser = findViewById(R.id.txtemailuser);
        txtUrlUser = findViewById(R.id.txturluser);
        btnLogOut = findViewById(R.id.btnlogout);


        setSupportActionBar(toolBarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hồ sơ cá nhân");
        collapsingToolbarProfile.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarProfile.setCollapsedTitleTextColor(Color.WHITE);
        toolBarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void showAlertDialogLogoutFacebook(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn đăng xuất tài khoản?");
        builder.setCancelable(false);
        builder.setPositiveButton("ĐĂNG XUẤT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ProfileManagementActivity.this,"Bạn đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileManagementActivity.this, MainActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("BỎ QUA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showAlertDialogLogoutGoogle(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn đăng xuất tài khoản?");
        builder.setCancelable(false);
        builder.setPositiveButton("ĐĂNG XUẤT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ProfileManagementActivity.this, MainActivity.class);
                intent.putExtra("logout google", "");
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("BỎ QUA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
