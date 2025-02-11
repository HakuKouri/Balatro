package com.example.balatro;

import com.example.balatro.classes.Blind;
import com.example.balatro.classes.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.math.BigDecimal;

public class BlindPickPanels {

    public static BlindPickPanels smallPanel;

    @FXML
    private Button btnSelectBlind;
    @FXML
    private AnchorPane skipAnchorPane;
    @FXML
    private AnchorPane blindPanel;
    @FXML
    private Label lblBlindName;
    @FXML
    private ImageView imageViewBlindChip;
    @FXML
    private ImageView imageViewStakeImage;
    @FXML
    private Label lblMinScore;
    @FXML
    private Label lblEarn;

    private Blind blind;

    private GameController gameController;

    private final FXMLLoader loaderSkipPane = new FXMLLoader(getClass().getResource("blindSkipPane.fxml"));
    private final FXMLLoader loaderBossPane = new FXMLLoader(getClass().getResource("bossPane.fxml"));

    private BlindSkipPane blindSkipController;
    private AnchorPane skipPane;
    private AnchorPane bossPane;

    private static Image stakeImage;

    public void initialize() {
        System.out.printf("guck mal");
    }

    public static void setStageImage(Image image) {
        stakeImage = image;
    }

    public void setGameScreenController(GameController gameController) {this.gameController = gameController;}

    public void setBlind(Blind blind, Tag tag, int blindNumber) {
        this.blind = blind;
        if(blindNumber == 1) {
            setButtonText("Select");
            setBlindName(blind.getBlindName());
            setBlindImage(new Image("file:"+ blind.getBlindImageUrl()));
            //setBossPanel(false);
            setActivity(false);
            setEarn(3);
            try {
                skipPane = loaderSkipPane.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blindSkipController = loaderSkipPane.getController();
            skipAnchorPane.getChildren().add(skipPane);
            setTag(tag);
        } else if(blindNumber == 2) {
            setButtonText("Upcoming");
            setBlindName(blind.getBlindName());
            setBlindImage(new Image("file:" + blind.getBlindImageUrl()));
            //setBossPanel(false);
            setActivity(false);
            setEarn(4);
            try {
                skipPane = loaderSkipPane.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blindSkipController = loaderSkipPane.getController();
            skipAnchorPane.getChildren().add(skipPane);
            setTag(tag);
        } else if(blindNumber == 3) {
            setButtonText("Upcoming");
            setBlindName(blind.getBlindName());
            setBlindImage(new Image("file:" + blind.getBlindImageUrl()));
            //setBossPanel(true);
            setActivity(false);
            setEarn(5);
            try {
                bossPane = loaderBossPane.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            skipAnchorPane.getChildren().add(bossPane);
        }
    }

    public void setButtonText(String text) {
        btnSelectBlind.setText(text);
    }

    public void setBossPanel(boolean isBoss) {
        try {
            if (isBoss) {
                skipPane.getChildren().add(loaderBossPane.load());
            } else {
                skipPane.getChildren().add(loaderSkipPane.load());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setActivity(boolean isActivity) {
        blindPanel.setDisable(isActivity);
    }

    public void setBlindName(String name) {
        lblBlindName.setText(name);
    }

    public void setBlindImage(Image image) {
        imageViewBlindChip.setImage(image);
    }

    public void setStakeImage(Image image) {
        imageViewStakeImage.setImage(stakeImage);
    }

    public void setMinScore(BigDecimal score) {
        lblMinScore.setText(score.toString());
    }

    public void setEarn(int score) {
        StringBuilder text = new StringBuilder();
        text.append("$".repeat(Math.max(0, score)));
        lblEarn.setText(text+"+");
    }

    public void play() {
        gameController.startRound(blind,new BigDecimal(lblMinScore.getText()));
    }

    public void skip() {
        gameController.skip(blindSkipController.getTag());
    }

    public void setTag(Tag tag) {
        blindSkipController.setTag(tag);
    }
}
