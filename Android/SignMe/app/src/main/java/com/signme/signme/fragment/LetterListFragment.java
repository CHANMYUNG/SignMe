package com.signme.signme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NooHeat on 06/10/2017.
 */

public class LetterListFragment extends Fragment {
    private FrameLayout fragmentContainer;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset = new ArrayList<>();
    private Retrofit retrofit;
    private APIInterface apiInterface;

    public static LetterListFragment newInstance(int index) {
        LetterListFragment fragment = new LetterListFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey_list, container, false);

        fragmentContainer = (FrameLayout) view.findViewById(R.id.fragment_container);

        mDataset = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.letter_survey_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LetterListAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        if (getArguments().getInt("index", 0) == 0) {
            loadAllLetters();
        }
        if (getArguments().getInt("index", 0) == 1) {
            loadLetters(LetterTypes.RESPONSELESSLETTER);
        }
        if (getArguments().getInt("index", 0) == 2) {
            loadLetters(LetterTypes.RESPONSELETTER);
        }
        if (getArguments().getInt("index", 0) == 3) {
            loadLetters(LetterTypes.SURVEY);
        }

        return view;
    }

    private void loadAllLetters() {
        mDataset.clear();
        mAdapter.notifyDataSetChanged();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonArray> call = null;


        call = apiInterface.getLetterList(getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.code() == 200) {
                    JsonArray letters = response.body();
                    Iterator iterator = letters.iterator();
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

    public void loadLetters(final LetterTypes type) {
        Log.d("!@#!#", "뿌아아아: ");
        mDataset.clear();
        mAdapter.notifyDataSetChanged();

        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonArray> call = null;

        if (type == LetterTypes.RESPONSELESSLETTER) {
            call = apiInterface.getResponselessList(getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
        }
        if (type == LetterTypes.RESPONSELETTER) {
            call = apiInterface.getResponseList(getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
        }
        if (type == LetterTypes.SURVEY) {
            call = apiInterface.getSurveyList(getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
        }

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.code() == 200) {
                    JsonArray letters = response.body();
                    Iterator iterator = letters.iterator();
                    while (iterator.hasNext()) {
                        JsonObject item = (JsonObject) iterator.next();

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

    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }

    public void willBeDisplayed() {
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    public void refresh() {
        if (getArguments().getInt("index", 0) == 0) {
            loadLetters(LetterTypes.RESPONSELESSLETTER);
        }
        if (getArguments().getInt("index", 0) == 1) {
            loadLetters(LetterTypes.RESPONSELETTER);
        }
        if (getArguments().getInt("index", 0) == 2) {
            loadLetters(LetterTypes.SURVEY);
        }
    }
}
