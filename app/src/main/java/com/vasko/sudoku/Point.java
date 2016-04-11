package com.vasko.sudoku;

import java.io.Serializable;

public class Point implements Serializable {

    private int x;
    private int y;

    public Point(int x, int y) {
        if (x < 1) {
            throw new IllegalArgumentException("Point(" + x + ", " + y + "): x must be > 0");
        } else if (y < 1) {
            throw new IllegalArgumentException("Point(" + x + ", " + y + "): y must be > 0");
        } else if (x > 9) {
            throw new IllegalArgumentException("Point(" + x + ", " + y + "): x must be < 10");
        } else if (y > 9) {
            throw new IllegalArgumentException("Point(" + x + ", " + y + "): y must be < 10");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
