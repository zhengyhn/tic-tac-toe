package io.github.zhengyhn.tictactoe.robot;

import io.github.zhengyhn.tictactoe.ChessState;
import io.github.zhengyhn.tictactoe.GameState;
import io.github.zhengyhn.tictactoe.calculator.StateUtil;

public class MinMaxRobot implements IRobot {
    private static volatile IRobot instance;

    public static IRobot getInstance() {
        if (instance == null) {
            synchronized (MinMaxRobot.class) {
                if (instance == null) {
                    instance = new MinMaxRobot();
                }
            }
        }
        return instance;
    }

    @Override
    public Point getNextStep(ChessState[][] states){
        Point bestPoint = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j < states[i].length; ++j) {
                if (!states[i][j].equals(ChessState.EMPTY)) {
                    continue;
                }
                states[i][j] = ChessState.ROBOT;
                int score = minMax(states, 0, true);
                if (score < bestScore) {
                    bestScore = score;
                    bestPoint = new Point(i, j);
                }
                states[i][j] = ChessState.EMPTY;
            }
        }
        return bestPoint;
    }

    private int minMax(ChessState[][] states, int depth, boolean isMax) {
        GameState result = StateUtil.getInstance().getWinState(states);
        if (result != GameState.RUNNING) {
            switch (result) {
                case DRAW:
                    return -depth;
                case WIN_ROBOT:
                    return -10 + depth;
                case WIN_PLAYER:
                    return 10 - depth;
            }
        }
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < states.length; ++i) {
                for (int j = 0; j < states[i].length; ++j) {
                    if (states[i][j] != ChessState.EMPTY) {
                        continue;
                    }
                    states[i][j] = ChessState.PLAYER;
                    int score = minMax(states, depth + 1, !isMax);
                    best = Math.max(score, best);
                    states[i][j] = ChessState.EMPTY;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < states.length; ++i) {
                for (int j = 0; j < states[i].length; ++j) {
                    if (states[i][j] != ChessState.EMPTY) {
                        continue;
                    }
                    states[i][j] = ChessState.ROBOT;
                    int score = minMax(states, depth + 1, !isMax);
                    best = Math.min(score, best);
                    states[i][j] = ChessState.EMPTY;
                }
            }
            return best;
        }
    }
}
