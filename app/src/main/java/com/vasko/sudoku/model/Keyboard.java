package com.vasko.sudoku.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vasko.sudoku.R;

public class Keyboard {

    private final Sudoku mSudoku;

    private Keyboard(final Sudoku sudoku, ViewGroup container) {
        mSudoku = sudoku;

        container.removeAllViews();
        LayoutInflater.from(container.getContext()).inflate(R.layout.keyboard_layout, container, true);
        for (int i = -1; i < 10; ++i) {
            setupKey(container, i);
        }
    }

    private void setupKey(ViewGroup container, final int number) {
        Button button = null;
        switch (number) {
            case -1:
                button = (Button) container.findViewById(R.id.hint);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSudoku.drawHintOnActivePoint();
                    }
                });
                return;
            case 0:
                button = (Button) container.findViewById(R.id.empty);
                break;
            case 1:
                button = (Button) container.findViewById(R.id.one);
                break;
            case 2:
                button = (Button) container.findViewById(R.id.two);
                break;
            case 3:
                button = (Button) container.findViewById(R.id.three);
                break;
            case 4:
                button = (Button) container.findViewById(R.id.four);
                break;
            case 5:
                button = (Button) container.findViewById(R.id.five);
                break;
            case 6:
                button = (Button) container.findViewById(R.id.six);
                break;
            case 7:
                button = (Button) container.findViewById(R.id.seven);
                break;
            case 8:
                button = (Button) container.findViewById(R.id.eight);
                break;
            case 9:
                button = (Button) container.findViewById(R.id.nine);
                break;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSudoku.drawNumberOnActivePoint(number);
            }
        });
    }

    public static class Builder {
        private ViewGroup container;
        private Sudoku sudoku;

        public Builder layout(ViewGroup container) {
            this.container = container;
            return this;
        }

        public Builder sudoku(Sudoku sudoku) {
            this.sudoku = sudoku;
            return this;
        }

        public void build() {
            if (container == null) {
                throw new IllegalArgumentException("container must be != null");
            } else if (sudoku == null) {
                throw new IllegalArgumentException("sudoku must be != null");
            }
            new Keyboard(sudoku, container);
        }
    }
}
