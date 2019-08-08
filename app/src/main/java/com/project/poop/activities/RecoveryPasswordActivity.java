package com.project.poop.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poop.Fragments.HomeFragment;
import com.project.poop.R;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.Encoded;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.libraries.Utils;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.login.ResponseLogin;
import com.project.poop.models.qualification.ResponseCreateQualification;
import com.project.poop.models.recoverypassword.ResponseChangePassword;
import com.project.poop.models.recoverypassword.ResponseCode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A login screen that offers login via email/password.
 */
public class RecoveryPasswordActivity extends AppCompatActivity {

    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private EditText email, code_, password;
    private String userEmail, userCode, userPassword;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);

        manageSharedPreferences = new ManageSharedPreferences(this);
        progress = new ManagerProgressDialog(this);
        context = this;

        email = findViewById(R.id.email);
        code_ = findViewById(R.id.code);
        password = findViewById(R.id.password);

        code_.setVisibility(View.GONE);
        password.setVisibility(View.GONE);

    }

    public void sendCode(View view) {

        if (checkFields()){

            MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_one);
            mp.start();

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

            CheckConexion checkConexion = new CheckConexion(this);

            if (password.getVisibility() == View.GONE){
                if (checkConexion.isConnected()) {
                    Call<ResponseCode> call = retrofitIR.sendCode(email.getText().toString());
                    //asynchronous call
                    call.enqueue(callBackResponseCode);
                } else {
                    checkConexion.check();
                }
            } else {
                if (checkChangePassword()){
                    //Encoded.encodeSHA256(pass)
                    if (checkConexion.isConnected()) {
                        Call<ResponseChangePassword> call = retrofitIR.changePassword(email.getText().toString(), Encoded.encodeSHA256(password.getText().toString()), code_.getText().toString());
                        //asynchronous call
                        call.enqueue(callBackResponseChangePassword);
                    } else {
                        checkConexion.check();
                    }
                } else {
                    MediaPlayer m = MediaPlayer.create(this, R.raw.btn_two);
                    m.start();
                }
            }

        } else {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.btn_two);
            mp.start();
        }
    }


    private boolean checkFields() {
        boolean cancel = false;
        View focusView = null;

        email.setError(null);

        userEmail = email.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }

    private boolean checkChangePassword() {
        boolean cancel = false;
        View focusView = null;

        email.setError(null);
        code_.setError(null);
        password.setError(null);

        userEmail = email.getText().toString();
        userCode = code_.getText().toString();
        userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        }

        if (TextUtils.isEmpty(userCode)) {
            code_.setError(getString(R.string.error_field_required));
            focusView = code_;
            cancel = true;
        }

        if (TextUtils.isEmpty(userPassword)) {
            password.setError(getString(R.string.error_field_required));
            focusView = password;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }

        return !cancel;
    }

    private Callback<ResponseCode> callBackResponseCode = new Callback<ResponseCode>() {
        @Override
        public void onResponse(Call<ResponseCode> call, Response<ResponseCode> response) {

            int code = response.code();
            if (code == 200) {
                ResponseCode responseCode = response.body();
                if (responseCode != null) {
                    if (responseCode.getStatus() == 200) {
                        Toast.makeText(context, responseCode.getMessage(), Toast.LENGTH_SHORT).show();
                        code_.setVisibility(View.VISIBLE);
                        password.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, responseCode.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseCode> call, Throwable t) {

        }
    };

    private Callback<ResponseChangePassword> callBackResponseChangePassword = new Callback<ResponseChangePassword>() {
        @Override
        public void onResponse(Call<ResponseChangePassword> call, Response<ResponseChangePassword> response) {

            int code = response.code();
            if (code == 200) {
                ResponseChangePassword responseChangePassword = response.body();
                if (responseChangePassword != null) {
                    if (responseChangePassword.getStatus() == 200) {
                        Toast.makeText(context, responseChangePassword.getMessage(), Toast.LENGTH_SHORT).show();
                        code_.setVisibility(View.VISIBLE);
                        password.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(context, responseChangePassword.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseChangePassword> call, Throwable t) {

        }
    };

}

