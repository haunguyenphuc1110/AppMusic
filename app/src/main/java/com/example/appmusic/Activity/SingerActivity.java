package com.example.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.SingerListAdapter;
import com.example.appmusic.Model.CaSi;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingerActivity extends AppCompatActivity {

    Toolbar toolbarCasi;
    RecyclerView recyclerViewCasi;
    ArrayList<CaSi> arrCaSi;
    SingerListAdapter singerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer);
        addControl();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<CaSi>> callback = dataService.getAllCaSi();
        callback.enqueue(new Callback<List<CaSi>>() {
            @Override
            public void onResponse(Call<List<CaSi>> call, Response<List<CaSi>> response) {
                ArrayList<CaSi> arrCaSi = (ArrayList<CaSi>) response.body();
                singerListAdapter = new SingerListAdapter(SingerActivity.this, arrCaSi);
                recyclerViewCasi.setLayoutManager(new GridLayoutManager(SingerActivity.this, 2));
                recyclerViewCasi.setAdapter(singerListAdapter);
            }

            @Override
            public void onFailure(Call<List<CaSi>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbarCasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nghệ Sĩ Nổi Bật");
        toolbarCasi.setTitleTextColor(getResources().getColor(R.color.colorBackToolBar));
        toolbarCasi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControl() {
        toolbarCasi = findViewById(R.id.toolbarcasi);
        recyclerViewCasi = findViewById(R.id.recyclerviewlistcasi);
    }
}
