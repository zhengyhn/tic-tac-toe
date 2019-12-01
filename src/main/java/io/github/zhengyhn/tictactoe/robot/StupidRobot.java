package io.github.zhengyhn.tictactoe.robot;

import io.github.zhengyhn.tictactoe.ChessState;

public class StupidRobot implements IRobot {
    private static volatile IRobot instance;

    public static IRobot getInstance() {
        if (instance == null) {
            synchronized (StupidRobot.class) {
                if (instance == null) {
                    instance = new StupidRobot();
                }
            }
        }
        return instance;
    }

    @Override
    public Point getNextStep(ChessState[][] states) {
        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j < states[i].length; ++j) {
                if (states[i][j].equals(ChessState.EMPTY)) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }
}
