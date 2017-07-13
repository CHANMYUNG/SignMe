package com.signme.signme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class MypageActivity extends AppCompatActivity {
    public static Activity mypageActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        mypageActivity = this;

    }

    public void mypageonClick1(View view) {
        Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
        startActivity(intent);
    }

    public void backonClick(View view) {
        MypageActivity.mypageActivity.finish();
        finish();
    }

}
