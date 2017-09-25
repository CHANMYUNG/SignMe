package com.signme.signme.server;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by dsm2016 on 2017-09-06.
 */

public interface APIinterface {

    @GET("account/id/check/:id")
    Call<JSONObject> id_check(@Path("check_id")String check_id);

    @FormUrlEncoded
    @POST("/account/sign/in")
    Call<ResponseBody> doSignIn(@FieldMap Map<String,String> user);

    @GET("/account/email/check/:email")
    Call<JSONObject> email_check(@Path("check_email") String check_email);

    @FormUrlEncoded
    @POST("/account/sign/up")
    Call<ResponseBody> SignUp(@FieldMap Map<String,String> signup);

    @GET("/account/uid/check/:uid")
    Call<JSONObject> uid_check(@Path("check_uid") String check_uid);

    @GET("/letter/responseless")
     Call<JSONObject>letter_list(@Query("letterNumber")int letterNumber );

    @GET("/letter/responseless/:letterNumber")
    Call<contentlistRepo> getletterNumber(@Path("letterNumber")int letterNumber);

    @FormUrlEncoded
    @POST("/logout")
    Call<Void> doLogOut();

    @FormUrlEncoded
    @POST("/foget/id")
    Call<Void> forgetid(@Field("name") String name,
                        @Field("email") String email);
    @FormUrlEncoded
    @POST("/foget/pw")
    Call<Void> forgetpw(@Field("id") String id,
                        @Field("email") String email,
                        @Field("Cercode") String Cercode
    );


    @GET("/jwt")
    Call<Void> doJWTCheck();
}
