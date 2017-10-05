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

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NooHeat on 28/09/2017.
 */

public class HomeFragment extends Fragment {
    private FrameLayout fragmentContainer;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset = new ArrayList<>();
    private Retrofit retrofit;
    private APIInterface apiInterface;


    private AHBottomNavigation bottomNavigation;
    private AHBottomNavigationViewPager viewPager;
    private LetterListFragment currentFragment = null;
    private LetterListViewPagerAdapter adapter;

    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (getArguments().getInt("index", 0) == 0) {
            view = inflater.inflate(R.layout.fragment_letter_list_2, container, false);
            initLetterList(view);
            return view;
        }
        if (getArguments().getInt("index", 0) == 1) {
            view = inflater.inflate(R.layout.fragment_task, container, false);
            initTask(view);
            return view;
        }
        return view;
    }

    // 가정통신문 리스트 UI
    private void initLetterList(View view) {

        bottomNavigation = (AHBottomNavigation) view.findViewById(R.id.bottom_navigation);
        viewPager = (AHBottomNavigationViewPager) view.findViewById(R.id.view_pager);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("전체보기", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("비응답형", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("응답형", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("설문조사", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (currentFragment == null) currentFragment = adapter.getCurrentFragment();

                if (wasSelected && currentFragment != null) {
                    currentFragment.refresh();
                    return true;
                }
                if (currentFragment != null) currentFragment.willBeHidden();

                viewPager.setCurrentItem(position, false);

                if (currentFragment == null) return true;

                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();

                return true;
            }
        });

        viewPager.setOffscreenPageLimit(4);
        adapter = new LetterListViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        currentFragment = adapter.getCurrentFragment();

        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);

        bottomNavigation.setCurrentItem(0);
    }

    private void initTask(View view) {
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
        }
    }
//
//    public void loadLetterListFromServer() {
//        mDataset.clear();
//        mAdapter.notifyDataSetChanged();
//        retrofit = new Retrofit.Builder()
//                .baseUrl(APIInterface.URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        apiInterface = retrofit.create(APIInterface.class);
//
//        Call<JsonArray> call = apiInterface.getLetterList(getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
//
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                if (response.code() == 200) {
//                    JsonArray letters = response.body();
//                    Iterator iterator = letters.iterator();
//                    while (iterator.hasNext()) {
//                        JsonObject item = (JsonObject) iterator.next();
//                        Log.d("ASDASD", item.toString());
//                        LetterTypes type = LetterTypes.valueOf(item.get("type").toString().replace("\"", ""));
//                        int letterNumber = Integer.parseInt(item.get("letterNumber").toString().replace("\"", ""));
//                        String title = item.get("title").toString().replace("\"", "");
//                        String writerName = item.get("writerName").toString().replace("\"", "");
//                        String openDate = item.get("openDate").toString().replace("\"", "");
//
//                        LetterListItem letterItem = new LetterListItem();
//                        letterItem.setLetterNumber(letterNumber);
//                        letterItem.setType(type);
//                        letterItem.setTitle(title);
//                        letterItem.setWriterName(writerName);
//                        letterItem.setOpenDate(openDate);
//                        letterItem.setLetterNumber(letterNumber);
//                        if (type != LetterTypes.RESPONSELESSLETTER) {
//                            letterItem.setAnswered(Boolean.parseBoolean(item.get("isAnswered").toString().replace("\"", "")));
//                            letterItem.setCloseDate(item.get("closeDate").toString().replace("\"", ""));
//
//                        }
//
//                        mDataset.add(letterItem);
//                    }
//                    mAdapter.notifyDataSetChanged();
//                } else if (response.code() == 401) {
//
//                    Toast.makeText(getActivity().getApplicationContext(), "로그인이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show();
//
//                    Intent loginActivity = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
//                    startActivity(loginActivity);
//
//                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), "일시적인 서버오류입니다. ", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//                Toast.makeText(getActivity().getApplicationContext(), "오류가 발생했습니다. ", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("!@!", "onResume: ");
    }
}
