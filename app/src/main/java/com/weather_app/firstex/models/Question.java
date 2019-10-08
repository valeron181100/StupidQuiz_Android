package com.weather_app.firstex.models;

public class Question {
    private int mTextResId;
    private boolean isAnswerFalse;

    public Question(int textResId, boolean isAnswerFalse){
        this.mTextResId = textResId;
        this.isAnswerFalse = isAnswerFalse;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerFalse() {
        return isAnswerFalse;
    }


}
