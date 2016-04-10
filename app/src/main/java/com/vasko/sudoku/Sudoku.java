package com.vasko.sudoku;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

public class Sudoku {
    private final Map<Point, Integer> mInitialMap;
    private final Map<Point, TextBox> mMap;
    private Point mActivePoint;

    private Sudoku(Context context, Map<Point, Integer> initialMap, ViewGroup container) {
        mInitialMap = initialMap;
        mMap = new HashMap<>();

        LayoutInflater.from(context).inflate(R.layout.sudoku_layout, container, true);
        ViewGroup sudokuLayout = (ViewGroup) container.findViewById(R.id.sudoku_layout);
        ViewCompat.setElevation(sudokuLayout, PointHelper.convertDpToPixel(8));
        for (int y = 0; y < 3; ++y) {
            initRow(sudokuLayout, y);
        }
        resetToStart();
    }

    private void initRow(ViewGroup sudoku, int y) {
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
        TextBox text = null;
        switch (number) {
            case 0:
                text = (TextBox) box.findViewById(R.id.one);
                break;
            case 1:
                text = (TextBox) box.findViewById(R.id.two);
                break;
            case 2:
                text = (TextBox) box.findViewById(R.id.three);
                break;
            case 3:
                text = (TextBox) box.findViewById(R.id.four);
                break;
            case 4:
                text = (TextBox) box.findViewById(R.id.five);
                break;
            case 5:
                text = (TextBox) box.findViewById(R.id.six);
                break;
            case 6:
                text = (TextBox) box.findViewById(R.id.seven);
                break;
            case 7:
                text = (TextBox) box.findViewById(R.id.eight);
                break;
            case 8:
                text = (TextBox) box.findViewById(R.id.nine);
                break;
        }
        setupBox(text, x, y, number);
    }

    private void setupBox(TextBox text, int x, int y, int number) {
        int realX = x * 3 + 1 + (number % 3);
        int realY = y * 3 + 1 + (number / 3);
        final Point point = new Point(realX, realY);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanSelectorOnAllBoxes();
                mActivePoint = point;
                v.setSelected(true);
            }
        });
        mMap.put(point, text);
    }

    private void cleanSelectorOnAllBoxes() {
        for (TextBox box : mMap.values()) {
            box.setSelected(false);
        }
        mActivePoint = null;
    }

    public void drawOnActivePoint(int number) {
        drawPoint(mActivePoint, number);
    }

    public void resetToStart() {
        clearAllData();
        for (Point point : mInitialMap.keySet()) {
            int number = mInitialMap.get(point);
            drawPoint(point, number);
        }
    }

    private void clearAllData() {
        for (Point point : mMap.keySet()) {
            drawPoint(point, 0);
        }
    }

    public void solve() {
        resetToStart();
        SudokuSolver.startSolving(mMap);
    }

    private void drawPoint(Point point, int number) {
        if (!PointHelper.getAllowedIntegers().contains(number)) {
            throw new IllegalArgumentException("Allowed integers: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9'");
        }
        if (point != null) {
            TextBox box = mMap.get(point);
            if (number > 0) {
                box.setText(String.valueOf(number));
                boolean foundError = PointHelper.checkNumber(mMap, point, number);
                box.setError(foundError);
            } else {
                box.setText("");
                box.setError(false);
            }
        }
        cleanSelectorOnAllBoxes();
    }

    public static class Builder {
        private Context context;
        private ViewGroup container;
        private Map<Point, Integer> initialMap;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder layout(ViewGroup container) {
            this.container = container;
            return this;
        }

        public Builder initial(Map<Point, Integer> initialMap) {
            this.initialMap = initialMap;
            return this;
        }

        public Sudoku build() {
            if (context == null) {
                throw new IllegalArgumentException("context must be != null");
            } else if (container == null) {
                throw new IllegalArgumentException("container must be != null");
            }
            if (initialMap == null) {
                initialMap = new HashMap<>();
            }
            return new Sudoku(context, initialMap, container);
        }
    }

}
