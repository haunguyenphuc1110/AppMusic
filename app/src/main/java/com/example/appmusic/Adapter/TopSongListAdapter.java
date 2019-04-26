package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Model.TopSong;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopSongListAdapter extends RecyclerView.Adapter<TopSongListAdapter.ViewHolder> {

    Context context;
    ArrayList<TopSong> arrTopSong;

    public TopSongListAdapter(Context context, ArrayList<TopSong> arrTopSong) {
        this.context = context;
        this.arrTopSong = arrTopSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_topsong_list,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TopSong topSong = arrTopSong.get(i);
        viewHolder.txtNameTopSong.setText(topSong.getTenTopSong());
        Picasso.with(context).load(topSong.getHinhTopSong()).into(viewHolder.imgTopSong);
    }

    @Override
    public int getItemCount() {
        return arrTopSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgTopSong;
        TextView txtNameTopSong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTopSong = itemView.findViewById(R.id.imgdanhsachtopsong);
            txtNameTopSong = itemView.findViewById(R.id.txttentopsong);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SongListActivity.class);
                    intent.putExtra("itemtopsong", arrTopSong.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
