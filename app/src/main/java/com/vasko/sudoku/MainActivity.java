package com.vasko.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Sudoku sudoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup sudokuContainer = (ViewGroup) findViewById(R.id.sudoku_container);
        sudoku = new Sudoku.Builder()
                .context(this)
                .layout(sudokuContainer)
                .click(null)
                .build();

        ViewGroup keyboardContainer = (ViewGroup) findViewById(R.id.keyboard_container);
        new Keyboard.Builder()
                .context(this)
                .layout(keyboardContainer)
                .sudoku(sudoku)
                .click(null)
                .build();

        start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem item = menu.findItem(R.id.refresh);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.setDuration(400);
                imageView.startAnimation(animation);
                start();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void start() {
        HashMap<Point, Character> initial = new HashMap<>();
        initial.put(new Point(1, 1), '7');
        initial.put(new Point(2, 1), '3');
        initial.put(new Point(4, 1), '5');
        initial.put(new Point(5, 1), '6');

        initial.put(new Point(1, 2), '2');
        initial.put(new Point(3, 2), '1');
        initial.put(new Point(4, 2), '7');
        initial.put(new Point(6, 2), '8');
        initial.put(new Point(9, 2), '6');

        initial.put(new Point(3, 3), '8');
        initial.put(new Point(4, 3), '3');
        initial.put(new Point(6, 3), '9');


        initial.put(new Point(1, 4), '3');
        initial.put(new Point(2, 4), '4');
        initial.put(new Point(3, 4), '6');
        initial.put(new Point(7, 4), '9');
        initial.put(new Point(9, 4), '8');

        initial.put(new Point(2, 5), '2');
        initial.put(new Point(8, 5), '4');

        initial.put(new Point(1, 6), '8');
        initial.put(new Point(3, 6), '7');
        initial.put(new Point(7, 6), '6');
        initial.put(new Point(8, 6), '3');
        initial.put(new Point(9, 6), '1');


        initial.put(new Point(4, 7), '2');
        initial.put(new Point(6, 7), '3');
        initial.put(new Point(7, 7), '4');

        initial.put(new Point(1, 8), '9');
        initial.put(new Point(4, 8), '4');
        initial.put(new Point(6, 8), '1');
        initial.put(new Point(7, 8), '5');
        initial.put(new Point(9, 8), '3');

        initial.put(new Point(5, 9), '8');
        initial.put(new Point(6, 9), '5');
        initial.put(new Point(8, 9), '9');
        initial.put(new Point(9, 9), '2');
        sudoku.drawStart(initial);
    }


}
