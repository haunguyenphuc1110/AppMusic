package com.example.appmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.Model.Playlist;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }

    class ViewHolder{
        TextView txtNamePlaylist;
        ImageView imgBackgroundPlaylist;
        ImageView imgPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_playlist,null);
            viewHolder = new ViewHolder();
            viewHolder.txtNamePlaylist = convertView.findViewById(R.id.txtnameplaylist);
            viewHolder.imgPlaylist = convertView.findViewById(R.id.imgplaylist);
            viewHolder.imgBackgroundPlaylist = convertView.findViewById(R.id.imgbackgroundplaylist);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhPlaylist()).into(viewHolder.imgBackgroundPlaylist);
        Picasso.with(getContext()).load(playlist.getHinhIcon()).into(viewHolder.imgPlaylist);
        viewHolder.txtNamePlaylist.setText(playlist.getTen());
        return convertView;
    }
}
