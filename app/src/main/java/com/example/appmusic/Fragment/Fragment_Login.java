package com.example.appmusic.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appmusic.Activity.MainActivity;
import com.example.appmusic.Model.LoginModel;
import com.example.appmusic.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class Fragment_Login extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 123;
    View view;
    Button btnLoginFaceBook, btnLoginGoogle;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //Google Login API
        LoginModel loginModel = new LoginModel();
        mGoogleApiClient = loginModel.getGoogleApiClient(getContext(), this);


        //Facebook Login API
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        btnLoginFaceBook = view.findViewById(R.id.btnloginfacebook);
        btnLoginGoogle = view.findViewById(R.id.btnlogingoogle);
        btnLoginFaceBook.setOnClickListener(this);
        btnLoginGoogle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnloginfacebook:
                LoginManager.getInstance().logInWithReadPermissions(Fragment_Login.this, Arrays.asList("public_profile"));
                break;
            case R.id.btnlogingoogle:
                signIn();
                break;

        }
    }

    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    private void signIn() {
        Intent iGoogle = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(iGoogle, RC_SIGN_IN);
        showProgressDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                progressDialog.cancel();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
