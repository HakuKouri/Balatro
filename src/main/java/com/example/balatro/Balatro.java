package com.example.balatro;

import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.controller.GameController;
import com.example.balatro.models.GameModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Balatro extends Application
{
    static private Stage primaryStage;

    private static final FXMLLoader fxmlLoaderTitle = new FXMLLoader(Balatro.class.getResource("title-screen.fxml"));

    //region GAMEMODEL
    private static GameModel gameModel;

    public static GameModel getGameModel() {
        return gameModel;
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {

        //SqlHandler.main();
        Balatro.primaryStage = primaryStage;

        Scene scene = new Scene(fxmlLoaderTitle.load(), 1280, 720);
        primaryStage.setTitle("Balatro");
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();

        Thread sqlThread = new Thread(() -> SqlHandler.main());

        sqlThread.start();
        try {
            System.out.println("test before join");
            sqlThread.join();
            gameModel = new GameModel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("test after join");
        //WebHandler.setupDb();

    }



    public static void newGame(GameSetup gameSetup) throws IOException {
        FXMLLoader fxmlLoaderGame = new FXMLLoader(Balatro.class.getResource("game-screen.fxml"));
        Scene scene = new Scene(fxmlLoaderGame.load(), 1280, 720);
        primaryStage.setScene(scene);

        GameController controller = fxmlLoaderGame.getController();
        System.out.println(controller);
        controller.startNewGame(gameSetup);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
