package com.signme.signme.adapter;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by dsm2016 on 2017-09-17.
 */

public class ContentlistItem {
    public int letterNumber;
    public String title;
    public int writerUid;
    public   String contents;
    public String openDate;

    public static final Comparator<ContentlistItem> ALRHA_COMPARATOR=new Comparator<ContentlistItem>() {
        private final Collator sCollator=Collator.getInstance();
        @Override
        public int compare(ContentlistItem contentItem, ContentlistItem t1) {
            return sCollator.compare(contentItem.title,t1.title);
        }
    };


}
