package com.signme.signme;

/**
 * Created by dsm2016 on 2017-07-16.
 */

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable;
    private String titleStr;
    private Drawable imageDrawable;

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setImage(Drawable Image) {
        imageDrawable = Image;
    }

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public Drawable getImage() {
        return this.imageDrawable;
    }
}
