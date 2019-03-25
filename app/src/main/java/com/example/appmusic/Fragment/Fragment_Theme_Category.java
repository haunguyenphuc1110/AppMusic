package com.example.appmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Activity.ThemeListActivity;
import com.example.appmusic.Activity.CategoryListActivity;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.Model.ChuDeTheLoai;
import com.example.appmusic.Model.TheLoai;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Theme_Category extends Fragment {

    View view;
    HorizontalScrollView horizontalScrollView;
    TextView txtMoreChuDeTheLoai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theme_category, container ,false);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollview);
        txtMoreChuDeTheLoai = view.findViewById(R.id.txtxemthemchudetheloai);
        txtMoreChuDeTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ThemeListActivity.class));
            }
        });
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<ChuDeTheLoai> callback = dataService.getChuDeTheLoai();
        callback.enqueue(new Callback<ChuDeTheLoai>() {
            @Override
            public void onResponse(Call<ChuDeTheLoai> call, Response<ChuDeTheLoai> response) {
                ChuDeTheLoai chuDeTheLoai = (ChuDeTheLoai) response.body();

                final ArrayList<ChuDe> arrChuDe = new ArrayList<>();
                arrChuDe.addAll(chuDeTheLoai.getChuDe());

                final ArrayList<TheLoai> arrTheLoai = new ArrayList<>();
                arrTheLoai.addAll(chuDeTheLoai.getTheLoai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(600,400);
                layout.setMargins(10,20,10,10);

                //For Chu De
                for (int i = 0 ; i < arrChuDe.size() ; i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (arrChuDe.get(i).getHinhChuDe() != null){
                        Picasso.with(getActivity()).load(arrChuDe.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);

                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), CategoryListActivity.class);
                            intent.putExtra("chude", arrChuDe.get(finalI));
                            startActivity(intent);
                        }
                    });
                }

                //For The Loai
                for (int i = 0 ; i < arrTheLoai.size() ; i++){
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (arrTheLoai.get(i).getHinhTheLoai() != null){
                        Picasso.with(getActivity()).load(arrTheLoai.get(i).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);

                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), SongListActivity.class);
                            intent.putExtra("idtheloai", arrTheLoai.get(finalI));
                            startActivity(intent);
                        }
                    });
                }

                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<ChuDeTheLoai> call, Throwable t) {

            }
        });
    }

}
