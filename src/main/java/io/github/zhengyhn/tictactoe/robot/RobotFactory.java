package io.github.zhengyhn.tictactoe.robot;

public class RobotFactory {
    public static IRobot getRobot(RobotType type) {
        switch (type) {
            case STUPID:
                return StupidRobot.getInstance();
            case MIN_MAX:
                return MinMaxRobot.getInstance();
            default:
                throw new RuntimeException("Type: " + type + " hasn't been implemented");
        }
    }
}
