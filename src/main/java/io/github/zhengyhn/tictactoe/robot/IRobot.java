package io.github.zhengyhn.tictactoe.robot;

import io.github.zhengyhn.tictactoe.ChessState;

public interface IRobot {
    Point getNextStep(ChessState[][] states);
}
