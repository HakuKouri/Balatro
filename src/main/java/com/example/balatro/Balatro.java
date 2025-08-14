package com.example.balatro;

import com.example.balatro.domain.game.GameSetup;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.controller.GameController;
import com.example.balatro.domain.util.FxmlUtil;
import com.example.balatro.domain.util.MenuManager;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.ProfileModel;
import com.example.balatro.models.SettingsModel;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;

public class Balatro extends Application
{
    //region Primary Stage
    private static Stage primaryStage;
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    //endregion

    //region Game & Settings Model
    private static GameModel gameModel;
    public static GameModel getGameModel() {
        return gameModel;
    }


    private static final SettingsModel settingsModel = new SettingsModel();
    public static SettingsModel getSettings() { return settingsModel; }

    private static final String rootPath = "settings.xml";
    //endregion

    //region Title Screen
    public static AnchorPane mainPane;
    //endregion

    private static AnchorPane card3DPane;
    public static AnchorPane getCard3DPane() {
        return card3DPane;
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        initSettings();
        Balatro.primaryStage = primaryStage;
        setupStage();
        initSql();
        initGameModel();

        gameModel.getProfiles().setAll(SqlHandler.getAllProfileModels());
        for (ProfileModel profileModel : gameModel.getProfiles()) {
            System.out.println("Balatro | Profile Id: " + profileModel.getId());
        }
        gameModel.changeActiveProfile(gameModel.getProfiles().getFirst());

        loadTitlePane();
    }

    private void loadTitlePane() {
        mainPane = new AnchorPane();
        mainPane.setPrefSize(settingsModel.getWindowWidth(), settingsModel.getWindowHeight());

// 2D UI z. B. Titelbildschirm laden
        Pair<Object, AnchorPane> titleScreen = FxmlUtil.loadWithPane("/com/example/balatro/title/title-screen.fxml");
        AnchorPane titlePane = titleScreen.getValue();
        titlePane.setMaxSize(settingsModel.getWindowWidth(), settingsModel.getWindowHeight());

// Optional: eigener 3D-Bereich für Karten
        AnchorPane card3DPane = new AnchorPane(); // Hier landen Karten mit Float/3D-Effekten
        card3DPane.setPickOnBounds(false); // WICHTIG: soll keine Clicks blockieren
        Balatro.card3DPane = card3DPane;

// SubScene mit 3D-Tiefe nur für Karten
        SubScene cardScene = new SubScene(card3DPane, settingsModel.getWindowWidth(), settingsModel.getWindowHeight(), true, javafx.scene.SceneAntialiasing.BALANCED);
        PerspectiveCamera cardCamera = new PerspectiveCamera(true);
        cardCamera.setTranslateZ(-2000); // Leicht zurückgesetzt, damit Rotation sichtbar ist
        cardScene.setCamera(cardCamera);
        cardScene.setFill(null); // transparent

// Wichtig: Die SubScene darf keine Klicks blockieren
        cardScene.setPickOnBounds(false);

// cardScene über alles legen
        mainPane.getChildren().addAll(titlePane, cardScene);

// Hauptscene – KEIN 3D nötig
        Scene scene = new Scene(mainPane, settingsModel.getWindowWidth(), settingsModel.getWindowHeight(), false);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

//        mainPane = new AnchorPane();
//        mainPane.setPrefSize(settingsModel.getWindowWidth(), settingsModel.getWindowHeight());
//
//        Pair<Object, AnchorPane> titleScreen = FxmlUtil.loadWithPane("/com/example/balatro/title/title-screen.fxml");
//        AnchorPane titlePane = titleScreen.getValue();
//        titlePane.setMaxSize(settingsModel.getWindowWidth(), settingsModel.getWindowHeight());
//        mainPane.getChildren().add(titlePane);
//
//        Scene scene = new Scene(mainPane, primaryStage.getWidth(), primaryStage.getHeight(), true);
//
//        PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
////        perspectiveCamera.setTranslateZ(-1000);
////        perspectiveCamera.setNearClip(0.1);
////        perspectiveCamera.setFarClip(5000);
//        scene.setCamera(perspectiveCamera);
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();
    }

    private void initGameModel() {
        gameModel = new GameModel();
        MenuManager.init(gameModel);
    }

    private void initSettings() {
        File settingsFile = new File(rootPath);
        if(!settingsFile.exists()) {
            SettingsModel.createSettingsFile(settingsFile.getPath());
        }

        settingsModel.setSettings(rootPath);
        settingsModel.updateSettings(rootPath);
    }

    private void initSql() {
        //region Sql
        Thread sqlThread = new Thread(SqlHandler::main);

        sqlThread.start();
        try {
            sqlThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //endregion
    }

    private void setupStage() {
        primaryStage.setTitle("Balatro");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
    }

    public static void newGame(GameSetup gameSetup) throws IOException {
        //clear Pane
        mainPane.getChildren().clear();

        //declare
        Pair<GameController, AnchorPane> gameScreen = FxmlUtil.loadWithPane("/com/example/balatro/main/game-screen.fxml");
        GameController controller = gameScreen.getKey();
        AnchorPane gamePane = gameScreen.getValue();

        //add Game Pane to Main Pane
        mainPane.getChildren().addAll(gamePane, card3DPane);

        //start new Game
        controller.startNewGame(gameSetup);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
