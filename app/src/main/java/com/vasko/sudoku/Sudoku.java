package com.vasko.sudoku;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Sudoku {
    private final Map<Point, Integer> mInitialMap;
    private final Map<Point, Integer> mSolvedMap;
    private final Map<Point, Box> mMap;
    private final Context mContext;
    private Point mActivePoint;

    private Sudoku(Map<Point, Integer> initialMap, ViewGroup container) {
        mMap = new HashMap<>();
        mInitialMap = initialMap;
        mSolvedMap = SudokuSolver.getSolvedMap(mInitialMap);
        mContext = container.getContext();

        LayoutInflater.from(mContext).inflate(R.layout.sudoku_layout, container, true);
        ViewGroup sudokuLayout = (ViewGroup) container.findViewById(R.id.sudoku_layout);
        ViewCompat.setElevation(sudokuLayout, PointHelper.convertDpToPixel(8));
        for (int y = 0; y < 3; ++y) {
            initRow(sudokuLayout, y);
        }
        drawInitialSudoku();
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
                cleanSelectorOnAllBoxes();
                mActivePoint = point;
                mMap.get(point).setSelected(true);
            }
        });
        mMap.put(point, new Box(text));
    }

    public void drawInitialSudoku() {
        clearSudokuField();
        for (Point point : mInitialMap.keySet()) {
            drawNumberOnPoint(point, mInitialMap.get(point));
        }
        cleanSelectorOnAllBoxes();
    }

    public void drawSolvedSudoku() {
        clearSudokuField();
        for (Point point : mSolvedMap.keySet()) {
            drawNumberOnPoint(point, mSolvedMap.get(point));
        }
        cleanSelectorOnAllBoxes();
    }

    public void drawNumberOnActivePoint(int number) {
        drawNumberOnPoint(mActivePoint, number);
        cleanSelectorOnAllBoxes();
    }

    public void drawHintOnActivePoint() {
        if (mSolvedMap.get(mActivePoint) != null) {
            drawNumberOnPoint(mActivePoint, mSolvedMap.get(mActivePoint));
            cleanSelectorOnAllBoxes();
        } else {
            Toast.makeText(mContext, R.string.select_field, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearSudokuField() {
        for (Point point : mMap.keySet()) {
            drawNumberOnPoint(point, 0);
        }
    }

    private void cleanSelectorOnAllBoxes() {
        for (Box box : mMap.values()) {
            box.setSelected(false);
        }
        mActivePoint = null;
    }

    private void drawNumberOnPoint(Point point, int number) {
        if (!PointHelper.getAllowedIntegers().contains(number)) {
            throw new IllegalArgumentException("Allowed integers: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9");
        }
        if (point != null) {
            Box box = mMap.get(point);
            box.setValue(number);
            boolean foundError = PointHelper.checkNumber(mMap, point, number);
            box.setError(foundError);
        } else {
            Toast.makeText(mContext, R.string.select_field, Toast.LENGTH_SHORT).show();
        }
    }

    public static class Builder {
        private ViewGroup container;
        private Map<Point, Integer> initialMap;

        public Builder layout(ViewGroup container) {
            this.container = container;
            return this;
        }

        public Builder initial(Map<Point, Integer> initialMap) {
            this.initialMap = initialMap;
            return this;
        }

        public Sudoku build() {
            if (container == null) {
                throw new IllegalArgumentException("container must be != null");
            }
            if (initialMap == null) {
                initialMap = new HashMap<>();
            }
            return new Sudoku(initialMap, container);
        }
    }

}
