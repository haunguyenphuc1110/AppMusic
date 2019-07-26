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

import com.example.appmusic.Activity.CategoryListActivity;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThemeListAdapter extends RecyclerView.Adapter<ThemeListAdapter.ViewHolder> {

    Context context;
    ArrayList<ChuDe> arrChuDe;

    public ThemeListAdapter(Context context, ArrayList<ChuDe> arrChuDe) {
        this.context = context;
        this.arrChuDe = arrChuDe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_theme_list,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChuDe chude = arrChuDe.get(i);
        viewHolder.txtTenChuDe.setText(chude.getTenChuDe());
        Picasso.get().load(chude.getHinhChuDe()).into(viewHolder.imgChuDe);
    }

    @Override
    public int getItemCount() {
        return arrChuDe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgChuDe;
        TextView txtTenChuDe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChuDe = itemView.findViewById(R.id.imgdanhsachchude);
            txtTenChuDe = itemView.findViewById(R.id.txttenchude);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CategoryListActivity.class);
                    intent.putExtra("chude", arrChuDe.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
