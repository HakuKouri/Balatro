package com.example.balatro.controller;

import com.example.balatro.Balatro;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class TitleScreenController
{
    public Pane titleScreen;
    public Canvas canvasGame;
    public Button btnTitleOption;
    public Button btnTitleQuit;
    public Button btnTitleCollection;
    public StackPane titlePane;
    @FXML
    private MediaView mediaTitleBackground;
    @FXML
    private Button btnTitlePlay;
    
    @FXML
    private Pane panePlayMenu;

    private double angle = 0;

    public void initialize() {
        titlePane.setVisible(false);
        btnTitleQuit.setMaxHeight(btnTitlePlay.getPrefHeight()*0.858);

        String s = getClass().getResource("/com/video/balatro_background_animation.mp4").toExternalForm();
        Media media = new Media(s);

        //MediaPlayer player = new MediaPlayer(media);
        //player.setCycleCount(Integer.MAX_VALUE);
        //mediaTitleBackground.setMediaPlayer(player);
        //mediaTitleBackground.getMediaPlayer().play();
        //startBackgroundAnimation();
        int width = (int) canvasGame.getWidth();
        int height = (int) canvasGame.getHeight();

        System.out.println("width: " + width + " height: " + height);
        GraphicsContext gc = canvasGame.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        AnimationTimer timer = new AnimationTimer() {
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
    }

    public void openNewGameMenu() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/com/example/balatro/newGameMenu-screen.fxml"));
        titlePane.setVisible(true);
        titlePane.getChildren().add(pane);
    }

    public void openOptionsMenu() throws IOException {
        TabPane pane = FXMLLoader.load(getClass().getResource("/com/example/balatro/option-screen.fxml"));
        titlePane.setVisible(true);
        titlePane.getChildren().add(pane);
    }

    public void closeGame() {
        Balatro.getPrimaryStage().close();
    }


}
