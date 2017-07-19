package com.signme.signme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by NooHeat on 11/06/2017.
 */

public class LoginActivity extends AppCompatActivity {

    public static Activity loginActivity;

    AQuery aquery;
    public static final String url = "http://13.124.15.202:80/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this;
    }


    public void loginClicked(View view) {
        aquery = new AQuery(getApplicationContext());

        EditText idField = (EditText) findViewById(R.id.idField);
        EditText passwordField = (EditText) findViewById(R.id.passwordField);

        String id = idField.getText().toString();
        String password = passwordField.getText().toString();

         if ((id.trim().equals("") || password.trim().equals(""))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            Dialog dialog = builder.setMessage("아이디와 비밀번호는 반드시 입력해주세요.").setPositiveButton("OK", null).create();
            dialog.show();

        } else if (id.contains(" ") || password.contains(" ")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            Dialog dialog = builder.setMessage("아이디나 비밀번호에는 공백이 포함될 수 없습니다").setPositiveButton("OK", null).create();
            dialog.show();

        } else {
            HashMap<String, Object> params = new HashMap<>();

            params.put("id", id);
            params.put("password", password);

            aquery.ajax(url + "account/login", params, String.class, new AjaxCallback<String>() {

                @Override
                public void callback(String url, String response /* ResponseType responseValue */, AjaxStatus status) {
                    /*
                    내용 추가 필요
                     */

                }

            });

            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Intent intent = new Intent(getApplicationContext(), TutorMainActivity.class);

            startActivity(intent);

            LandingActivity.landingActivity.finish();
            finish();

        }

    }

    public void BackonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
        LandingActivity.landingActivity.finish();
    }

    public void CloseonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(intent);
        LandingActivity.landingActivity.finish();
    }

    public void goRegisterClicked(View view) {

        Log.d(this.getLocalClassName(), "goRegisterClicked: ");
    }

    public void goForgetClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), FogetidActivity.class);
        startActivity(intent);
        Log.d(this.getLocalClassName(), "goForgetClicked: ");
    }
}
