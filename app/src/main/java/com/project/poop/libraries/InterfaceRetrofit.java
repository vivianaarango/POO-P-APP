package com.project.poop.libraries;


import com.project.poop.models.login.ResponseLogin;

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

    @FormUrlEncoded
    @POST(/*"api/*/"user/login")
    Call<ResponseLogin> login(
            @Field("email") String email,
            @Field("password") String pass
    );


}
