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
    protected int calScoreInternal(ChessState[][] states) {
        int totalScore = 0;
        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j < states[i].length; ++j) {
                if (states[i][j] == ChessState.EMPTY) {
                    continue;
                }
                ChessState target = states[i][j];
                int count = 1;
                int up = i - 1;
                while (up >= 0 && states[up][j] == target) {
                    ++count;
                    --up;
                }
                int down = i + 1;
                while (down < states.length && states[down][j] == target) {
                    ++count;
                    ++down;
                }
                int score = 0;
                if (count >= states[i].length) {
                    score += count * 10;
                } else if ((up >= 0 && (states[up][j] == ChessState.EMPTY)) &&
                        ((down < states.length) && (states[down][j] == ChessState.EMPTY))) {
                    score += count * 5;
                } else if ((up >= 0 && (states[up][j] == ChessState.EMPTY)) ||
                        ((down < states.length) && (states[down][j] == ChessState.EMPTY))) {
                    score += count;
                }
                score = target == ChessState.PLAYER ? score : -score;
                totalScore += score;
            }
        }
        return totalScore;
    }
    @Override
    protected ChessState findSame(ChessState[][] states) {
        for (int j = 0; j < states[0].length; ++j) {
            boolean same = true;
            ChessState target = states[0][j];
            if (target == ChessState.EMPTY) {
                continue;
            }
            for (int i = 1; i < states.length; ++i) {
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
