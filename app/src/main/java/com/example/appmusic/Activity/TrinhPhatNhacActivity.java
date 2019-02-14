package com.example.appmusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appmusic.Adapter.ViewPagerPhatDanhSachNhac;
import com.example.appmusic.Fragment.Fragment_Dia_Nhac;
import com.example.appmusic.Fragment.Fragment_Phat_Danh_Sach_Nhac;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class TrinhPhatNhacActivity extends AppCompatActivity {

    Toolbar toolBarPhatNhac;
    SeekBar skTime;
    TextView txtTime, txtTotalTime;
    ImageButton imgPlay, imgNext, imgPrevious, imgRandom, imgRepeat;
    ViewPager viewPagerPhatNhac;

    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Phat_Danh_Sach_Nhac fragment_phat_danh_sach_nhac;

    MediaPlayer mediaPlayer;

    int position = 0;
    boolean isRepeat = false;
    boolean isRandom = false;
    boolean isNext = false;


    public static ArrayList<BaiHat> arrBaiHatCommon = new ArrayList<>();
    public static ViewPagerPhatDanhSachNhac adapterPhatDanhSachNhac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinh_phat_nhac);


        //Kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getDataFromIntent();

        addControls();

        addEvents();

    }

    @Override
    public void onBackPressed() //Tat bai hat khi nhan nut Back tren dien thoai
    {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            arrBaiHatCommon.clear();
        }
        super.onBackPressed();
    }


    @Override
    public void onPause() //Ngung bai hat khi nhan nut Back tren dien thoai
    {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            arrBaiHatCommon.clear();
        }
        super.onPause();
    }

    private void addEvents() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapterPhatDanhSachNhac.getItem(1) != null) {
                    if (arrBaiHatCommon.size() > 0) {
                        fragment_dia_nhac.playNhac(arrBaiHatCommon.get(0).getHinhBaiHat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay);
                } else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause);
                }
            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRepeat == false) {
                    //Neu nut Random dang bat
                    if (isRandom == true) {
                        isRandom = false;//Set lai bang false
                        imgRepeat.setImageResource(R.drawable.iconsyned);
                        imgRandom.setImageResource(R.drawable.iconsuffle);
                    }
                    //Set binh thuong neu nut Random khong bat
                    imgRepeat.setImageResource(R.drawable.iconsyned);
                    isRepeat = true;
                } else {
                    imgRepeat.setImageResource(R.drawable.iconrepeat);
                    isRepeat = false;
                }
            }
        });

        imgRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRandom == false) {
                    //Neu nut Repeat dang bat
                    if (isRepeat == true) {
                        isRepeat = false;//Set lai bang false
                        imgRandom.setImageResource(R.drawable.iconshuffled);
                        imgRepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    //Set binh thuong neu nut Repeat khong bat
                    imgRandom.setImageResource(R.drawable.iconshuffled);
                    isRandom = true;
                } else {
                    imgRandom.setImageResource(R.drawable.iconsuffle);
                    isRandom = false;
                }
            }
        });

        skTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrBaiHatCommon.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < arrBaiHatCommon.size()) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (isRepeat == true) {
                            if (position == 0) {
                                position = arrBaiHatCommon.size();
                            }
                            position -= 1;
                        }
                        if (isRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(arrBaiHatCommon.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (arrBaiHatCommon.size() - 1)) {
                            position = 0;
                        }
                        new MusicPlayer().execute(arrBaiHatCommon.get(position).getLinkBaiHat());
                        fragment_dia_nhac.playNhac(arrBaiHatCommon.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(arrBaiHatCommon.get(position).getTenBaiHat());
                        updateTimeSong();
                    }
                }
                imgPrevious.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPrevious.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrBaiHatCommon.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (position < arrBaiHatCommon.size()) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position--;

                        if (position < 0) {
                            position = arrBaiHatCommon.size() - 1;
                        }

                        if (isRepeat == true) {
                            position += 1;
                        }
                        if (isRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(arrBaiHatCommon.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        new MusicPlayer().execute(arrBaiHatCommon.get(position).getLinkBaiHat());
                        fragment_dia_nhac.playNhac(arrBaiHatCommon.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(arrBaiHatCommon.get(position).getTenBaiHat());
                        updateTimeSong();
                    }
                }
                imgPrevious.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPrevious.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });

        toolBarPhatNhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
                arrBaiHatCommon.clear();
            }
        });
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        arrBaiHatCommon.clear();

        if (intent != null) {
            if (intent.hasExtra("cakhuc")) {
                BaiHat baihat = intent.getParcelableExtra("cakhuc");
                arrBaiHatCommon.add(baihat);
            }

            if (intent.hasExtra("danhsachcakhuc")) {
                ArrayList<BaiHat> arrBaiHat = intent.getParcelableArrayListExtra("danhsachcakhuc");
                arrBaiHatCommon = arrBaiHat;
            }
        }
    }

    private void addControls() {
        //Khoi tao cac control
        toolBarPhatNhac = findViewById(R.id.toolbartrinhphatnhac);
        skTime = findViewById(R.id.seekbarcakhuc);
        txtTime = findViewById(R.id.txttimecakhuc);
        txtTotalTime = findViewById(R.id.txttotaltimecakhuc);
        imgPlay = findViewById(R.id.imgbuttonplay);
        imgNext = findViewById(R.id.imgbuttonnext);
        imgPrevious = findViewById(R.id.imgbuttonpreview);
        imgRandom = findViewById(R.id.imgbuttonrandom);
        imgRepeat = findViewById(R.id.imgbuttonrepeat);
        viewPagerPhatNhac = findViewById(R.id.viewpagertrinhphatnhac);
        setSupportActionBar(toolBarPhatNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarPhatNhac.setTitleTextColor(Color.WHITE);


        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_phat_danh_sach_nhac = new Fragment_Phat_Danh_Sach_Nhac();


        adapterPhatDanhSachNhac = new ViewPagerPhatDanhSachNhac(getSupportFragmentManager());

        adapterPhatDanhSachNhac.addFragment(fragment_phat_danh_sach_nhac);
        adapterPhatDanhSachNhac.addFragment(fragment_dia_nhac);


        viewPagerPhatNhac.setAdapter(adapterPhatDanhSachNhac);

        fragment_dia_nhac = (Fragment_Dia_Nhac) adapterPhatDanhSachNhac.getItem(1);

        if (arrBaiHatCommon.size() > 0) {
            getSupportActionBar().setTitle(arrBaiHatCommon.get(0).getTenBaiHat());
            new MusicPlayer().execute(arrBaiHatCommon.get(0).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause);

        }
    }

    class MusicPlayer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                //Tranh truong hop vao view phat nhac ma nhac da ket thuc
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.setDataSource(s);
                mediaPlayer.prepare();//Co ham nay moi chay duoc nhac
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            setTimeSong();
            updateTimeSong();
        }

    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    skTime.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            isNext = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        }, 300);

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNext == true) {
                    if (position < arrBaiHatCommon.size()) {
                        imgPlay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (isRepeat == true) {
                            if (position == 0) {
                                position = arrBaiHatCommon.size();
                            }
                            position -= 1;
                        }
                        if (isRandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(arrBaiHatCommon.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (arrBaiHatCommon.size() - 1)) {
                            position = 0;
                        }
                        new MusicPlayer().execute(arrBaiHatCommon.get(position).getLinkBaiHat());
                        fragment_dia_nhac.playNhac(arrBaiHatCommon.get(position).getLinkBaiHat());
                        getSupportActionBar().setTitle(arrBaiHatCommon.get(position).getTenBaiHat());
                    }
                    imgPrevious.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPrevious.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    }, 5000);
                    isNext = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void setTimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        skTime.setMax(mediaPlayer.getDuration());
    }
}
