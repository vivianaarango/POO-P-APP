package com.project.poop.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
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
import com.project.poop.models.calendar.DataCalendar;
import com.project.poop.models.calendar.ResponseCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


@RequiresApi(api = Build.VERSION_CODES.N)
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
    private ExpandableListView listView;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private ExpandableListAdapterCalendar listAdapter;
    private ImageButton add;


    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);
        custom_font_text = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Louis George Cafe Bold.ttf");
        initData();

        month = view.findViewById(R.id.month);
        compactCalendar = view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        Date date = new Date();
        month.setText(dateFormatMonth.format(date));
        month.setTypeface(custom_font_text);

        add = view.findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        listView = view.findViewById(R.id.listViewCalendar);

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

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        getCalendarList();
    }

    private void getCalendarList() {
        if (checkConexion.isConnected()) {
            Call<ResponseCalendar> call = retrofitIR.getCalendarList(manageSharedPreferences.getUserId());
            //Call<ResponseCalendar> call = retrofitIR.getCalendarList("11");
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
                    setCalendarEvents(responseCalendar.getData());
                    setCalendarList(responseCalendar.getData());
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


    private void setCalendarEvents(List<DataCalendar> dataCalendar){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if ( dataCalendar != null ) {
            for (int i = 0; i < dataCalendar.size(); i++){
                try {
                    Date mDate = sdf.parse(dataCalendar.get(i).getFecha());
                    long timeInMilliseconds = mDate.getTime();

                    Event ev1 = new Event(Color.RED, timeInMilliseconds, dataCalendar.get(i).getDescription());
                    compactCalendar.addEvent(ev1);

                    List<Event> events = compactCalendar.getEvents(1433701251000L);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }


        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendar.getEvents(dateClicked);

                if (events.size() > 0) {
                    Toast.makeText(thiscontext, ""+events.get(0).getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(thiscontext, "No hay eventos agendados para este día", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

    private void setCalendarList(List<DataCalendar> dataCalendar){

        listDataHeader.add("Fechas guardadas");
        List<String> items = new ArrayList<>();
        final List<String> hours = new ArrayList<>();;

        if (dataCalendar != null){
            for (int i = 0; i < dataCalendar.size(); i++){

                String []  fecha = dataCalendar.get(i).getFecha(). split(" ");


                items.add(fecha[0]+" - "+dataCalendar.get(i).getDescription());
                hours.add(fecha[1]);

                listHash.put(listDataHeader.get(0), items);
            }
        }


        listAdapter = new ExpandableListAdapterCalendar(thiscontext, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Integer hour = childPosition;
                Toast.makeText(thiscontext, ""+hours.get(hour), Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void add() {

        DateFragment nextFrag= new DateFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_calendar, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}


