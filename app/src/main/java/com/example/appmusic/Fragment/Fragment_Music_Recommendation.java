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

import com.example.appmusic.Adapter.MusicRecommendationAdapter;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Music_Recommendation extends Fragment {
    
    View view;
    RecyclerView recyclerViewMusicRecommendation;
    MusicRecommendationAdapter musicRecommendationAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_recommendation, container, false);
        recyclerViewMusicRecommendation = view.findViewById(R.id.recyclerviewbaihatgoiy);
        recyclerViewMusicRecommendation.setNestedScrollingEnabled(false);
        
        getData();
        
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getMusicRecommendation();
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> arrBaiHat = (ArrayList<BaiHat>) response.body();
                musicRecommendationAdapter = new MusicRecommendationAdapter(getActivity(), arrBaiHat);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewMusicRecommendation.setLayoutManager(linearLayoutManager);
                recyclerViewMusicRecommendation.setAdapter(musicRecommendationAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
