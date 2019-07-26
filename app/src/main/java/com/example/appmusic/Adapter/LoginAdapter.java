package com.example.appmusic.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

//This class controls fragments. In this case, Fragment Login is used
public class LoginAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayFragment = new ArrayList<>();
    private ArrayList<String> arrayTitle = new ArrayList<>();

    public LoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return arrayFragment.get(i);
    }

    @Override
    public int getCount() {
        return arrayFragment.size();
    }

    public void addFragment(Fragment fragment, String title){
        arrayFragment.add(fragment);
        arrayTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayTitle.get(position);
    }
}
