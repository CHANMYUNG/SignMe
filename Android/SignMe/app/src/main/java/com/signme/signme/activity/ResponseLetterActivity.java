package com.signme.signme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.signme.signme.R;
import com.signme.signme.server.APIInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dsm2016 on 2017-08-23.
 */

public class ResponseLetterActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button responseSubmitButton;
    private boolean answer = false;
    private Retrofit retrofit;
    private APIInterface apiInterface;
    private int letterNumber;
    private boolean modifyMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_letter);
        //참여 or 불참 라디오버튼
        Intent intent = getIntent();
        letterNumber = intent.getIntExtra("letterNumber", -1);
        modifyMode = intent.getBooleanExtra("modify", false);

        retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonObject> call = apiInterface.getResponseLetter("/letter/response/" + letterNumber, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject letter = response.body();
                TextView title = (TextView) findViewById(R.id.response_title);
                TextView contents = (TextView) findViewById(R.id.response_contents);
                TextView openDate = (TextView) findViewById(R.id.response_openDate);
                TextView closeDate = (TextView) findViewById(R.id.response_closeDate);

                title.setText(letter.get("title").toString().replace("\"", ""));
                contents.setText(letter.get("contents").toString().replace("\"", ""));
                openDate.setText(letter.get("openDate").toString().replace("\"", ""));
                closeDate.setText(letter.get("closeDate").toString().replace("\"", ""));

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.response_radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.response_radio_yes) answer = true;
                else answer = false;
            }
        });

        responseSubmitButton = (Button) findViewById(R.id.response_submit_button);
        responseSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modifyMode == false) {
                    answerToResponseLetter();
                } else {
                    modifyAnswer();
                }
            }
        });
    }

    private void answerToResponseLetter() {
        retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).build();
        apiInterface = retrofit.create(APIInterface.class);

        Map<String, Object> fieldMap = new HashMap<>();

        fieldMap.put("answer", answer);

        Call<Void> call = apiInterface.doAnswerToResponse("answer/response/" + letterNumber, fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "응답완료.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "이미 응답하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void modifyAnswer() {
        retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).build();
        apiInterface = retrofit.create(APIInterface.class);

        Map<String, Object> fieldMap = new HashMap<>();

        fieldMap.put("answer", answer);

        Call<Void> call = apiInterface.changeAnswerToResponse("answer/response/" + letterNumber, fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "응답완료.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "이미 응답하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
