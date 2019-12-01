package io.github.zhengyhn.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToeApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainView mainView = new MainController().getMainView();
        Scene scene = new Scene(mainView, 1080, 720);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);
        primaryStage.show();
    }

    public static void main(String[] args) {
        TicTacToeApplication.launch(args);
    }
}
