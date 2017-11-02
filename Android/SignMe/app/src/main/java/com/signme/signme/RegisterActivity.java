package com.signme.signme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void CloseButtonClicked(View view){
        Intent intent = new Intent(getApplication(), LandingActivity.class);
        startActivity(intent);
    }

    public void BackButtonClicked(View view){
        Intent intent = new Intent(getApplication(), LandingActivity.class);
        startActivity(intent);
    }
}