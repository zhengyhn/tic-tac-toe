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
    protected int calScoreInternal(ChessState[][] states) {
        int totalScore = 0;
        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j < states[i].length; ++j) {
                if (states[i][j] == ChessState.EMPTY) {
                    continue;
                }
                ChessState target = states[i][j];
                int count = 1;
                int left = j - 1;
                while (left >= 0 && states[i][left] == target) {
                    ++count;
                    --left;
                }
                int right = j + 1;
                while (right < states[i].length && states[i][right] == target) {
                    ++count;
                    ++right;
                }
                int score = 0;
                if (count >= states[i].length) {
                    score += count * 10;
                } else if ((left >= 0 && (states[i][left] == ChessState.EMPTY)) &&
                        ((right < states[i].length) && (states[i][right] == ChessState.EMPTY))) {
                    score += count * 5;
                } else if ((left >= 0 && (states[i][left] == ChessState.EMPTY)) ||
                        ((right < states[i].length) && (states[i][right] == ChessState.EMPTY))) {
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
        for (int i = 0; i < states.length; ++i) {
            boolean same = true;
            ChessState target = states[i][0];
            if (target == ChessState.EMPTY) {
                continue;
            }
            for (int j = 1; j < states[i].length; ++j) {
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
