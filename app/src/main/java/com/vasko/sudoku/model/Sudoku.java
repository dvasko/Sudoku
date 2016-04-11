package com.vasko.sudoku.model;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vasko.sudoku.R;
import com.vasko.sudoku.helper.PointHelper;
import com.vasko.sudoku.helper.SavedInstance;
import com.vasko.sudoku.helper.SudokuSolver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Sudoku implements Serializable {

    private static final String SAVED_INSTANCE = "SAVED_INSTANCE";

    private final Map<Point, Integer> mInitialMap;
    private final Map<Point, Integer> mSolvedMap;
    private final Map<Point, Box> mMap;
    private transient final Context mContext;
    private Point mActivePoint;

    private Sudoku(Map<Point, Integer> initialMap, ViewGroup container, Bundle savedInstanceState) {
        mMap = new HashMap<>();
        mInitialMap = initialMap;
        mSolvedMap = SudokuSolver.getSolvedMap(mInitialMap);
        mContext = container.getContext();

        container.removeAllViews();
        LayoutInflater.from(mContext).inflate(R.layout.sudoku_layout, container, true);
        ViewGroup sudokuLayout = (ViewGroup) container.findViewById(R.id.sudoku_layout);
        ViewCompat.setElevation(sudokuLayout, PointHelper.convertDpToPixel(8));
        for (int y = 0; y < 3; ++y) {
            initRow(sudokuLayout, y);
        }
        drawInitialSudoku();

        if (savedInstanceState != null) {
            SavedInstance savedInstance = (SavedInstance) savedInstanceState.getSerializable(SAVED_INSTANCE);
            drawRestoredSudoku(savedInstance.getData());
        }
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
            mMap.get(point).disable();
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

    private void drawRestoredSudoku(Map<Point, Box> savedMap) {
        Map<Point, Box> errorMap = new HashMap<>();
        for (Point point : savedMap.keySet()) {
            Box savedBox = savedMap.get(point);
            if (savedBox.hasError()) {
                errorMap.put(point, savedBox);
                continue;
            }
            if (mMap.get(point).getValue() == 0) {
                drawNumberOnPoint(point, savedBox.getValue());
            }
        }
        for (Point point : errorMap.keySet()) {
            if (mMap.get(point).getValue() == 0) {
                drawNumberOnPoint(point, errorMap.get(point).getValue());
            }
        }
    }

    private void clearSudokuField() {
        for (Point point : mMap.keySet()) {
            drawNumberOnPoint(point, 0);
        }
    }

    public void drawHintOnActivePoint() {
        if (mActivePoint != null) {
            drawNumberOnActivePoint(mSolvedMap.get(mActivePoint));
        } else {
            Toast.makeText(mContext, R.string.select_field, Toast.LENGTH_SHORT).show();
        }
    }

    public void drawNumberOnActivePoint(int number) {
        drawNumberOnPoint(mActivePoint, number);
        cleanSelectorOnAllBoxes();
        checkWin();
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

    private void cleanSelectorOnAllBoxes() {
        for (Box box : mMap.values()) {
            box.setSelected(false);
        }
        mActivePoint = null;
    }

    private void checkWin() {
        for (Point point : mMap.keySet()) {
            Box box = mMap.get(point);
            if (box.getValue() <= 0 || box.hasError()) {
                return;
            }
        }
        Toast.makeText(mContext, R.string.won, Toast.LENGTH_SHORT).show();
    }

    public void onSavedInstanceState(Bundle outState) {
        outState.putSerializable(SAVED_INSTANCE, new SavedInstance(mMap));
    }

    public static class Builder {
        private ViewGroup container;
        private Map<Point, Integer> initialMap;
        private Bundle savedInstanceState;
        private boolean haveSavedState;

        public Builder savedInstance(Bundle savedInstanceState) {
            this.savedInstanceState = savedInstanceState;
            haveSavedState = true;
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
            if (container == null) {
                throw new IllegalArgumentException("container must be != null");
            }
            if (!haveSavedState) {
                throw new IllegalArgumentException("must set savedInstanceState");
            }
            if (initialMap == null) {
                initialMap = new HashMap<>();
            }
            return new Sudoku(initialMap, container, savedInstanceState);
        }


    }

}
