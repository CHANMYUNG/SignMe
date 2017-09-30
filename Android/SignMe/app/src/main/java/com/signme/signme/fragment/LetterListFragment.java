package com.signme.signme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.signme.signme.LetterTypes;
import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.adapter.LetterListAdapter;
import com.signme.signme.model.LetterListItem;
import com.signme.signme.activity.SettingActivity;
import com.signme.signme.server.APIInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NooHeat on 28/09/2017.
 */

public class LetterListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset;
    private Retrofit retrofit;
    private APIInterface apiInterface;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_letter_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.letter_list);

        getLetterListFromServer();

        rootView.findViewById(R.id.go_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GO SETTING", "CLICKED");
                // ((LetterListAdapter) mAdapter).clear();
                Intent setting = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                getActivity().startActivity(setting);
            }
        });

        return rootView;
    }

    // TODO: RecyclerView 클릭시 몇번째 아이템인지 알아내기 -> 그에 맞는 Intent
    private void getLetterListFromServer() {
        mDataset = new ArrayList<>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.letter_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LetterListAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonArray> call = apiInterface.getLetterList(getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

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
                        String writerName = item.get("writerName").toString().replace("\"", "");
                        String openDate = item.get("openDate").toString().replace("\"", "");

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
                        Log.d("PROCESSED", letterItem.toString());
                        mDataset.add(letterItem);
                    }
                    mAdapter.notifyDataSetChanged();
                } else if (response.code() == 401) {

                    Toast.makeText(getActivity().getApplicationContext(), "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show();

                    Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "일시적인 서버오류입니다. ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "오류가 발생했습니다. ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
