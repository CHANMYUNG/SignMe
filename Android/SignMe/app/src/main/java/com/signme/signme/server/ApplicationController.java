package com.signme.signme.server;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dsm2016 on 2017-09-25.
 */

public class ApplicationController extends Application {
    private static ApplicationController instance;
    public static ApplicationController getInstance(){return instance;}

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance=this;
    }
    private APIinterface apIinterface;
    public  APIinterface getApIinterface(){return apIinterface;}
    private String baseUrl;
    public void buildNetworkService(){
        if(apIinterface==null){
            baseUrl="http://192.168.1.101:8000/";
            Gson gson=new GsonBuilder()
                    .create();
            GsonConverterFactory factory=GsonConverterFactory.create(gson);
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(factory)
                    .build();
            apIinterface=retrofit.create(APIinterface.class);
        }
    }
}
