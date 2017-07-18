package com.signme.signme;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Context mContext = getApplicationContext();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.pw_dialog, (ViewGroup) findViewById(R.id.layout_root));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(MypageActivity.this);
        aDialog.setTitle("비밀번호 입력");
        aDialog.setView(layout);

        aDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ModifyActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog ad = aDialog.create();
        ad.show();


    }

    public void backonClick(View view) {
        MypageActivity.mypageActivity.finish();
        finish();
    }

}
