package com.example.balatro.controller;

import com.example.balatro.Balatro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.Objects;

public class TitleScreenController
{
    public Pane titleScreen;
    @FXML
    private MediaView mediaTitleBackground;
    @FXML
    private Button btnTitlePlay;
    @FXML
    private Pane panePlayMenu;

    public void initialize() {
        btnTitlePlay.getStyleClass().add("shadow-b");

        String s = getClass().getResource("/com/video/balatro_background_animation.mp4").toExternalForm();
        Media media = new Media(s);

        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(Integer.MAX_VALUE);
        mediaTitleBackground.setMediaPlayer(player);
        mediaTitleBackground.getMediaPlayer().play();
    }

    public void openNewGameMenu() throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("/com/example/balatro/newGameMenu-screen.fxml"));
        panePlayMenu.getChildren().add(pane);
    }

    public void openOptionsMenu() throws IOException {
        TabPane pane = FXMLLoader.load(getClass().getResource("/com/example/balatro/option-screen.fxml"));
        titleScreen.getChildren().add(pane);
    }

    public void closeGame() {
        Balatro.getPrimaryStage().close();
    }
}
