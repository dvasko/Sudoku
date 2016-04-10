package com.vasko.sudoku;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class Box {

    private int value;
    private TextView textView;

    public Box(TextView textView) {
        this.textView = textView;
    }

    public int getValue() {
        return value;
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
    }

    public void setError(boolean foundError) {
        if (foundError) {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.red));
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.getContext(), R.color.black));
        }
    }

}
