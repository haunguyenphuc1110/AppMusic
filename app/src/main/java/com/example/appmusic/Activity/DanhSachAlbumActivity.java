package com.example.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.DanhSachAlbumAdapter;
import com.example.appmusic.Model.Album;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachAlbumActivity extends AppCompatActivity {

    Toolbar toolbarDanhSachAlbum;
    RecyclerView recyclerViewDanhSachAlbum;
    ArrayList<Album> arrAlbum;
    DanhSachAlbumAdapter danhSachAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_album);
        addControls();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.getAllAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                arrAlbum = (ArrayList<Album>) response.body();
                danhSachAlbumAdapter = new DanhSachAlbumAdapter(DanhSachAlbumActivity.this, arrAlbum);
                recyclerViewDanhSachAlbum.setLayoutManager(new GridLayoutManager(DanhSachAlbumActivity.this, 2));
                recyclerViewDanhSachAlbum.setAdapter(danhSachAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbarDanhSachAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Albums");
        toolbarDanhSachAlbum.setTitleTextColor(getResources().getColor(R.color.colorBackToolBar));
        toolbarDanhSachAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbarDanhSachAlbum = findViewById(R.id.toolbardanhsachalbum);
        recyclerViewDanhSachAlbum = findViewById(R.id.recyclerviewalbum);
    }
}
