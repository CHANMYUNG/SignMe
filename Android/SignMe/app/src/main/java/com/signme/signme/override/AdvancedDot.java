package com.signme.signme.override;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NooHeat on 08/10/2017.
 */

public class AdvancedDot extends DotSpan {
    private ArrayList<Integer> colors;
    private float radius;

    public AdvancedDot() {
        super();
        radius = 3;
    }

    public AdvancedDot(float radius) {
        super(radius);
        this.radius = radius;
    }


    /*
    @param Color... decoration colors (must be less than 3)
     */
    public AdvancedDot setColors(ArrayList<Integer> colors) {
        this.colors = new ArrayList<>();
        if (colors.size() > 3) {
            this.colors.add(colors.get(0));
            this.colors.add(colors.get(1));
            this.colors.add(colors.get(2));
        } else {
            this.colors = colors;
        }

        return this;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
        if (colors.size() == 1) {
            paint.setColor(colors.get(0));
            super.drawBackground(canvas, paint, left, right, top + 10, baseline, bottom, charSequence, start, end, lineNum);
        }
        if (colors.size() == 2) {
            paint.setColor(colors.get(0));
            super.drawBackground(canvas, paint, left - 10, right - 10, top + 10, baseline, bottom, charSequence, start, end, lineNum);
            paint.setColor(colors.get(1));
            super.drawBackground(canvas, paint, left + 10, right + 10, top + 10, baseline, bottom, charSequence, start, end, lineNum);
        }
        if (colors.size() == 3) {
            paint.setColor(colors.get(0));
            super.drawBackground(canvas, paint, left - 20, right - 20, top + 10, baseline, bottom, charSequence, start, end, lineNum);
            paint.setColor(colors.get(1));
            super.drawBackground(canvas, paint, left, right, top + 10, baseline, bottom, charSequence, start, end, lineNum);
            paint.setColor(colors.get(2));
            super.drawBackground(canvas, paint, left + 20, right + 20, top + 10, baseline, bottom, charSequence, start, end, lineNum);
        }
    }
}
