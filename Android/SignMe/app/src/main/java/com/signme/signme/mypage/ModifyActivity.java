package com.signme.signme.mypage;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.signme.signme.R;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class ModifyActivity extends AppCompatActivity {
    public static Activity modifyActivity;
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText pw1;
    private EditText pw2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifyActivity = this;
        setContentView(R.layout.activity_modify);
        Log.d("aa", "되나?");
        name=(EditText)findViewById(R.id.name_modify);
        phone=(EditText)findViewById(R.id.phone_modify);
        email=(EditText)findViewById(R.id.email_modify);
        pw1=(EditText)findViewById(R.id.pwchange_modify);
        pw2=(EditText)findViewById(R.id.pwchange2_modify);
    }

    //뒤로가기
    public void backonClick(View view) {
        ModifyActivity.modifyActivity.finish();
        finish();
    }
    //회원정보 수정 버튼
    public void modifyonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
        builder.setTitle("수정하기");
        builder.setMessage("정말로 수정하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

             ModifyActivity.modifyActivity.finish();
                finish();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }
}
