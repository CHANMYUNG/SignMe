package com.signme.signme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.signme.signme.LetterTypes;
import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.adapter.LetterListAdapter;

import com.signme.signme.adapter.WrapLayoutManager;
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
 * Created by dsm2016 on 2017-09-04.
 */

public class LetterListActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset;
    private Retrofit retrofit;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_letter_list);
        getLetterListFromServer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("E!@#", "onRestart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("E!@#", "onResume: ");
    }

    // TODO: RecyclerView 클릭시 몇번째 아이템인지 알아내기 -> 그에 맞는 Intent
    private void getLetterListFromServer() {
        mDataset = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.letter_list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new WrapLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new LetterListAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonArray> call = apiInterface.getLetterList(getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.code() == 200) {
                    JsonArray letters = response.body();
                    Iterator iterator = letters.iterator();
                    while (iterator.hasNext()) {
                        JsonObject item = (JsonObject) iterator.next();
                        Log.d("ASDASD", item.toString());
                        LetterTypes type = LetterTypes.valueOf(item.get("type").toString().replace("\"", ""));
                        int letterNumber = Integer.parseInt(item.get("letterNumber").toString().replace("\"", ""));
                        String title = item.get("title").toString().replace("\"", "");
                        String openDate = item.get("openDate").toString().replace("\"", "");

                        LetterListItem letterItem = new LetterListItem();
                        letterItem.setLetterNumber(letterNumber);
                        letterItem.setType(type);
                        letterItem.setTitle(title);
                        letterItem.setOpenDate(openDate);
                        letterItem.setLetterNumber(letterNumber);
                        if (type != LetterTypes.RESPONSELESSLETTER) {
                            letterItem.setAnswered(Boolean.parseBoolean(item.get("isAnswered").toString().replace("\"", "")));
                            letterItem.setCloseDate(item.get("closeDate").toString().replace("\"", ""));

                        }
                        Log.d("PROCESSED", letterItem.toString());
                        mDataset.add(letterItem);
                    }
                    mAdapter.notifyItemChanged(mDataset.size() - 1);
                    //mAdapter.notifyDataSetChanged();

                } else if (response.code() == 401) {

                    Toast.makeText(getApplicationContext(), "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show();

                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
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
