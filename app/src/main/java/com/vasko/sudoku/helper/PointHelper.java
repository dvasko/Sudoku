package com.vasko.sudoku.helper;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.vasko.sudoku.model.Box;
import com.vasko.sudoku.model.Point;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PointHelper {

    private static Set<Integer> validIntegers;

    public static Set<Integer> getAllowedIntegers() {
        if (validIntegers == null) {
            Set<Integer> set = new HashSet<>();
            set.add(0);
            set.add(1);
            set.add(2);
            set.add(3);
            set.add(4);
            set.add(5);
            set.add(6);
            set.add(7);
            set.add(8);
            set.add(9);
            validIntegers = set;
        }
        return validIntegers;
    }

    public static boolean checkNumber(Map<Point, Box> map, Point point, int number) {
        boolean foundError = checkRow(map, point, number);
        if (!foundError) {
            foundError = checkColumn(map, point, number);
        }
        if (!foundError) {
            foundError = checkBox(map, point, number);
        }
        return foundError;
    }

    private static boolean checkRow(Map<Point, Box> map, Point point, int number) {
        for (int x = 1; x < 10; ++x) {
            Point tempPoint = new Point(x, point.getY());
            if (point.equals(tempPoint)) {
                continue;
            }
            if (checkPoint(map, tempPoint, number)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumn(Map<Point, Box> map, Point point, int number) {
        for (int y = 1; y < 10; ++y) {
            Point tempPoint = new Point(point.getX(), y);
            if (point.equals(tempPoint)) {
                continue;
            }
            if (checkPoint(map, tempPoint, number)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkBox(Map<Point, Box> map, Point point, int number) {
        for (int i = 0, x = (((point.getX() - 1) / 3) * 3) + 1; i < 3; ++i, ++x) {
            for (int j = 0, y = (((point.getY() - 1) / 3) * 3) + 1; j < 3; ++j, ++y) {
                Point tempPoint = new Point(x, y);
                if (point.equals(tempPoint)) {
                    continue;
                }
                if (checkPoint(map, tempPoint, number)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkPoint(Map<Point, Box> map, Point point, int number) {
        Box box = map.get(point);
        return box.getValue() > 0 && box.getValue() == number;
    }

    @SuppressWarnings("SameParameterValue")
    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
