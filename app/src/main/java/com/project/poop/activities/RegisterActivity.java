package com.project.poop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.poop.R;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.Encoded;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.libraries.Utils;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.login.ResponseLogin;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements Callback<ResponseLogin> {

    private EditText userEmail;
    private EditText userPass;
    private EditText userName;
    private EditText userPhone;
    private String userEmailText;
    private String userPassText;
    private String userNameText;
    private String userPhoneText;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        manageSharedPreferences = new ManageSharedPreferences(this);

        userEmail = (EditText) findViewById(R.id.user_email);
        userPass = (EditText) findViewById(R.id.user_pass);
        userName = (EditText) findViewById(R.id.user_name);
        userPhone = (EditText) findViewById(R.id.user_phone);

        progress = new ManagerProgressDialog(this);

    }

    public void btnLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnRegister(View view) {

        if (checkFields()){

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

            String pass = userPass.getText().toString();
            CheckConexion checkConexion = new CheckConexion(this);

            if (checkConexion.isConnected()) {
                progress.showProgress();
                Call<ResponseLogin> call = retrofitIR.register(userEmail.getText().toString(), Encoded.encodeSHA256(pass), userName.getText().toString(), userPhone.getText().toString());
                //asynchronous call
                call.enqueue(this);
            } else {
                checkConexion.check();
            }

        }
    }

    @Override
    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
        int code = response.code();
        if (code == 200) {
            ResponseLogin responseLogin = response.body();

            if (responseLogin.getStatus() == 200) {
                manageSharedPreferences.setSession(true);
                manageSharedPreferences.setUserId(responseLogin.getData().getId_user());
                manageSharedPreferences.setUserEmail(responseLogin.getData().getEmail());
                manageSharedPreferences.setUserName(responseLogin.getData().getName());
                manageSharedPreferences.setEasy(responseLogin.getData().getDifficulty().get(0).getIs_approved());
                manageSharedPreferences.setMedium(responseLogin.getData().getDifficulty().get(1).getIs_approved());
                manageSharedPreferences.setHard(responseLogin.getData().getDifficulty().get(2).getIs_approved());
                manageSharedPreferences.setExpert(responseLogin.getData().getDifficulty().get(3).getIs_approved());

                Intent intent = new Intent(this, DrawerActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (responseLogin != null) {
                    if (responseLogin.getMessage() != null) {
                        Toast.makeText(this, responseLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        progress.dismissProgress();

    }

    @Override
    public void onFailure(Call<ResponseLogin> call, Throwable t) {

    }

    private boolean checkFields() {
        boolean cancel = false;
        View focusView = null;

        userName.setError(null);
        userEmail.setError(null);
        userPass.setError(null);
        userPhone.setError(null);

        userNameText = userName.getText().toString();

        if (TextUtils.isEmpty(userNameText)) {
            userName.setError(getString(R.string.error_field_required));
            focusView = userName;
            cancel = true;
        }

        userEmailText = userEmail.getText().toString();

        if (TextUtils.isEmpty(userEmailText)) {
            userEmail.setError(getString(R.string.error_field_required));
            focusView = userEmail;
            cancel = true;
        }

        userPassText = userPass.getText().toString();

        if (TextUtils.isEmpty(userPassText)) {
            userPass.setError(getString(R.string.error_field_required));
            focusView = userPass;
            cancel = true;
        }

        userPhoneText = userPhone.getText().toString();

        if (TextUtils.isEmpty(userPhoneText)) {
            userPhone.setError(getString(R.string.error_field_required));
            focusView = userPhone;
            cancel = true;
        } else if (Utils.isValidPhoneNumber(userPhoneText)) {
            userPhone.setError(getString(R.string.error_phone));
            focusView = userPhone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }
}
