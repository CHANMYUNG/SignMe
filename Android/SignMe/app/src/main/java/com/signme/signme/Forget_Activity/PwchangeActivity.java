package com.signme.signme.Forget_Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.activity.ChangePwdActivity;

/**
 * Created by dsm2016 on 2017-07-14.
 */

public class PwchangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwchange);
        Button check=(Button)findViewById(R.id.pwchange_check);
        final EditText pwfield=(EditText)findViewById(R.id.pwchange_et);
        final EditText pw2field=(EditText)findViewById(R.id.pwchange2_et);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw=pwfield.getText().toString();
                String pw2=pw2field.getText().toString();

                if(pw.equals(pw2)){

                    AlertDialog.Builder dialog=new AlertDialog.Builder(PwchangeActivity.this);
                    dialog.setTitle("저장");
                    dialog.setMessage("변경한 내용을 저장하시겠습니까?");
                    dialog.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                /*
                                레트로핏
                                */
                            Intent intent=new Intent(PwchangeActivity.this, LoginActivity.class);
                            startActivity(intent);
                                finish();
                        }
                    });
                    dialog.setNegativeButton("취소",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
                else {
                    Toast.makeText(PwchangeActivity.this,"새 비밀번호가 맞지 않습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });
        //뒤로가기
        ImageView back=(ImageView)findViewById(R.id.pwchange_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
