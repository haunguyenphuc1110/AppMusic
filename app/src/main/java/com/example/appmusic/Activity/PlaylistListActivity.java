package com.example.appmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.PlaylistListAdapter;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistListActivity extends AppCompatActivity {

    Toolbar toolbarDanhSachPlaylist;
    RecyclerView recyclerViewDanhSachPlaylist;
    ArrayList<Playlist> arrPlaylist;
    PlaylistListAdapter playlistListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_list);
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
                playlistListAdapter = new PlaylistListAdapter(PlaylistListActivity.this, arrPlaylist);
                recyclerViewDanhSachPlaylist.setLayoutManager(new GridLayoutManager(PlaylistListActivity.this, 2));
                recyclerViewDanhSachPlaylist.setAdapter(playlistListAdapter);
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
