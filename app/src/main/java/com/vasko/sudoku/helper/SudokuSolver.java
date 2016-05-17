package com.vasko.sudoku.helper;

import com.vasko.sudoku.model.Point;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SudokuSolver {

    private static final Map<Point, Integer> grid = new HashMap<>();

    public static Map<Point, Integer> getSolvedMap(Map<Point, Integer> mMap) {
        if (mMap.isEmpty()) {
            mMap = randomMap();
        }
        fillGrid(mMap);
        boolean solved = solve(new Point(1, 1));
        if (solved) {
            return grid;
        }
        return null;
    }

    private static Map<Point, Integer> randomMap() {
        Map<Point, Integer> tempMap = new HashMap<>();
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Collections.shuffle(list);
        for (int i = 1; i < 10; ++i) {
            tempMap.put(new Point(i, 1), list.get(i - 1));
        }
        return tempMap;
    }

    // everything is put together here
    // very simple solution
    // must return true, if the sudoku is solved, return false otherwise
    private static boolean solve(Point point) {

        // if the cell is null, we have reached the end
        if (point == null)
            return true;

        // if grid[cur] already has a value, there is nothing to solve here,
        // continue on to next cell
        if (grid.get(point) != 0) {
            // return whatever is being returned by solve(next)
            // i.e the state of sudoku's solution is not being determined by
            // this cell, but by other cells
            return solve(getNextCell(point));
        }

        // this is where each possible value is being assigned to the cell, and
        // checked if a solutions could be arrived at.

        // if grid[cur] doesn't have a value
        // try each possible value
        for (int i = 1; i <= 9; i++) {
            // check if valid, if valid, then update
            boolean valid = isValid(point, i);

            if (!valid) // i not valid for this cell, try other values
                continue;

            // assign here
            grid.put(point, i);

            // continue with next cell
            boolean solved = solve(getNextCell(point));
            // if solved, return, else try other values
            if (solved)
                return true;
            else
                grid.put(point, 0); // reset
            // continue with other possible values
        }

        // if you reach here, then no value from 1 - 9 for this cell can solve
        // return false
        return false;
    }

    private static boolean isValid(Point point, int value) {

        if (grid.get(point) != 0) {
            throw new RuntimeException(
                    "Cannot call for cell which already has a value");
        }

        // if v present row, return false
        for (int c = 1; c < 10; c++) {
            if (grid.get(new Point(point.getX(), c)) == value)
                return false;
        }

        // if v present in col, return false
        for (int r = 1; r < 10; r++) {
            if (grid.get(new Point(r, point.getY())) == value)
                return false;
        }

        // if v present in grid, return false

        // to get the grid we should calculate (x1,y1) (x2,y2)
        int x1 = 3 * ((point.getX() - 1) / 3) + 1;
        int y1 = 3 * ((point.getY() - 1) / 3) + 1;
        int x2 = x1 + 2;
        int y2 = y1 + 2;

        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                if (grid.get(new Point(x, y)) == value)
                    return false;

        // if value not present in row, col and bounding box, return true
        return true;
    }

    // simple function to get the next cell
    // read for yourself, very simple and straight forward
    private static Point getNextCell(Point point) {

        int row = point.getX();
        int col = point.getY();

        // next cell => col++
        col++;

        // if col > 8, then col = 0, row++
        // reached end of row, got to next row
        if (col > 9) {
            // goto next line
            col = 1;
            row++;
        }

        // reached end of matrix, return null
        if (row > 9)
            return null; // reached end

        return new Point(row, col);
    }

    private static void fillGrid(Map<Point, Integer> map) {
        for (int x = 1; x < 10; ++x) {
            for (int y = 1; y < 10; ++y) {
                Point point = new Point(x, y);
                if (map.keySet().contains(point)) {
                    grid.put(point, map.get(point));
                } else {
                    grid.put(point, 0);
                }
            }
        }
    }

}

