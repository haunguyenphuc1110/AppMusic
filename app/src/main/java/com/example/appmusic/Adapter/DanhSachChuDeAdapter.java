package com.example.appmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appmusic.Activity.DanhSachTheLoaiTheoChuDeActivity;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachChuDeAdapter extends RecyclerView.Adapter<DanhSachChuDeAdapter.ViewHolder> {

    Context context;
    ArrayList<ChuDe> arrChuDe;

    public DanhSachChuDeAdapter(Context context, ArrayList<ChuDe> arrChuDe) {
        this.context = context;
        this.arrChuDe = arrChuDe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_danh_sach_chude,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChuDe chude = arrChuDe.get(i);
        Picasso.with(context).load(chude.getHinhChuDe()).into(viewHolder.imgChuDe);
    }

    @Override
    public int getItemCount() {
        return arrChuDe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgChuDe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgChuDe = itemView.findViewById(R.id.imgdanhsachchude);
            imgChuDe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachTheLoaiTheoChuDeActivity.class);
                    intent.putExtra("chude", arrChuDe.get(getPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
