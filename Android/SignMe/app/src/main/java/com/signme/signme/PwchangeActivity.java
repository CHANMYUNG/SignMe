package com.signme.signme;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dsm2016 on 2017-07-14.
 */

public class PwchangeActivity extends AppCompatActivity {
    public static Activity pwchangeActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pwchangeActivity=this;
        setContentView(R.layout.activity_pwchange);
    }
}
