package com.example.appmusic.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.Activity.MainActivity;
import com.example.appmusic.Adapter.MusicLibraryAdapter;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This fragment is on viewpager at MainActivity
public class Fragment_Music_Library extends Fragment {

    View view;
    TextView txtBannerLogin, txtNoSong;
    RecyclerView recyclerViewMusicLibrary;
    MusicLibraryAdapter musicLibraryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_library, container,false);
        txtBannerLogin =  view.findViewById(R.id.txtbannerlogin);
        txtNoSong = view.findViewById(R.id.txtlibrarynosong);
        recyclerViewMusicLibrary = view.findViewById(R.id.recyclerviewmusiclibrary);

        if (MainActivity.profile.getId() == null){
            txtNoSong.setVisibility(View.VISIBLE);
            txtBannerLogin.setVisibility(View.VISIBLE);
            recyclerViewMusicLibrary.setVisibility(View.GONE);
        }
        else
            getUserFavoriteSong(MainActivity.profile.getId());
        
        return view;
    }

    private void getUserFavoriteSong(String userid){
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getUserFavoriteSong(userid);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> arrBaiHat = (ArrayList<BaiHat>) response.body();

                musicLibraryAdapter = new MusicLibraryAdapter(getActivity(), arrBaiHat);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerViewMusicLibrary.setLayoutManager(linearLayoutManager);
                recyclerViewMusicLibrary.setAdapter(musicLibraryAdapter);

                txtNoSong.setVisibility(View.GONE);
                txtBannerLogin.setVisibility(View.GONE);
                recyclerViewMusicLibrary.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
