package com.mathew.retrofittest.Network;

import com.mathew.retrofittest.pojo.CountResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by root on 9/22/17.
 */

public interface ApiServices {

    @GET("api/logout-count-api.php")
    Call<CountResponse> login(
            @Query("id") String id
    );



  /*  @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> Login(

            @Field("emailid") String emailid,
            @Field("password") String password,
            @Field("device_type") String device_type,
            @Field("device_token") String device_token

    );*/



}