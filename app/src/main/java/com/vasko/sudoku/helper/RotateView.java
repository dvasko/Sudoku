package com.vasko.sudoku.helper;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class RotateView {

    public static void with(View view) {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(400);
        view.startAnimation(animation);
    }

}
