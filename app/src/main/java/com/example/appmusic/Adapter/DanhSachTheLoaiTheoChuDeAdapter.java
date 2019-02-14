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
import com.example.appmusic.Activity.DanhSachTheLoaiTheoChuDeActivity;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.Model.TheLoai;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachTheLoaiTheoChuDeAdapter extends RecyclerView.Adapter<DanhSachTheLoaiTheoChuDeAdapter.ViewHolder> {

    Context context;
    ArrayList<TheLoai> arrTheLoai;

    public DanhSachTheLoaiTheoChuDeAdapter(Context context, ArrayList<TheLoai> arrTheLoai) {
        this.context = context;
        this.arrTheLoai = arrTheLoai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_danh_sach_the_loai_theo_chu_de,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TheLoai theLoai = arrTheLoai.get(i);
        viewHolder.txtTenTheLoaiTheoChuDe.setText(theLoai.getTenTheLoai());
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(viewHolder.imgTheLoaiTheoChuDe);
    }

    @Override
    public int getItemCount() {
        return arrTheLoai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgTheLoaiTheoChuDe;
        TextView txtTenTheLoaiTheoChuDe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTheLoaiTheoChuDe = itemView.findViewById(R.id.imgtheloaitheochude);
            txtTenTheLoaiTheoChuDe = itemView.findViewById(R.id.txttentheloaitheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhSachBaiHatActivity.class);
                    intent.putExtra("idtheloai", arrTheLoai.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
