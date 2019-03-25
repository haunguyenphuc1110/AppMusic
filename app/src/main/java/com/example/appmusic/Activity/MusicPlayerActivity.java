package com.example.appmusic.Activity;

import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appmusic.Adapter.ViewPagerMusicListPlayer;
import com.example.appmusic.Fragment.Fragment_Music_Disc;
import com.example.appmusic.Fragment.Fragment_Music_Lyric;
import com.example.appmusic.Fragment.Fragment_Playing_List;
import com.example.appmusic.Model.BaiHat;
import com.example.appmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MusicPlayerActivity extends AppCompatActivity {

    Toolbar toolBarPhatNhac;
    SeekBar skTime;
    TextView txtTime, txtTotalTime;
    ImageButton imgPlay, imgNext, imgPrevious, imgRandom, imgRepeat;
    ViewPager viewPagerPhatNhac;//View pager chua cac fragment
    ProgressDialog progressDialog;//Show dialog

    Fragment_Music_Disc fragment_music_disc;
    Fragment_Playing_List fragment_playing_list;
    Fragment_Music_Lyric fragment_music_lyric;

    MediaPlayer mediaPlayer;

    int position = 0;

    boolean isRepeat = false;
    boolean isRandom = false;
    boolean isNext = false;


    public static ArrayList<BaiHat> arrBaiHatCommon = new ArrayList<>();//Share parameter with 3 Fragment
    public static ViewPagerMusicListPlayer viewPagerMusicListPlayer;//Hold 3 Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);


        //Kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getDataFromIntent();

        addControls();

        addEvents();

        new InitView().execute();
    }

    @Override
    public void onBackPressed() //Stop song when press Back Button on SmartPhone
    {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            arrBaiHatCommon.clear();
        }

        finish();
        super.onBackPressed();
    }

    @Override
    public void onPause() //Stop song when press Back Button on SmartPhone
    {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            arrBaiHatCommon.clear();
        }
        finish();
        super.onPause();
    }

    private void addEvents() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            if (viewPagerMusicListPlayer.getItem(1) != null) {
                if (arrBaiHatCommon.size() > 0) {
                    fragment_music_disc.setImage(arrBaiHatCommon.get(0).getHinhBaiHat());
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
                imgPlay.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.ic_pause);
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
                    imgRepeat.setImageResource(R.drawable.ic_syned);
                    imgRandom.setImageResource(R.drawable.ic_suffle);
                }
                //Set binh thuong neu nut Random khong bat
                imgRepeat.setImageResource(R.drawable.ic_syned);
                isRepeat = true;
            } else {
                imgRepeat.setImageResource(R.drawable.ic_repeat);
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
                    imgRandom.setImageResource(R.drawable.ic_shuffled);
                    imgRepeat.setImageResource(R.drawable.ic_repeat);
                }
                //Set binh thuong neu nut Repeat khong bat
                imgRandom.setImageResource(R.drawable.ic_shuffled);
                isRandom = true;
            } else {
                imgRandom.setImageResource(R.drawable.ic_suffle);
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
                    imgPlay.setImageResource(R.drawable.ic_pause);
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
                    fragment_music_disc.setImage(arrBaiHatCommon.get(position).getHinhBaiHat());
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
                    imgPlay.setImageResource(R.drawable.ic_pause);
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
                    fragment_music_disc.setImage(arrBaiHatCommon.get(position).getHinhBaiHat());
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

        skTime = findViewById(R.id.seekbarcakhuc);
        txtTime = findViewById(R.id.txttimecakhuc);
        txtTotalTime = findViewById(R.id.txttotaltimecakhuc);
        imgPlay = findViewById(R.id.imgbuttonplay);
        imgNext = findViewById(R.id.imgbuttonnext);
        imgPrevious = findViewById(R.id.imgbuttonpreview);
        imgRandom = findViewById(R.id.imgbuttonrandom);
        imgRepeat = findViewById(R.id.imgbuttonrepeat);

        viewPagerPhatNhac = findViewById(R.id.viewpagertrinhphatnhac);

        toolBarPhatNhac = findViewById(R.id.toolbartrinhphatnhac);
        setSupportActionBar(toolBarPhatNhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarPhatNhac.setTitleTextColor(Color.WHITE);


        fragment_music_disc = new Fragment_Music_Disc();
        fragment_playing_list = new Fragment_Playing_List();
        fragment_music_lyric = new Fragment_Music_Lyric();


        viewPagerMusicListPlayer = new ViewPagerMusicListPlayer(getSupportFragmentManager());

        viewPagerMusicListPlayer.addFragment(fragment_playing_list);
        viewPagerMusicListPlayer.addFragment(fragment_music_disc);
        viewPagerMusicListPlayer.addFragment(fragment_music_lyric);


        viewPagerPhatNhac.setAdapter(viewPagerMusicListPlayer);

        fragment_music_disc = (Fragment_Music_Disc) viewPagerMusicListPlayer.getItem(1);

        if (arrBaiHatCommon.size() > 0) {
            getSupportActionBar().setTitle(arrBaiHatCommon.get(0).getTenBaiHat());
            new MusicPlayer().execute(arrBaiHatCommon.get(0).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.ic_pause);
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
                    imgPlay.setImageResource(R.drawable.ic_pause);
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
                    fragment_music_disc.setImage(arrBaiHatCommon.get(position).getLinkBaiHat());
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

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

    class InitView extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
