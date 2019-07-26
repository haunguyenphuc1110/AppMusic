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

import com.example.appmusic.Activity.SingerActivity;
import com.example.appmusic.Adapter.SingerAdapter;
import com.example.appmusic.Model.CaSi;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Singer extends Fragment {

    View view;
    RecyclerView recyclerViewCaSi;
    TextView txtMoreCaSi;
    SingerAdapter singerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_singer, container, false);
        recyclerViewCaSi = view.findViewById(R.id.recyclerviewcasi);
        txtMoreCaSi = view.findViewById(R.id.txtxemthemcasi);
        getData();
        addEvents();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<CaSi>> callback = dataService.getCaSi();
        callback.enqueue(new Callback<List<CaSi>>() {
            @Override
            public void onResponse(Call<List<CaSi>> call, Response<List<CaSi>> response) {
                ArrayList<CaSi> arrCaSi = (ArrayList<CaSi>) response.body();
                singerAdapter = new SingerAdapter(getActivity(), arrCaSi);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewCaSi.setLayoutManager(linearLayoutManager);
                recyclerViewCaSi.setAdapter(singerAdapter);
            }

            @Override
            public void onFailure(Call<List<CaSi>> call, Throwable t) {

            }
        });
    }

    private void addEvents(){
        txtMoreCaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SingerActivity.class));
            }
        });
    }
}
