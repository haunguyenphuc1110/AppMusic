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
import com.example.appmusic.Adapter.PlayingListAdapter;
import com.example.appmusic.R;

public class Fragment_Playing_List extends Fragment {

    View view;
    RecyclerView recyclerViewPhatNhac;
    PlayingListAdapter playingListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playing_list, container, false);
        recyclerViewPhatNhac = view.findViewById(R.id.recyclerviewPhatDanhSachNhac);

        if (MusicPlayerActivity.arrBaiHatCommon.size() > 0){
            playingListAdapter = new PlayingListAdapter(getActivity(), MusicPlayerActivity.arrBaiHatCommon);
            recyclerViewPhatNhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPhatNhac.setAdapter(playingListAdapter);
        }

        return view;
    }
}
