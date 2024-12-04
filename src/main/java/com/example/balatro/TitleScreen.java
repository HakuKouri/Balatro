package com.example.balatro;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class TitleScreen
{
    @FXML
    private MediaView mediaTitleBackground;
    @FXML
    private Button btnTitlePlay;

    public void initialize() {

        btnTitlePlay.getStyleClass().add("shadow-b");

        String s = "file:///C:/Users/IT/Java/Balatro/src/main/resources/com/video/balatro%20background%20animation.mp4";
        Media media = new Media(s);
        MediaPlayer player = new MediaPlayer(media);
        mediaTitleBackground.setMediaPlayer(player);
        mediaTitleBackground.getMediaPlayer().play();
    }


}
