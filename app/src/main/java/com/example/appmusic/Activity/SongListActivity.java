package com.example.appmusic.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appmusic.Adapter.SongListAdapter;
import com.example.appmusic.Model.Album;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.Model.CaSi;
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.Model.QuangCao;
import com.example.appmusic.Model.TheLoai;
import com.example.appmusic.Model.TopSong;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewDanhSachBaiHat;
    Button actionButton;
    ImageView imgDanhSachCaKhuc;
    SongListAdapter songListAdapter;
    ArrayList<BaiHat> arrBaiHat;

    TopSong topSong;
    QuangCao quangCao;
    Playlist playlist;
    TheLoai theLoai;
    Album album;
    CaSi caSi;


    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        //Kiểm tra tín hiệu mạng
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Anh Xa cac thanh phan trong layout
        addControls();

        //Initial
        init();

        //Get data from Banner, playlist, album, concept to SongListActivity
        getData();

        //Kiem tra top song co ton tai
        if(topSong != null && !topSong.getTenTopSong().equals("")){
            setValueInView(topSong.getTenTopSong(), topSong.getHinhTopSong());
            getDataTopSong(topSong.getIdTopSong());
        }

        //Kiem tra quang cao co ton tai
        if(quangCao != null && !quangCao.getTenBaiHat().equals("")){
            setValueInView(quangCao.getTenBaiHat(), quangCao.getHinhBaiHat());
            getDataQuangCao(quangCao.getIdQuangCao());
        }

        //Kiem tra playlist co ton tai
        if(playlist != null && !playlist.getTen().equals("")){
            setValueInView(playlist.getTen(), playlist.getHinhIcon());
            getDataPlaylist(playlist.getIdPlaylist());
        }

        //Kiem tra the loai co ton tai
        if(theLoai != null && !theLoai.getTenTheLoai().equals("")){
            setValueInView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            getDataTheLoai(theLoai.getIdTheLoai());
        }

        //Kiem tra album co ton tai
        if(album != null && !album.getTenAlbum().equals("")){
            setValueInView(album.getTenAlbum(), album.getHinhAlbum());
            getDataAlbum(album.getIdAlbum());
        }

        //Kiem tra casi co ton tai
        if(caSi != null && !caSi.getTenCaSi().equals("")){
            setSingerValueInView(caSi.getTenCaSi(), caSi.getHinhIcon(), caSi.getHinhBackground());
            getDataSinger(caSi.getTenCaSi());
        }

    }

    private void getDataSinger(String tenCaSi) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhSachBaiHatTheoCaSi(tenCaSi);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrBaiHat = (ArrayList<BaiHat>) response.body();
                songListAdapter = new SongListAdapter(SongListActivity.this, arrBaiHat );
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(songListAdapter);
                addEvents();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataTopSong(String idTopSong) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhSachBaiHatTheoTopSong(idTopSong);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrBaiHat = (ArrayList<BaiHat>) response.body();
                songListAdapter = new SongListAdapter(SongListActivity.this, arrBaiHat );
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(songListAdapter);
                addEvents();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataAlbum(String idAlbum) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhSachBaiHatTheoAlbum(idAlbum);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrBaiHat = (ArrayList<BaiHat>) response.body();
                songListAdapter = new SongListAdapter(SongListActivity.this, arrBaiHat );
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(songListAdapter);
                addEvents();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataTheLoai(String idTheLoai) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhSachBaiHatTheoTheLoai(idTheLoai);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrBaiHat = (ArrayList<BaiHat>) response.body();
                songListAdapter = new SongListAdapter(SongListActivity.this, arrBaiHat );
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(songListAdapter);
                addEvents();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataPlaylist(String idplaylist) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhSachBaiHatTheoPLaylist(idplaylist);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrBaiHat = (ArrayList<BaiHat>) response.body();
                songListAdapter = new SongListAdapter(SongListActivity.this, arrBaiHat );
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(songListAdapter);
                addEvents();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void getDataQuangCao(String idquangcao) {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getDanhSachBaiHatTheoQuangCao(idquangcao);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                arrBaiHat = (ArrayList<BaiHat>) response.body();
                songListAdapter = new SongListAdapter(SongListActivity.this, arrBaiHat );
                recyclerViewDanhSachBaiHat.setLayoutManager(new LinearLayoutManager(SongListActivity.this));
                recyclerViewDanhSachBaiHat.setAdapter(songListAdapter);
                addEvents();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void setValueInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitleEnabled(false);
        getSupportActionBar().setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Picasso.with(this).load(hinh).into(imgDanhSachCaKhuc);
    }


    private void setSingerValueInView(String tenCaSi, String hinhIcon, String hinhBackground) {
        collapsingToolbarLayout.setTitleEnabled(false);
        getSupportActionBar().setTitle(tenCaSi);
        try {
            URL url = new URL(hinhBackground);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Picasso.with(this).load(hinhIcon).into(imgDanhSachCaKhuc);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void addControls() {
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbardanhsach);
        recyclerViewDanhSachBaiHat = findViewById(R.id.recyclerviewdanhsachbaihat);
        actionButton = findViewById(R.id.btnplayall);
        imgDanhSachCaKhuc = findViewById(R.id.imgdanhsachcakhuc);
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("itemtopsong")){
                topSong = (TopSong) intent.getSerializableExtra("itemtopsong");
            }
            if (intent.hasExtra("banner")){
                quangCao = (QuangCao) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("itemalbum")){
                album = (Album) intent.getSerializableExtra("itemalbum");
            }
            if (intent.hasExtra("itemcasi")){
                caSi = (CaSi) intent.getSerializableExtra("itemcasi");
            }
        }
    }

    private void addEvents(){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrBaiHat.size() > 0) {
                    showProgressDialog(SongListActivity.this);
                    Intent intent = new Intent(SongListActivity.this, MusicPlayerActivity.class);
                    intent.putExtra("danhsachcakhuc", arrBaiHat);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        hideProgressDialog();
        super.onResume();
    }

    private void showProgressDialog(Context context){
        dialog = new SpotsDialog.Builder().setContext(context).setMessage("...").setCancelable(false).build();
        dialog.show();
    }

    private void hideProgressDialog() {
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
