package com.signme.signme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.signme.signme.LetterTypes;
import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.adapter.LetterListAdapter;
import com.signme.signme.model.LetterListItem;
import com.signme.signme.server.APIInterface;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ghdth on 2017-10-09.
 */

public class ResponsedLetterActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset = new ArrayList<>();
    private Retrofit retrofit;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsed_letter);
        //뒤로가기
        ImageView backbtn = (ImageView) findViewById(R.id.respnosed_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getLetterListFromServer();
    }

    private void getLetterListFromServer() {
        mDataset = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.responsed_rc);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LetterListAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonArray> call = apiInterface.getResponsedLetterList(getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("!@#@", "onResponse: " + response.code());
                if (response.code() == 200) {
                    JsonArray letters = response.body();
                    Iterator iterator = letters.iterator();
                    Log.d("!@#@!#", "onResponse: " + letters.size());
                    while (iterator.hasNext()) {
                        JsonObject item = (JsonObject) iterator.next();

                        int letterNumber = Integer.parseInt(item.get("letterNumber").toString().replace("\"", ""));
                        String title = item.get("title").toString().replace("\"", "");
                        String writerName = item.get("writerName").toString().replace("\"", "");
                        String openDate = item.get("openDate").toString().replace("\"", "");
                        LetterTypes type = LetterTypes.valueOf(item.get("type").toString().replace("\"", ""));
                        LetterListItem letterItem = new LetterListItem();
                        letterItem.setLetterNumber(letterNumber);
                        letterItem.setType(type);
                        letterItem.setTitle(title);
                        letterItem.setWriterName(writerName);
                        letterItem.setOpenDate(openDate);
                        letterItem.setLetterNumber(letterNumber);
                        if (type != LetterTypes.RESPONSELESSLETTER) {
                            letterItem.setAnswered(Boolean.parseBoolean(item.get("isAnswered").toString().replace("\"", "")));
                            letterItem.setCloseDate(item.get("closeDate").toString().replace("\"", ""));

                        }

                        mDataset.add(letterItem);
                    }
                    mAdapter.notifyDataSetChanged();
                } else if (response.code() == 401) {

                    Toast.makeText(getApplicationContext(), "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show();

                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);

                } else {
                    Toast.makeText(getApplicationContext(), "일시적인 서버오류입니다. ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

