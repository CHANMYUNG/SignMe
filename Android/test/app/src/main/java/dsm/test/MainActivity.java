package dsm.test;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ms_square.etsyblur.BlurConfig;
import com.ms_square.etsyblur.BlurDialogFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import static android.R.attr.gravity;

public class MainActivity extends AppCompatActivity {
    MaterialCalendarView mcv;
    final Context context = this;
    private TextView tv;
    private long btnPressTime = 0;
    //일정내용 들어가는 부분
    final String content="일정이 없습니다.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.scahuletext);

        mcv=(MaterialCalendarView)findViewById(R.id.calendarView);
        mcv.addDecorators(
                new SundayDecorator(),
                new OneDayDecorator()


        );
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.d("wowo","wowo");
                Toast.makeText(MainActivity.this,date.getYear()+"/"+date.getMonth()+"/"+date.getDay(),Toast.LENGTH_LONG).show();

    }
});

    }
     private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

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

        if (isFinishing()) {
            return;
        }

        mcv.addDecorator(new EventDecorator(Color.RED, calendarDays));
    }
}
}
