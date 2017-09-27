package com.signme.signme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.signme.signme.Forget_Activity.FogetidActivity;
import com.signme.signme.server.APIinterface;
import com.signme.signme.tutoreal.TutorMainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NooHeat on 11/06/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private APIinterface apiInterface;
    public static Activity loginActivity;
    public static String id;
    EditText idField;
    EditText passwordField;
    //AQuery aquery;
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivity = this;


    }


    public void loginClicked(View view) {
        // aquery = new AQuery(getApplicationContext());

        idField = (EditText) findViewById(R.id.idField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        String user_id = idField.getText().toString();
        String user_password = passwordField.getText().toString();

        if ((user_id.trim().equals("") || user_password.trim().equals(""))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            Dialog dialog = builder.setMessage("아이디와 비밀번호는 반드시 입력해주세요.").setPositiveButton("OK", null).create();
            dialog.show();

        } else if (user_id.contains(" ") || user_password.contains(" ")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            Dialog dialog = builder.setMessage("아이디나 비밀번호에는 공백이 포함될 수 없습니다").setPositiveButton("OK", null).create();
            dialog.show();

        } else {
            if (!user_id.isEmpty() && !user_password.isEmpty()) {
                postLoginData(user_id, user_password);
//                    Intent intent = new Intent(getApplicationContext(), TutorMainActivity.class);
//                    startActivity(intent);
                // postLoginData(user_id,user_password);
                   /* Map<String,String> params=new HashMap<>();
                    params.put("id",id);
                    params.put("password",password);
                    aquery.ajax("http://13.124.15.202:80/account/sign/in",params,String.class,new AjaxCallback<String>(){
                        @Override
                        public void callback(String url, String response, AjaxStatus status) {
                            int statusCode=status.getCode();
                            Log.d("statuCode",Integer.toString(statusCode));
                            if(statusCode==201){
                                finish();
                                Intent intent = new Intent(getApplicationContext(), TutorMainActivity.class);
                                startActivity(intent);

                            }
                             else if(statusCode==400){
                                Log.d("연결","x");
                                Toast toast=Toast.makeText(getApplicationContext(),"연결 확인해주세요", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                Dialog dialog = builder.setMessage("아이디 비밀번호 다시 입력해 주세요.").setPositiveButton("OK", null).create();
                                dialog.show();
                            }
                        }
                    });
*/
            }


            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);


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

    public void postLoginData(final String id, String password) {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIinterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map map = new HashMap();
        map.put("id", id);
        map.put("password", password);
        map.put("type", "USER");
        apiInterface = retrofit.create(APIinterface.class);
        Call<JsonObject> call = apiInterface.doSignIn(map);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("ASDAS!@#!@#!@#%!%", response.code() + "");
                if (response.code() == 201) {
                    LoginActivity.id = id;

                    Log.d("RESPONSE", response.body().toString());
                    String token = response.body().get("x-access-token").toString();

                    SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
                    SharedPreferences.Editor editor = test.edit();

                    editor.putString("signme-x-access-token", token);
                    editor.commit(); //완료한다.

                    finish();
                    Intent intent = new Intent(getApplicationContext(), TutorMainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    idField.setText("");
                    passwordField.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "아이디나 비빌번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                    idField.setText("");
                    passwordField.setText("");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                idField.setText("");
                passwordField.setText("");

            }

        });


    }

}
