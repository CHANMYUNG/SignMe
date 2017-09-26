package com.signme.signme.adapter;

import com.signme.signme.LetterTypes;

/**
 * Created by dsm2016 on 2017-09-17.
 */

public class LetterListItem {
    private LetterTypes type;
    private int letterNumber;
    private String title;
    private String openDate;

    private String closeDate;

    public LetterListItem() {
    }

    public LetterListItem(LetterTypes type, int letterNumber, String title, String openDate) {
        this.type = type;
        this.letterNumber = letterNumber;
        this.title = title;
        this.openDate = openDate;
    }

    public LetterListItem(LetterTypes type, int letterNumber, String title, String openDate, String closeDate) {
        this.type = type;
        this.letterNumber = letterNumber;
        this.title = title;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }


    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public LetterTypes getType() {
        return type;
    }

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

    public void setType(LetterTypes type) {
        this.type = type;
    }

    public void setLetterNumber(int letterNumber) {
        this.letterNumber = letterNumber;
    }

    public int getLetterNumber() {
        return letterNumber;
    }
}
