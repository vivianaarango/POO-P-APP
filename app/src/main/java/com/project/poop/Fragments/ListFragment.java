package com.project.poop.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poop.R;
import com.project.poop.activities.CorrectActivity;
import com.project.poop.activities.FailedActivity;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.question.ResponseAnswer;
import com.project.poop.models.themes.DataTheme;
import com.project.poop.models.themes.ResponseTheme;

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


public class ListFragment extends Fragment {

    private TextView textView;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private Context thiscontext;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;
    private ArrayList<DataTheme> dataTheme;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        thiscontext = getActivity();

        listView = (ExpandableListView) view.findViewById(R.id.listView);
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


        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        getThemes();

       /* listDataHeader.add("EDMTDev");
        listDataHeader.add("Android");
        listDataHeader.add("Xamarin");
        listDataHeader.add("UWP");

        List<String> edmtDev = new ArrayList<>();
        edmtDev.add("This is Expandable ListView");

        List<String> androidStudio = new ArrayList<>();
        androidStudio.add("Expandable ListView");
        androidStudio.add("Google Map");
        androidStudio.add("Chat Application");
        androidStudio.add("Firebase ");

        List<String> xamarin = new ArrayList<>();
        xamarin.add("Xamarin Expandable ListView");
        xamarin.add("Xamarin Google Map");
        xamarin.add("Xamarin Chat Application");
        xamarin.add("Xamarin Firebase ");

        List<String> uwp = new ArrayList<>();
        uwp.add("UWP Expandable ListView");
        uwp.add("UWP Google Map");
        uwp.add("UWP Chat Application");
        uwp.add("UWP Firebase ");

        listHash.put(listDataHeader.get(0),edmtDev);
        listHash.put(listDataHeader.get(1),androidStudio);
        listHash.put(listDataHeader.get(2),xamarin);
        listHash.put(listDataHeader.get(3),uwp);
        */
    }

    private void getThemes() {
        if (checkConexion.isConnected()) {
            Call<ResponseTheme> call = retrofitIR.getThemes("list");
            //asynchronous call
            call.enqueue(callBackResponseTheme);
        } else {
            checkConexion.check();
        }
    }


    private Callback<ResponseTheme> callBackResponseTheme = new Callback<ResponseTheme>() {
        @Override
        public void onResponse(Call<ResponseTheme> call, Response<ResponseTheme> response) {
            int code = response.code();
            if (code == 200) {
                ResponseTheme responseTheme = response.body();
                if (responseTheme != null) {
                    if (responseTheme.getStatus() == 200) {
                        for (int i = 0; i < responseTheme.getData().size(); i++){

                            listDataHeader.add(responseTheme.getData().get(i).getName());

                            List<String> items = new ArrayList<>();
                            for (int j = 0; j < responseTheme.getData().get(i).getItems().size(); j++){
                                items.add(responseTheme.getData().get(i).getItems().get(j).getName());
                            }

                            listHash.put(listDataHeader.get(i), items);
                        }

                        listAdapter = new ExpandableListAdapter(thiscontext, listDataHeader, listHash);
                        listView.setAdapter(listAdapter);

                    } else {
                        Toast.makeText(thiscontext, "Error al cargar temas de consulta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseTheme> call, Throwable t) {

        }
    };
}
