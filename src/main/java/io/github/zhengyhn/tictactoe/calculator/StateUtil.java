package io.github.zhengyhn.tictactoe.calculator;

import io.github.zhengyhn.tictactoe.ChessState;
import io.github.zhengyhn.tictactoe.GameState;

import java.util.ArrayList;
import java.util.List;

public class StateUtil {
    private volatile static StateUtil instance;
    private List<AbstractCalculator> calculators;

    public static StateUtil getInstance() {
        if (instance == null) {
            synchronized (StateUtil.class) {
                if (instance == null) {
                    instance = new StateUtil();
                }
            }
        }
        return instance;
    }

    public StateUtil() {
        calculators = new ArrayList<AbstractCalculator>() {{
            add(RowCalculator.getInstance());
            add(ColumnCalculator.getInstance());
            add(DiagonalCalculator.getInstance());
        }};
        for (int i = 0; i < calculators.size() - 1; ++i) {
            calculators.get(i).setNext(calculators.get(i + 1));
        }
    }

    public GameState getWinState(ChessState[][] states) {
        boolean isFull = true;
        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j < states[i].length; ++j) {
                if (states[i][j] == ChessState.EMPTY) {
                    isFull = false;
                    break;
                }
            }
        }
        ChessState resultState = calculators.get(0).calculate(states);
        if (resultState != ChessState.EMPTY) {
            return resultState == ChessState.PLAYER ? GameState.WIN_PLAYER : GameState.WIN_ROBOT;
        }
        return isFull ? GameState.DRAW : GameState.RUNNING;
    }
}
