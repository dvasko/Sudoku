package com.vasko.sudoku.helper;

import com.vasko.sudoku.model.Box;
import com.vasko.sudoku.model.Point;

import java.io.Serializable;
import java.util.Map;

public class SavedInstance implements Serializable {

    private final Map<Point, Box> data;

    public SavedInstance(Map<Point, Box> data) {
        this.data = data;
    }

    public Map<Point, Box> getData() {
        return data;
    }
}
