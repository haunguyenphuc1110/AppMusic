package com.example.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.TopSongListAdapter;
import com.example.appmusic.Model.TopSong;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopSongActivity extends AppCompatActivity {

    Toolbar toolbarTopSong;
    RecyclerView recyclerViewTopSong;
    ArrayList<TopSong> arrTopSong;
    TopSongListAdapter topSongListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_song);
        addControls();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<TopSong>> callback = dataService.getAllTopSong();
        callback.enqueue(new Callback<List<TopSong>>() {
            @Override
            public void onResponse(Call<List<TopSong>> call, Response<List<TopSong>> response) {
                arrTopSong = (ArrayList<TopSong>) response.body();
                topSongListAdapter = new TopSongListAdapter(TopSongActivity.this, arrTopSong);
                recyclerViewTopSong.setLayoutManager(new GridLayoutManager(TopSongActivity.this, 2));
                recyclerViewTopSong.setAdapter(topSongListAdapter);
            }

            @Override
            public void onFailure(Call<List<TopSong>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbarTopSong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Top 100");
        toolbarTopSong.setTitleTextColor(getResources().getColor(R.color.colorBackToolBar));
        toolbarTopSong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbarTopSong = findViewById(R.id.toolbartopsong);
        recyclerViewTopSong = findViewById(R.id.recyclerviewlisttopsong);
    }
}
