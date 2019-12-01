package io.github.zhengyhn.tictactoe;

import io.github.zhengyhn.tictactoe.robot.RobotType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class MainView extends GridPane {
    @Getter
    @Setter
    private Button[][] grids;
    @Getter
    private ToggleGroup chessRadioGroup;
    @Getter
    private ChoiceBox<RobotType> robotChoiceBox;
    @Getter
    private ChoiceBox<Integer> gridCountChoiceBox;
    @Getter
    private Button startBtn;
    @Getter
    private GridPane sidePane;
    @Getter
    private GridPane boardPane;

    public MainView() {
        final int boardPaneRow = 1;
        final int boardPaneCol = 1;
        boardPane = new GridPane();
        refreshChessBoard(3);
        this.add(boardPane, boardPaneCol, boardPaneRow);

        sidePane = new GridPane();
        final int sidePaneRow = 1;
        final int sidePaneCol = boardPaneCol + 1;

        final int gridCountRow = sidePaneRow;
        final int gridCountCol = 1;
        Label gridCountLabel = new Label("Grid count: ");
        sidePane.add(gridCountLabel, gridCountCol, gridCountRow);
        gridCountChoiceBox = new ChoiceBox<>();
        gridCountChoiceBox.getItems().addAll(3, 5);
        gridCountChoiceBox.setValue(3);
        sidePane.add(gridCountChoiceBox, gridCountCol + 1, gridCountRow);

        final int chessTypeRow = gridCountRow + 1;
        final int chessTypeCol = 1;
        Label chessTypeLabel = new Label("Chess: ");
        sidePane.add(chessTypeLabel, chessTypeCol, chessTypeRow, 1, 1);

        RadioButton xRadioButton = new RadioButton("White");
        xRadioButton.fire();
        RadioButton oRadioButton = new RadioButton("Black");
        chessRadioGroup = new ToggleGroup();
        xRadioButton.setToggleGroup(chessRadioGroup);
        oRadioButton.setToggleGroup(chessRadioGroup);
        sidePane.add(xRadioButton, chessTypeCol + 1, chessTypeRow, 1, 1);
        sidePane.add(oRadioButton, chessTypeCol + 2, chessTypeRow, 1, 1);

        final int robotTypeRow = chessTypeRow + 2;
        final int robotTypeCol = 1;
        Label robotTypeLabel = new Label("Robot: ");
        sidePane.add(robotTypeLabel, robotTypeCol, robotTypeRow, 1, 1);

        robotChoiceBox = new ChoiceBox();
        robotChoiceBox.getItems().addAll(RobotType.STUPID, RobotType.MIN_MAX);
        robotChoiceBox.setValue(RobotType.MIN_MAX);
        sidePane.add(robotChoiceBox, robotTypeCol + 1, robotTypeRow);

        final int startBtnRow = robotTypeRow + 2;
        final int startBtnCol = 1;
        startBtn = new Button("Start");
        sidePane.add(startBtn, startBtnCol, startBtnRow);

        sidePane.setAlignment(Pos.TOP_LEFT);
        sidePane.setHgap(5);
        sidePane.setVgap(5);
        sidePane.setPadding(new Insets(10, 10, 10, 10));

        this.add(sidePane, sidePaneCol, sidePaneRow);

        setAlignment(Pos.CENTER);
    }

    public void refreshChessBoard(int size) {
        boardPane.getChildren().clear();
        grids = new Button[size][size];
        for (int i = 0; i < this.getGrids().length; ++i) {
            for (int j = 0; j < this.getGrids()[i].length; ++j) {
                Button b = new Button();
                b.setPrefWidth(100);
                b.setPrefHeight(100);
                b.setStyle("-fx-border-color: #3e3e3e; -fx-border-width: 1px;");
                b.setBackground(Background.EMPTY);
                grids[i][j] = b;
                boardPane.add(b, j + 1, i + 1, 1, 1);
            }
        }
    }
}
