package com.example.appmusic.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appmusic.Activity.MusicPlayerActivity;
import com.example.appmusic.Adapter.PlayingListAdapter;
import com.example.appmusic.Interface.RecyclerViewClickListenerInterface;
import com.example.appmusic.R;



public class Fragment_Playing_List extends Fragment implements RecyclerViewClickListenerInterface {

    View view;
    RecyclerView recyclerViewPhatNhac;
    PlayingListAdapter playingListAdapter;
    public static int playingListCurrentIndex = -1;
    OnDataPass dataPasser;

    public interface OnDataPass {
        void onDataPass(int index, int key);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playing_list, container, false);
        recyclerViewPhatNhac = view.findViewById(R.id.recyclerviewPhatDanhSachNhac);
        if (MusicPlayerActivity.arrBaiHatCommon.size() > 0){
            playingListAdapter = new PlayingListAdapter(getActivity(), MusicPlayerActivity.arrBaiHatCommon, this);
            recyclerViewPhatNhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPhatNhac.setAdapter(playingListAdapter);
        }

        return view;
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        //Toast.makeText(getActivity(), MusicPlayerActivity.arrBaiHatCommon.get(position).getTenBaiHat(), Toast.LENGTH_SHORT).show();
        passData(position, 123);
    }

    public void passData(int index, int key) {
        dataPasser.onDataPass(index, key);
    }
}
