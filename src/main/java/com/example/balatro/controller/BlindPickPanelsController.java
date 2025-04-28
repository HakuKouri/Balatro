package com.example.balatro.controller;

import com.example.balatro.classes.Blind;
import com.example.balatro.classes.Tag;
import com.example.balatro.models.GameModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.math.BigDecimal;

public class BlindPickPanelsController {

    public static BlindPickPanelsController smallPanel;

    public Label effectText_label;

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

    private ObjectProperty<Blind> blind = new SimpleObjectProperty<>(new Blind());

    private final GameController gameController = GameController.getInstance();
    private final GameModel model = GameController.getGameModel();

    private final FXMLLoader loaderSkipPane = new FXMLLoader(getClass().getResource("/com/example/balatro/blindSkipPane.fxml"));
    private final FXMLLoader loaderBossPane = new FXMLLoader(getClass().getResource("/com/example/balatro/bossPane.fxml"));

    private BlindSkipPaneController blindSkipController;
    private AnchorPane skipPane;
    private AnchorPane bossPane;

    public void initialize() {
        imageViewStakeImage.imageProperty().bind(model.getChosenStake().imageProperty());
        imageViewBlindChip.imageProperty().bind(blind.get().imageProperty());
        lblBlindName.textProperty().bind(blind.get().blindNameProperty());
        effectText_label.textProperty().bind(blind.get().blindDescriptionProperty());
    }

    public void setBlind(Blind blind, Tag tag, int blindNumber) {
        this.blind.get().setBlind(blind);
        if(blindNumber == 1) {
            setButtonText("Select");

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

    public void setMinScore(BigDecimal score) {
        lblMinScore.setText(score.toString());
    }

    public void setEarn(int score) {
        StringBuilder text = new StringBuilder();
        text.append("$".repeat(Math.max(0, score)));
        lblEarn.setText(text+"+");
    }

    public void play() {
        model.setRound(model.getRound()+1);
        model.toggleHandButtonVisibility();
        gameController.startRound(blind.get(),new BigDecimal(lblMinScore.getText()));
    }

    public void skip() {
        gameController.skip(blindSkipController.getTag());
    }

    public void setTag(Tag tag) {
        blindSkipController.setTag(tag);
    }
}
