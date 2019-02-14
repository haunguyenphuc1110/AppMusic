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

import com.example.appmusic.Activity.TrinhPhatNhacActivity;
import com.example.appmusic.Adapter.TrinhPhatNhacAdapter;
import com.example.appmusic.R;

public class Fragment_Phat_Danh_Sach_Nhac extends Fragment {

    View view;
    RecyclerView recyclerViewPhatNhac;
    TrinhPhatNhacAdapter trinhPhatNhacAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_phat_danh_sach_nhac, container, false);
        recyclerViewPhatNhac = view.findViewById(R.id.recyclerviewPhatDanhSachNhac);

        if (TrinhPhatNhacActivity.arrBaiHatCommon.size() > 0){
            trinhPhatNhacAdapter = new TrinhPhatNhacAdapter(getActivity(), TrinhPhatNhacActivity.arrBaiHatCommon);
            recyclerViewPhatNhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPhatNhac.setAdapter(trinhPhatNhacAdapter);
        }

        return view;
    }
}
