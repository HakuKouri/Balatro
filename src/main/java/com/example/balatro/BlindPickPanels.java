package com.example.balatro;

import com.example.balatro.classes.Blind;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.math.BigInteger;

public class BlindPickPanels {

    @FXML
    private Button btnSelectBlind;
    @FXML
    private Pane skipPane;
    @FXML
    private AnchorPane blindPanel;
    @FXML
    private Label lblBlindName;
    @FXML
    private ImageView imageViewBlindChip;
    @FXML
    private ImageView stakeImage;
    @FXML
    private Label lblMinScore;
    @FXML
    private Label lblEarn;

    private GameScreenController gameScreenController;
    private Blind blind;

    public void initialize() {
        System.out.printf(blindPanel.layoutYProperty().toString());
    }

    public void setGameScreenController(GameScreenController gameScreenController) {this.gameScreenController = gameScreenController;}

    public void setBlind(Blind blind) {
        this.blind = blind;
    }


    public void setButtonText(String text) {
        btnSelectBlind.setText(text);
    }

    public void setBossPanel(boolean isBoss) {
        try {
            if (isBoss) {
                skipPane.getChildren().add(FXMLLoader.load(getClass().getResource("bossPane.fxml")));
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("blindSkipPane.fxml"));
                BlindSkipPane pane = loader.getController();
                skipPane.getChildren().add(loader.load());

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
        stakeImage.setImage(image);
    }

    public void setMinScore(BigInteger score) {
        lblMinScore.setText(score.toString());
    }

    public void setEarn(int score) {
        String text = "";
        for (int i = 0; i < score; i++) {
            text +="$";
        }
        lblEarn.setText(text+"+");
    }

    public void play() {
        gameScreenController.startRound(blind,new BigInteger(lblMinScore.getText()));
    }

    public void skip() {
        gameScreenController.skip();
    }
}
