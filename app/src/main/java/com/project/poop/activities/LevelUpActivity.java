package com.project.poop.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.poop.R;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;


/**
 * A login screen that offers login via email/password.
 */
public class LevelUpActivity extends AppCompatActivity {

    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private Context context;
    private Typeface custom_font;
    private Typeface custom_font_text;
    private TextView levelUpText;
    private TextView levelUpTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelup);

        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/orange juice 2.0.ttf");
        custom_font_text = Typeface.createFromAsset(getAssets(),  "fonts/Louis George Cafe Bold.ttf");

        levelUpText = (TextView) findViewById(R.id.levelUp);
        levelUpTextDescription = (TextView) findViewById(R.id.levelUpText);
        levelUpText.setTypeface(custom_font_text);
        levelUpTextDescription.setTypeface(custom_font_text);

        manageSharedPreferences = new ManageSharedPreferences(this);
        progress = new ManagerProgressDialog(this);
        context = this;
    }

    public void btnMain(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_one);
        mp.start();

        manageSharedPreferences.setGameId("false");
        Intent intent = new Intent(context, DrawerActivity.class);
        startActivity(intent);
        finish();
    }
}

