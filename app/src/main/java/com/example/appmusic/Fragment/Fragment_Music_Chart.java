package com.example.appmusic.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appmusic.Adapter.MusicChartAdapter;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;
import com.example.appmusic.Service.APIService;
import com.example.appmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Music_Chart extends Fragment {

    View view;
    RecyclerView recyclerViewMusicChart;
    MusicChartAdapter musicChartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_music_chart, container,false);
        recyclerViewMusicChart = view.findViewById(R.id.recyclerviewbaihathot);

        getData();

        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.getBaiHatHot();
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> baiHats = (ArrayList<BaiHat>) response.body();
                musicChartAdapter = new MusicChartAdapter(getActivity(),baiHats);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewMusicChart.setLayoutManager(linearLayoutManager);
                recyclerViewMusicChart.setAdapter(musicChartAdapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
