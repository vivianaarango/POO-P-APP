package com.project.poop.Fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.poop.R;
import com.project.poop.activities.GameActivity;
import com.project.poop.activities.LoginActivity;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.calendar.ResponseDate;
import com.project.poop.models.themes.ResponseTheme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Integer.parseInt;


public class ProfileFragment extends Fragment {

    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;
    private TextView name, email, level, phone, result1, result2, result3, result4;
    private Button off;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);
        name = view.findViewById(R.id.txt_name);
        email = view.findViewById(R.id.txt_email);

        result1 = view.findViewById(R.id.textEasy);
        result2 = view.findViewById(R.id.textMedium);
        result3 = view.findViewById(R.id.textHard);
        result4 = view.findViewById(R.id.textExpert);

        initData();
        setProgress();

        return view;
    }

    private void initData() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Base.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        retrofitIR = retrofit.create(InterfaceRetrofit.class);

        checkConexion = new CheckConexion(thiscontext);

        name.setText(manageSharedPreferences.getUserName());
        email.setText(manageSharedPreferences.getUserEmail());
        
        //level.setText(R.string.easy);

        /*if (manageSharedPreferences.getMedium().equals("1")){
            level.setText(R.string.medium);
        }

        if (manageSharedPreferences.getHard().equals("1")){
            level.setText(R.string.hard);
        }

        if (manageSharedPreferences.getExpert().equals("1")){
            level.setText(R.string.expert);
        }*/
    }

    public void setProgress() {
        result1.setText(manageSharedPreferences.getEasyPercent()+"%");
        result2.setText(manageSharedPreferences.getMediumPercent()+"%");
        result3.setText(manageSharedPreferences.getHardPercent()+"%");
        result4.setText(manageSharedPreferences.getExpertPercent()+"%");

        Integer a = parseInt(manageSharedPreferences.getEasyPercent());
        Integer b = parseInt(manageSharedPreferences.getMediumPercent());
        Integer c = parseInt(manageSharedPreferences.getHardPercent());
        Integer d = parseInt(manageSharedPreferences.getExpertPercent());

        if (a >= 0 && a < 20) {
            result1.setBackgroundResource(R.drawable.btn_red);
        } else if (a > 20 && a < 65) {
            result1.setBackgroundResource(R.drawable.btn_yellow);
        } else {
            result1.setBackgroundResource(R.drawable.btn_accent);
        }

        if (b >= 0 && b < 20) {
            result2.setBackgroundResource(R.drawable.btn_red);
        } else if (b > 20 && b < 65) {
            result2.setBackgroundResource(R.drawable.btn_yellow);
        } else {
            result2.setBackgroundResource(R.drawable.btn_accent);
        }

        if (c >= 0 && c < 20) {
            result3.setBackgroundResource(R.drawable.btn_red);
        } else if (c > 20 && c < 65) {
            result3.setBackgroundResource(R.drawable.btn_yellow);
        } else {
            result3.setBackgroundResource(R.drawable.btn_accent);
        }

        if (d >= 0 && d < 20) {
            result4.setBackgroundResource(R.drawable.btn_red);
        } else if (d > 20 && d < 65) {
            result4.setBackgroundResource(R.drawable.btn_yellow);
        } else {
            result4.setBackgroundResource(R.drawable.btn_accent);
        }

    }
}
