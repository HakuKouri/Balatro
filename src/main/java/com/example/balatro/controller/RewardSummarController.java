package com.example.balatro.controller;

import javafx.event.ActionEvent;

public class RewardSummarController {

    GameController gameController;

    public void cashout(ActionEvent actionEvent) {
        int reward= 0;
        gameController.addMoney(reward);
    }

    public void setGameScreenController(GameController gameController) {
        this.gameController = gameController;
    }
}
