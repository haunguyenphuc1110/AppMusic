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
import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder>{

    Context context;
    ArrayList<Playlist> arrPlaylist;

    public PlaylistAdapter(Context context, ArrayList<Playlist> arrPlaylist) {
        this.context = context;
        this.arrPlaylist = arrPlaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_playlist_item,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Playlist playlist = arrPlaylist.get(i);
        viewHolder.txtTitlePlaylist.setText(playlist.getTen());
        Picasso.get().load(playlist.getHinhIcon()).into(viewHolder.imgPlaylist);
    }

    @Override
    public int getItemCount() {
        return arrPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPlaylist;
        TextView txtTitlePlaylist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlaylist = itemView.findViewById(R.id.imgplaylist);
            txtTitlePlaylist = itemView.findViewById(R.id.txtnameplaylist);
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
