package com.signme.signme.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.signme.signme.R;
import com.signme.signme.server.APIInterface;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ghdth on 2017-10-10.
 */

public class ChangePwdActivity extends AppCompatActivity {
    EditText nowpwdfield, newpwdfield, confrim_newpwdfield;
    String nowpwd, newpwd, confrim_newpwd;
    Retrofit retrofit;
    APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
//        nowpwdfield=(EditText)findViewById(R.id.nowpwd_et);
//        newpwdfield=(EditText)findViewById(R.id.newpwd_et);
//        confrim_newpwdfield=(EditText)findViewById(R.id.comfrim_newpwd_et);
//        nowpwd=nowpwdfield.getText().toString();
//        newpwd=newpwdfield.getText().toString();
//        confrim_newpwd=confrim_newpwdfield.getText().toString();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);

        //저장
        ImageView pwdcheck = (ImageView) findViewById(R.id.pwd_check);
        pwdcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowpwdfield = (EditText) findViewById(R.id.nowpwd_et);
                newpwdfield = (EditText) findViewById(R.id.newpwd_et);
                confrim_newpwdfield = (EditText) findViewById(R.id.comfrim_newpwd_et);
                nowpwd = nowpwdfield.getText().toString();
                newpwd = newpwdfield.getText().toString();
                confrim_newpwd = confrim_newpwdfield.getText().toString();
                Log.d("비밀번호", newpwd);
                Log.d("비밀번호2", confrim_newpwd);
                if (newpwd.equals(confrim_newpwd)) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChangePwdActivity.this);
                    dialog.setTitle("저장");
                    dialog.setMessage("변경한 내용을 저장하시겠습니까?");
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Map<String, Object> fieldMap = new HashMap<>();
                            fieldMap.put("oldPassword", nowpwd);
                            fieldMap.put("newPassword", newpwd);
                            Call<Void> call = apiInterface.changePassword(fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 204) {
                                        Toast.makeText(getApplicationContext(), "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    if (response.code() == 200) {
                                        new SweetAlertDialog(ChangePwdActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                                .setTitleText("비밀번호 변경")
                                                .setContentText("비밀번호 변경을 완료했습니다.")
                                                .setConfirmText("완료").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.cancel();
                                                finish();
                                            }
                                        }).show();
                                    }
                                    if (response.code() == 500) {
                                        Toast.makeText(getApplicationContext(), "서버측 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    });
                    dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(ChangePwdActivity.this, "새 비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //뒤로가기
        ImageView pwdback = (ImageView) findViewById(R.id.pwd_back);
        pwdback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
