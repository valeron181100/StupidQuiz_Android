package com.weather_app.firstex.models;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.function.Consumer;

import androidx.annotation.RequiresApi;

public class IntegerProperty {
    private int value;
    private ArrayList<IntegerPropertyCaller> listeners;

    public IntegerProperty(int value){
        this.value = value;
        listeners = new ArrayList<>();
    }

    public int getValue() {
        return value;
    }


    public void setValue(final int value) {
        for(IntegerPropertyCaller p : listeners){
            p.call(IntegerProperty.this.value, value);
        }
        this.value = value;
        Log.d("QUIZ", "Value Updated");
    }

    public void addOnChangeListener(IntegerPropertyCaller caller){
        listeners.add(caller);
    }


    public void inc(){
        for(IntegerPropertyCaller p : listeners){
            p.call(IntegerProperty.this.value, value + 1);
        }
        value++;
    }

    public void dec(){
        for(IntegerPropertyCaller p : listeners){
            p.call(IntegerProperty.this.value, value - 1);
        }
        value--;
    }
}


