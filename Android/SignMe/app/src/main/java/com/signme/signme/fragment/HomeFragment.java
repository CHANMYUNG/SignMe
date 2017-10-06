package com.signme.signme.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.signme.signme.Calender.EventDecorator;
import com.signme.signme.Calender.OneDayDecorator;
import com.signme.signme.Calender.SundayDecorator;
import com.signme.signme.LetterTypes;
import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.adapter.LetterListAdapter;
import com.signme.signme.model.LetterListItem;
import com.signme.signme.server.APIInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

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
    private FrameLayout fragmentContainer2;
    private FrameLayout fragmentContainer;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset = new ArrayList<>();
    private Retrofit retrofit;
    private APIInterface apiInterface;
    private TextView tv;
    private long btnPressTime = 0;
    MaterialCalendarView mcv;
    final Context context=getActivity();
    //일정내용 들어가는 부분
    final String content="일정이 없습니다.";

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
        Log.d("idfd","dfdf");
        if (getArguments().getInt("index", 0) == 0) {
            view = inflater.inflate(R.layout.fragment_letter_list, container, false);
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
        fragmentContainer = (FrameLayout) view.findViewById(R.id.fragment_container);

        mDataset = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.letter_list);
        mRecyclerView = (RecyclerView) mRecyclerView.findViewById(R.id.letter_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LetterListAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        loadLetterListFromServer();
    }

    private void initTask(View view) {
        fragmentContainer2=(FrameLayout)view.findViewById(R.id.fragment_Calender);
        tv=(TextView)view.findViewById(R.id.scahuletext);
        mcv=(MaterialCalendarView)view.findViewById(R.id.calendarView);
        mcv.addDecorators(
                new SundayDecorator(),
                new OneDayDecorator()

        );
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView mcv, @NonNull CalendarDay date, boolean selected) {
                Log.d("wowo","wowo");
                //날짜 한번 클릭했을 때
                if(System.currentTimeMillis()>btnPressTime+500){
                    btnPressTime = System.currentTimeMillis();
                    mcv.state().edit()
                            .setFirstDayOfWeek(Calendar.MONDAY)
                            .setMinimumDate(CalendarDay.from(1900, 1, 1))
                            .setMaximumDate(CalendarDay.from(2100, 12, 31))
                            .setCalendarDisplayMode(CalendarMode.WEEKS)
                            .commit();
                    tv.setText(content);
                    return;
                }
                //날짜 클릭 두 번 했을 때
                if (System.currentTimeMillis()<=btnPressTime+1000){
                    mcv.state().edit()
                            .setFirstDayOfWeek(Calendar.MONDAY)
                            .setMinimumDate(CalendarDay.from(1900, 1, 1))
                            .setMaximumDate(CalendarDay.from(2100, 12, 31))
                            .setCalendarDisplayMode(CalendarMode.MONTHS)
                            .commit();
                    tv.setText("");
                }

            }
        });

    }
    public class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            //일정이 있는 특정 날짜에 점 찍어 주는 것

            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE,3);
            }



            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if ( getActivity().isFinishing()) {
                return;
            }

            mcv.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
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
            loadLetterListFromServer();
        }
    }

    public void loadLetterListFromServer() {
        mDataset.clear();
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onResume() {
        super.onResume();
        Log.d("!@!", "onResume: ");
    }
}
