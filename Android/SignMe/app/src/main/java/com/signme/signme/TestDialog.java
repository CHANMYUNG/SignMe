package com.signme.signme;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by dsm2016 on 2017-08-24.
 */

public class TestDialog extends AppCompatActivity {


    DrawPoint drawPoint;
    CanvasView mView;

   /* public TestDialog(Context context) {
        super(context);

        mView = new CanvasView(context);
        drawPoint = new DrawPoint();
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.signpad);
       // getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,android.R.drawable.ic_dialog_alert);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mView=new CanvasView(this);
        drawPoint=new DrawPoint();

        FrameLayout frame = (FrameLayout) findViewById(R.id.signmainLayout);
          frame.addView(mView, 0);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPoint.addPoint(event.getRawX(), event.getRawY(), false);
                mView.invalidate();
            case MotionEvent.ACTION_MOVE:
                drawPoint.addPoint(event.getRawX(), event.getRawY(), true);
                mView.invalidate();
        }
        return true;
    }

    //    Canvas
    protected class CanvasView extends View {
        public CanvasView(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) {
            drawCanvas(canvas);
        }

        public void drawCanvas(Canvas canvas) {
            for (int i = 0; i < drawPoint.arrayTouchPoint.size(); i++) {
                if (drawPoint.arrayTouchPoint.get(i).draw) {
                    canvas.drawLine(drawPoint.getX(i - 1), drawPoint.getY(i - 1), drawPoint.getX(i), drawPoint.getY(i), drawPoint.paint);
                } else {
                    canvas.drawPoint(drawPoint.getX(i), drawPoint.getY(i), drawPoint.paint);
                }
                this.invalidate();
            }
        }
    }

    /**
     * 확인 버튼 누르면 어떻게 되는지나 이런건 전부 onCreate쪽에 버튼 객체 가져와서 이벤트 처리하는 방식으로 구현하구
     * 액티비티에서 원래 하듯이 뭔소린지 알지? 저거 저렇게 액티비티 안에다가 다이얼로그 만들면 나중에 나같은 애들한테 뚜까맞는다
     */
}
