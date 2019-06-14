package com.project.poop.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.project.poop.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarFragment extends Fragment {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private Context thiscontext;
    private TextView month;
    private Typeface custom_font_text;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        thiscontext = getActivity();
        month = (TextView) view.findViewById(R.id.month);
        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        custom_font_text = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Louis George Cafe Bold.ttf");

        Date date = new Date();
        month.setText(dateFormatMonth.format(date));
        month.setTypeface(custom_font_text);

        Event ev1 = new Event(Color.GREEN, 1560574800000L, "Some extra data that I want to store.");
        compactCalendar.addEvent(ev1);

        Event ev2 = new Event(Color.GREEN, 1560661200000L, "Wuuuu.");
        compactCalendar.addEvent(ev2);

        List<Event> events = compactCalendar.getEvents(1433701251000L); // can also take a Date object

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


}
