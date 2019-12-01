package io.github.zhengyhn.tictactoe;

import io.github.zhengyhn.tictactoe.calculator.StateUtil;
import io.github.zhengyhn.tictactoe.robot.IRobot;
import io.github.zhengyhn.tictactoe.robot.Point;
import io.github.zhengyhn.tictactoe.robot.RobotFactory;
import io.github.zhengyhn.tictactoe.robot.RobotType;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainController {
    @Getter
    private MainView mainView;
    private ChessState[][] states;
    private RadioButton selectedChess;
    private GameState gameState;
    private static final String whiteBackgroundImgPath = "whitePiece.png";
    private static final String blackBackgroundImgPath = "blackPiece.png";
    private Background whiteBackground;
    private Background blackBackground;

    public MainController() {
        mainView = new MainView();
        gameState = GameState.READY;
        mainView.getBoardPane().setDisable(true);

        BackgroundImage whiteBackgroundImg = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource(whiteBackgroundImgPath).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        whiteBackground = new Background(whiteBackgroundImg);
        BackgroundImage blackBackgroundImg = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource(blackBackgroundImgPath).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        blackBackground = new Background(blackBackgroundImg);

        mainView.getGridCountChoiceBox().getSelectionModel()
                .selectedIndexProperty()
                .addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    if (gameState != GameState.READY) {
                        return;
                    }
                    mainView.refreshChessBoard(mainView.getGridCountChoiceBox().getItems().get(newValue.intValue()));
                });
        mainView.getStartBtn().setOnAction(event -> {
            startGame();
        });

    }

    private void startGame() {
        mainView.getSidePane().setDisable(true);
        mainView.getBoardPane().setDisable(false);
        gameState = GameState.RUNNING;
        for (Button[] row: mainView.getGrids()) {
            for (Button btn: row) {
                btn.setBackground(null);
            }
        }
        for (int i = 0; i < mainView.getGrids().length; ++i) {
            for (int j = 0; j < mainView.getGrids()[i].length; ++j) {
                Button button = mainView.getGrids()[i][j];
                final int row = i;
                final int col = j;
                button.setOnAction(event -> {
                    passToPlayer(row, col);
                });
            }
        }
        states = new ChessState[mainView.getGrids().length][mainView.getGrids().length];
        for (ChessState[] row: states) {
            Arrays.fill(row, ChessState.EMPTY);
        }
    }

    private void passToPlayer(int row,  int col) {
        if (gameState != GameState.RUNNING || states[row][col] != ChessState.EMPTY) {
            return;
        }
        states[row][col] = ChessState.PLAYER;
        selectedChess = (RadioButton) mainView.getChessRadioGroup().getSelectedToggle();

        mainView.getGrids()[row][col].setBackground(
                selectedChess.getText().equals("White") ? whiteBackground : blackBackground);
        if (checkWinner() == GameState.RUNNING) {
            passToRobot();
        }
    }

    private void passToRobot() {
        RobotType robotType = mainView.getRobotChoiceBox().getValue();
        IRobot robot = RobotFactory.getRobot(robotType);
        Point point = robot.getNextStep(states);
        if (point == null) {
            showMessage("You win!");
            return;
        }
        states[point.getX()][point.getY()] = ChessState.ROBOT;
        mainView.getGrids()[point.getX()][point.getY()].setBackground(
                selectedChess.getText().equals("White") ? blackBackground : whiteBackground);

        checkWinner();
    }

    private GameState checkWinner() {
        GameState winState = StateUtil.getInstance().getWinState(states);
        switch (winState) {
            case WIN_PLAYER:
                showMessage("You win!");
                break;
            case WIN_ROBOT:
                showMessage("You lose!");
                break;
            case DRAW:
                showMessage("Draw!");
                break;
            default:
                break;
        }
        return winState;
    }

    private void showMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setContentText(text);
        alert.showAndWait();
        gameState = GameState.READY;
        mainView.getSidePane().setDisable(false);
        mainView.getBoardPane().setDisable(true);
    }
}
