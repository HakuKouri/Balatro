package com.example.balatro;

import javafx.event.ActionEvent;

public class RewardSummarController {

    GameScreenController gameScreenController;

    public void cashout(ActionEvent actionEvent) {
        int reward= 0;
        gameScreenController.addMoney(reward);
    }

    public void setGameScreenController(GameScreenController gameScreenController) {
        this.gameScreenController = gameScreenController;
    }
}
