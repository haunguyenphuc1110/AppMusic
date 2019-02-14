package com.example.appmusic.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appmusic.Adapter.DanhSachTheLoaiTheoChuDeAdapter;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.Model.TheLoai;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTheLoaiTheoChuDeActivity extends AppCompatActivity {

    ChuDe chuDe;
    Toolbar toolbarDanhSachTheLoaiTheoChuDe;
    RecyclerView recyclerViewDanhSachTheLoaiTheoChuDe;
    ArrayList<TheLoai> arrTheLoai;
    DanhSachTheLoaiTheoChuDeAdapter danhSachTheLoaiTheoChuDeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_the_loai_theo_chu_de);
        getIntentChuDe();
        addControls();
        init();
        getData(chuDe.getIdChuDe());
    }

    private void getData(String idchude) {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.getDanhSachTheLoaiTheoChuDe(idchude);
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                arrTheLoai = (ArrayList<TheLoai>) response.body();
                danhSachTheLoaiTheoChuDeAdapter = new DanhSachTheLoaiTheoChuDeAdapter(DanhSachTheLoaiTheoChuDeActivity.this, arrTheLoai);
                recyclerViewDanhSachTheLoaiTheoChuDe.setLayoutManager(new GridLayoutManager(DanhSachTheLoaiTheoChuDeActivity.this, 2));
                recyclerViewDanhSachTheLoaiTheoChuDe.setAdapter(danhSachTheLoaiTheoChuDeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void getIntentChuDe() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }

    private void init() {
        setSupportActionBar(toolbarDanhSachTheLoaiTheoChuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbarDanhSachTheLoaiTheoChuDe.setTitleTextColor(getResources().getColor(R.color.colorBackToolBar));
        toolbarDanhSachTheLoaiTheoChuDe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        toolbarDanhSachTheLoaiTheoChuDe = findViewById(R.id.toolbardanhsachtheloaitheochude);
        recyclerViewDanhSachTheLoaiTheoChuDe = findViewById(R.id.recyclerviewtheloaitheochude);
    }
}
