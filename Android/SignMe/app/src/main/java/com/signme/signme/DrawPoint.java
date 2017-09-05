package com.signme.signme;


import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2017-08-22.
 */

public class DrawPoint {
    ArrayList<TouchPoint> arrayTouchPoint;

    Paint paint;
    int color = Color.BLACK;

    public DrawPoint() {
        arrayTouchPoint = new ArrayList<TouchPoint>();
        this.paint = new Paint();

        paint.setStrokeWidth(3);
        paint.setColor(color);
        paint.setAntiAlias(true);
        Log.d("DrawPoint","5");
    }

    public class TouchPoint {
        float x;
        float y;
        boolean draw;

        public TouchPoint(float x, float y) {
            this.x = x;
            this.y = y;
            Log.d("TouchPoint","6");
        }
    }
        public float getX(int index) {
            return arrayTouchPoint.get(index).x;
        }
        public float getY(int index){
            return arrayTouchPoint.get(index).y;
        }

        public void addPoint(float x,float y, boolean draw){
            arrayTouchPoint.add(new TouchPoint(x,y));
            arrayTouchPoint.get(arrayTouchPoint.size()-1).draw=draw;
        Log.d("addPoint","7");
        }


}

