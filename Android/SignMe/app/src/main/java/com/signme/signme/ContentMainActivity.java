package com.signme.signme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import junit.framework.Test;

/**
 * Created by dsm2016 on 2017-08-23.
 */

public class ContentMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);

    }
    //가정통신문 제출 버튼
    public void spend_contentonClick(View view){
        //다이얼로그
    }
    //사인 엑티비티 실행  but, 삭제 예정
    public void testonclick(View v) {
        Intent intent=new Intent(getApplicationContext(),TestDialog.class);
        startActivity(intent);
    }
    //뒤로가기
    public void backonClick(View view){
        finish();
    }



}
