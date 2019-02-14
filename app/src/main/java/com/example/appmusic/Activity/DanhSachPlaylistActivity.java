package com.example.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.DanhSachPlaylistAdapter;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPlaylistActivity extends AppCompatActivity {

    Toolbar toolbarDanhSachPlaylist;
    RecyclerView recyclerViewDanhSachPlaylist;
    ArrayList<Playlist> arrPlaylist;
    DanhSachPlaylistAdapter danhSachPlaylistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_playlist);
        addControls();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.getAllPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                arrPlaylist = (ArrayList<Playlist>) response.body();
                danhSachPlaylistAdapter = new DanhSachPlaylistAdapter(DanhSachPlaylistActivity.this, arrPlaylist);
                recyclerViewDanhSachPlaylist.setLayoutManager(new GridLayoutManager(DanhSachPlaylistActivity.this, 2));
                recyclerViewDanhSachPlaylist.setAdapter(danhSachPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbarDanhSachPlaylist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlists");
        toolbarDanhSachPlaylist.setTitleTextColor(getResources().getColor(R.color.colorBackToolBar));
        toolbarDanhSachPlaylist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbarDanhSachPlaylist = findViewById(R.id.toolbardanhsachplaylist);
        recyclerViewDanhSachPlaylist = findViewById(R.id.recyclerviewplaylist);
    }
}
