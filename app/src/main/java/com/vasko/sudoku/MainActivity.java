package com.vasko.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Point, TextView> map = new HashMap<>();
    private Point activePoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout sudokuContainer = (FrameLayout) findViewById(R.id.sudoku_container);
        Callback callback = new Callback() {
            @Override
            public void onClick(Point point) {
                activePoint = point;
            }
        };
        Sudoku sudoku = new Sudoku.Builder()
                .context(this)
                .layout(sudokuContainer)
                .click(callback)
                .build();

        sudoku.draw(1,1,'4');

    }


}
