package com.vasko.sudoku.model;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.vasko.sudoku.R;

import java.io.Serializable;

public class Box implements Serializable {

    private transient final TextView textView;
    private int value;
    private boolean foundError;

    Box(TextView textView) {
        this.textView = textView;
    }

    public int getValue() {
        return value;
    }

    boolean hasError() {
        return foundError;
    }

    void setValue(int number) {
        value = number;
        if (number > 0) {
            textView.setText(String.valueOf(number));
        } else {
            textView.setText("");
        }
    }

    void setSelected(boolean selected) {
        textView.setSelected(selected);
        if (!foundError) {
            if (selected) {
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
            } else {
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.black));
            }
        }
    }

    void setError(boolean foundError) {
        this.foundError = foundError;
        if (foundError) {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.red));
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.black));
        }
    }

    void disable() {
        textView.setEnabled(false);
    }
}
