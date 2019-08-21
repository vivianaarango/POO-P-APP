package com.project.poop.libraries;


import com.project.poop.models.calendar.ResponseCalendar;
import com.project.poop.models.calendar.ResponseDate;
import com.project.poop.models.login.ResponseLogin;
import com.project.poop.models.qualification.ResponseCreateQualification;
import com.project.poop.models.qualification.ResponseQualification;
import com.project.poop.models.question.ResponseAnswer;
import com.project.poop.models.question.ResponseQuestion;
import com.project.poop.models.recoverypassword.ResponseChangePassword;
import com.project.poop.models.recoverypassword.ResponseCode;
import com.project.poop.models.themes.ResponseTheme;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by 3d design on 18/07/2016.
 */
public interface InterfaceRetrofit {

    /*Login*/
    @FormUrlEncoded
    @POST(/*"api/*/"user/login")
    Call<ResponseLogin> login(
            @Field("email") String email,
            @Field("password") String pass
    );

    /*Registro*/
    @FormUrlEncoded
    @POST(/*"api/*/"user/register")
    Call<ResponseLogin> register(
            @Field("email") String email,
            @Field("password") String pass,
            @Field("name") String name,
            @Field("phone") String phone
    );

    /*Obtener pregunta*/
    @FormUrlEncoded
    @POST(/*"api/*/"question/getquestion")
    Call<ResponseQuestion> getQuestion(
            @Field("id_user") String id_user,
             @Field("difficulty") String difficulty,
            @Field("id_game") String id_game

    );

    /*Obtener pregunta*/
    @FormUrlEncoded
    @POST(/*"api/*/"question/getquestion")
    Call<ResponseQuestion> startGame(
            @Field("id_user") String id_user,
            @Field("difficulty") String difficulty
    );

    /*Validar respuesta*/
    @FormUrlEncoded
    @POST(/*"api/*/"question/validateanswer")
    Call<ResponseAnswer> validateAnswer(
            @Field("id_question") String id_question,
            @Field("id_answer") String id_answer,
            @Field("id_game") String id_game
    );

    /*Obtener temas de consulta*/
    @FormUrlEncoded
    @POST(/*"api/*/"theme/themelist")
    Call<ResponseTheme> getThemes(
            @Field("list") String list
    );

    /*Obtener lista de eventos y/o fechas guardadas*/
    @FormUrlEncoded
    @POST(/*"api/*/"calendar/list")
    Call<ResponseCalendar> getCalendarList(
            @Field("id_user") String id_user
    );

    /* Guardar fecha */
    @FormUrlEncoded
    @POST(/*"api/*/"calendar/create")
    Call<ResponseDate> createDate(
            @Field("id_user") String id_user,
            @Field("description") String description,
            @Field("date") String date
    );

    /* Listar materias */
    @FormUrlEncoded
    @POST(/*"api/*/"qualification/list")
    Call<ResponseQualification> listQualification(
            @Field("id_user") String id_user
    );

    /* Crear materias */
    @FormUrlEncoded
    @POST(/*"api/*/"qualification/create")
    Call<ResponseCreateQualification> createQualification(
            @Field("id_user") String id_user,
            @Field("name") String name,
            @Field("cut_one") String cut_one,
            @Field("cut_two") String cut_two,
            @Field("cut_three") String cut_three
    );

    /* Editar materias */
    @FormUrlEncoded
    @POST(/*"api/*/"qualification/create")
    Call<ResponseCreateQualification> editQualification(
            @Field("id_user") String id_user,
            @Field("name") String name,
            @Field("cut_one") String cut_one,
            @Field("cut_two") String cut_two,
            @Field("cut_three") String cut_three,
            @Field("subject") String subject
    );


    /* Enviar código de verificación */
    @FormUrlEncoded
    @POST(/*"api/*/"user/restorepasswordmail")
    Call<ResponseCode> sendCode(
            @Field("email") String email
    );

    /* Cambiar contraseña */
    @FormUrlEncoded
    @POST(/*"api/*/"user/changepassword")
    Call<ResponseChangePassword> changePassword(
            @Field("email") String email,
            @Field("password") String password,
            @Field("code") String code

    );
}
