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

import com.example.appmusic.Activity.MusicPlayerActivity;
import com.example.appmusic.Adapter.MusicLyricAdapter;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Fragment_Music_Lyric extends Fragment {
    View view;
    ArrayList<String> rowLyrics;
    RecyclerView recyclerViewLoiBaiHat;
    MusicLyricAdapter musicLyricAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_lyric, container,false);
        recyclerViewLoiBaiHat = view.findViewById(R.id.recyclerviewloibaihat);
        rowLyrics = new ArrayList<>();
        if (MusicPlayerActivity.arrBaiHatCommon.size() == 1) {
            try {
                String link = MusicPlayerActivity.arrBaiHatCommon.get(0).getLoiBaiHat();
                URL url = new URL(link);

                // read text returned by server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    rowLyrics.add(new String(line));
                }
                in.close();
            }
            catch (MalformedURLException e) {
                System.out.println("Malformed URL: " + e.getMessage());
            }
            catch (IOException e) {
                System.out.println("I/O Error: " + e.getMessage());
            }

            musicLyricAdapter = new MusicLyricAdapter(getActivity(), rowLyrics);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewLoiBaiHat.setLayoutManager(linearLayoutManager);
            recyclerViewLoiBaiHat.setAdapter(musicLyricAdapter);
        }
        return view;
    }
}
