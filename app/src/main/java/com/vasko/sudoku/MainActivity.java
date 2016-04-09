package com.vasko.sudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private Sudoku sudoku;
    private Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout sudokuContainer = (FrameLayout) findViewById(R.id.sudoku_container);
        sudoku = new Sudoku.Builder()
                .context(this)
                .layout(sudokuContainer)
                .click(null)
                .build();

        FrameLayout keyboardContainer = (FrameLayout) findViewById(R.id.keyboard_container);
        keyboard = new Keyboard.Builder()
                .context(this)
                .layout(keyboardContainer)
                .sudoku(sudoku)
                .click(null)
                .build();


    }


}
