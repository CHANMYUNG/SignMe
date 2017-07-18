package com.signme.signme;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class ModifyActivity extends AppCompatActivity {
    public static Activity modifyActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifyActivity = this;
        setContentView(R.layout.activity_modify);
        Log.d("aa", "되나?");
    }

    public void backonClick(View view) {
        ModifyActivity.modifyActivity.finish();
        finish();
    }

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
