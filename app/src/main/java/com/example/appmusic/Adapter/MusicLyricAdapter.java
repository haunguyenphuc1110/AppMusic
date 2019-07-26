package com.example.appmusic.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.R;

import java.util.ArrayList;


public class MusicLyricAdapter extends RecyclerView.Adapter<MusicLyricAdapter.ViewHolder>{

    Context context;
    ArrayList<String> rowLyrics;

    public MusicLyricAdapter(Context context, ArrayList<String> rowLyrics) {
        this.context = context;
        this.rowLyrics = rowLyrics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_lyric, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtLoiBaiHat.setText(rowLyrics.get(i));
    }

    @Override
    public int getItemCount() {
        return rowLyrics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtLoiBaiHat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoiBaiHat = itemView.findViewById(R.id.txtlyric);
        }
    }

}
