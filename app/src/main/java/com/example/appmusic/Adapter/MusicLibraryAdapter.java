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
import android.widget.Toast;

import com.example.appmusic.Activity.MainActivity;
import com.example.appmusic.Activity.MusicPlayerActivity;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicLibraryAdapter extends RecyclerView.Adapter<MusicLibraryAdapter.ViewHoler> {

    Context context;
    ArrayList<BaiHat> arrBaiHat;

    public MusicLibraryAdapter(Context context, ArrayList<BaiHat> arrBaiHat) {
        this.context = context;
        this.arrBaiHat = arrBaiHat;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_music_library,viewGroup, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler viewHoler, int i) {
        BaiHat baihat = arrBaiHat.get(i);
        viewHoler.txtTenBaiHat.setText(baihat.getTenBaiHat());
        viewHoler.txtTenCaSi.setText(baihat.getCaSi());
        Picasso.with(context).load(baihat.getHinhBaiHat()).into(viewHoler.imgHinhBaiHat);
    }

    @Override
    public int getItemCount() {
        return arrBaiHat.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder{

        TextView txtTenBaiHat, txtTenCaSi;
        ImageView imgLuotThich, imgHinhBaiHat, imgPlaylistRemove;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtTenBaiHat = itemView.findViewById(R.id.txtlibrarytenbaihat);
            txtTenCaSi = itemView.findViewById(R.id.txtlibrarytencasi);
            imgHinhBaiHat = itemView.findViewById(R.id.imglibraryhinhbaihat);
            imgPlaylistRemove = itemView.findViewById(R.id.imglibraryplaylistremove);
            imgLuotThich = itemView.findViewById(R.id.imglibraryluotthich);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MusicPlayerActivity.class);
                    intent.putExtra("cakhuc", arrBaiHat.get(getPosition()));
                    context.startActivity(intent);
                }
            });

            imgLuotThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgLuotThich.setImageResource(R.drawable.ic_loved);
                    DataService dataService = APIService.getService();
                    Call<String> callback =dataService.updateLuotThich("1", arrBaiHat.get(getPosition()).getIdBaiHat());
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
            });

            imgPlaylistRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeFavoritePlaylist(MainActivity.profile.getId(),arrBaiHat.get(getPosition()).getIdBaiHat());
                    arrBaiHat.remove(getPosition());
                    notifyDataSetChanged();
                }
            });
        }

        private void removeFavoritePlaylist(String id, String idBaiHat) {
            DataService dataService = APIService.getService();
            Call<String> callback = dataService.removeUserFavoriteSong(id, idBaiHat);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String result = response.body();
                    if (result.equals("Success"))
                        Toast.makeText(context,"Đã gỡ bài hát khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}
