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
public class TimeOutActivity extends AppCompatActivity {

    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private Context context;
    private String difficulty;
    private String difficultyName;
    private Typeface custom_font;
    private Typeface custom_font_text;
    private TextView timeOutText;
    private TextView timeOutTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);

        manageSharedPreferences = new ManageSharedPreferences(this);
        progress = new ManagerProgressDialog(this);
        context = this;

        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/orange juice 2.0.ttf");
        custom_font_text = Typeface.createFromAsset(getAssets(),  "fonts/Louis George Cafe Bold.ttf");

        timeOutText = (TextView) findViewById(R.id.timeout);
        timeOutTextDescription = (TextView) findViewById(R.id.timeoutText);
        timeOutText.setTypeface(custom_font_text);
        timeOutTextDescription.setTypeface(custom_font_text);

        Bundle bundle = getIntent().getExtras();
        difficulty = bundle.getString("difficultyLevel");
        difficultyName = bundle.getString("difficultyName");
    }

    public void btnFail(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_one);
        mp.start();

        Bundle bundle = new Bundle();
        bundle.putString("difficultyLevel", difficulty);
        bundle.putString("difficultyName", difficultyName);
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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

