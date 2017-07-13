package com.signme.signme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by dsm2016 on 2017-07-12.
 */

public class ModifyActivity extends AppCompatActivity {
   public static Activity  modifyActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifyActivity=this;
        setContentView(R.layout.activity_modify);


    }
    public  void backonClick(View view){
        ModifyActivity.modifyActivity.finish();
        finish();
    }
}
