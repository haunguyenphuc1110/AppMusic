package com.example.appmusic.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Arrays;

import dmax.dialog.SpotsDialog;

public class Fragment_Music_Lyric extends Fragment {
    View view;
    RecyclerView recyclerViewLoiBaiHat;
    MusicLyricAdapter musicLyricAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_lyric, container,false);
        recyclerViewLoiBaiHat = view.findViewById(R.id.recyclerviewloibaihat);
        if (MusicPlayerActivity.arrBaiHatCommon.size() > 0) {
            new LyricReading().execute(MusicPlayerActivity.arrBaiHatCommon.get(MusicPlayerActivity.position).getLoiBaiHat());
        }
        return view;
    }

    class LyricReading extends AsyncTask<String, String, ArrayList<String>> {
        AlertDialog dialog = new SpotsDialog.Builder().setContext(getContext()).setMessage("...").setCancelable(false).build();

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> rowLyrics = new ArrayList<>();

            try {
                URL url = new URL(strings[0]);

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
            return rowLyrics;
        }

        @Override
        protected void onPostExecute(ArrayList<String> rowLyrics) {
            musicLyricAdapter = new MusicLyricAdapter(getActivity(), rowLyrics);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerViewLoiBaiHat.setLayoutManager(linearLayoutManager);
            recyclerViewLoiBaiHat.setAdapter(musicLyricAdapter);
            dialog.dismiss();
        }

    }
}
