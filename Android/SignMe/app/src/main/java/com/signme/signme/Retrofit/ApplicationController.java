package com.signme.signme.Retrofit;

import android.app.Application;
import android.app.usage.NetworkStats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class ApplicationController extends Application {
    private static ApplicationController instance;
    public static ApplicationController getInstance(){return instance;}

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance=this;
    }
    private NetworkService networkService;
    public NetworkService getNetworkService(){return networkService;}

    private String baseUrl;
    public void buildNetworkService(String ip,int port){
        synchronized (ApplicationController.class){
            if(networkService==null){
                baseUrl=String.format("http://%s:%d",ip,port);
                Gson gson=new GsonBuilder()
                        .create();
                GsonConverterFactory factory=GsonConverterFactory.create();
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(String.valueOf(factory))
                        .build();
                networkService=retrofit.create(NetworkService.class);
            }
        }
    }

    public interface NetworkService{

    }
}
