package com.signme.signme.response;

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
import android.widget.TextView;
import android.widget.Toast;

import com.signme.signme.R;

import junit.framework.Test;

/**
 * Created by dsm2016 on 2017-08-23.
 */

public class ResponseActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private TextView title_text,name_text,content_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);
        //참여 or 불참 라디오버튼
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

    //뒤로가기
    public void backOnClick(View view){
        finish();
    }
}
