package com.signme.signme.fragment;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.signme.signme.Calender.OneDayDecorator;
import com.signme.signme.Calender.SundayDecorator;
import com.signme.signme.R;

/**v
 * Created by NooHeat on 28/09/2017.
 */

public class TaskFragment extends Fragment  {
    View rootView;

    MaterialCalendarView mcv;
    OneDayDecorator oneDayDecorator=new OneDayDecorator();
    SundayDecorator sundayDecorator=new SundayDecorator();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Log.d("wpfqkf","wha");
        rootView = inflater.inflate(R.layout.fragment_task, container, false);
       initTask(rootView);
        return rootView;
    }
    void initTask(View view){

    }

}