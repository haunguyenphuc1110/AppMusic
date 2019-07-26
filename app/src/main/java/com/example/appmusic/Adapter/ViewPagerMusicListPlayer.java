package com.example.appmusic.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerMusicListPlayer extends FragmentPagerAdapter {

    public final ArrayList<Fragment> arrFragment = new ArrayList<>();

    public ViewPagerMusicListPlayer(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return arrFragment.get(i);
    }

    @Override
    public int getCount() {
        return arrFragment.size();
    }

    public void addFragment(Fragment fragment){
        arrFragment.add(fragment);
    }
}
