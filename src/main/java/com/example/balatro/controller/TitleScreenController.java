package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.controller.menuController.NewGameMenuController;
import com.example.balatro.domain.util.MenuManager;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;

import javafx.scene.layout.Pane;
import javafx.scene.paint.*;

import java.io.IOException;

public class TitleScreenController
{
    //region FXML
    public Pane titleScreen;
    public Canvas canvasGame;
    public Button btnTitleOption;
    public Button btnTitleQuit;
    public Button btnTitleCollection;
    public StackPane titleMenuOverlay_StackPane;
    @FXML
    private MediaView mediaTitleBackground;
    @FXML
    private Button btnTitlePlay;
    
    @FXML
    private Pane panePlayMenu;
    //endregion

    //region FXML Loader
    private final FXMLLoader newGameLoader = new FXMLLoader(getClass().getResource("/com/example/menu/balatro/menu/newGameMenu-screen.fxml"));
    private final FXMLLoader optionLoader = new FXMLLoader(getClass().getResource("/com/example/balatro/menu/option-screen.fxml"));


    private NewGameMenuController newGameMenuController;
    private OptionScreenController optionScreenController;
    private GridPane newGameGrid;
    private AnchorPane optionsTabPane;
    //endregion

    //region Global Variables
    private double angle = 0;
    private AnimationTimer timer;

    //endregion

    private static TitleScreenController instance;

    public static TitleScreenController getInstance() {
        return instance;
    }

    public void initialize() {
        instance = this;
        titleMenuOverlay_StackPane.setVisible(false);
        MenuManager.setRootPane(titleMenuOverlay_StackPane);
        btnTitleQuit.setMaxHeight(btnTitlePlay.getPrefHeight()*0.858);

        double width = Balatro.getSettings().getWindowWidth();
        double height = Balatro.getSettings().getWindowHeight();

        canvasGame.setWidth(Balatro.getPrimaryStage().getWidth());
        canvasGame.setHeight(Balatro.getPrimaryStage().getHeight());

        GraphicsContext gc = canvasGame.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        timer = new AnimationTimer() {
            long lastTime = 0;

            @Override
            public void handle(long now) {
                if (now - lastTime < 16_000_000) return; // ~60 FPS
                lastTime = now;
                angle += 0.04; // Rotation

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        // Normalisierte Koordinaten
                        double nx = (x - width / 2.0) / width;
                        double ny = (y - height / 2.0) / height;

                        // Abstand & Winkel zum Mittelpunkt
                        double r = Math.sqrt(nx * nx + ny * ny);
                        double theta = Math.atan2(ny, nx);

                        // Drehen
                        theta += angle + r * 5;

                        // Wellenmuster
                        double wave = Math.sin(theta * 3 + r * 10);

                        double red = 0.5 + 0.5 * Math.sin(wave + angle);
                        double green = 0.2 + 0.3 * Math.sin(wave * 2 + angle);
                        double blue = 0.5 + 0.5 * Math.cos(wave - angle);

                        // Farbverlauf basierend auf Sinuswert
                        Color color = Color.color(
                                Math.max(0, Math.min(1, red)),
                                Math.max(0, Math.min(1, green)),
                                Math.max(0, Math.min(1, blue))
                        );

                        pw.setColor(x, y, color);
                    }
                }
            }
        };
        timer.start();

//        try {
//            newGameGrid = newGameLoader.load();
//            newGameMenuController = newGameLoader.getController();
//            optionsTabPane = optionLoader.load();
//            optionScreenController = optionLoader.getController();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void openNewGameMenu() {
        MenuManager.getInstance().openNewGame();
//        titlePane.getChildren().add(newGameGrid);
//        titlePane.setVisible(true);
//        newGameMenuController.setSize(Balatro.getSettings().getWindowHeight());
//        newGameMenuController.setDeck();
//        timer.stop();
    }

    public void openOptionsMenu() throws IOException {
        titleMenuOverlay_StackPane.setVisible(true);
        titleMenuOverlay_StackPane.getChildren().add(optionsTabPane);
    }


    public void closeNewGameMenu() {
        MenuManager.getInstance().closeMenu();
//        titleMenuOverlay_StackPane.getChildren().remove(newGameGrid);
//        titleMenuOverlay_StackPane.setVisible(false);
    }

    public void closeGame() {
        Balatro.getPrimaryStage().close();
    }

}
