package com.signme.signme.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by NooHeat on 30/09/2017.
 */

public class WrapLayoutManager extends LinearLayoutManager {
    public WrapLayoutManager(Context context) {
        super(context);
    }

    public WrapLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WrapLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d("ASDASD", "onLayoutChildren: ");
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            Log.d("FUCKING ERROR OCCURED", "onLayoutChildren: BUT I CAUGHT :)");
        }
    }


}
