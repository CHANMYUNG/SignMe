package com.signme.signme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import junit.framework.Test;

/**
 * Created by dsm2016 on 2017-08-23.
 */

public class ContentMainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        radioGroup.clearCheck();
        String selectedResult="";
        if(radioGroup.getCheckedRadioButtonId()>0){
            View radioButton=radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
            int radioid=radioGroup.indexOfChild((radioButton));
            RadioButton btn=(RadioButton)radioGroup.getChildAt(radioid);
            selectedResult=(String)btn.getText();
        }
        Toast.makeText(getApplicationContext(),selectedResult,Toast.LENGTH_SHORT).show();
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
