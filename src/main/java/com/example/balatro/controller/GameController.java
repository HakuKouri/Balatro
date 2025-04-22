package com.example.balatro.controller;

import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class GameController
{

    //region FXML IDs
    @FXML
    private AnchorPane holdingHand_AnchorPane;
    @FXML
    private StackPane playedCards_StackPane;
    @FXML
    private AnchorPane gameScreenAnchor;


    @FXML
    private Label toBeatEffect;
    @FXML
    private GridPane toBeat;
    @FXML
    private AnchorPane smallBlindAnchor;
    @FXML
    private AnchorPane bigBlindAnchor;
    @FXML
    private AnchorPane bossBlindAnchor;
    @FXML
    private Label labelBlind;

    //region Score Display
    @FXML
    private ImageView stakeImageView;
    @FXML
    private Label pointsScoredLabel;
    //endregion

    //region Handinfo
    @FXML
    private Label infoHandName;
    @FXML
    private Label infoHandLevel;
    @FXML
    private Label infoHandChips;
    @FXML
    private Label infoHandMulti;
    //endregion

    //region Run Info
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
    //endregion

    //region to beat elements
    @FXML
    private ImageView toBeatImage;
    @FXML
    private ImageView toBeatStake;
    @FXML
    private Label toBeatScore;
    @FXML
    private Label toBeatReward;
    @FXML
    private ImageView imageViewDeckField;
    @FXML
    private VBox spaceTag;
    @FXML
    private StackPane spaceJoker;
    @FXML
    private StackPane spaceConsumable;
    @FXML
    private AnchorPane placeHolderShop;
    @FXML
    private AnchorPane placeHolderReward;
    @FXML
    private HBox blindBox;
    //endregion

    //TEST
    @FXML
    private ImageView testImageView;

    @FXML
    private Button testButton;

    //region FXMLLOADER
    private final FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
    private final FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
    private final FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("/com/example/balatro/shop-part.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-summary.fxml"));
    private final FXMLLoader loaderHoldingHand = new FXMLLoader(getClass().getResource("/com/example/balatro/holdingHand_StackPane.fxml"));
    private final FXMLLoader loaderPlayedCards = new FXMLLoader(getClass().getResource("/com/example/balatro/playedCards_StackPane.fxml"));
    //endregion

    //region CONTROLLER
    private BlindPickPanelsController smallController;
    private BlindPickPanelsController bigController;
    private BlindPickPanelsController bossController;
    private ShopPartController shopController;
    private RewardSummarController rewardSummarController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;
    //endregion

    private AnchorPane shop = null;
    private AnchorPane reward = null;
    //endregion


    //region INSTANCE
    private static GameController instance;

    public static GameController getInstance() {
        return instance;
    }
    //endregion

    //region GAMEMODEL
    private static final GameModel gameModel = new GameModel();

    public static GameModel getGameModel() {
        return gameModel;
    }
    //endregion

    //UI HANDLER
    public void initialize(){
        instance = this;

        //LOAD / READY PLACEHOLDER
        try {
            AnchorPane holdingHand = loaderHoldingHand.load();
            holdingHandController = loaderHoldingHand.getController();
            holdingHand_AnchorPane.getChildren().add(holdingHand);

            AnchorPane playedCards = loaderPlayedCards.load();
            playedCardsController = loaderPlayedCards.getController();
            playedCards_StackPane.getChildren().add(playedCards);

            //region PLACEHOLDER
            AnchorPane smallBlind = loaderSmall.load();
            smallController = loaderSmall.getController();
            smallBlindAnchor.getChildren().add(smallBlind);

            AnchorPane bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigBlindAnchor.getChildren().add(bigBlind);

            AnchorPane boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossBlindAnchor.getChildren().add(boss);

            blindBox.setTranslateY(600);

            shop = loaderShop.load();
            shopController = loaderShop.getController();
            placeHolderShop.getChildren().add(shop);

            reward = loaderReward.load();
            rewardSummarController = loaderReward.getController();
            placeHolderReward.getChildren().add(reward);

            placeHolderShop.setTranslateY(560);
            placeHolderReward.setTranslateY(560);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        toggleBlind();

        gameModel.getTagQueue().addListener((ListChangeListener<Tag>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    spaceTag.getChildren().addAll(change.getAddedSubList());
                }
                if(change.next()) {
                    spaceTag.getChildren().addAll(change.getAddedSubList());
                }
            }
        });

        //Bind Shop
        placeHolderShop.translateYProperty().bind(Bindings.createIntegerBinding(
                () -> gameModel.isShopVisibility() ? 0 : 560,
                gameModel.shopVisibilityProperty()
        ));

        //Bind Reward
        placeHolderReward.translateYProperty().bind(Bindings.createIntegerBinding(
                () -> gameModel.isRewardVisibility() ? 0 : 560,
                gameModel.rewardVisibilityProperty()
        ));

        //Bind Deck Cover
        imageViewDeckField.imageProperty().bind(gameModel.getChosenDeck().imageProperty());

        //Bind Points Scored
        stakeImageView.imageProperty().bind(gameModel.getChosenStake().imageProperty());

        pointsScoredLabel.textProperty().bind(
                Bindings.createStringBinding( () -> gameModel.getScoredPoints().toString(),
                gameModel.scoredPointsProperty()));

        //Bind HandInfo
        infoHandName.textProperty().bind(gameModel.getBestHand().nameProperty());
        infoHandLevel.textProperty().bind(
                Bindings.when(gameModel.getBestHand().levelProperty().greaterThan(0))
                        .then(Bindings.concat("lv. ", gameModel.getBestHand().levelProperty().asString()))
                        .otherwise("lv."));
        infoHandChips.textProperty().bind(Bindings.convert(gameModel.getBestHand().chipsProperty()));
        infoHandMulti.textProperty().bind(Bindings.convert(gameModel.getBestHand().multiProperty()));

        //Joker Space Bind
        gameModel.getActiveJokerObList().addListener((ListChangeListener<? super Joker>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    spaceJoker.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    spaceJoker.getChildren().removeAll(change.getRemoved());
                }
            }
        });

        testButton.setOnAction(event -> {
            gameModel.setScoredPoints(gameModel.getScoredPoints().add(BigDecimal.valueOf(10)));
            System.out.println(gameModel.getScoredPoints().toString());
        });
    }

    private void setBlindPanels() {
        int ante = gameModel.getAnte();
        int round = gameModel.getRound();
        smallController.setBlind(gameModel.getRunBlinds().get((ante-1)*3), gameModel.getAllTagList().get(round-ante), 1);
        bigController.setBlind(gameModel.getRunBlinds().get((ante-1)*3+1), gameModel.getAllTagList().get(round-ante), 2);
        bossController.setBlind(gameModel.getRunBlinds().get((ante-1)*3+2), gameModel.getAllTagList().get(round-ante), 3);

        smallController.setMinScore(gameModel.getChipRequirement()[ante].multiply(new BigDecimal(gameModel.getAllBlindsList().get((ante-1)* 3).getBlindScoreMultiplier().replace("x base",""))));
        bigController.setMinScore(gameModel.getChipRequirement()[ante].multiply(new BigDecimal(gameModel.getAllBlindsList().get((ante-1)* 3 +1).getBlindScoreMultiplier().replace("x base",""))));
        bossController.setMinScore(gameModel.getChipRequirement()[ante].multiply(new BigDecimal(gameModel.getAllBlindsList().get((ante-1)* 3+2).getBlindScoreMultiplier().replace("x base",""))));
    }

    public void toggleBlind() {
        int round = gameModel.getRound();
        gameModel.toggleBlindVisibity();
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
        TranslateTransition transitionSmall = new TranslateTransition(Duration.seconds(.5), smallBlindAnchor);
        TranslateTransition transitionBig = new TranslateTransition(Duration.seconds(.5), bigBlindAnchor);
        TranslateTransition transitionBoss = new TranslateTransition(Duration.seconds(.5), bossBlindAnchor);
        if (gameModel.getBlindsVisibility()) {
            transitionBlindBox.setToY(0);
            if(round%3 == 1) transitionSmall.setToY(-50);
            if(round%3 == 2) transitionBig.setToY(-50);
            if(round%3 == 0) transitionBoss.setToY(-50);
        } else {
            transitionBlindBox.setToY(600);
            transitionSmall.setToY(0);
            transitionBig.setToY(0);
            transitionBoss.setToY(0);
        }
        transitionBlindBox.play();
        transitionSmall.play();
        transitionBig.play();
        transitionBoss.play();
    }

    //SETTING UP GAME
    private void setPlayingDeck() {
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                gameModel.getDeckFull().add(new PlayingCard(j,i));
            }
        }
        gameModel.getDeckToPlay().addAll(gameModel.getDeckFull());
        Collections.shuffle(gameModel.getDeckToPlay(), new Random());
    }

    public void createBlindList() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 0)
                    gameModel.getRunBlinds().add(gameModel.getAllBlindsList().get(0));
                else if(j == 1)
                    gameModel.getRunBlinds().add(gameModel.getAllBlindsList().get(1));
                else
                    gameModel.getRunBlinds().add(gameModel.getAllBlindsList().get(gameModel.getRand().nextInt(gameModel.getAllBlindsList().size() - 2 + 1) + 1));
            }
        }
    }

    //PLAYING CARD HANDLER
    public void playSelectedCards() {
        List<PlayingCard> selectedCards = gameModel.getSelectedCards();
        if(!selectedCards.isEmpty()) {
            for(PlayingCard card : selectedCards) {
                card.setTranslateX(0);
                card.setClickAble(false);
            }

            playedCardsController.addSelectedCards();

            gameModel.clearSelectedCards();

            delay(2000,() -> {playedCardsController.removeAllCards();});

            gameModel.handButtonVisibilityProperty().set(true);
            holdingHandController.moveCards();
        }

    }

    //GAME HANDLER
    public void startNewGame(GameSetup gameSetup) {
        gameModel.setRand(new Random());
        gameModel.setChosenDeck(gameSetup.getChosenDeck());
        gameModel.setChosenStake(gameSetup.getChosenStake());
        gameModel.setHands(4);
        gameModel.setDiscards(3);
        gameModel.setAnte(1);
        gameModel.setRound(1);
        gameModel.setMoney(0);

        gameModel.setRand(new Random());

        Planet.resetUniquePlanets();

        setPlayingDeck();
        createBlindList();
        setBlindPanels();
    }

    public void startRound(Blind blind, BigDecimal score) {
        gameModel.setScoreToReach(score);
        toggleBlind();
        labelBlind.setText(blind.getBlindName());
        holdingHandController.drawCards();
        toBeatScore.setText(String.valueOf(gameModel.getScoreToReach()));
        toBeatImage.setImage(new Image("file:"+blind.getBlindImageUrl()));
        toBeatStake.setImage(new Image("file:"+ gameModel.getChosenStake().getStakeImageChipUrl()));
        toBeatReward.setText("$$$");
    }

    public void nextRound() {
        //closeShop();
        gameModel.setShopVisibility(false);
        gameModel.setRound(gameModel.getRound() + 1);
        toggleBlind();
    }

    public void skip(Tag tag) {
        System.out.println(tag.getTagImageUrl());
        gameModel.addTagToTagQueue(tag);
        System.out.println(tag);
        System.out.println(spaceTag.getChildren().stream().findFirst());
        gameModel.setRound(gameModel.getRound() + 1);
    }

    public void addMoney(int reward) {
        gameModel.setMoney(gameModel.getMoney() + reward);

        gameModel.setShopVisibility(true);
    }

    //BACKGROUND HANDLER
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException ignored) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

}
