package com.project.poop.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Retrofit;



public class DateFragment extends Fragment {

    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;

    private TextView txtV_selectDate, txtV_selectHour;
    private DatePickerDialog.OnDateSetListener date ;
    private TimePickerDialog.OnTimeSetListener hour;
    final Calendar myCalendar  = Calendar.getInstance();
    private String dayOfWeek;


    public DateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_date, container, false);

        thiscontext = getActivity();
        txtV_selectDate = view.findViewById(R.id.txtv_reserver_date);
        txtV_selectHour = view.findViewById(R.id.txtv_reserver_hour);

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
                dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();

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


}
