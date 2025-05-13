package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Joker;
import com.example.balatro.classes.Planet;
import com.example.balatro.classes.Tag;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.RewardModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RewardSummarController {

    //region FXML
    @FXML
    private GridPane blindRewardPane;
    @FXML
    private VBox rewardVBox;

    @FXML
    private ImageView rewardBlindChip;
    @FXML
    private ImageView rewardBlindStake;
    @FXML
    private Label rewardBlindScore;
    @FXML
    private Label rewardBlindReward;

    private int reward = 0;
    //endregion

    private final GameModel gameModel = Balatro.getGameModel();
    private final RewardModel rewardModel = new RewardModel();

    private FXMLLoader rewardFxmlLoader = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-pane.fxml"));
    private GridPane rewardGridPane;

    public void initialize() {
        try {
            rewardGridPane = rewardFxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rewardBlindChip.imageProperty().bind(gameModel.getActiveBlind().imageProperty());
        rewardBlindStake.imageProperty().bind(gameModel.getChosenStake().imageProperty());
        rewardBlindScore.textProperty().bind(gameModel.scoreToReachProperty().asString());
        rewardBlindReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, gameModel.getActiveBlind().getBlindReward())),
            gameModel.getActiveBlind().blindRewardProperty()
            ));

        //TODO REWARD ROCKET BIND

        gameModel.rewardVisibilityProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) setRewards(gameModel.getActiveJokerObList());
        });
    }

    public void setRewards(List<Joker> jokers ) {
        //clear Rewards
        while(rewardVBox.getChildren().size() > 2) {
            rewardVBox.getChildren().remove(2);
        }

        //Blind Reward
        blindRewardPane.setVisible(gameModel.activeBlindProperty().get().isRewarded());

        //remaining Hands Reward
        if(gameModel.getHands() > 0) {
            rewardVBox.getChildren().add(createRewardPane(gameModel.getHands(), "Remaining Hands ($1 each)", gameModel.getHands(),true));
        }

        //Satellite Reward
        List<Joker> satelliteJokers = jokers.stream().filter(x -> Objects.equals(x.getName(), "Satellite")).collect(Collectors.toList());
        if(!satelliteJokers.isEmpty()) {
            for(Joker joker : satelliteJokers) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getJokerEffect(), Planet.getUniquePlanetsPlayedCount(), false));
            }
        }

        //Rocker Reward
        List<Joker> rocketList = jokers.stream().filter(x -> Objects.equals(x.getName(), "Rocket")).collect(Collectors.toList());
        if(!rocketList.isEmpty()) {
            for(Joker joker : rocketList) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getJokerEffect(), gameModel.getRocketJokers().get(joker).get(), false));
            }
        }

        //Delayed Gratification Reward
        List<Joker> delayedGrafList = jokers.stream().filter(x -> Objects.equals(x.getName(), "Delayed Gratification")).collect(Collectors.toList());
        if(!delayedGrafList.isEmpty() && gameModel.getDiscards() == gameModel.getMaxDiscards()) {
            for(Joker joker : delayedGrafList) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getJokerEffect(), gameModel.getDiscards(), false));
            }
        }

        //Cloud 9 Reward
        List<Joker> cloud9List = jokers.stream().filter(x -> Objects.equals(x.getName(), "Cloud 9")).collect(Collectors.toList());
        if(!cloud9List.isEmpty()) {
            for(Joker joker : cloud9List) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getJokerEffect(), (int) gameModel.getDeckFull().stream().filter(x -> x.getValue() == 9).count(), false));
            }
        }

        //Golden Joker Reward
        List<Joker> goldenJokerList = jokers.stream().filter(x -> Objects.equals(x.getName(), "Golden Joker")).collect(Collectors.toList());
        if(!goldenJokerList.isEmpty()) {
            for(Joker joker : goldenJokerList) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getJokerEffect(), 4, false));
            }
        }

        //Boss Beat Tag Reward
        List<Tag> tagList = gameModel.getTagQueue().stream().filter(x -> Objects.equals(x.getTagName(), "Investment")).collect(Collectors.toList());
        if(!tagList.isEmpty() && gameModel.getActiveBlind().getBlindId() > 1) {
            for(Tag tag : tagList) {
                rewardVBox.getChildren().add(createRewardPane(0, tag.getTagBenefit(), 25,true));
            }
        }

        //Interest Reward
        if(gameModel.getMoney() >= 5) {
            int interestReward = gameModel.getMoney() / 5;
            rewardVBox.getChildren().add(createRewardPane(interestReward, "1 interest per $5 (" + gameModel.getMaxInterest() + " max)", Math.min(gameModel.getMaxInterest(), interestReward), false));
        }
    }

    public void cashOut(ActionEvent actionEvent) {
        int reward= 0;
        gameModel.addMoney(reward);
        gameModel.setRewardVisibility(false);
        gameModel.setShopVisibility(true);
    }

    public GridPane createRewardPane(int count, String effect, int money, boolean tag) {
        reward += money;
        RewardPaneController controller = rewardFxmlLoader.getController();
        controller.setValues(count, effect, money, tag);

        return rewardGridPane;
    }
}
