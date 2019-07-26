package com.example.appmusic.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

public class MusicChartAdapter extends RecyclerView.Adapter<MusicChartAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> arrBaiHat;

    public MusicChartAdapter(Context context, ArrayList<BaiHat> arrBaiHat) {
        this.context = context;
        this.arrBaiHat = arrBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.row_music_chart,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BaiHat baihat = arrBaiHat.get(i);
        int index = i+1;
        if (i >= 9)
            viewHolder.txtIndex.setText(index+"");
        else
            viewHolder.txtIndex.setText("0"+index);
        viewHolder.txtTenBaiHat.setText(baihat.getTenBaiHat());
        viewHolder.txtTenCaSi.setText(baihat.getCaSi());
        viewHolder.txtLike.setText(baihat.getLuotThich());
        Picasso.get().load(baihat.getHinhBaiHat()).into(viewHolder.imgHinhBaiHat);

    }

    @Override
    public int getItemCount() {
        return arrBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenBaiHat, txtTenCaSi, txtIndex, txtLike;
        ImageView imgHinhBaiHat, imgLuotThich, imgAddPlaylist;
        ProgressDialog progressDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenBaiHat = itemView.findViewById(R.id.txttenbathathot);
            txtTenCaSi = itemView.findViewById(R.id.txttencasibathathot);
            txtIndex = itemView.findViewById(R.id.txtindexbaihathot);
            txtLike = itemView.findViewById(R.id.txtluotthichmusicchart);
            imgHinhBaiHat = itemView.findViewById(R.id.imgbaihathot);
            imgLuotThich = itemView.findViewById(R.id.imgluotthich);
            imgAddPlaylist = itemView.findViewById(R.id.imgplaylistaddrecommend);

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

        private void showProgressDialog(){
            if (progressDialog == null){
                progressDialog = new ProgressDialog(context);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }else{
                progressDialog.dismiss();
                progressDialog = null;
            }
        }

        private void hideProgressDialog() {
            if(progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}
