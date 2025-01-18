package com.example.balatro;

import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.classes.WebHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Balatro extends Application
{
    static private Stage primaryStage;
    static public GameScreenController gameScreenController = new GameScreenController();

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Balatro.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(Balatro.class.getResource("title-screen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setTitle("Balatro");
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();

        SqlHandler.main();
        //WebHandler.setupDb();

    }


    public static void newGame(GameSetup gameSetup) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Balatro.class.getResource("game-screen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        primaryStage.setScene(scene);

        gameScreenController.startNewGame(gameSetup);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
