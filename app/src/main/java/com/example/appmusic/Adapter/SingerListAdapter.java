package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.Activity.SongListActivity;
import com.example.appmusic.Model.CaSi;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingerListAdapter extends RecyclerView.Adapter<SingerListAdapter.ViewHolder> {

    Context context;
    ArrayList<CaSi> arrCaSi;

    public SingerListAdapter(Context context, ArrayList<CaSi> arrCaSi) {
        this.context = context;
        this.arrCaSi = arrCaSi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_singer_list,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CaSi caSi = arrCaSi.get(i);
        viewHolder.txtNameCaSi.setText(caSi.getTenCaSi());
        Picasso.get().load(caSi.getHinhIcon()).into(viewHolder.imgCaSi);
    }

    @Override
    public int getItemCount() {
        return arrCaSi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgCaSi;
        TextView txtNameCaSi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCaSi = itemView.findViewById(R.id.imgdanhsachcasi);
            txtNameCaSi = itemView.findViewById(R.id.txttencasi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SongListActivity.class);
                    intent.putExtra("itemcasi", arrCaSi.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
