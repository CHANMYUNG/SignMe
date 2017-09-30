package com.signme.signme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.signme.signme.activity.MainActivity;

/**
 * Created by Kimsoomin on 2017-09-04.
 */

public class RegisterActivity  extends AppCompatActivity{

    CheckBox check1;
    CheckBox check2;
    CheckBox check3;
    CheckBox check4;
    TextView law1;
    TextView law2;

    AQuery aquery;
    public static final String url = "http://13.124.15.202:80/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        law1 =  (TextView)findViewById(R.id.law1);
        law1.setMovementMethod(new ScrollingMovementMethod());

        law2 = (TextView)findViewById(R.id.law2);
        law2.setMovementMethod(new ScrollingMovementMethod());

        check1 = (CheckBox)findViewById(R.id.check1);
        check2 = (CheckBox)findViewById(R.id.check2);
        check3 = (CheckBox)findViewById(R.id.check3);
        check4 = (CheckBox)findViewById(R.id.check4);
    }

    public void allcheck(View view){
        check1.setChecked(true);
        check2.setChecked(true);
        check3.setChecked(true);
        check4.setChecked(true);
    }

    public void CheckOpenButton1(View view){
        law1.setVisibility(View.VISIBLE);
    }

    public void CheckOpenButton2(View view){
        law2.setVisibility(View.VISIBLE);
    }

    public void CloseButtonClicked(View view){
        Intent close2 = new Intent(getApplication(), MainActivity.class);
        startActivity(close2);
    }

    public void BackButtonClicked(View view){
        Intent back = new Intent(getApplication(), MainActivity.class);
        startActivity(back);
    }

    public void registerokClicked(View v){
        /*if(check1.isChecked() && check2.isChecked() && check3.isChecked() && check4.isChecked()) {
            Intent agreeok = new Intent(getApplication(), Register2Activity.class);
            startActivity(agreeok);
        } else {
            Toast.makeText(this, "모든 항목이 선택 되어있어야 합니다.", Toast.LENGTH_SHORT).show();
            check1.setChecked(false);
            check2.setChecked(false);
            check3.setChecked(false);
            check4.setChecked(false);
        }*/

        Intent registeragreeok = new Intent(getApplicationContext(), Register2Activity.class);
        startActivity(registeragreeok);
    }
}

