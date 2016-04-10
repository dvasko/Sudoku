package com.vasko.sudoku;

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

    public static void startSolving(Map<Point, TextBox> mMap) {
        transformMapToGrid(mMap);
        boolean solved = solve(new Cell(0, 0));
        if (solved) {
            transformGridToMap(mMap);
        }
    }

    // everything is put together here
    // very simple solution
    // must return true, if the sudoku is solved, return false otherwise
    private static boolean solve(Cell cell) {

        // if the cell is null, we have reached the end
        if (cell == null)
            return true;

        // if grid[cur] already has a value, there is nothing to solve here,
        // continue on to next cell
        if (grid[cell.row][cell.col] != 0) {
            // return whatever is being returned by solve(next)
            // i.e the state of sudoku's solution is not being determined by
            // this cell, but by other cells
            return solve(getNextCell(cell));
        }

        // this is where each possible value is being assigned to the cell, and
        // checked if a solutions could be arrived at.

        // if grid[cur] doesn't have a value
        // try each possible value
        for (int i = 1; i <= 9; i++) {
            // check if valid, if valid, then update
            boolean valid = isValid(cell, i);

            if (!valid) // i not valid for this cell, try other values
                continue;

            // assign here
            grid[cell.row][cell.col] = i;

            // continue with next cell
            boolean solved = solve(getNextCell(cell));
            // if solved, return, else try other values
            if (solved)
                return true;
            else
                grid[cell.row][cell.col] = 0; // reset
            // continue with other possible values
        }

        // if you reach here, then no value from 1 - 9 for this cell can solve
        // return false
        return false;
    }

    private static boolean isValid(Cell cell, int value) {

        if (grid[cell.row][cell.col] != 0) {
            throw new RuntimeException(
                    "Cannot call for cell which already has a value");
        }

        // if v present row, return false
        for (int c = 0; c < 9; c++) {
            if (grid[cell.row][c] == value)
                return false;
        }

        // if v present in col, return false
        for (int r = 0; r < 9; r++) {
            if (grid[r][cell.col] == value)
                return false;
        }

        // if v present in grid, return false

        // to get the grid we should calculate (x1,y1) (x2,y2)
        int x1 = 3 * (cell.row / 3);
        int y1 = 3 * (cell.col / 3);
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
    private static Cell getNextCell(Cell cell) {

        int row = cell.row;
        int col = cell.col;

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

        return new Cell(row, col);
    }

    private static void transformGridToMap(Map<Point, TextBox> map) {
        for (Point point : map.keySet()) {
            TextBox text = map.get(point);
            String number = String.valueOf(grid[point.getX() - 1][point.getY() - 1]);
            text.setText(number);
        }
    }

    private static void transformMapToGrid(Map<Point, TextBox> map) {
        for (Point point : map.keySet()) {
            String text = map.get(point).getText().toString();
            if(text.isEmpty()) {
                continue;
            }
            grid[point.getX() - 1][point.getY() - 1] = Integer.parseInt(text);
        }
    }

    static class Cell {

        final int row;
        final int col;

        public Cell(int row, int col) {
            super();
            this.row = row;
            this.col = col;
        }

    }
}

