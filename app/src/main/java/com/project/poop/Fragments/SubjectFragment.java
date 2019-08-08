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
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.poop.R;
import com.project.poop.activities.GameActivity;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.libraries.MinMaxFilter;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.calendar.ResponseDate;
import com.project.poop.models.qualification.ResponseCreateQualification;
import com.project.poop.models.qualification.ResponseQualification;
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


public class SubjectFragment extends Fragment {

    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;
    private CheckBox corte1, corte2, corte3;
    private EditText corte_1, corte_2, corte_3, name;
    private String a, b, c;
    private Button save;

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject, container, false);
        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);

        corte1 = view.findViewById(R.id.validate_cut_one);
        corte2 = view.findViewById(R.id.validate_cut_two);
        corte3 = view.findViewById(R.id.validate_cut_three);

        corte_1 = view.findViewById(R.id.cut_one);
        corte_2 = view.findViewById(R.id.cut_two);
        corte_3 = view.findViewById(R.id.cut_three);

        name = view.findViewById(R.id.subject);
        save = view.findViewById(R.id.saveSubject);

        corte_1.setVisibility(View.GONE);
        corte_2.setVisibility(View.GONE);
        corte_3.setVisibility(View.GONE);

        corte1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCut();
            }
        });
        corte2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCut();
            }
        });
        corte3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCut();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubject();
            }
        });

        corte_1.setFilters(new InputFilter[]{ new MinMaxFilter("0", "50")});
        corte_2.setFilters(new InputFilter[]{ new MinMaxFilter("0", "50")});
        corte_3.setFilters(new InputFilter[]{ new MinMaxFilter("0", "50")});

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


    }

    private void createSubject() {
        if (corte1.isChecked() == true ){
            a = corte_1.getText().toString();
        } else {
            a = "-1";
        }

        if (corte2.isChecked() == true ){
            b = corte_2.getText().toString();
        } else {
            b = "-1";
        }

        if (corte3.isChecked() == true ){
            c = corte_3.getText().toString();
        } else {
            c = "-1";
        }

        if (checkConexion.isConnected()) {
            Call<ResponseCreateQualification> call = retrofitIR.createQualification(manageSharedPreferences.getUserId(), name.getText().toString(), a, b, c);
            //asynchronous call
            call.enqueue(callBackResponseCreateQualification);
        } else {
            checkConexion.check();
        }
    }

    private Callback<ResponseCreateQualification> callBackResponseCreateQualification = new Callback<ResponseCreateQualification>() {
        @Override
        public void onResponse(Call<ResponseCreateQualification> call, Response<ResponseCreateQualification> response) {

            int code = response.code();
            if (code == 200) {
                ResponseCreateQualification responseCreateQualification = response.body();
                if (responseCreateQualification != null) {
                    if (responseCreateQualification.getStatus() == 200) {
                        Toast.makeText(thiscontext, responseCreateQualification.getMessage(), Toast.LENGTH_SHORT).show();

                        MediaPlayer mp = MediaPlayer.create(thiscontext, R.raw.btn_one);
                        mp.start();

                        HomeFragment nextFrag= new HomeFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_subject, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                    } else {
                        Toast.makeText(thiscontext, "Error al crear materias", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseCreateQualification> call, Throwable t) {

        }
    };

    public void setCut() {

        if (corte1.isChecked() == true ){
            corte_1.setVisibility(View.VISIBLE);
        } else {
            corte_1.setVisibility(View.GONE);
        }

        if (corte2.isChecked() == true ){
            corte_2.setVisibility(View.VISIBLE);
        } else {
            corte_2.setVisibility(View.GONE);
        }

        if (corte3.isChecked() == true ){
            corte_3.setVisibility(View.VISIBLE);
        } else {
            corte_3.setVisibility(View.GONE);
        }
    }

}
