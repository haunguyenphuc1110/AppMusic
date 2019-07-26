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
import com.example.appmusic.Model.Album;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder>{

    Context context;
    ArrayList<Album> arrAlbum;

    public AlbumListAdapter(Context context, ArrayList<Album> arrAlbum) {
        this.context = context;
        this.arrAlbum = arrAlbum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_album_list,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Album album = arrAlbum.get(i);
        viewHolder.txtTenAlbum.setText(album.getTenAlbum());
        viewHolder.txtTenCaSiAlbum.setText(album.getTenCaSiAlbum());
        Picasso.get().load(album.getHinhAlbum()).into(viewHolder.imgAlbum);
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
                    Intent intent = new Intent(context, SongListActivity.class);
                    intent.putExtra("itemalbum", arrAlbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
