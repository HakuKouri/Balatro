package com.example.balatro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.layout.Pane;
import java.io.IOException;

public class TitleScreen
{
    @FXML
    private MediaView mediaTitleBackground;
    @FXML
    private Button btnTitlePlay;
    @FXML
    private Pane panePlayMenu;

    public void initialize() {


        btnTitlePlay.getStyleClass().add("shadow-b");

        String s = "file:///C:/JAVA/Balatro/Balatro/src/main/resources/com/video/balatro_background_animation.mp4";
        Media media = new Media(s);
        MediaPlayer player = new MediaPlayer(media);
        player.setCycleCount(Integer.MAX_VALUE);
        mediaTitleBackground.setMediaPlayer(player);
        mediaTitleBackground.getMediaPlayer().play();


    }

    public void openNewGameMenu() throws IOException
    {
        System.out.println("Play button pressed");
        Pane pane = FXMLLoader.load(getClass().getResource("newGameMenu-screen.fxml"));
        panePlayMenu.getChildren().add(pane);
    }

}
