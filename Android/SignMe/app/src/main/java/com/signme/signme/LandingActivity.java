package com.signme.signme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import java.util.HashMap;

/**
 * Created by NooHeat on 11/06/2017.
 */

public class LandingActivity extends AppCompatActivity{

    public static Activity landingActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_landing);

        landingActivity = this;
        
    }


    public void goLoginClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void registerClicked(View view) {

    }
}
