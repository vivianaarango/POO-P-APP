package com.project.poop.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.poop.Fragments.CalendarFragment;
import com.project.poop.Fragments.ContentFragment;
import com.project.poop.Fragments.DateFragment;
import com.project.poop.Fragments.HomeFragment;
import com.project.poop.Fragments.ListFragment;
import com.project.poop.Fragments.PlayFragment;
import com.project.poop.Fragments.ProfileFragment;
import com.project.poop.R;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setBackgroundColor(getResources().getColor(R.color.white));
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();*/

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        drawerLayout.addDrawerListener(this);

        View header = navigationView.getHeaderView(0);
        //header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
        /*    @Override
            public void onClick(View view) {
                Toast.makeText(DrawerActivity.this, getString(R.string.app_name),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int title = 0;
        switch (menuItem.getItemId()) {
            case R.id.nav_play:
                title = 0;
                setFragment(title);
                break;
            case R.id.nav_search:
                title = 1;
                setFragment(title);
                break;
            case R.id.nav_calendar:
                title = 2;
                setFragment(title);
                break;
            case R.id.nav_notes:
                title = 3;
                setFragment(title);
                break;
            case R.id.nav_profile:
                title = 5;
                setFragment(title);
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }

        //setTitle(getString(title));

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        //cambio en la posición del drawer
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {
        //el drawer se ha abierto completamente
        //Toast.makeText(this, getString(R.string.navigation_drawer_open),
        //        Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawerClosed(@NonNull View view) {
        //el drawer se ha cerrado completamente
    }

    @Override
    public void onDrawerStateChanged(int i) {
        //cambio de estado, puede ser STATE_IDLE, STATE_DRAGGING or STATE_SETTLING
    }

    @SuppressLint("NewApi")
    public void setFragment(int position) {
        //MediaPlayer mp = MediaPlayer.create(this, R.raw.item);
        switch (position) {
            case 0:
                PlayFragment nextFrag = new PlayFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, nextFrag)
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                ListFragment listFragment = new ListFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, listFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                CalendarFragment calendarFragment = null;
                calendarFragment = new CalendarFragment();

                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, calendarFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                HomeFragment homeFragment = new HomeFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, homeFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case 5:
                ProfileFragment profileFragment = new ProfileFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, profileFragment)
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }

    public void setTab (int title) {
        Fragment fragment = ContentFragment.newInstance(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
    }

}
