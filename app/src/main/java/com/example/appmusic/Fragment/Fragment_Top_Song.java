package com.example.appmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.Activity.TopSongActivity;
import com.example.appmusic.Adapter.TopSongAdapter;
import com.example.appmusic.Model.TopSong;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Top_Song extends Fragment {

    View view;
    RecyclerView recyclerViewTopSong;
    TextView txtMoreTopSong;
    TopSongAdapter topSongAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_song, container, false);
        recyclerViewTopSong = view.findViewById(R.id.recyclerviewtopsong);
        txtMoreTopSong = view.findViewById(R.id.txtxemthemtopsong);
        getData();
        addEvents();
        return view;
    }

    private void addEvents() {
        txtMoreTopSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TopSongActivity.class));
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<TopSong>> callback = dataService.getTopSongRandom();
        callback.enqueue(new Callback<List<TopSong>>() {
            @Override
            public void onResponse(Call<List<TopSong>> call, Response<List<TopSong>> response) {
                ArrayList<TopSong> arrTopSong = (ArrayList<TopSong>) response.body();
                topSongAdapter = new TopSongAdapter(getActivity(), arrTopSong);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewTopSong.setLayoutManager(linearLayoutManager);
                recyclerViewTopSong.setAdapter(topSongAdapter);
            }

            @Override
            public void onFailure(Call<List<TopSong>> call, Throwable t) {

            }
        });
    }
}
