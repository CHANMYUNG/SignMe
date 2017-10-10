package com.signme.signme.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.signme.signme.R;
import com.signme.signme.adapter.LetterListAdapter;
import com.signme.signme.model.LetterListItem;
import com.signme.signme.server.APIInterface;

import java.util.ArrayList;

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
        ImageView backbtn=(ImageView)findViewById(R.id.respnosed_back);
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

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LetterListAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

    }
}

