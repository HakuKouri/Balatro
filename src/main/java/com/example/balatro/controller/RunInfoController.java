package com.example.balatro.controller;

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

    private GameModel model = GameController.getGameModel();

    public void initialize() {
        handsLabel.textProperty().bind(model.handsProperty().asString());
        discardsLabel.textProperty().bind(model.discardsProperty().asString());
        moneyLabel.textProperty().bind(model.moneyProperty().asString());
        anteLabel.textProperty().bind(model.anteProperty().asString("%d/8"));
        roundLabel.textProperty().bind(model.roundProperty().asString());
    }
}
