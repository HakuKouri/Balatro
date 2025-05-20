package com.example.balatro;

import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.controller.GameController;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.SettingsModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Balatro extends Application
{
    //region Primary Stage
    static private Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    //endregion

    //region Game Model
    private static GameModel gameModel;

    public static GameModel getGameModel() {
        return gameModel;
    }
    //endregion

    //region Settings Model
    private String rootPath = getRootPath() + "settings.xml";
    private static final SettingsModel settingsModel = new SettingsModel();

    public static SettingsModel getSettings() { return settingsModel; }
    //endregion

    //region Title Screen
    private final FXMLLoader fxmlLoaderMain = new FXMLLoader(Balatro.class.getResource("balatro-screen.fxml"));
    private final FXMLLoader fxmlLoaderTitle = new FXMLLoader(Balatro.class.getResource("title-screen.fxml"));
    private static final FXMLLoader fxmlLoaderGame = new FXMLLoader(Balatro.class.getResource("game-screen.fxml"));
    public static AnchorPane mainPane;
    //endregion

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Balatro.primaryStage = primaryStage;
        primaryStage.setTitle("Balatro");
        primaryStage.setMaximized(true);
        primaryStage.show();

        //region Settings
        File settingsFile = new File(rootPath);
        if(!settingsFile.exists()) {
            System.out.println("Create Settings File");
            SettingsModel.createSettingsFile(settingsFile.getPath());
        }
        settingsModel.setSettings(rootPath);
        //endregion

        //region Sql
        Thread sqlThread = new Thread(() -> SqlHandler.main());

        sqlThread.start();
        try {
            System.out.println("test before join");
            sqlThread.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //endregion

        gameModel = new GameModel();

        //region add Main Pane
        mainPane = fxmlLoaderMain.load();
        mainPane.getChildren().add(fxmlLoaderTitle.load());
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        //endregion
    }

    public void ScreenResolutions() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode[] modes = gd.getDisplayModes();
    }

    public static void newGame(GameSetup gameSetup) throws IOException {
        AnchorPane gamePane = fxmlLoaderGame.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(gamePane);
        GameController controller = fxmlLoaderGame.getController();
        controller.startNewGame(gameSetup);
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
