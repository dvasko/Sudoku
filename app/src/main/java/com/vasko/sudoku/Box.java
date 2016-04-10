package com.vasko.sudoku;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class Box {

    private final TextView textView;
    private int value;
    private boolean foundError;

    public Box(TextView textView) {
        this.textView = textView;
    }

    public int getValue() {
        return value;
    }

    public boolean hasError() {
        return foundError;
    }

    public void setValue(int number) {
        value = number;
        if (number > 0) {
            textView.setText(String.valueOf(number));
        } else {
            textView.setText("");
        }
    }

    public void setSelected(boolean selected) {
        textView.setSelected(selected);
        if (!foundError) {
            if (selected) {
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.white));
            } else {
                textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.black));
            }
        }
    }

    public void setError(boolean foundError) {
        this.foundError = foundError;
        if (foundError) {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.red));
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.black));
        }
    }

    public void disable() {
        textView.setEnabled(false);
    }
}
