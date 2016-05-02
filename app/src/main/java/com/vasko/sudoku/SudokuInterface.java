package com.vasko.sudoku;

import android.os.Bundle;

public interface SudokuInterface extends KeyboardInterface {

    void drawInitialSudoku();

    void drawSolvedSudoku();

    void onSavedInstanceState(Bundle bundle);

}
