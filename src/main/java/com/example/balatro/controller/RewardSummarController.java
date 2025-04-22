package com.example.balatro.controller;

import com.example.balatro.classes.Planet;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.RewardModel;
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
    private Label rewardInterestCount;
    @FXML
    private Label rewardInterestReward;
    //endregion

    private final GameModel gameModel = GameController.getGameModel();
    private final RewardModel rewardModel = new RewardModel();

    public void initialize() {
        rewardBlindStake.imageProperty().bind(gameModel.getChosenStake().imageProperty());
        rewardBlindChip.imageProperty().bind(gameModel.getActiveBlind().imageProperty());
        rewardBlindScore.textProperty().bind(gameModel.scoreToReachProperty().asString());
        rewardBlindReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, gameModel.getActiveBlind().getBlindReward())),
            gameModel.getActiveBlind().blindRewardProperty()
            ));
        rewardHandCount.textProperty().bind(gameModel.handsProperty().asString());
        rewardHandReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(gameModel.getHands()),
                gameModel.handsProperty()
        ));
        rewardSatelliteReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Planet.getUniquePlanetsPlayedCount())
        ));
        //TODO REWARD ROCKET BIND

        rewardDelayedGratificationReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$$".repeat(gameModel.getDiscards())
        ));

        //Cloud9 Binding
        rewardModel.cloud9Property().bind(Bindings.createIntegerBinding(
                () -> (int) gameModel.getDeckFull()
                        .stream()
                        .filter(card -> card.getRank() == "9")
                        .count()
        ));
        rewardCloud9Reward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(rewardModel.getCloud9())
        ));

        //Interest Binding
        rewardModel.interestRewardProperty().bind(Bindings.createIntegerBinding(
                () -> gameModel.getMoney() / 5
        ));
        rewardInterestCount.textProperty().bind(rewardModel.interestRewardProperty().asString());
        rewardInterestReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(rewardModel.getInterestReward())
        ));
    }

    public void cashOut(ActionEvent actionEvent) {
        int reward= 0;
        gameModel.addMoney(reward);
        gameModel.setRewardVisibility(false);
        gameModel.setShopVisibility(true);
    }

}
