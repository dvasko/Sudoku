package com.vasko.sudoku.helper;

import com.vasko.sudoku.model.Point;

import java.util.HashMap;
import java.util.Map;

public class SudokuGenerator {

    public enum Difficulty {
        BEGINNER, ADVANCED, EXPERT
    }

    public static Map<Point, Integer> generate(Difficulty difficulty) {
        //TODO someone will implement this method
        HashMap<Point, Integer> initial = new HashMap<>();
        initial.put(new Point(1, 1), 7);
        initial.put(new Point(2, 1), 3);
        initial.put(new Point(4, 1), 5);
        initial.put(new Point(5, 1), 6);

        initial.put(new Point(1, 2), 2);
        initial.put(new Point(3, 2), 1);
        initial.put(new Point(4, 2), 7);
        initial.put(new Point(6, 2), 8);
        initial.put(new Point(9, 2), 6);

        initial.put(new Point(3, 3), 8);
        initial.put(new Point(4, 3), 3);
        initial.put(new Point(6, 3), 9);


        initial.put(new Point(1, 4), 3);
        initial.put(new Point(2, 4), 4);
        initial.put(new Point(3, 4), 6);
        initial.put(new Point(7, 4), 9);
        initial.put(new Point(9, 4), 8);

        initial.put(new Point(2, 5), 2);
        initial.put(new Point(8, 5), 4);

        initial.put(new Point(1, 6), 8);
        initial.put(new Point(3, 6), 7);
        initial.put(new Point(7, 6), 6);
        initial.put(new Point(8, 6), 3);
        initial.put(new Point(9, 6), 1);


        initial.put(new Point(4, 7), 2);
        initial.put(new Point(6, 7), 3);
        initial.put(new Point(7, 7), 4);

        initial.put(new Point(1, 8), 9);
        initial.put(new Point(4, 8), 4);
        initial.put(new Point(6, 8), 1);
        initial.put(new Point(7, 8), 5);
        initial.put(new Point(9, 8), 3);

        initial.put(new Point(5, 9), 8);
        initial.put(new Point(6, 9), 5);
        initial.put(new Point(8, 9), 9);
        initial.put(new Point(9, 9), 2);
        return initial;
    }

}
