package com.signme.signme;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

/**
 * Created by dsm2016 on 2017-07-08.
 */

public class ViewFlipperAction implements View.OnTouchListener {
    Context context;
    //전체화면 개수
    int countIndexes;
    //현재화면인덱스
    int currentIndex;
    //터치시작 x좌표
    float downX;
    //터치끝 x좌표
    float upX;
    ViewFlipper flipper;
    ViewFlipperCallback indexCallback;

    public interface ViewFlipperCallback {
        void onFlipperActionCallback(int position);
    }

    public ViewFlipperAction(Context context, ViewFlipper flipper) {
        this.context = context;
        this.flipper = flipper;
        if (context instanceof ViewFlipperCallback) {
            indexCallback = (ViewFlipperCallback) context;
        }
        currentIndex = 0;
        downX = 0;
        upX = 0;
        countIndexes = flipper.getChildCount();
        indexCallback.onFlipperActionCallback(currentIndex);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upX = event.getX();
        }
        if (upX < downX) {
            flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
            if (currentIndex < (countIndexes - 1)) {
                flipper.showNext();
                currentIndex++;
                indexCallback.onFlipperActionCallback(currentIndex);
            }
        } else if (upX > downX) {
            flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_in));
            flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.push_right_out));
            if (currentIndex > 0) {
                flipper.showPrevious();
                currentIndex--;
                indexCallback.onFlipperActionCallback(currentIndex);
            }
        }

        return true;
    }
}
