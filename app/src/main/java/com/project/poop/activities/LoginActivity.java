package com.project.poop.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.project.poop.R;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.Encoded;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.models.login.ResponseLogin;
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
public class LoginActivity extends AppCompatActivity implements Callback<ResponseLogin> {


    private EditText userEmail;
    private EditText userPass;
    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    //private ManagerProgressDialog progress;
    //private ManageSharedPreferences manageSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.user_name);
        userPass = (EditText) findViewById(R.id.user_pass);

        //todo delete this
        //userEmail.setText("natym");
        //userPass.setText("1234");

       // manageSharedPreferences = new ManageSharedPreferences(this);

        //if (manageSharedPreferences.getUserName() != null) {
        //    userName.setText(manageSharedPreferences.getUserName());
        //}

        //progress = new ManagerProgressDialog(this);

    }

    public void btnLogin(View view) {

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
            //progress.showProgress();
            Call<ResponseLogin> call = retrofitIR.login(userEmail.getText().toString(), Encoded.encodeSHA256(pass));
            //asynchronous call
            call.enqueue(this);
        } else {
            checkConexion.check();
        }
    }


    @Override
    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
        int code = response.code();
        if (code == 200) {
            ResponseLogin responseLogin = response.body();

            if (responseLogin.getStatus() == 200) {
               // manageSharedPreferences.setSession(true);
               // manageSharedPreferences.setUserIdentification(responseAuth.getData().getClient().getIdentify());
               // manageSharedPreferences.setUserId(responseAuth.getData().getClient().getId());
               // manageSharedPreferences.setCompanyId(responseAuth.getData().getCompany().getId());
               // manageSharedPreferences.setName(responseAuth.getData().getClient().getName());
               // manageSharedPreferences.setPhone(responseAuth.getData().getClient().getPhone());
               // Intent intent = new Intent(this, HistoryActivity.class);
               // startActivity(intent);
               // finish();
                Toast.makeText(this, responseLogin.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                if (responseLogin != null) {
                    if (responseLogin.getMessage() != null) {
                        Toast.makeText(this, responseLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        //progress.dismissProgress();
    }

    @Override
    public void onFailure(Call<ResponseLogin> call, Throwable t) {

    }
}

