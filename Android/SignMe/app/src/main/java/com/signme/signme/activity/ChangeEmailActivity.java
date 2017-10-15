package com.signme.signme.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.callback.AjaxCallback;
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
 * Created by ghdth on 2017-10-09.
 */

public class ChangeEmailActivity extends AppCompatActivity {
    private EditText emailfield, newfield;
    private String email, newEmail;
    private Retrofit retrofit;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        newfield = (EditText) findViewById(R.id.new_email);
        emailfield = (EditText) findViewById(R.id.now_email);

        //Call<Void> call = apiInterface.modifyAnswerToSurvey();

        //뒤로가기
        ImageView backbtn = (ImageView) findViewById(R.id.modify_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //저장
        ImageView checkbtn = (ImageView) findViewById(R.id.modify_check);
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailfield.getText().toString();
                newEmail = newfield.getText().toString();
                Log.d("이메일", email);
                Log.d("이메일2", newEmail);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ChangeEmailActivity.this);
                dialog.setTitle("저장");
                dialog.setMessage("변경한 내용을 저장하시겠습니까?");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        retrofit = new Retrofit.Builder()
                                .baseUrl(APIInterface.URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        apiInterface = retrofit.create(APIInterface.class);

                        Map<String, Object> fieldMap = new HashMap<>();
                        fieldMap.put("oldEmail", email);
                        fieldMap.put("newEmail", newEmail);

                        Call<Void> call = apiInterface.changeEmail(fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

                        call.enqueue(new Callback<Void>() {
                                         @Override
                                         public void onResponse(Call<Void> call, Response<Void> response) {
                                             if (response.code() == 204) {
                                                 Toast.makeText(getApplicationContext(), "현재 이메일을 잘못입력하셨습니다.", Toast.LENGTH_SHORT).show();
                                             }
                                             if (response.code() == 200) {
                                                 new SweetAlertDialog(ChangeEmailActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                                         .setTitleText("이메일 변경")
                                                         .setContentText("이메일 변경을 완료했습니다.")
                                                         .setConfirmText("완료").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                     @Override
                                                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                         sweetAlertDialog.cancel();
                                                         finish();
                                                     }
                                                 }).show();
                                             }
                                             if (response.code() == 500) {
                                                 Toast.makeText(getApplicationContext(), "중복되는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                             }
                                         }

                                         @Override
                                         public void onFailure(Call<Void> call, Throwable t) {

                                         }
                                     }

                        );

//                            Map<String, Object> fieldMap = new HashMap<>();
//                            fieldMap.put("email",email);
//                            apiInterface = retrofit.create(APIInterface.class);
//                            Call<Void> call=apiInterface.modifyAnswerToResponse("",fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
//                            call.enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call, Response<Void> response) {
//
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call, Throwable t) {
//
//                                }
//                            });

                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();


            }
        });

    }
}
