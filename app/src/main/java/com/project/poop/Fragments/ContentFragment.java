package com.project.poop.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.project.poop.Fragments.SimpleFragmentPagerAdapter;

import com.project.poop.R;

/**
 * Created by dani on 28/12/16.
 */
public class ContentFragment extends Fragment {

    private Context thiscontext;
    private static final String TEXT = "text";

    public static ContentFragment newInstance(String text) {
        ContentFragment frag = new ContentFragment();

        Bundle args = new Bundle();
        args.putString(TEXT, text);
        frag.setArguments(args);


        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_fragment, container, false);

        thiscontext = getActivity();
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(thiscontext, getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_answer);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_list);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_calendario);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_cuaderno);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(R.layout.view_home_tab);
        }

        return view;
    }
}

