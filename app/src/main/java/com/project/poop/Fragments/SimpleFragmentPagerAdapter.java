package com.project.poop.Fragments;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.poop.R;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    TabLayout mTabLayout;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PlayFragment();
        } else if (position == 1){
            return new ListFragment();
        } else if (position == 2){
            return new CalendarFragment();
        } else {
            return new HomeFragment();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab

}