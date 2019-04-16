package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Model.QuangCao;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> arrBanner;

    public BannerAdapter(Context context, ArrayList<QuangCao> arrBanner) {
        this.context = context;
        this.arrBanner = arrBanner;
    }

    @Override
    public int getCount() {
        return arrBanner.size();
    }


    //Return boolean value when compare view through object that you want to custom
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    //Custom object
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_banner, null);

        ImageView imgBackGroundBanner = view.findViewById(R.id.imgbackgroundbanner);
        ImageView imgSongBanner = view.findViewById(R.id.imgsongbanner);

        Picasso.with(context).load(arrBanner.get(position).getHinhQuangCao()).into(imgBackGroundBanner);
        Picasso.with(context).load(arrBanner.get(position).getHinhBaiHat()).into(imgSongBanner);


        //Catch event when click banner
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(context, SongListActivity.class);
            intent.putExtra("banner", arrBanner.get(position));
            context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
