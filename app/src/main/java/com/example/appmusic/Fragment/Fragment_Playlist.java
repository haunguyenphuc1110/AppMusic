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

import com.example.appmusic.Activity.PlaylistListActivity;
import com.example.appmusic.Adapter.PlaylistAdapter;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Playlist extends Fragment {

    View view;
    RecyclerView recyclerView;
    TextView txtMorePlaylist;
    PlaylistAdapter playlistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container,false);
        recyclerView = view.findViewById(R.id.recyclerviewplaylist);
        txtMorePlaylist = view.findViewById(R.id.txtxemthemplaylist);
        getData();
        addEvents();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.getPlaylistRandom();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> playlists = (ArrayList<Playlist>) response.body();
                playlistAdapter = new PlaylistAdapter(getActivity(),playlists);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(playlistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void addEvents() {
        txtMorePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PlaylistListActivity.class));
            }
        });
    }



}
