package com.signme.signme.survey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.signme.signme.R;
import com.signme.signme.server.APIinterface;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NooHeat on 27/09/2017.
 */

public class SurveyActivity extends AppCompatActivity {
    private static final String URL = "http://10.211.55.2:8000/";
    APIinterface apiInterface;
    Retrofit retrofit;
    SurveyQuestionAdapter adapter;
    ListView questionListView;
    TextView surveyTitle;
    TextView surveySummary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        int letterNumber = getIntent().getIntExtra("letterNumber", -1);
        questionListView = (ListView) findViewById(R.id.survey_question_list);

        adapter = new SurveyQuestionAdapter();

        questionListView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIinterface.class);

        Call<JsonObject> call = apiInterface.getSurvey("/survey/" + letterNumber, getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
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
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

        Log.d("LETTER NUMBER #!&*$!&*", letterNumber + "");

    }
}
