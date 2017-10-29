package com.signme.signme.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.signme.signme.Calender.EventDecorator;
import com.signme.signme.Calender.OneDayDecorator;
import com.signme.signme.Calender.SundayDecorator;
import com.signme.signme.LoginActivity;
import com.signme.signme.R;
import com.signme.signme.activity.ChangeEmailActivity;
import com.signme.signme.activity.ChangePwdActivity;
import com.signme.signme.activity.ResponsedLetterActivity;
import com.signme.signme.model.LetterListItem;
import com.signme.signme.override.AdvancedDot;
import com.signme.signme.server.APIInterface;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private FrameLayout fragmentContainer2;
    private FrameLayout fragmentContainer3;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<LetterListItem> mDataset = new ArrayList<>();
    private Retrofit retrofit;
    private APIInterface apiInterface;

    private long btnPressTime = 0;
    MaterialCalendarView mcv;
    final Context context = getActivity();
    //일정내용 들어가는 부분
    final String content = "일정이 없습니다.";

    private AHBottomNavigation bottomNavigation;
    private AHBottomNavigationViewPager viewPager;
    private LetterListFragment currentFragment = null;
    private LetterListViewPagerAdapter adapter;


    private Map<CalendarDay, ArrayList<Integer>> dayMap;

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
        Log.d("idfd", "dfdf");

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
        if (getArguments().getInt("index", 0) == 2) {
            view = inflater.inflate(R.layout.fragment_mypage, container, false);
            initMypage(view);
        }
        return view;
    }

    // 가정통신문 리스트 UI
    private void initLetterList(View view) {

        bottomNavigation = (AHBottomNavigation) view.findViewById(R.id.bottom_navigation);
        viewPager = (AHBottomNavigationViewPager) view.findViewById(R.id.view_pager);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("전체보기", R.drawable.ic_chat_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("비응답형", R.drawable.ic_insert_drive_file_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("응답형", R.drawable.ic_description_black_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("설문조사", R.drawable.ic_edit_black_24dp, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setTranslucentNavigationEnabled(true);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Log.d("#$#$", "onTabSelected: " + wasSelected);
                Log.d("#$#$", "onTabSelected1: " + (currentFragment == null));
                if (currentFragment == null) currentFragment = adapter.getCurrentFragment();
                Log.d("#$#$", "onTabSelected2: " + (currentFragment != null));
                if (wasSelected && (currentFragment != null)) {
                    currentFragment = (LetterListFragment) adapter.getItem(position);
                    currentFragment.refresh();
                    return true;
                }
                //if (currentFragment != null) currentFragment.willBeHidden();

                viewPager.setCurrentItem(position, true);

                if (currentFragment == null) return true;

                currentFragment = adapter.getCurrentFragment();
                //currentFragment.willBeDisplayed();

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
        fragmentContainer2 = (FrameLayout) view.findViewById(R.id.fragment_Calender);
        mcv = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        mcv.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar minimumDate = Calendar.getInstance();
        minimumDate.set(2017, Calendar.JANUARY, 1);
        Calendar maximumDate = Calendar.getInstance();
        maximumDate.set(2019, Calendar.DECEMBER, 31);
        mcv.state().edit()
                .setMinimumDate(minimumDate.getTime())
                .setMaximumDate(maximumDate.getTime())
                .commit();

        mcv.addDecorators(
                new SundayDecorator(),
                new OneDayDecorator()

        );

        mcv.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                final int year = date.getYear();
                final int month = date.getMonth() + 1;
                final int day = date.getDay();

                Map<String, Object> queryMap = new HashMap<>();
                queryMap.put("year", year);
                queryMap.put("month", month);
                queryMap.put("day", day);


                retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).addConverterFactory(GsonConverterFactory.create()).build();
                apiInterface = retrofit.create(APIInterface.class);

                Call<JsonArray> call = apiInterface.getTask(queryMap, getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));

                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                        if (response.code() == 200) {
                            JsonArray tasks = response.body();

                            SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText(year + "년 " + month + "월 " + day + "일 일정");

                            String contents = "";
                            for (int i = 0; i < tasks.size(); i++) {
                                JsonObject task = (JsonObject) tasks.get(i);
                                String type = task.get("type").toString().replace("\"", "");
                                if (type.equals("RESPONSELESS")) contents += "[비응답형]";
                                if (type.equals("RESPONSE")) contents += "[응답형]";
                                if (type.equals("SURVEY")) contents += "[설문조사]";
                                contents += " " + task.get("title").toString().replace("\"", "");
                                contents += " | 작성자 : " + task.get("writerName").toString().replace("\"", "");
                                if (i < tasks.size() - 1) contents += "\n";
                            }
                            dialog.setContentText(contents);
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {

                    }
                });
                Log.d("DASDASD", "onDateSelected: " + date.getDate());
            }
        });

        loadTasks();
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
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }


        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (getActivity().isFinishing()) {
                return;
            }
            Iterator<CalendarDay> dayIterator = dayMap.keySet().iterator();
            while (dayIterator.hasNext()) {
                ArrayList<CalendarDay> days = new ArrayList<>();
                CalendarDay day = dayIterator.next();
                days.add(day);
                final ArrayList<Integer> colorList = dayMap.get(day);
                mcv.addDecorator(new EventDecorator(Color.parseColor("#000000"), days) {
                    @Override
                    public void decorate(DayViewFacade view) {
                        view.addSpan(new AdvancedDot(5).setColors(colorList));
                    }
                });
            }
        }
    }

    //mypage
    private void initMypage(View view) {
        fragmentContainer3 = (FrameLayout) view.findViewById(R.id.fragment_Mypage);
        //이메일 수정
        RelativeLayout email_rl = (RelativeLayout) view.findViewById(R.id.email_rl);
        email_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailintent = new Intent(getActivity(), ChangeEmailActivity.class);
                startActivity(emailintent);
            }
        });
        //비밀번호 수정
        RelativeLayout pwd_rl = (RelativeLayout) view.findViewById(R.id.pwd_rl);
        pwd_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pwdintent = new Intent(getActivity(), ChangePwdActivity.class);
                startActivity(pwdintent);

            }
        });
        //참여한 가정통신문
        final RelativeLayout reseponsed_rl = (RelativeLayout) view.findViewById(R.id.respnosed_rl);
        reseponsed_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent responsedintent = new Intent(getActivity(), ResponsedLetterActivity.class);
                startActivity(responsedintent);
            }
        });


        //푸시알람 설정정
        ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.fcm_btn);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getActivity(), "푸시 알람이 설정 됬습니다.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "푸시 알람이 해제 됬습니다.", Toast.LENGTH_LONG).show();

                }
            }
        });

        //로그아웃
        RelativeLayout logoutlayout = (RelativeLayout) view.findViewById(R.id.Logout_layout);
        logoutlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("로그아웃");
                dialog.setMessage("로그아웃하시겠습니까?");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSharedPreferences("test", MODE_PRIVATE).edit().clear().commit();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        //탈퇴
        RelativeLayout outbtn = (RelativeLayout) view.findViewById(R.id.secession);
        outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("탈퇴");
                dialog.setMessage("탈퇴하시겠습니까?");
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).addConverterFactory(GsonConverterFactory.create()).build();
                        apiInterface = retrofit.create(APIInterface.class);

                        Call<Void> call = apiInterface.accountLeave("USER", getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 200) {
                                    getActivity().getSharedPreferences("test", MODE_PRIVATE).edit().clear().commit();
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                                            .setTitleText("탈퇴")
                                            .setContentText("탈퇴하였습니다.")
                                            .setConfirmText("완료").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    }).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }


    public void loadTasks() {
        dayMap = new HashMap<>();
        // TODO: 점찍기........startDate, endDate 사이의 모든 날에 색 입히기.;;
        retrofit = new Retrofit.Builder().baseUrl(APIInterface.URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(APIInterface.class);

        Call<JsonArray> call = apiInterface.getTask(new HashMap<String, Object>(), getActivity().getSharedPreferences("test", MODE_PRIVATE).getString("signme-x-access-token", null));
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray tasks = response.body();

                for (int i = 0; i < tasks.size(); i++) {
                    JsonObject task = (JsonObject) tasks.get(i);
                    int color = Color.parseColor(task.get("color").toString().replace("\"", ""));

                    String startDate = task.get("startDate").toString().replace("\"", "");
                    String[] splitedStartDate = startDate.split(" ");

                    String endDate = task.get("endDate").toString().replace("\"", "");
                    String[] splitedEndDate = endDate.split(" ");

                    DateTime startDateTime = new DateTime(Integer.parseInt(splitedStartDate[0].split("-")[0]), Integer.parseInt(splitedStartDate[0].split("-")[1]), Integer.parseInt(splitedStartDate[0].split("-")[2]), 0, 0, 0);
                    DateTime endDateTime = new DateTime(Integer.parseInt(splitedEndDate[0].split("-")[0]), Integer.parseInt(splitedEndDate[0].split("-")[1]), Integer.parseInt(splitedEndDate[0].split("-")[2]), 23, 59, 59);

                    while (true) {
                        if (endDateTime.toDate().after(startDateTime.toDate())) {
                            ArrayList<Integer> list = dayMap.get(CalendarDay.from(startDateTime.toDate()));
                            if (list == null) list = new ArrayList<>();
                            list.add(color);
                            dayMap.put(CalendarDay.from(startDateTime.toDate()), list);
                        } else {
                            break;
                        }
                        startDateTime = startDateTime.plusDays(1);
                    }
                }
                new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
                Log.d("!@#!@#@!", "onFailure: " + "FAILD");
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
