package com.project.poop.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.poop.Fragments.PlayFragment;
import com.project.poop.R;
import com.project.poop.libraries.Base;
import com.project.poop.libraries.CheckConexion;
import com.project.poop.libraries.Encoded;
import com.project.poop.libraries.InterfaceRetrofit;
import com.project.poop.libraries.Utils;
import com.project.poop.managers.ManageSharedPreferences;
import com.project.poop.managers.ManagerProgressDialog;
import com.project.poop.models.login.ResponseLogin;
import com.project.poop.models.question.ResponseAnswer;
import com.project.poop.models.question.ResponseQuestion;

import java.lang.ref.Reference;
import java.security.Provider;

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
public class GameActivity extends AppCompatActivity implements Callback<ResponseQuestion> {


    private Retrofit retrofit;
    private InterfaceRetrofit retrofitIR;
    private ManagerProgressDialog progress;
    private ManageSharedPreferences manageSharedPreferences;
    private CheckConexion checkConexion;
    private String level;
    private String difficulty;
    private TextView levelText;
    private TextView timeOut;
    private TextView questionText;
    private TextView questionNumber;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private String id_question;
    private String id_answer;
    private String A, B, C, D;
    private Context context;
    private CountDownTimer countDown;
    private Typeface custom_font;
    private Typeface custom_font_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        manageSharedPreferences = new ManageSharedPreferences(this);
        progress = new ManagerProgressDialog(this);
        context = this;
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/orange juice 2.0.ttf");
        //custom_font_text = Typeface.createFromAsset(getAssets(),  "fonts/Roboto-Black.ttf");
        custom_font_text = Typeface.createFromAsset(getAssets(),  "fonts/Louis George Cafe Bold.ttf");

        Bundle bundle = getIntent().getExtras();
        level = bundle.getString("difficultyLevel");
        difficulty = bundle.getString("difficultyName");

        initViews();
        getQuestion();

        answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledButton();
                id_answer = A;
                validateAnswer();
            }
        });

        answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledButton();
                id_answer = B;
                validateAnswer();
            }
        });

        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledButton();
                id_answer = C;
                validateAnswer();
            }
        });

        answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disabledButton();
                id_answer = D;
                validateAnswer();
            }
        });

    }

    private void initViews() {
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

        checkConexion = new CheckConexion(this);

        levelText = (TextView) findViewById(R.id.level_text);
        timeOut = (TextView) findViewById(R.id.time);
        questionText = (TextView) findViewById(R.id.question);
        questionNumber = (TextView) findViewById(R.id.question_number);
        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);

        timeOut.setTypeface(custom_font_text);
        levelText.setTypeface(custom_font);
        questionText.setTypeface(custom_font_text);
        questionNumber.setTypeface(custom_font_text);
        answerA.setTypeface(custom_font_text);
        answerB.setTypeface(custom_font_text);
        answerC.setTypeface(custom_font_text);
        answerD.setTypeface(custom_font_text);

    }


    public void getQuestion() {

        if (checkConexion.isConnected()) {
            progress.showProgress();
            Call<ResponseQuestion> call;
            if ( manageSharedPreferences.getGameId() == null || manageSharedPreferences.getGameId().equals("false") ) {
                call = retrofitIR.startGame(manageSharedPreferences.getUserId(), level);
            } else {
                call = retrofitIR.getQuestion(manageSharedPreferences.getUserId(), level, manageSharedPreferences.getGameId());
            }
            call.enqueue(this);
        } else {
            checkConexion.check();
        }
    }

    private void validateAnswer() {

        if (checkConexion.isConnected()) {
            Call<ResponseAnswer> call = retrofitIR.validateAnswer(id_question, id_answer, manageSharedPreferences.getGameId());
            //asynchronous call
            call.enqueue(callBackResponseAnswer);
        } else {
            checkConexion.check();
        }
    }


    private Callback<ResponseAnswer> callBackResponseAnswer = new Callback<ResponseAnswer>() {
        @Override
        public void onResponse(Call<ResponseAnswer> call, Response<ResponseAnswer> response) {
            int code = response.code();
            Bundle bundle = new Bundle();
            bundle.putString("difficultyLevel", level);
            bundle.putString("difficultyName", difficulty);
            if (code == 200) {
                ResponseAnswer responseAnswer = response.body();
                if (responseAnswer != null) {
                    if (responseAnswer.getStatus() == 200) {
                        countDown.cancel();
                        Intent intent = new Intent(context, CorrectActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    } else {
                        manageSharedPreferences.setGameId("false");
                        countDown.cancel();
                        Intent intent = new Intent(context, FailedActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseAnswer> call, Throwable t) {

        }
    };

    public void getTimeOut(){
        timeOut.setText(null);
        countDown = new CountDownTimer(25000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeOut.setText("Te quedan " + millisUntilFinished / 1000 + " segundos");
            }

            public void onFinish() {
                manageSharedPreferences.setGameId("false");
                Bundle bundle = new Bundle();
                bundle.putString("difficultyLevel", level);
                bundle.putString("difficultyName", difficulty);
                Intent intent = new Intent(context, TimeOutActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    public void onResponse(Call<ResponseQuestion> call, Response<ResponseQuestion> response) {

        int code = response.code();
        if (code == 200) {
            ResponseQuestion responseQuestion = response.body();

            if (responseQuestion.getStatus() == 200) {

                if ( manageSharedPreferences.getGameId() == null || manageSharedPreferences.getGameId().equals("false") ) {
                    manageSharedPreferences.setGameId(responseQuestion.getData().getId_game());
                }
                Log.d("ggg","ggg"+manageSharedPreferences.getMedium());
                if (responseQuestion.getMessage().equals("1")) {

                    if (level.equals("1")) {
                        manageSharedPreferences.setMedium("1");
                    } else if (level.equals("2")) {
                        manageSharedPreferences.setHard("1");
                    } else if (level.equals("3")) {
                        manageSharedPreferences.setExpert("1");
                    }

                    countDown.cancel();
                    Intent intent = new Intent(context, LevelUpActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    levelText.setText(difficulty);
                    questionText.setText(responseQuestion.getData().getQuestion());
                    questionNumber.setText(responseQuestion.getData().getProgress().getTotal() + " correctas");

                    answerA.setText(responseQuestion.getData().getAnswer().get(0).getAnswer());
                    answerB.setText(responseQuestion.getData().getAnswer().get(1).getAnswer());
                    answerC.setText(responseQuestion.getData().getAnswer().get(2).getAnswer());
                    answerD.setText(responseQuestion.getData().getAnswer().get(3).getAnswer());

                    A = responseQuestion.getData().getAnswer().get(0).getId_answer();
                    B = responseQuestion.getData().getAnswer().get(1).getId_answer();
                    C = responseQuestion.getData().getAnswer().get(2).getId_answer();
                    D = responseQuestion.getData().getAnswer().get(3).getId_answer();

                    id_question = responseQuestion.getData().getId_question();

                    getTimeOut();
                }

            } else {
                if (responseQuestion != null) {
                    if (responseQuestion.getMessage() != null) {
                        Toast.makeText(this, responseQuestion.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DrawerActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }

        progress.dismissProgress();

    }

    @Override
    public void onFailure(Call<ResponseQuestion> call, Throwable t) {
        progress.dismissProgress();
    }

    @Override
    public void onBackPressed()
    {
        manageSharedPreferences.setGameId("false");

        if ( countDown != null){
            countDown.cancel();
        }
        Intent intent = new Intent(context, DrawerActivity.class);
        startActivity(intent);
        finish();
    }

    public void disabledButton(){
        answerA.setEnabled(false);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);
    }
}

