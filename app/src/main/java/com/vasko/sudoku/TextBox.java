package com.vasko.sudoku;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextBox extends TextView {

    public TextBox(Context context) {
        super(context);
    }

    public TextBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setError(boolean foundError) {
        if (foundError) {
            setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else {
            setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
    }
}
