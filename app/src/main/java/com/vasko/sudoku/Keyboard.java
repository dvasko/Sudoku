package com.vasko.sudoku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Keyboard {

    private Sudoku mSudoku;
    private KeyCallback mCallback;

    private Keyboard(Context context, final Sudoku sudoku, ViewGroup container, KeyCallback callback) {
        this.mSudoku = sudoku;
        this.mCallback = callback;

        LayoutInflater.from(context).inflate(R.layout.keyboard_layout, container, true);
        for (int i = 0; i < 10; ++i) {
            setupKey(container, i);
        }
    }

    private void setupKey(ViewGroup container, int number) {
        Button button = null;
        switch (number) {
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
                Character character = ((Button) v).getText().charAt(0);
                mSudoku.drawOnActivePoint(character);
                if (mCallback != null) {
                    mCallback.onKey(character);
                }
            }
        });
    }

    public static class Builder {
        private Context context;
        private ViewGroup container;
        private KeyCallback callback;
        private Sudoku sudoku;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder layout(ViewGroup container) {
            this.container = container;
            return this;
        }

        public Builder click(KeyCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder sudoku(Sudoku sudoku) {
            this.sudoku = sudoku;
            return this;
        }

        public Keyboard build() {
            if (context == null) {
                throw new IllegalArgumentException("context must be != null");
            } else if (container == null) {
                throw new IllegalArgumentException("container must be != null");
            } else if (sudoku == null) {
                throw new IllegalArgumentException("sudoku must be != null");
            }
            return new Keyboard(context, sudoku, container, callback);
        }
    }
}
