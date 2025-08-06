package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.card.Integer;
import com.example.balatro.domain.card.Planet;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class RewardSummaryController {

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
    @FXML
    private Button cashOut_Button;
    //endregion

    //region Attributes
    private IntegerProperty reward = new SimpleIntegerProperty(0);
    private final GameModel gameModel = Balatro.getGameModel();
    //endregion

    //region Getter Setter
    public int getReward() {
        return reward.get();
    }

    public IntegerProperty rewardProperty() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward.set(reward);
    }
    //endregion

    public void initialize() {
        rewardProperty().addListener((observable, oldValue, newValue) -> {
            cashOut_Button.setText("Cash out: $" + newValue);
        });

        rewardBlindChip.imageProperty().bind(gameModel.getActiveBlind().imageProperty());
        rewardBlindStake.imageProperty().bind(gameModel.getRunState().getChosenStake().imageProperty());
        rewardBlindScore.textProperty().bind(gameModel.scoreToReachProperty().asString());
        rewardBlindReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, gameModel.getActiveBlind().getBlindReward())),
            gameModel.getActiveBlind().blindRewardProperty()
            ));

        //TODO REWARD ROCKET BIND

        gameModel.rewardVisibilityProperty().addListener((observable, oldValue, newValue) -> {
            gameModel.pickedBlindVisibilityProperty().setValue(false);
            if(newValue) setRewards(gameModel.getActiveJokerList());
        });
    }

    //region Function
    private void addToReward(int value) {
        setReward(getReward() + value);
    }

    private void setRewards(List<Integer> jokers ) {
        //clear Rewards
        while(rewardVBox.getChildren().size() > 2) {
            rewardVBox.getChildren().remove(2);
        }

        //Blind Reward
        blindRewardPane.setVisible(gameModel.activeBlindProperty().get().isRewarded());
        if(gameModel.activeBlindProperty().get().isRewarded()) {
            addToReward(gameModel.activeBlindProperty().get().getBlindReward());

        }

        //remaining Hands Reward
        if(gameModel.getCurrentRound().getHands() > 0) {
            rewardVBox.getChildren().add(createRewardPane(gameModel.getCurrentRound().getHands(), "Remaining Hands ($1 each)", gameModel.getCurrentRound().getHands(),true));
        }

        //Satellite Reward
        List<Integer> satelliteJokers = jokers.stream().filter(x -> Objects.equals(x.getCardName(), "Satellite")).toList();
        if(!satelliteJokers.isEmpty()) {
            for(Integer joker : satelliteJokers) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getCardDescription(), Planet.getUniquePlanetsPlayedCount(), false));
            }
        }

        //Rocker Reward
        List<Integer> rocketList = jokers.stream().filter(x -> Objects.equals(x.getCardName(), "Rocket")).toList();
        if(!rocketList.isEmpty()) {
            for(Integer joker : rocketList) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getCardDescription(), gameModel.getRocketJokers().get(joker).get(), false));
            }
        }

        //Delayed Gratification Reward
        List<Integer> delayedGrafList = jokers.stream().filter(x -> Objects.equals(x.getCardName(), "Delayed Gratification")).toList();
        if(!delayedGrafList.isEmpty() && gameModel.getCurrentRound().getDiscards() == gameModel.getRunState().getMaxDiscards()) {
            for(Integer joker : delayedGrafList) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getCardDescription(), gameModel.getCurrentRound().getDiscards(), false));
            }
        }

        //Cloud 9 Reward
        List<Integer> cloud9List = jokers.stream().filter(x -> Objects.equals(x.getCardName(), "Cloud 9")).toList();
        if(!cloud9List.isEmpty()) {
            for(Integer joker : cloud9List) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getCardDescription(), (int) gameModel.getRunState().getPlayingDeck().getFullDeck().stream().filter(x -> x.getValue() == 9).count(), false));
            }
        }

        //Golden Joker Reward
        List<Integer> goldenJokerList = jokers.stream().filter(x -> Objects.equals(x.getCardName(), "Golden Joker")).toList();
        if(!goldenJokerList.isEmpty()) {
            for(Integer joker : goldenJokerList) {
                rewardVBox.getChildren().add(createRewardPane(0, joker.getCardDescription(), 4, false));
            }
        }

        //Boss Beat Tag Reward
        List<Tag> tagList = gameModel.getTagQueue().stream().filter(x -> Objects.equals(x.getTagName(), "Investment")).toList();
        if(!tagList.isEmpty() && gameModel.getActiveBlind().getBlindId() > 1) {
            for(Tag tag : tagList) {
                rewardVBox.getChildren().add(createRewardPane(0, tag.getTagBenefit(), 25,true));
            }
        }

        //Interest Reward
        if(gameModel.getRunState().getMoney() >= 5) {
            int interestReward = Math.max(gameModel.getRunState().getMoney() / 5, gameModel.getRewardModel().getMaxInterest());
            rewardVBox.getChildren().add(createRewardPane(interestReward, "1 interest per $5 (" + gameModel.getRewardModel().getMaxInterest() + " max)", Math.min(gameModel.getRewardModel().getMaxInterest(), interestReward), false));
        }
    }

    public void cashOut(ActionEvent actionEvent) {
        gameModel.getActiveBlind().setBlind(new Blind());
        gameModel.getRunState().addMoney(getReward());
        gameModel.setRewardVisibility(false);
        gameModel.setShopVisibility(true);
        gameModel.setScoredPoints(BigDecimal.valueOf(0));

        setReward(0);
        GameController.getInstance().restockShop();
    }

    public AnchorPane createRewardPane(int count, String effect, int money, boolean tag) {
        addToReward(money);

        try {
            FXMLLoader rewardFxmlLoader = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-pane.fxml"));
            AnchorPane rewardPane = rewardFxmlLoader.load();
            RewardPaneController controller = rewardFxmlLoader.getController();
            controller.createPane(count, effect, money, tag);
            return rewardPane;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

}
