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
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistListAdapter extends RecyclerView.Adapter<PlaylistListAdapter.ViewHolder> {

    Context context;
    ArrayList<Playlist> arrPlaylist;

    public PlaylistListAdapter(Context context, ArrayList<Playlist> arrPlaylist) {
        this.context = context;
        this.arrPlaylist = arrPlaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_playlist_list,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Playlist playlist = arrPlaylist.get(i);
        viewHolder.txtTenPlaylist.setText(playlist.getTen());
        Picasso.with(context).load(playlist.getHinhIcon()).into(viewHolder.imgPlaylist);
    }

    @Override
    public int getItemCount() {
        return arrPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPlaylist;
        TextView txtTenPlaylist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlaylist = itemView.findViewById(R.id.imgdanhsachplaylist);
            txtTenPlaylist = itemView.findViewById(R.id.txttenplaylist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SongListActivity.class);
                    intent.putExtra("itemplaylist", arrPlaylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
