package com.vasko.sudoku;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Sudoku {
    private Map<Point, TextView> mMap;
    private Callback mCallback;

    private Sudoku(LinearLayout mSudokuLayout, Callback callback) {
        this.mMap = new HashMap<>();
        this.mCallback = callback;

        for (int y = 0; y < 3; ++y) {
            initRow(mSudokuLayout, y);
        }
    }

    public Map<Point, TextView> getMap() {
        return mMap;
    }

    private void initRow(LinearLayout sudoku, int y) {
        LinearLayout row = null;
        switch (y) {
            case 0:
                row = (LinearLayout) sudoku.findViewById(R.id.first_row);
                break;
            case 1:
                row = (LinearLayout) sudoku.findViewById(R.id.second_row);
                break;
            case 2:
                row = (LinearLayout) sudoku.findViewById(R.id.third_row);
                break;
        }
        for (int x = 0; x < 3; ++x) {
            initBigBox(row, x, y);
        }
    }

    private void initBigBox(LinearLayout row, int x, int y) {
        LinearLayout box = null;
        switch (x) {
            case 0:
                box = (LinearLayout) row.findViewById(R.id.first_column);
                break;
            case 1:
                box = (LinearLayout) row.findViewById(R.id.second_column);
                break;
            case 2:
                box = (LinearLayout) row.findViewById(R.id.third_column);
                break;
        }
        for (int i = 0; i < 9; ++i) {
            initBox(box, x, y, i);
        }
    }

    private void initBox(LinearLayout box, int x, int y, int number) {
        TextView text = null;
        switch (number) {
            case 0:
                text = (TextView) box.findViewById(R.id.one);
                break;
            case 1:
                text = (TextView) box.findViewById(R.id.two);
                break;
            case 2:
                text = (TextView) box.findViewById(R.id.three);
                break;
            case 3:
                text = (TextView) box.findViewById(R.id.four);
                break;
            case 4:
                text = (TextView) box.findViewById(R.id.five);
                break;
            case 5:
                text = (TextView) box.findViewById(R.id.six);
                break;
            case 6:
                text = (TextView) box.findViewById(R.id.seven);
                break;
            case 7:
                text = (TextView) box.findViewById(R.id.eight);
                break;
            case 8:
                text = (TextView) box.findViewById(R.id.nine);
                break;
        }
        setupBox(text, x, y, number);
    }

    private void setupBox(TextView text, int x, int y, int number) {
        int realX = x * 3 + 1 + (number % 3);
        int realY = y * 3 + 1 + (number / 3);
        final Point point = new Point(realX, realY);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClick(point);
            }
        });
        mMap.put(point, text);
    }

    public static class Builder {
        private LinearLayout sudokuLayout;
        private Callback callback;

        public Builder() {
        }

        public Builder layout(LinearLayout sudokuLayout) {
            this.sudokuLayout = sudokuLayout;
            return this;
        }

        public Builder click(Callback callback) {
            this.callback = callback;
            return this;
        }

        public Sudoku build() {
            return new Sudoku(sudokuLayout, callback);
        }
    }

    public interface Callback {
        void onClick(Point point);
    }

}
