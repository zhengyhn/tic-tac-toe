package io.github.zhengyhn.tictactoe.calculator;

import io.github.zhengyhn.tictactoe.ChessState;

public class RowCalculator extends AbstractCalculator {
    private volatile static AbstractCalculator instance;

    public static AbstractCalculator getInstance() {
        if (instance == null) {
            synchronized (RowCalculator.class) {
                if (instance == null) {
                    instance = new RowCalculator();
                }
            }
        }
        return instance;
    }

    @Override
    protected ChessState findSame(ChessState[][] states) {
        for (int i = 0; i < states.length; ++i) {
            boolean same = true;
            ChessState target = states[i][0];
            for (int j = 0; j < states[i].length; ++j) {
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
