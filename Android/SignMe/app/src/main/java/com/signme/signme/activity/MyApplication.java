package com.signme.signme.activity;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by NooHeat on 06/11/2017.
 */

public class MyApplication extends Application {
    public static SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MYAPPLICATION", "onCreate: ");
        preferences = getSharedPreferences("test", MODE_PRIVATE);
    }
}
