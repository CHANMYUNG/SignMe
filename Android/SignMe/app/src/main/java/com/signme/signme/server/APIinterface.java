package com.signme.signme.server;

import android.view.animation.BounceInterpolator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by dsm2016 on 2017-09-06.
 */

public interface APIInterface {

    String URL = "http://192.168.1.100:7800/";

    @GET("account/id/check/:id")
    Call<JSONObject> id_check(@Path("check_id") String check_id);

    @FormUrlEncoded
    @POST("/account/sign/in")
    Call<JsonObject> doSignIn(@FieldMap Map<String, String> user);

    @GET("/account/email/check/:email")
    Call<JsonObject> email_check(@Path("check_email") String check_email);

    @FormUrlEncoded
    @POST("/account/sign/up")
    Call<ResponseBody> SignUp(@FieldMap Map<String, String> signup);

    @GET("/account/uid/check/:uid")
    Call<JSONObject> uid_check(@Path("check_uid") String check_uid);

    @GET("/letter/responseless")
    Call<JSONObject> letter_list(@Query("letterNumber") int letterNumber);

    @GET
    Call<JsonObject> getResponselessLetter(@Url String url, @Header("signme-x-access-token") String token);
//
//    @FormUrlEncoded
//    @POST("/logout")
//    Call<Void> doLogOut();
//
//    @FormUrlEncoded
//    @POST("/foget/id")
//    Call<Void> forgetid(@Field("name") String name,
//                        @Field("email") String email);
//
//    @FormUrlEncoded
//    @POST("/foget/pw")
//    Call<Void> forgetpw(@Field("id") String id,
//                        @Field("email") String email,
//                        @Field("Cercode") String Cercode
//    );


    @GET("/letter")
    Call<JsonArray> getLetterList(@Header("signme-x-access-token") String token);

    @GET("/letter/survey")
    Call<JsonArray> getSurveyList(@Header("signme-x-access-token") String token);

    @GET("/letter/responseless")
    Call<JsonArray> getResponselessList(@Header("signme-x-access-token") String token);

    @GET("/letter/response")
    Call<JsonArray> getResponseList(@Header("signme-x-access-token") String token);

    @GET
    Call<JsonObject> getSurvey(@Url String url, @Header("signme-x-access-token") String token);

    @FormUrlEncoded
    @POST
    Call<Void> doAnswerToSurvey(@Url String url, @FieldMap Map<String, Object> fieldMap, @Header("signme-x-access-token") String token);

    @FormUrlEncoded
    @PUT
    Call<Void> changeAnswerToSurvey(@Url String url, @FieldMap Map<String, Object> fieldMap, @Header("signme-x-access-token") String token);


    @FormUrlEncoded
    @POST
    Call<Void> doAnswerToResponse(@Url String url, @FieldMap Map<String, Object> fieldMap, @Header("signme-x-access-token") String token);

    @FormUrlEncoded
    @PUT
    Call<Void> changeAnswerToResponse(@Url String url, @FieldMap Map<String, Object> fieldMap, @Header("signme-x-access-token") String token);

    @GET
    Call<JsonObject> getResponseLetter(@Url String url, @Header("signme-x-access-token") String token);

    @GET("/task")
    Call<JsonArray> getTask(@QueryMap Map<String, Object> queryMap, @Header("signme-x-access-token") String token);


    @FormUrlEncoded
    @POST("/change/email")
    Call<Void> changeEmail(@FieldMap Map<String, Object> fieldMap, @Header("signme-x-access-token") String token);

    @FormUrlEncoded
    @POST("/change/password")
    Call<Void> changePassword(@FieldMap Map<String, Object> fieldMap, @Header("signme-x-access-token") String token);

    @FormUrlEncoded
    @POST("/account/leave")
    Call<Void> accountLeave(@Field("type") String type, @Header("signme-x-access-token") String token);


    @GET("/letter/responsed")
    Call<JsonArray> getResponsedLetterList(@Header("signme-x-access-token") String token);

    //Call<Void>

}
