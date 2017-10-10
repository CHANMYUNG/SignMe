package com.signme.signme.support;

import android.graphics.drawable.Drawable;

/**
 * Created by ghdth on 2017-10-08.
 */

public class MypageItem {
    private Drawable iconDrawable ;
    private String titleStr ;


    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }


    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }



}
