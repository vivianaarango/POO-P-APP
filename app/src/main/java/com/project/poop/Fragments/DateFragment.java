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


public class DateFragment extends Fragment {

    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;

    private TextView txtV_selectDate, txtV_selectHour;
    private DatePickerDialog.OnDateSetListener date ;
    private TimePickerDialog.OnTimeSetListener hour;
    final Calendar myCalendar  = Calendar.getInstance();
    private String fecha;
    private Button save;
    private EditText description;

    public DateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_date, container, false);

        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);
        txtV_selectDate = view.findViewById(R.id.txtv_reserver_date);
        txtV_selectHour = view.findViewById(R.id.txtv_reserver_hour);
        save = view.findViewById(R.id.saveDate);
        description = view.findViewById(R.id.description);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDate();
            }
        });

        txtV_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSelectDate();
            }
        });

        txtV_selectHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSelectHour();
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

        final long today = System.currentTimeMillis() - 1000;
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                /*Validate day of weekend*/
                String inputDateStr = String.format("%s/%s/%s", dayOfMonth, monthOfYear+1, year);
                Date inputDate = null;

                try {
                    inputDate = new SimpleDateFormat("yyyy/MM/dd").parse(inputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(inputDate);
                //dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();

                if (myCalendar.getTimeInMillis()<today){
                    //Make them try again
                    actionSelectDate();
                    Toast.makeText(thiscontext, "Fecha invalida, intenta de nuevo", Toast.LENGTH_LONG).show();
                }else{
                    update_txtV_reserver_date();
                }
            }
        };

        hour = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                //int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minuts = myCalendar.get(Calendar.MINUTE);
                update_txtV_reserver_hour(i, i1);
            }
        };
    }

    //here the user can to select a spesific date
    private void actionSelectDate() {
        new DatePickerDialog(thiscontext, R.style.DatePickerDialogTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    //here the user can to select a spesific hour
    private void actionSelectHour(){
        TimePickerDialog pickUpTime = new TimePickerDialog(thiscontext,R.style.DatePickerDialogTheme, hour,myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),false);
        pickUpTime.show();
    }

    //here is assigned tha select date to the "TextView" element
    private void update_txtV_reserver_date() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtV_selectDate.setText(sdf.format(myCalendar.getTime()));
    }

    //here is assigned tha select hour to the "TextView" element
    private void  update_txtV_reserver_hour(int hour, int minute){
        String hourFormat = hour +":"+minute+":"+00;
        txtV_selectHour.setText(hourFormat);
    }

    private void saveDate(){
        MediaPlayer mp = MediaPlayer.create(thiscontext, R.raw.btn_one);
        mp.start();
        fecha = txtV_selectDate.getText()+" "+txtV_selectHour.getText();
        //Log.d("holu","date:" + txtV_selectHour.getText()+" "+ txtV_selectDate.getText());
        if (checkConexion.isConnected()) {
            Call<ResponseDate> call = retrofitIR.createDate(manageSharedPreferences.getUserId(), description.getText().toString() ,fecha);
            //asynchronous call
            call.enqueue(callBackResponseDate);
        } else {
            checkConexion.check();
        }
    }

    private Callback<ResponseDate> callBackResponseDate = new Callback<ResponseDate>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onResponse(Call<ResponseDate> call, Response<ResponseDate> response) {
            int code = response.code();
            if (code == 200) {
                ResponseDate responseDate = response.body();
                if (responseDate != null) {
                    if (responseDate.getStatus() == 200) {
                        CalendarFragment nextFrag= new CalendarFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_date, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                        Toast.makeText(thiscontext, responseDate.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(thiscontext, "Error al cargar temas de consulta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseDate> call, Throwable t) {

        }
    };
}
