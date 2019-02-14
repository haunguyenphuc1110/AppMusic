package com.example.appmusic.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerPhatDanhSachNhac extends FragmentPagerAdapter {

    public final ArrayList<Fragment> arrFragment = new ArrayList<>();

    public ViewPagerPhatDanhSachNhac(FragmentManager fm) {
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
