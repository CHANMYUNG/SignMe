package com.signme.signme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.signme.signme.adapter.LetterListAdapter;

import com.signme.signme.adapter.LetterListItem;
import com.signme.signme.server.APIinterface;

import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dsm2016 on 2017-09-04.
 */

public class LetterListActivity extends AppCompatActivity {

    private final String URL = "http://10.211.55.2:8000/";
    private Retrofit retrofit;
    private ListView mListView = null;
    //private LetterListActivity.ListViewAdapter mAdapter = null;
    LetterListAdapter mAdapter;
    private String repo_title;
    private String repo_date;
    private APIinterface apiInterface;
    //ArrayAdapter<ListViewAdapter> adapter;
    int clickCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signlist);

        mListView = (ListView) findViewById(R.id.contentlist);


        mAdapter = new LetterListAdapter();

        mListView.setAdapter(mAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(APIinterface.class);

        Call<JsonArray> call = apiInterface.getLetterList(getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.code() == 200) {
                    JsonArray letters = response.body();

                    Iterator iterator = letters.iterator();
                    while (iterator.hasNext()) {
                        JsonObject item = (JsonObject) iterator.next();


                        LetterTypes type = LetterTypes.valueOf(item.get("type").toString().replace("\"", ""));
                        int letterNumber = Integer.parseInt(item.get("letterNumber").toString().replace("\"", ""));
                        String title = item.get("title").toString().replace("\"", "");
                        String openDate = item.get("openDate").toString().replace("\"", "");


                        LetterListItem letterItem = new LetterListItem();
                        letterItem.setType(type);
                        letterItem.setTitle(title);
                        letterItem.setOpenDate(openDate);
                        if (type != LetterTypes.RESPONSELESSLETTER) {
                            letterItem.setCloseDate(item.get("closeDate").toString().replace("\"", ""));
                        }
                        mAdapter.addItem(letterItem);
                        mAdapter.notifyDataSetChanged();

                    }
                } else if (response.code() == 401) {

                    Toast.makeText(getApplicationContext(), "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG);

                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "일시적인 서버오류입니다. ", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "오류가 발생했습니다. ", Toast.LENGTH_SHORT);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LetterListItem item = (LetterListItem) parent.getAdapter().getItem(position);
                Log.d("!@#*!&@#!*@#&*", item.getTitle());
            }
        });
    }
}
