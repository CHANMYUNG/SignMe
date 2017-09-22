package com.signme.signme.Forget_Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.signme.signme.PwchangeActivity;
import com.signme.signme.R;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class FogetpwActivity extends AppCompatActivity {
    public static Activity fogetpwActivity;
    public static final String url = "http://13.124.15.202:80/";
    //AQuery aquery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fogetpwActivity = this;
        setContentView(R.layout.activity_forgetpw);
        final Spinner spinner = (Spinner) findViewById(R.id.email2);
        final EditText tv = (EditText) findViewById(R.id.email3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(spinner.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //FogetpwActivity 닫기
    public void closeonClick(View view) {
        FogetpwActivity.fogetpwActivity.finish();
        finish();
    }
    //FogetidActivity실행
    public void fogetidonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), FogetidActivity.class);
        startActivity(intent);
        FogetpwActivity.fogetpwActivity.finish();
        finish();
    }
    ///아이디 확인 버튼
    public  void idcheckonClick(View view){
        //aquery = new AQuery(getApplicationContext());
        EditText idfiled=(EditText)findViewById(R.id.user_id);
        //EditText emailfield1=(EditText)findViewById(R.id.email);
       // EditText emailfield2=(EditText)findViewById(R.id.email3);
        String id=idfiled.getText().toString();
        //String email1=emailfield1.getText().toString();
       // String email2=emailfield2.getText().toString();
        if(id.trim().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(FogetpwActivity.this);
            Dialog dialog = builder.setMessage("아이디를 입력해주세요.").setPositiveButton("OK", null).create();
            dialog.show();
        }
        /*
        아이디 비교
         */
    }
    //비밀번호 변경
    public  void emailcode_checkonClick(View view){
        /*
        인증 코드 입력 받고 맞는지 비교
         */
        Intent intent =new Intent(getApplicationContext(),PwchangeActivity.class);
        startActivity(intent);
    }
    //아이디 찾기로 이동
    public void id_findonClick(View view){
        Intent intent=new Intent(getApplicationContext(),FogetidActivity.class);
        startActivity(intent);
    }
    //이메일 인증 코드 전송
    public void spend_emailcodeonClick(View view){
        /*
        이메일 비교 코드 작성
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(FogetpwActivity.this);
        Dialog dialog = builder.setMessage("인증코드를 전송했습니다.")
                .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //인증코드 요청하기
                    }
                }).create();
        dialog.show();
    }
}
