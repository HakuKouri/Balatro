package com.example.balatro;

import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.controller.GameController;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.SettingsModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private static final SettingsModel settingsModel = new SettingsModel();
    private String rootPath = getRootPath() + "settings.xml";

    public static SettingsModel getSettings() { return settingsModel; }
    //endregion

    //region Title Screen
    private static final FXMLLoader fxmlLoaderTitle = new FXMLLoader(Balatro.class.getResource("title-screen.fxml"));
    private AnchorPane anchorPane;
    //endregion

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Balatro.primaryStage = primaryStage;

        //region Settings
        File settingsFile = new File(rootPath);
        if(!settingsFile.exists()) {
            System.out.println("Create Settings File");
            SettingsModel.createSettingsFile(settingsFile.getPath());
        }
        settingsModel.setSettings(rootPath);
        //endregion

        Thread sqlThread = new Thread(() -> SqlHandler.main());

        sqlThread.start();
        try {
            System.out.println("test before join");
            sqlThread.join();
            gameModel = new GameModel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Primary Screen: " + Screen.getPrimary());
        System.out.println("Primary Resolution: " + Screen.getPrimary().getBounds().getWidth() + "x" + Screen.getPrimary().getBounds().getHeight());
        System.out.println("Settings Screen: " + Screen.getScreens().get(settingsModel.getScreen()));
        System.out.println("Settings Resolution: " +  Screen.getScreens().get(settingsModel.getScreen()).getBounds().getWidth() + "x" +  Screen.getScreens().get(settingsModel.getScreen()).getBounds().getHeight());

        anchorPane = fxmlLoaderTitle.load();
        anchorPane.setPrefSize(Screen.getScreens().get(settingsModel.getScreen()).getBounds().getWidth(), Screen.getScreens().get(settingsModel.getScreen()).getBounds().getHeight());
        Scene scene = new Scene(anchorPane);
        System.out.println("Scene Width " + scene.getWidth());
        System.out.println("Scene Height " + scene.getHeight());

        primaryStage.setTitle("Balatro");
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);

        primaryStage.show();
        System.out.println("Primary Width: " + primaryStage.getWidth());
        System.out.println("Primary Height: " + primaryStage.getHeight());

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
        Scene scene = new Scene(fxmlLoaderGame.load(), primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);

        GameController controller = fxmlLoaderGame.getController();
        System.out.println(controller);
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
