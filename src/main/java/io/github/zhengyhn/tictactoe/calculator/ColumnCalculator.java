package io.github.zhengyhn.tictactoe.calculator;

import io.github.zhengyhn.tictactoe.ChessState;

public class ColumnCalculator extends AbstractCalculator {
    private volatile static AbstractCalculator instance;

    public static AbstractCalculator getInstance() {
        if (instance == null) {
            synchronized (ColumnCalculator.class) {
                if (instance == null) {
                    instance = new ColumnCalculator();
                }
            }
        }
        return instance;
    }

    @Override
    protected ChessState findSame(ChessState[][] states) {
        for (int j = 0; j < states[0].length; ++j) {
            boolean same = true;
            ChessState target = states[0][j];
            for (int i = 0; i < states.length; ++i) {
                if (states[i][j] != target) {
                    same = false;
                    break;
                }
            }
            if (same) {
                return target;
            }
        }
        return ChessState.EMPTY;
    }
}
