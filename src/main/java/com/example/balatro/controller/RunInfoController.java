package com.example.balatro.controller;

import com.example.balatro.models.RunInfoModel;
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

    private RunInfoModel model = new RunInfoModel();

    public void initialize() {
        handsLabel.textProperty().bind(model.hands.asString());
        discardsLabel.textProperty().bind(model.discards.asString());
        moneyLabel.textProperty().bind(model.money.asString());
        anteLabel.textProperty().bind(model.ante.asString("%d/8"));
        roundLabel.textProperty().bind(model.round.asString());
    }


    public int getAnte() {
        return model.getAnte();
    }

    public int getRound() {
        return model.getRound();
    }

    public int getHands() {
        return model.getHands();
    }

    public void setHands(int i) {
        model.setHands(i);
    }

    public void setDiscards(int i) {
        model.setDiscards(i);
    }

    public void setAnte(int i) {
        model.setAnte(i);
    }

    public void setRound(int i) {
        model.setRound(i);
    }

    public void setMoney(int i) {
        model.setMoney(i);
    }

    public int getMoney() {
        return model.getMoney();
    }
}
