package io.github.zhengyhn.tictactoe.calculator;

import io.github.zhengyhn.tictactoe.ChessState;

public class DiagonalCalculator extends AbstractCalculator {
    private volatile static AbstractCalculator instance;

    public static AbstractCalculator getInstance() {
        if (instance == null) {
            synchronized (DiagonalCalculator.class) {
                if (instance == null) {
                    instance = new DiagonalCalculator();
                }
            }
        }
        return instance;
    }

    @Override
    protected ChessState findSame(ChessState[][] states) {
        boolean same = true;
        ChessState target = states[0][0];
        for (int i = 1, j = 1; i < states.length; ++i, ++j) {
            if (states[i][j] != target) {
                same = false;
                break;
            }
        }
        if (same) {
            return target;
        }
        same = true;
        target = states[0][states.length - 1];
        for (int i = 1, j = states.length - 2; i < states.length; ++i, --j) {
            if (states[i][j] != target) {
                same = false;
                break;
            }
        }
        if (same) {
            return target;
        }
        return ChessState.EMPTY;
    }
}
