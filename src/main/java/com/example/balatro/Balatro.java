package com.example.balatro;

import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.controller.GameController;
import com.example.balatro.models.GameModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Balatro extends Application
{
    static private Stage primaryStage;
    public MediaView mediaBackground;
    public Canvas canvasGame;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static final FXMLLoader fxmlLoaderTitle = new FXMLLoader(Balatro.class.getResource("title-screen.fxml"));

    //region GAMEMODEL
    private static GameModel gameModel;

    public static GameModel getGameModel() {
        return gameModel;
    }


    @Override
    public void start(Stage primaryStage) throws IOException
    {
        ScreenResolutions();
        //SqlHandler.main();
        Balatro.primaryStage = primaryStage;

        Scene scene = new Scene(fxmlLoaderTitle.load(), 2560, 1440);
        primaryStage.setTitle("Balatro");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
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

    public void ScreenResolutions() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        for(GraphicsDevice device : ge.getScreenDevices())
            for(DisplayMode mode : device.getDisplayModes())
                System.out.println(mode);

        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode[] modes = gd.getDisplayModes();

            for (DisplayMode mode : modes) {
                System.out.println(mode.toString());
                System.out.println("Resolution: " + mode.getWidth() + " x " + mode.getHeight());
            }

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
