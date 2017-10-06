package com.signme.signme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.model.SurveyQuestionItem;
import com.signme.signme.server.APIInterface;
import com.signme.signme.adapter.SurveyQuestionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NooHeat on 27/09/2017.
 */

public class SurveyActivity extends AppCompatActivity {
    APIInterface apiInterface;
    Retrofit retrofit;
    SurveyQuestionAdapter adapter;
    ListView questionListView;
    TextView surveyTitle;
    TextView surveySummary;
    int letterNumber;
    private static Activity thisActivity;
    private boolean modifyMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        thisActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        letterNumber = getIntent().getIntExtra("letterNumber", -1);
        modifyMode = getIntent().getBooleanExtra("modify", false);

        Log.d("MODE", modifyMode + "");
        questionListView = (ListView) findViewById(R.id.survey_question_list);

        View header = getLayoutInflater().inflate(R.layout.survey_summary_header, null, false);
        View footer = getLayoutInflater().inflate(R.layout.survey_submit_button, null, false);

        questionListView.addHeaderView(header);
        questionListView.addFooterView(footer);

        adapter = new SurveyQuestionAdapter();

        questionListView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonObject> call = apiInterface.getSurvey("/survey/" + letterNumber, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonObject survey = response.body();

                    surveyTitle = (TextView) findViewById(R.id.survey_title);
                    surveyTitle.setText(survey.get("title").toString().replace("\"", ""));

                    surveySummary = (TextView) findViewById(R.id.survey_summary);
                    surveySummary.setText(survey.get("summary").toString().replace("\"", ""));

                    JsonArray questions = survey.getAsJsonArray("items");
                    Iterator quesionIterator = questions.iterator();
                    while (quesionIterator.hasNext()) {

                        String question = quesionIterator.next().toString().replace("\"", "");
                        SurveyQuestionItem questionItem = new SurveyQuestionItem();
                        questionItem.setQuestion(question);

                        adapter.addItem(questionItem);
                        Log.d("notifyDataSetChanged", "!@#!@#");
                        adapter.notifyDataSetChanged();
                    }
                }
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "삭제된 설문조사입니다.", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 401) {
                    Toast.makeText(getApplicationContext(), "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                }
                if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("ERR:: GETTING SURVEY", t.getStackTrace().toString());
                Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다. 다시 시도해주세요. ", Toast.LENGTH_SHORT).show();
            }
        });


        Button submitButton = (Button) footer.findViewById(R.id.survey_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> answers = new ArrayList<Integer>();

                answers.addAll(adapter.getAnswers());

                Log.d("toSTring", answers.toString());

                if (answers.contains(null)) {
                    Toast.makeText(getApplicationContext(), "빈틈없이 입력해주세요!", Toast.LENGTH_SHORT).show();
                } else if (modifyMode == false) {
                    answerToSurvey(answers);
                } else if (modifyMode == true) {
                    modifyAnswer(answers);
                }
            }
        });
    }

    public void answerToSurvey(ArrayList<Integer> answers) {
        Log.d("POSTSURVEY", "ADIUHASDHUAS");
        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);

        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("answers", answers);

        Call<Void> call = apiInterface.doAnswerToSurvey("/answer/survey/" + letterNumber, fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Log.d("SUCCEED", "BRO!");
                    Toast.makeText(getApplicationContext(), "답변 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (statusCode == 400) {
                    Toast.makeText(getApplicationContext(), "서버 오류입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "이미 답변하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ERR:: ANSWER TO SURVEY", t.getStackTrace() + "");
                Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다. 다시 시도해주세요. ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void modifyAnswer(ArrayList<Integer> answers) {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIInterface.class);

        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("answers", answers);

        Call<Void> call = apiInterface.modifyAnswerToSurvey("/answer/survey/" + letterNumber, fieldMap, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    Log.d("SUCCEED", "BRO!");
                    Toast.makeText(getApplicationContext(), "답변 완료", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (statusCode == 400) {
                    Toast.makeText(getApplicationContext(), "서버 오류입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "이미 답변하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ERR:: ANSWER TO SURVEY", t.getStackTrace() + "");
                Toast.makeText(getApplicationContext(), "일시적인 서버 오류입니다. 다시 시도해주세요. ", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
