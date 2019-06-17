package com.project.poop.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.project.poop.R;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.calendar.ResponseCalendar;
import com.project.poop.models.themes.ResponseTheme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CalendarFragment extends Fragment {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private Context thiscontext;
    private TextView month;
    private Typeface custom_font_text;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);
        initData();

        month = view.findViewById(R.id.month);
        compactCalendar = view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        custom_font_text = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Louis George Cafe Bold.ttf");

        Date date = new Date();
        month.setText(dateFormatMonth.format(date));
        month.setTypeface(custom_font_text);


        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);
                Toast.makeText(thiscontext, "Day was clicked: " + dateClicked + " with events " + events, Toast.LENGTH_SHORT).show();

                /*if (dateClicked.toString().compareTo("Fri Jun 14 00:00:00 GMT-05:00 2019") == 0) {
                    Toast.makeText(thiscontext, "Teachers' Professional Day", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(thiscontext, "No Events Planned for that day"+dateClicked.toString(), Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

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

        getCalendarList();
    }

    private void getCalendarList() {
        if (checkConexion.isConnected()) {
            Call<ResponseCalendar> call = retrofitIR.getCalendarList(manageSharedPreferences.getUserId());
            //asynchronous call
            call.enqueue(callBackResponseCalendar);
        } else {
            checkConexion.check();
        }
    }


    private Callback<ResponseCalendar> callBackResponseCalendar = new Callback<ResponseCalendar>() {
        @Override
        public void onResponse(Call<ResponseCalendar> call, Response<ResponseCalendar> response) {
            int code = response.code();
            if (code == 200) {
                ResponseCalendar responseCalendar = response.body();
                if (responseCalendar != null) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    for (int i = 0; i < responseCalendar.getData().size(); i++){
                        try {
                            Date mDate = sdf.parse(responseCalendar.getData().get(i).getFecha());
                            long timeInMilliseconds = mDate.getTime();

                            Random rnd = new Random();
                            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

                            Event ev1 = new Event(color, timeInMilliseconds, responseCalendar.getData().get(i).getDescription());
                            compactCalendar.addEvent(ev1);

                            List<Event> events = compactCalendar.getEvents(1433701251000L);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(thiscontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(thiscontext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseCalendar> call, Throwable t) {

        }
    };

}
