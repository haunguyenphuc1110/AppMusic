package com.example.appmusic.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appmusic.Interface.RecyclerViewClickListenerInterface;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;

import java.util.ArrayList;

public class PlayingListAdapter extends RecyclerView.Adapter<PlayingListAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHat> arrBaiHat;
    static RecyclerViewClickListenerInterface itemListener;

    public PlayingListAdapter(Context context, ArrayList<BaiHat> arrBaiHat, RecyclerViewClickListenerInterface itemListener) {
        this.context = context;
        this.arrBaiHat = arrBaiHat;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_playing_list, viewGroup, false);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtIndex, txtTenBaiHat, txtCaSi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.txtphatnhacindex);
            txtTenBaiHat = itemView.findViewById(R.id.txttenbaihatduocphat);
            txtCaSi = itemView.findViewById(R.id.txttencasibaihat);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getPosition());
        }
    }
}
