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

    public int calScore(ChessState[][] states) {
        int score = this.calScoreInternal(states);
        if (this.next != null) {
            score += this.next.calScore(states);
        }
        return score;
    }

    protected abstract int calScoreInternal(ChessState[][] states);
    protected abstract ChessState findSame(ChessState[][] states);
}
