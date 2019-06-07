package com.project.poop.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.project.poop.Fragments.SimpleFragmentPagerAdapter;
import com.project.poop.R;

public class PrincipalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.game_question);
        tabLayout.getTabAt(1).setIcon(R.drawable.study1);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_calendario);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_cuaderno);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) tab.setCustomView(R.layout.view_home_tab);
        }
    }
}
