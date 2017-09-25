package com.signme.signme.adapter;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by dsm2016 on 2017-09-17.
 */

public class ContentlistItem {
   // public int letterNumber;
    public String title;
  //  public int writerUid;
   // public   String contents;
    public String openDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }
}
