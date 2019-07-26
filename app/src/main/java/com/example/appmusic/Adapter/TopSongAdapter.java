package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.ViewHolder> {

    Context context;
    ArrayList<TopSong> arrTopSong;

    public TopSongAdapter(Context context, ArrayList<TopSong> arrTopSong) {
        this.context = context;
        this.arrTopSong = arrTopSong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_topsong_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TopSong topSong = arrTopSong.get(i);
        Picasso.get().load(topSong.getHinhTopSong()).into(viewHolder.imgTopSong);
        viewHolder.txtNameTopSong.setText(topSong.getTenTopSong());
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
            imgTopSong = itemView.findViewById(R.id.imgtopsong);
            txtNameTopSong = itemView.findViewById(R.id.txtnametopsong);

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
