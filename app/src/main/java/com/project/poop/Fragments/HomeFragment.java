package com.project.poop.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poop.R;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.qualification.ResponseQualification;
import com.project.poop.models.question.ResponseAnswer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    private ImageButton add, edit;
    private ExpandableListView listView;
    private ExpandableListAdapterQualification listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;
    //private ImageButton search;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        thiscontext = getActivity();
        manageSharedPreferences = new ManageSharedPreferences(thiscontext);

        listView = (ExpandableListView) view.findViewById(R.id.listView1);
        initData();

        add = view.findViewById(R.id.btn_aggregate);
        edit = view.findViewById(R.id.btn_edit_subject);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
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


        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        getSubjects();

    }

    private void getSubjects() {
        if (checkConexion.isConnected()) {
            Call<ResponseQualification> call = retrofitIR.listQualification(manageSharedPreferences.getUserId());
            //asynchronous call
            call.enqueue(callBackResponseQualification);
        } else {
            checkConexion.check();
        }
    }


    private Callback<ResponseQualification> callBackResponseQualification = new Callback<ResponseQualification>() {
        @Override
        public void onResponse(Call<ResponseQualification> call, Response<ResponseQualification> response) {
            Log.d("si","kas");
            int code = response.code();
            if (code == 200) {
                ResponseQualification responseQualification = response.body();
                if (responseQualification != null) {
                    if (responseQualification.getStatus() == 200) {
                        for (int i = 0; i < responseQualification.getData().size(); i++){

                            listDataHeader.add(responseQualification.getData().get(i).getName());

                            List<String> items = new ArrayList<>();
                            for (int j = 0; j < responseQualification.getData().get(i).getCuts().size(); j++){
                                String text = responseQualification.getData().get(i).getCuts().get(j).getCut() +": "+ responseQualification.getData().get(i).getCuts().get(j).getQualification();
                                if (responseQualification.getData().get(i).getCuts().get(j).getIsCalculed() ==  1){
                                    items.add("<font color=#C2BFBF>" + text + "</font>");
                                } else {
                                    items.add(text);
                                }
                            }

                            if (responseQualification.getData().get(i).getTotal() >= 30){
                                items.add("<font color=#99D17B><b>Acumulado: " + responseQualification.getData().get(i).getTotal() + "</b></font>");
                            } else {
                                items.add("<font color=#F45B69><b>Acumulado: " + responseQualification.getData().get(i).getTotal() + "</b></font>");
                            }


                            listHash.put(listDataHeader.get(i), items);
                        }

                        listAdapter = new ExpandableListAdapterQualification(thiscontext, listDataHeader, listHash);
                        listView.setAdapter(listAdapter);

                    } else {
                        Toast.makeText(thiscontext, "Error al cargar notas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseQualification> call, Throwable t) {

        }
    };

    private void add() {
        SubjectFragment nextFrag= new SubjectFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_home, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    private void edit() {
        EditSubjectFragment nextFrag= new EditSubjectFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_home, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}
