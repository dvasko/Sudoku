package com.vasko.sudoku;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PointHelper {

    private static Set<Character> validChars;

    public static Set<Character> getAllowedChars() {
        if (validChars == null) {
            Set<Character> set = new HashSet<>();
            set.add(' ');
            set.add('1');
            set.add('2');
            set.add('3');
            set.add('4');
            set.add('5');
            set.add('6');
            set.add('7');
            set.add('8');
            set.add('9');
            validChars = set;
        }
        return validChars;
    }

    public static boolean checkNumber(Map<Point, TextBox> map, Point point, char number) {
        boolean foundError = checkRow(map, point, number);
        if (!foundError) {
            foundError = checkColumn(map, point, number);
        }
        if (!foundError) {
            foundError = checkBox(map, point, number);
        }
        return foundError;
    }

    private static boolean checkRow(Map<Point, TextBox> map, Point point, char number) {
        for (int x = 1; x < 10; ++x) {
            Point tempPoint = new Point(x, point.getY());
            if (point.equals(tempPoint)) {
                continue;
            }
            TextBox temp = map.get(tempPoint);
            if (temp.getText().length() > 0 && temp.getText().charAt(0) == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumn(Map<Point, TextBox> map, Point point, char number) {
        for (int y = 1; y < 10; ++y) {
            Point tempPoint = new Point(point.getX(), y);
            if (point.equals(tempPoint)) {
                continue;
            }
            TextBox temp = map.get(tempPoint);
            if (temp.getText().length() > 0 && temp.getText().charAt(0) == number) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkBox(Map<Point, TextBox> map, Point point, char number) {
        for (int i = 0, x = (((point.getX() - 1) / 3) * 3) + 1; i < 3; ++i, ++x) {
            for (int j = 0, y = (((point.getY() - 1) / 3) * 3) + 1; j < 3; ++j, ++y) {
                Point tempPoint = new Point(x, y);
                if (point.equals(tempPoint)) {
                    continue;
                }
                TextBox temp = map.get(tempPoint);
                if (temp.getText().length() > 0 && temp.getText().charAt(0) == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
