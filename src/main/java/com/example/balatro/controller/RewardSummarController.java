package com.example.balatro.controller;

import com.example.balatro.classes.Planet;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RewardSummarController {

    //region FXML
    @FXML
    private Pane rewardBlind;
    @FXML
    private ImageView rewardBlindChip;
    @FXML
    private ImageView rewardBlindStake;
    @FXML
    private Label rewardBlindScore;
    @FXML
    private Label rewardBlindReward;
    @FXML
    private Pane rewardHand;
    @FXML
    private Label rewardHandCount;
    @FXML
    private Label rewardHandReward;
    @FXML
    private Pane rewardSatellite;
    @FXML
    private Label rewardSatelliteReward;
    @FXML
    private Pane rewardRocket;
    @FXML
    private Label rewardRocketReward;
    @FXML
    private Pane rewardDelayedGratification;
    @FXML
    private Label rewardDelayedGratificationReward;
    @FXML
    private Pane rewardCloud9;
    @FXML
    private Label rewardCloud9Reward;
    @FXML
    private Pane rewardGoldenJoker;
    @FXML
    private Label rewardGoldenJokerReward;
    @FXML
    private Pane rewardTag;
    @FXML
    private Pane rewardInterest;
    @FXML
    private Label rewardInterestCound;
    @FXML
    private Label rewardInterestReward;
    //endregion

    private final GameModel model = GameController.getGameModel();

    public void initialize() {
        rewardBlindStake.imageProperty().bind(model.getChosenStake().imageProperty());
        rewardBlindChip.imageProperty().bind(model.getActiveBlind().imageProperty());
        rewardBlindScore.textProperty().bind(model.scoreToReachProperty().asString());
        rewardBlindReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, model.getActiveBlind().getBlindReward())),
            model.getActiveBlind().blindRewardProperty()
            ));
        rewardHandCount.textProperty().bind(model.handsProperty().asString());
        rewardHandReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(model.getHands()),
                model.handsProperty()
        ));
        rewardSatelliteReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Planet.getUniquePlanetsPlayedCount())
        ));

    }

    public void cashOut(ActionEvent actionEvent) {
        int reward= 0;
        model.addMoney(reward);
    }

}
