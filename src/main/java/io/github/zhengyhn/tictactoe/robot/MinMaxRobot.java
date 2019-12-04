package io.github.zhengyhn.tictactoe.robot;

import io.github.zhengyhn.tictactoe.ChessState;
import io.github.zhengyhn.tictactoe.GameState;
import io.github.zhengyhn.tictactoe.calculator.StateUtil;

public class MinMaxRobot implements IRobot {
    private static volatile IRobot instance;
    private static final int MAX_LEVEL = 4;

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
                if (bestPoint == null) {
                    bestPoint = new Point(i, j);
                }
                states[i][j] = ChessState.ROBOT;
                int score = minMax(states, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (score < bestScore) {
                    bestScore = score;
                    bestPoint = new Point(i, j);
                }
                states[i][j] = ChessState.EMPTY;
            }
        }
        return bestPoint;
    }

    private int minMax(ChessState[][] states, int depth, boolean isMax, int alpha, int beta) {
        GameState result = StateUtil.getInstance().getWinState(states);
        if (result != GameState.RUNNING) {
            switch (result) {
                case DRAW:
                    return -depth;
                case WIN_ROBOT:
                    return -100 + depth;
                case WIN_PLAYER:
                    return 100 - depth;
            }
        }
        if (depth >= MAX_LEVEL) {
            int score = StateUtil.getInstance().getScore(states);
            return score > 0 ? score - depth : score + depth;
        }
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < states.length; ++i) {
                for (int j = 0; j < states[i].length; ++j) {
                    if (states[i][j] != ChessState.EMPTY) {
                        continue;
                    }
                    states[i][j] = ChessState.PLAYER;
                    int score = minMax(states, depth + 1, !isMax, alpha, beta);
                    best = Math.max(score, best);
                    states[i][j] = ChessState.EMPTY;
                    alpha = Math.max(best, alpha);
                    if (beta <= alpha) {
                        return best;
                    }
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
                    int score = minMax(states, depth + 1, !isMax, alpha, beta);
                    best = Math.min(score, best);
                    states[i][j] = ChessState.EMPTY;
                    beta = Math.min(best, beta);
                    if (beta <= alpha) {
                        return best;
                    }
                }
            }
            return best;
        }
    }
}
