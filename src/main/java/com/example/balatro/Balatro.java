package com.example.balatro;

import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Balatro extends Application
{
    static private Stage primaryStage;
    static public GameController gameController = new GameController();

    private static FXMLLoader fxmlLoaderTitle = new FXMLLoader(Balatro.class.getResource("title-screen.fxml"));
    private static FXMLLoader fxmlLoaderGame = new FXMLLoader(Balatro.class.getResource("game-screen.fxml"));

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Balatro.primaryStage = primaryStage;

        Scene scene = new Scene(fxmlLoaderTitle.load(), 1280, 720);
        primaryStage.setTitle("Balatro");
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();

        SqlHandler.main();
        //WebHandler.setupDb();
    }


    public static void newGame(GameSetup gameSetup) throws IOException {
        Scene scene = new Scene(fxmlLoaderGame.load(), 1280, 720);
        primaryStage.setScene(scene);

        gameController.startNewGame(gameSetup);

    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
