package io.github.zhengyhn.tictactoe.robot;

import lombok.Getter;

public enum RobotType {
    STUPID("Stupid"),
    MIN_MAX("MinMax"),
    ;

    @Getter
    private String name;
    RobotType(String name) {
       this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
