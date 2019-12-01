package io.github.zhengyhn.tictactoe.calculator;

import io.github.zhengyhn.tictactoe.ChessState;
import lombok.Setter;

public abstract class AbstractCalculator {
    @Setter
    protected AbstractCalculator next;

    public ChessState calculate(ChessState[][] states) {
        ChessState sameState = this.findSame(states);
        if (sameState == ChessState.EMPTY && this.next != null) {
            sameState = this.next.calculate(states);
        }
        return sameState;
    }

    protected abstract ChessState findSame(ChessState[][] states);
}
