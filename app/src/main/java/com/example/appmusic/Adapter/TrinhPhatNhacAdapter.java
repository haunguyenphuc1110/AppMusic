package com.example.appmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;

import java.util.ArrayList;

public class TrinhPhatNhacAdapter extends RecyclerView.Adapter<TrinhPhatNhacAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHat> arrBaiHat;

    public TrinhPhatNhacAdapter(Context context, ArrayList<BaiHat> arrBaiHat) {
        this.context = context;
        this.arrBaiHat = arrBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_phat_nhac, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BaiHat baihat = arrBaiHat.get(i);
        viewHolder.txtIndex.setText(i + 1 + "");
        viewHolder.txtTenBaiHat.setText(baihat.getTenBaiHat());
        viewHolder.txtCaSi.setText(baihat.getCaSi());
    }

    @Override
    public int getItemCount() {
        return arrBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtIndex, txtTenBaiHat, txtCaSi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.txtphatnhacindex);
            txtTenBaiHat = itemView.findViewById(R.id.txttenbaihatduocphat);
            txtCaSi = itemView.findViewById(R.id.txttencasibaihat);
        }
    }
}
