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

import com.vasko.sudoku.helper.SudokuGenerator;
import com.vasko.sudoku.helper.ToastBelowView;
import com.vasko.sudoku.model.Keyboard;
import com.vasko.sudoku.model.Sudoku;

import static com.vasko.sudoku.helper.SudokuGenerator.Difficulty.ADVANCED;

public class MainActivity extends AppCompatActivity {

    private SudokuInterface sudoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup sudokuContainer = (ViewGroup) findViewById(R.id.sudoku_container);
        sudoku = new Sudoku.Builder()
                .savedInstance(savedInstanceState)
                .layout(sudokuContainer)
                .initial(SudokuGenerator.generate(ADVANCED))
                .build();

        ViewGroup keyboardContainer = (ViewGroup) findViewById(R.id.keyboard_container);
        new Keyboard.Builder()
                .layout(keyboardContainer)
                .sudoku(sudoku)
                .build();
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
                sudoku.drawInitialSudoku();
            }
        });
        item.getActionView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastBelowView.show(v, getString(R.string.restart));
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.solve:
                sudoku.drawSolvedSudoku();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        sudoku.onSavedInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

}
