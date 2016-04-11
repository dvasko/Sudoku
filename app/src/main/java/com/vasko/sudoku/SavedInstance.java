package com.vasko.sudoku;

import java.io.Serializable;
import java.util.Map;

public class SavedInstance implements Serializable {

    private Map<Point, Box> data;

    public SavedInstance(Map<Point, Box> data) {
        this.data = data;
    }

    public Map<Point, Box> getData() {
        return data;
    }
}
