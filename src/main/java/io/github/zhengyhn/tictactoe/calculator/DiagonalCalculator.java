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
    protected int calScoreInternal(ChessState[][] states) {
        int totalScore = 0;
        for (int i = 0, j = 0; i < states.length; ++i, ++j) {
            if (states[i][j] == ChessState.EMPTY) {
                continue;
            }
            ChessState target = states[i][j];
            int count = 1;
            int leftRow = i - 1;
            int leftCol = j - 1;
            while (leftRow >= 0 && leftCol >= 0 && states[leftRow][leftCol] == target) {
                ++count;
                --leftRow;
                --leftCol;
            }
            int rightRow = i + 1;
            int rightCol = j + 1;
            while (rightRow < states.length && rightCol < states[i].length && states[rightRow][rightCol] == target) {
                ++count;
                ++rightRow;
                ++rightCol;
            }
            int score = 0;
            if (count >= states[i].length) {
                score += count * 10;
            } else if ((leftRow >= 0 && leftCol >= 0 && (states[leftRow][leftCol] == ChessState.EMPTY)) &&
                    ((rightRow < states.length && rightCol < states[i].length) &&
                            (states[rightRow][rightCol] == ChessState.EMPTY))) {
                score += count * 5;
            } else if ((leftRow >= 0 && leftCol >= 0 && (states[leftRow][leftCol] == ChessState.EMPTY)) ||
                    ((rightRow < states.length && rightCol < states[i].length) &&
                            (states[rightRow][rightCol] == ChessState.EMPTY))) {
                score += count;
            }
            score = target == ChessState.PLAYER ? score : -score;
            totalScore += score;
        }
        for (int i = states.length - 1, j = 0; i >= 0; --i, ++j) {
            if (states[i][j] == ChessState.EMPTY) {
                continue;
            }
            ChessState target = states[i][j];
            int count = 1;
            int leftRow = i + 1;
            int leftCol = j - 1;
            while (leftRow >= 0 && leftCol >= 0 && states[leftRow][leftCol] == target) {
                ++count;
                ++leftRow;
                --leftCol;
            }
            int rightRow = i - 1;
            int rightCol = j + 1;
            while (rightRow < states.length && rightCol < states[i].length && states[rightRow][rightCol] == target) {
                ++count;
                --rightRow;
                ++rightCol;
            }
            int score = 0;
            if (count >= states[i].length) {
                score += count * 10;
            } else if ((leftRow >= 0 && leftCol >= 0 && (states[leftRow][leftCol] == ChessState.EMPTY)) &&
                    ((rightRow < states.length && rightCol < states[i].length) &&
                            (states[rightRow][rightCol] == ChessState.EMPTY))) {
                score += count * 5;
            } else if ((leftRow >= 0 && leftCol >= 0 && (states[leftRow][leftCol] == ChessState.EMPTY)) ||
                    ((rightRow < states.length && rightCol < states[i].length) &&
                            (states[rightRow][rightCol] == ChessState.EMPTY))) {
                score += count;
            }
            score = target == ChessState.PLAYER ? score : -score;
            totalScore += score;
        }
        return totalScore;
    }

    @Override
    protected ChessState findSame(ChessState[][] states) {
        boolean same = true;
        ChessState target = states[0][0];
        if (target == ChessState.EMPTY) {
            return target;
        }
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
        if (target == ChessState.EMPTY) {
            return target;
        }
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
