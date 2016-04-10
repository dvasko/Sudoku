package com.vasko.sudoku;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class ToastBelowView {

    public static void show(View view, String text){
        final int[] screenPos = new int[2];
        final Rect displayFrame = new Rect();
        view.getLocationOnScreen(screenPos);
        view.getWindowVisibleDisplayFrame(displayFrame);
        final Context context = view.getContext();
        final int width = view.getWidth();
        final int height = view.getHeight();
        final int midY = screenPos[1] + height / 2;
        final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        Toast cheatSheet = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        if (midY < displayFrame.height()) {
            cheatSheet.setGravity(Gravity.TOP | Gravity.END,
                    screenWidth - screenPos[0] - width / 2, screenPos[1] + height / 2);
        } else {
            cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
        }
        cheatSheet.show();
    }
}