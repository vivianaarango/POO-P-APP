package com.project.poop.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
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


public class ProfileFragment extends Fragment {

    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;
    private TextView name, email, level, phone;
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
        level = view.findViewById(R.id.txt_level);
        phone = view.findViewById(R.id.txt_phone);
        off = view.findViewById(R.id.off);

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(thiscontext, R.raw.btn_two);
                mp.start();

                manageSharedPreferences.clearAll();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        initData();

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
        phone.setText(manageSharedPreferences.getPhone());
        level.setText(R.string.easy);

        if (manageSharedPreferences.getMedium().equals("1")){
            level.setText(R.string.medium);
        }

        if (manageSharedPreferences.getHard().equals("1")){
            level.setText(R.string.hard);
        }

        if (manageSharedPreferences.getExpert().equals("1")){
            level.setText(R.string.expert);
        }
    }
}
