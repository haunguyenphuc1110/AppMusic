package com.example.appmusic.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmusic.Activity.MainActivity;
import com.example.appmusic.Activity.MusicPlayerActivity;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicRecommendationAdapter extends RecyclerView.Adapter<MusicRecommendationAdapter.ViewHolder> {

    Context context;
    ArrayList<BaiHat> arrBaiHat;

    public MusicRecommendationAdapter(Context context, ArrayList<BaiHat> arrBaiHat) {
        this.context = context;
        this.arrBaiHat = arrBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_music_recommendation,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BaiHat baihat = arrBaiHat.get(i);
        viewHolder.txtTenBaiHat.setText(baihat.getTenBaiHat());
        viewHolder.txtTenCaSi.setText(baihat.getCaSi());
        Picasso.with(context).load(baihat.getHinhBaiHat()).into(viewHolder.imgHinhBaiHat);
    }

    @Override
    public int getItemCount() {
        return arrBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenBaiHat, txtTenCaSi, txtIndex;
        ImageView imgHinhBaiHat, imgLuotThich, imgAddPlaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenBaiHat = itemView.findViewById(R.id.txttenbathatgoiy);
            txtTenCaSi = itemView.findViewById(R.id.txttencasibathatgoiy);
            imgHinhBaiHat = itemView.findViewById(R.id.imgbaihatgoiy);
            imgLuotThich = itemView.findViewById(R.id.imgluotthichbaihatgoiy);
            imgAddPlaylist = itemView.findViewById(R.id.imgplaylistaddbaihatgoiy);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new SpotsDialog.Builder().setContext(context).setMessage("...").setCancelable(false).build();
                    Intent intent = new Intent(context, MusicPlayerActivity.class);
                    intent.putExtra("cakhuc", arrBaiHat.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imgLuotThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MainActivity.profile.getId() == null)//Check if user logged in or not
                        Toast.makeText(context,"Bạn chưa đăng nhập để thích bài hát này", Toast.LENGTH_SHORT).show();
                    else {
                        imgLuotThich.setImageResource(R.drawable.ic_loved);
                        DataService dataService = APIService.getService();
                        Call<String> callback = dataService.updateLuotThich("1", arrBaiHat.get(getPosition()).getIdBaiHat());
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String result = response.body();
                                if (result.equals("Success"))
                                    Toast.makeText(context, "Đã Thích", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        imgLuotThich.setEnabled(false);
                    }
                }
            });

            imgAddPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MainActivity.profile.getId() == null)//Check if user logged in or not
                        Toast.makeText(context,"Bạn chưa đăng nhập để thêm bài hát này", Toast.LENGTH_SHORT).show();
                    else {
                        addFavoritePlaylist(MainActivity.profile.getId(), arrBaiHat.get(getPosition()).getIdBaiHat());
                        notifyDataSetChanged();
                    }
                }
            });
        }

        private void addFavoritePlaylist(String id, String idBaiHat) {
            DataService dataService = APIService.getService();
            Call<String> callback = dataService.insertUserFavoriteSong(id, idBaiHat);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String result = response.body();
                    if (result.equals("Success"))
                        Toast.makeText(context,"Thêm vào danh sách yêu thích thành công", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context,"Bài hát đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}
