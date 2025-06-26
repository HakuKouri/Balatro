package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.models.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RunInfoController {

    @FXML
    private Label handsLabel;
    @FXML
    private Label discardsLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label anteLabel;
    @FXML
    private Label roundLabel;

    private final GameModel gameModel = Balatro.getGameModel();

    public void initialize() {
        handsLabel.textProperty().bind(gameModel.getCurrentRound().handsProperty().asString());
        discardsLabel.textProperty().bind(gameModel.getCurrentRound().discardsProperty().asString());
        moneyLabel.textProperty().bind(gameModel.getRunState().moneyProperty().asString());
        anteLabel.textProperty().bind(gameModel.getRunState().anteProperty().asString("%d/8"));
        roundLabel.textProperty().bind(gameModel.getRunState().roundProperty().asString());
    }
}
