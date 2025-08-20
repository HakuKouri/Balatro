package com.example.balatro;

import com.example.balatro.domain.game.GameSetup;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.controller.GameController;
import com.example.balatro.domain.util.FxmlUtil;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.ProfileModel;
import com.example.balatro.models.SettingsModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class Balatro extends Application {

    //region Singleton
    private static Stage primaryStage;
    private static GameModel gameModel;
    private static final SettingsModel settingsModel = new SettingsModel();

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static GameModel getGameModel() {
        return gameModel;
    }
    public static SettingsModel getSettings() {
        return settingsModel;
    }
    //endregion

    public static AnchorPane mainPane;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Balatro.primaryStage = primaryStage;
        initSql();

        gameModel = new GameModel();
        settingsModel.initOrCreate();

        setupStage();
        initGameModel();

        gameModel.getProfiles().setAll(SqlHandler.getAllProfileModels());
        for (ProfileModel profileModel : gameModel.getProfiles()) {
            System.out.println("Balatro | Profile Id: " + profileModel.getId());
        }
        gameModel.changeActiveProfile(gameModel.getProfiles().getFirst());

        loadTitlePane();

        primaryStage.setOnCloseRequest(e -> {
            if (settingsModel.getWindowMode().equals("windowed")) {
                settingsModel.setStoredWindowWidth(primaryStage.getWidth());
                settingsModel.setStoredWindowHeight(primaryStage.getHeight());
                settingsModel.saveToFile("settings.xml"); // oder dein Pfad
            }
        });

        //Scaling der mainPane im verhältnis
        primaryStage.widthProperty().addListener(e ->  updateScale());
        primaryStage.heightProperty().addListener( e -> updateScale());

        Screen.getScreens().forEach(screen -> System.out.println(screen.getVisualBounds()));

        Platform.runLater(this::updateScale);
    }

    private void updateScale() {
        double baseWidth = 1920;
        double baseHeight = 1080;

        double scaleX = primaryStage.getWidth() / baseWidth;
        double scaleY = primaryStage.getHeight() / baseHeight;
        double scale = Math.min(scaleX, scaleY);

        mainPane.setScaleX(scale);
        mainPane.setScaleY(scale);
    }

    private void applyFontCss(AnchorPane pane, double width) {
        ObservableList<String> stylesheets = pane.getStylesheets();
        stylesheets.removeIf(s -> s.contains("font-"));

        if (width < 1200) {
            stylesheets.add(getClass().getResource("/com/css/font-small.css").toExternalForm());
        } else if (width < 1600) {
            stylesheets.add(getClass().getResource("/com/css/font-medium.css").toExternalForm());
        } else {
            stylesheets.add(getClass().getResource("/com/css/font-large.css").toExternalForm());
        }
    }

    private void loadTitlePane() {
        mainPane = new AnchorPane();
       // mainPane.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());

        // 2D UI z. B. Titelbildschirm laden
        Pair<Object, AnchorPane> titleScreen = FxmlUtil.loadWithPane("/com/example/balatro/title/title-screen.fxml");
        AnchorPane titlePane = titleScreen.getValue();

        titlePane.setMaxSize(primaryStage.getWidth(), primaryStage.getHeight());

        // cardScene_GameCards über alles legen
        mainPane.getChildren().addAll(titlePane);

        PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
        perspectiveCamera.setPickOnBounds(false);

        // Hauptscene – KEIN 3D nötig
        Scene scene = new Scene(mainPane, primaryStage.getWidth(), primaryStage.getHeight(), false);
        scene.setCamera(perspectiveCamera);
        Platform.runLater(() -> {
            titlePane.widthProperty().addListener(e -> applyFontCss(titlePane, primaryStage.getWidth()));
            applyFontCss(titlePane, primaryStage.getWidth());
        });

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void initGameModel() {
        //gameModel = new GameModel();
        //MenuManager.init(gameModel);
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
        String windowMode = settingsModel.getWindowMode();

        if (windowMode.equalsIgnoreCase("fullscreen")) {
            primaryStage.setFullScreen(true);
        } else if (windowMode.equalsIgnoreCase("borderless")) {
            primaryStage.setFullScreen(false);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(false);
        } else if (windowMode.equalsIgnoreCase("windowed")) {
            primaryStage.setFullScreen(false);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(true);

            primaryStage.setWidth(settingsModel.getStoredWindowWidth());
            primaryStage.setHeight(settingsModel.getStoredWindowHeight());
        }

        settingsModel.setWindowWidth(primaryStage.getWidth());
        settingsModel.setWindowHeight(primaryStage.getHeight());
    }

    public static void newGame(GameSetup gameSetup) throws IOException {
        //clear Pane
        mainPane.getChildren().clear();

        //declare
        Pair<GameController, AnchorPane> gameScreen = FxmlUtil.loadWithPane("/com/example/balatro/main/game-screen.fxml");
        GameController controller = gameScreen.getKey();
        AnchorPane gamePane = gameScreen.getValue();

        //add Game Pane to Main Pane
        mainPane.getChildren().addAll(gamePane);

        //start new Game
        controller.startNewGame(gameSetup);
    }

    public static void main(String[] args) {
        launch(args);
    }


    //SubScene for Layering
//    private SubScene getSubScene(AnchorPane pane) {
//        SubScene cardScene = new SubScene(pane, settingsModel.getWindowWidth(), settingsModel.getWindowHeight(), true, javafx.scene.SceneAntialiasing.BALANCED);
//        PerspectiveCamera cardCamera = new PerspectiveCamera(true);
//        cardCamera.setTranslateZ(-2000); // Leicht zurückgesetzt, damit Rotation sichtbar ist
//        cardScene.setCamera(cardCamera);
//        //cardScene.setFill(null); // transparent
//
//        return cardScene;
//    }
//
//    private AnchorPane getPane() {
//        AnchorPane pane = new AnchorPane();
//        pane.setPrefSize(settingsModel.getWindowWidth(), settingsModel.getWindowHeight());
//        pane.setPickOnBounds(false);// WICHTIG: soll keine Clicks blockieren
//        pane.setBackground(null);
//
//        return pane;
//    }


}
