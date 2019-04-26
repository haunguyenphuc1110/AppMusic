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

import com.example.appmusic.Activity.CategoryListActivity;
import com.example.appmusic.Model.ChuDe;
import com.example.appmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThemeCategoryAdapter extends RecyclerView.Adapter<ThemeCategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<ChuDe> arrChuDe;

    public ThemeCategoryAdapter(Context context, ArrayList<ChuDe> arrChuDe) {
        this.context = context;
        this.arrChuDe = arrChuDe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_theme_category_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChuDe chuDe = arrChuDe.get(i);
        viewHolder.txtNameThemeCategory.setText(chuDe.getTenChuDe());
        Picasso.with(context).load(chuDe.getHinhChuDe()).into(viewHolder.imgThemeCategory);
    }

    @Override
    public int getItemCount() {
        return arrChuDe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgThemeCategory;
        TextView txtNameThemeCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThemeCategory = itemView.findViewById(R.id.imgchudetheloai);
            txtNameThemeCategory = itemView.findViewById(R.id.txttenchudetheloai);

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
