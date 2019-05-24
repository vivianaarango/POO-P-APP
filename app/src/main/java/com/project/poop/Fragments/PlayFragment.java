package com.project.poop.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.poop.R;
import com.project.poop.activities.GameActivity;
import com.project.poop.managers.ManageSharedPreferences;

public class PlayFragment extends Fragment {

    private ManageSharedPreferences manageSharedPreferences;
    private Context thiscontext;
    private Button easyBtn;
    private Button mediumBtn;
    private Button hardBtn;
    private Button expertBtn;

    public PlayFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play, container, false);

        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);

        easyBtn = view.findViewById(R.id.btn_easy);
        mediumBtn = view.findViewById(R.id.btn_medium);
        hardBtn = view.findViewById(R.id.btn_hard);
        expertBtn = view.findViewById(R.id.btn_expert);

        easyBtn.setEnabled(true);
        mediumBtn.setEnabled(manageSharedPreferences.getMedium());
        //hardBtn.setEnabled(manageSharedPreferences.getHard());
        hardBtn.setEnabled(true);
        expertBtn.setEnabled(manageSharedPreferences.getExpert());

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(),GameActivity.class);
                bundle.putString("difficultyLevel", "1");
                bundle.putString("difficultyName", "Nivel Facíl");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(),GameActivity.class);
                bundle.putString("difficultyLevel", "2");
                bundle.putString("difficultyName", "Nivel Medio");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(),GameActivity.class);
                bundle.putString("difficultyLevel", "3");
                bundle.putString("difficultyName", "Nivel Difícil");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        expertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(),GameActivity.class);
                bundle.putString("difficultyLevel", "4");
                bundle.putString("difficultyName", "Nivel Experto");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

}
