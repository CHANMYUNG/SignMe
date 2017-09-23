package com.nooheat.model;

/**
 * Created by NooHeat on 23/09/2017.
 */
public class Letter implements Comparable<Letter> {
    String openDate;


    @Override
    public int compareTo(Letter o) {
        return openDate.compareTo(o.openDate);
    }
}
