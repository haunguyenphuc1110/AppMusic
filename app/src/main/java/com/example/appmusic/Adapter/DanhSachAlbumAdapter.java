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

import com.example.appmusic.Activity.DanhSachBaiHatActivity;
import com.example.appmusic.Model.Album;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachAlbumAdapter extends RecyclerView.Adapter<DanhSachAlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> arrAlbum;

    public DanhSachAlbumAdapter(Context context, ArrayList<Album> arrAlbum) {
        this.context = context;
        this.arrAlbum = arrAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_danh_sach_album,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Album album = arrAlbum.get(i);
        viewHolder.txtTenAlbum.setText(album.getTenAlbum());
        viewHolder.txtTenCaSiAlbum.setText(album.getTenCaSiAlbum());
        Picasso.with(context).load(album.getHinhAlbum()).into(viewHolder.imgAlbum);
    }

    @Override
    public int getItemCount() {
        return arrAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgAlbum;
        TextView txtTenAlbum, txtTenCaSiAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.imgdanhsachalbum);
            txtTenAlbum = itemView.findViewById(R.id.txttenalbum);
            txtTenCaSiAlbum = itemView.findViewById(R.id.txttencasialbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("itemalbum", arrAlbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
