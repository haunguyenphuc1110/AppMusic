package com.example.appmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.ThemeListAdapter;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemeListActivity extends AppCompatActivity {

    Toolbar toolbarDanhSachChuDe;
    RecyclerView recyclerViewDanhSachChuDe;
    ArrayList<ChuDe> arrChuDe;
    ThemeListAdapter themeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_list);
        addControls();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.getAllChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                arrChuDe = (ArrayList<ChuDe>) response.body();
                themeListAdapter = new ThemeListAdapter(ThemeListActivity.this, arrChuDe);
                recyclerViewDanhSachChuDe.setLayoutManager(new GridLayoutManager(ThemeListActivity.this, 2));
                recyclerViewDanhSachChuDe.setAdapter(themeListAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbarDanhSachChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chủ Đề");
        toolbarDanhSachChuDe.setTitleTextColor(getResources().getColor(R.color.colorBackToolBar));
        toolbarDanhSachChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbarDanhSachChuDe = findViewById(R.id.toolbardanhsachchude);
        recyclerViewDanhSachChuDe = findViewById(R.id.recyclerviewchude);
    }
}
