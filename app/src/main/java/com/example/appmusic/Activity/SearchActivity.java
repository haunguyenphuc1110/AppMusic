package com.example.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.Adapter.SongSearchingAdapter;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    EditText txtSearch;
    ImageView imgBack;
    RecyclerView recyclerViewSearch;
    TextView txtNoSong;
    SongSearchingAdapter songSearchingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addControls();
        addEvents();
    }

    private void addEvents() {
        //Catch event Edit text
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchSong(txtSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //CLick event. After click, view move to Login Activity
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        recyclerViewSearch = findViewById(R.id.recyclerviewsearch);
        txtSearch = findViewById(R.id.txtsearch);
        imgBack = findViewById(R.id.imgbackarrow);
        txtNoSong = findViewById(R.id.txtnosong);
    }

    private void searchSong(String key){
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.searchBaiHat(key);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> arrBaiHat = (ArrayList<BaiHat>) response.body();

                if (arrBaiHat.size() > 0){
                    songSearchingAdapter = new SongSearchingAdapter(SearchActivity.this, arrBaiHat);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                    recyclerViewSearch.setLayoutManager(linearLayoutManager);
                    recyclerViewSearch.setAdapter(songSearchingAdapter);
                    txtNoSong.setVisibility(View.GONE);
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                }
                else{
                    txtNoSong.setVisibility(View.VISIBLE);
                    recyclerViewSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
