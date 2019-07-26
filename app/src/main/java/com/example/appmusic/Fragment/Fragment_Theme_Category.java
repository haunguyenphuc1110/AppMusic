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

import com.example.appmusic.Activity.ThemeListActivity;
import com.example.appmusic.Adapter.ThemeCategoryAdapter;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Theme_Category extends Fragment {

    View view;
    RecyclerView recyclerView;
    TextView txtMoreChuDeTheLoai;
    ThemeCategoryAdapter themeCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_category, container ,false);
        recyclerView = view.findViewById(R.id.recyclerviewchudetheloai);
        txtMoreChuDeTheLoai = view.findViewById(R.id.txtxemthemchudetheloai);
        getData();
        addEvents();
        return view;
    }

    private void addEvents() {
        txtMoreChuDeTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ThemeListActivity.class));
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.getChuDeTheLoai();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> arrChude = (ArrayList<ChuDe>) response.body();
                themeCategoryAdapter = new ThemeCategoryAdapter(getActivity(),arrChude);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(themeCategoryAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

}
