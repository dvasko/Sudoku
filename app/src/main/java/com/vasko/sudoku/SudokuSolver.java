package com.vasko.sudoku;

import java.util.HashMap;
import java.util.Map;

public class SudokuSolver {

    private static final int[][] grid = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public static Map<Point, Integer> getSolvedMap(Map<Point, Integer> mMap) {
        transformMapToGrid(mMap);
        boolean solved = solve(new Point(1, 1));
        if (solved) {
            return transformGridToMap();
        }
        return null;
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
        if (grid[point.getX() - 1][point.getY() - 1] != 0) {
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
            grid[point.getX() - 1][point.getY() - 1] = i;

            // continue with next cell
            boolean solved = solve(getNextCell(point));
            // if solved, return, else try other values
            if (solved)
                return true;
            else
                grid[point.getX() - 1][point.getY() - 1] = 0; // reset
            // continue with other possible values
        }

        // if you reach here, then no value from 1 - 9 for this cell can solve
        // return false
        return false;
    }

    private static boolean isValid(Point point, int value) {

        if (grid[point.getX() - 1][point.getY() - 1] != 0) {
            throw new RuntimeException(
                    "Cannot call for cell which already has a value");
        }

        // if v present row, return false
        for (int c = 0; c < 9; c++) {
            if (grid[point.getX() - 1][c] == value)
                return false;
        }

        // if v present in col, return false
        for (int r = 0; r < 9; r++) {
            if (grid[r][point.getY() - 1] == value)
                return false;
        }

        // if v present in grid, return false

        // to get the grid we should calculate (x1,y1) (x2,y2)
        int x1 = 3 * ((point.getX() - 1) / 3);
        int y1 = 3 * ((point.getY() - 1) / 3);
        int x2 = x1 + 2;
        int y2 = y1 + 2;

        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                if (grid[x][y] == value)
                    return false;

        // if value not present in row, col and bounding box, return true
        return true;
    }

    // simple function to get the next cell
    // read for yourself, very simple and straight forward
    private static Point getNextCell(Point point) {

        int row = point.getX() - 1;
        int col = point.getY() - 1;

        // next cell => col++
        col++;

        // if col > 8, then col = 0, row++
        // reached end of row, got to next row
        if (col > 8) {
            // goto next line
            col = 0;
            row++;
        }

        // reached end of matrix, return null
        if (row > 8)
            return null; // reached end

        return new Point(row + 1, col + 1);
    }

    private static Map<Point, Integer> transformGridToMap() {
        Map<Point, Integer> map = new HashMap<>();
        for (int x = 0; x < 9; ++x) {
            for (int y = 0; y < 9; ++y) {
                map.put(new Point(x + 1, y + 1), grid[x][y]);
            }
        }
        return map;
    }

    private static void transformMapToGrid(Map<Point, Integer> map) {
        for (Point point : map.keySet()) {
            grid[point.getX() - 1][point.getY() - 1] = map.get(point);
        }
    }

}

