package com.example.balatro.controller;

import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private AnchorPane placeHolderShopReward;
    @FXML
    private HBox blindBox;
    //endregion

    //TEST
    @FXML
    private ImageView testImageView;

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

    //region PLACEHOLDER
    private AnchorPane smallBlind = null;
    private AnchorPane bigBlind = null;
    private AnchorPane boss = null;
    private AnchorPane shop = null;
    private AnchorPane reward = null;
    private AnchorPane holdingHand = null;
    private AnchorPane playedCards = null;
    //endregion


    //INSTANCE
    public static GameController instance;
    //MODELS
    GameModel gameModel = new GameModel();

    public static GameController getInstance() {
        return instance;
    }

    //UI HANDLER
    public void initialize(){
        System.out.println("INITIALIZE");
        instance = this;

        //LOAD / READY PLACEHOLDER
        try {
            holdingHand = loaderHoldingHand.load();
            holdingHandController = loaderHoldingHand.getController();
            holdingHand_AnchorPane.getChildren().add(holdingHand);

            playedCards = loaderPlayedCards.load();
            playedCardsController = loaderPlayedCards.getController();
            playedCards_StackPane.getChildren().add(playedCards);

            smallBlind = loaderSmall.load();
            smallController = loaderSmall.getController();
            smallController.setGameScreenController(this);
            smallBlindAnchor.getChildren().add(smallBlind);

            bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigController.setGameScreenController(this);
            bigBlindAnchor.getChildren().add(bigBlind);

            boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossController.setGameScreenController(this);
            bossBlindAnchor.getChildren().add(boss);

            blindBox.setTranslateY(600);

            shop = loaderShop.load();
            shopController = loaderShop.getController();
            shopController.setGameScreenController(this);

            reward = loaderReward.load();
            rewardSummarController = loaderReward.getController();
            rewardSummarController.setGameScreenController(this);

            placeHolderShopReward.setTranslateY(560);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        //region

        holdingHandController.hideHandButtons();
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

        //Bind Deck Cover
        imageViewDeckField.imageProperty().bind(gameModel.getChosenDeck().imageProperty());

        //Bind Points Scored
        stakeImageView.imageProperty().bind(gameModel.getChosenStake().imageProperty());

        pointsScoredLabel.textProperty().bind(Bindings.createObjectBinding(
                () -> gameModel.getScoredPoints().toString(), gameModel.pointsScoredProperty));

        //Bind HandInfo
        infoHandName.textProperty().bind(gameModel.getBestHand().nameProperty());
        infoHandLevel.textProperty().bind(
                Bindings.when(gameModel.getBestHand().levelProperty().greaterThan(0))
                        .then(Bindings.concat("lv. ", gameModel.getBestHand().levelProperty().asString()))
                        .otherwise("lv."));
        infoHandChips.textProperty().bind(Bindings.convert(gameModel.getBestHand().chipsProperty()));
        infoHandMulti.textProperty().bind(Bindings.convert(gameModel.getBestHand().multiProperty()));

        //Bind Points Reached
        gameModel.pointsReachedProperty().bind(Bindings.createBooleanBinding(
                () -> gameModel.getScoreToReach().compareTo(gameModel.getScoredPoints()) >= 0,
                gameModel.pointsScoredProperty, gameModel.pointsReachedProperty()
        ));



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

    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;

        if(hands.isEmpty()) gameModel.setBestHand(new Hand());

        for (Hand hand : gameModel.getAllHandList()) {
            if(hands.contains(hand.getName())) {
                System.out.println(hand.getName());
                int points = hand.getChips() * hand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    gameModel.setBestHand(hand);
                }
            }
        }
    }

    private void openShop() {
        if(placeHolderShopReward.getTranslateY() != 0) {
            placeHolderShopReward.setTranslateY(0);
        }
        placeHolderShopReward.getChildren().clear();
        placeHolderShopReward.getChildren().add(shop);
    }

    public void closeShop() {
        placeHolderShopReward.setTranslateY(560);
    }

    public void openSummary() {
        if(placeHolderShopReward.getTranslateY() != 0) {
            placeHolderShopReward.setTranslateY(0);
        }
        placeHolderShopReward.getChildren().clear();
        placeHolderShopReward.getChildren().add(reward);
    }

    private void closeSummary() {
        placeHolderShopReward.setTranslateY(560);
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
    public void drawCard() {
        gameModel.getDeckToPlay().get(0).setOnMouseClicked(mouseEvent -> playingCardClicked((PlayingCard) mouseEvent.getSource()));
        gameModel.getDeckToPlay().get(0).setClickAble(true);

        holdingHandController.addCardToHand(gameModel.getDeckToPlay().get(0));
        gameModel.getDeckToPlay().remove(0);
        holdingHandController.moveCards();
    }

    public void drawCards() {

    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {drawCard();}
        if(gameModel.isSortedByRank()) holdingHandController.sortRank();
        else holdingHandController.sortSuit();
    }

    private void playingCardClicked(PlayingCard card) {
        if(!card.isClickAble()) return;

        if(card.getTranslateY() == 0 && gameModel.getSelectedCardCounter() < 5) {
            gameModel.addCardToSelectedCards(card);
            card.setTranslateY(-20);
            gameModel.incrementSelectedCardCounter();
        }
        else if(card.getTranslateY() == -20) {
            gameModel.removeCardFromSelectedCards(card);
            card.setTranslateY(0);
            gameModel.decrementSelectedCardCounter();
        }

        //gameModel.bestHandProperty()
        setHandInfo(checkHand.evaluateHands(gameModel.getSelectedCards()));
    }

    public void playCards(List<PlayingCard> playedCards) {
        playedCardsController.addAllCards(playedCards);
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        if(holdingHandController.getSelectedCardCounter() != 0) {
            holdingHandController.hideHandButtons();
            for(PlayingCard card : holdingHandController.getSelectedCards()) {
                card.setTranslateX(0);
                card.setClickAble(false);
            }

            playedCardsController.addAllCards(holdingHandController.getSelectedCards());

            List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(holdingHandController.getSelectedCards() , gameModel.getHandName());
            for(int i = 0; i < playedCardsController.count(); i++) {

                if(countedCards.contains(playedCardsController.getPlayedCards_StackPane().get(i))) {
                    playedCardsController.getPlayedCards_StackPane().get(i).setTranslateY(-20);
                } else
                    playedCardsController.getPlayedCards_StackPane().get(i).setTranslateY(0);
            }

            gameModel.clearSelectedCards();

            delay(2000,() -> {playedCardsController.removeAllCards(); drawCards(8 - holdingHandController.getHoldingHandSize());});

            holdingHandController.moveCards();
            holdingHandController.setSelectedCardCounter(0);
        }
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        if(holdingHandController.getSelectedCardCounter() != 0) {
            for(int i = 0; i < holdingHandController.getSelectedCardCounter(); i++) {
                holdingHandController.removeCardFromHoldingHand(holdingHandController.getSelectedCards().get(i));
                holdingHandController.removeCardFromHand(holdingHandController.getSelectedCards().get(i));
            }
        }
        holdingHandController.setSelectedCardCounter(0);
        holdingHandController.clearSelectedCards();

        drawCards(8 - holdingHandController.getHandCards().size());
        setHandInfo(new ArrayList<>());
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

        setPlayingDeck();
        createBlindList();
        setBlindPanels();
    }

    public void startRound(Blind blind, BigDecimal score) {
        holdingHandController.hideHandButtons();
        gameModel.setScoreToReach(score);
        toggleBlind();
        labelBlind.setText(blind.getBlindName());
        drawCards(gameModel.handSizeProperty().get() - gameModel.getHandCards().size());
        toBeatScore.setText(String.valueOf(gameModel.getScoreToReach()));
        toBeatImage.setImage(new Image("file:"+blind.getBlindImageUrl()));
        toBeatStake.setImage(new Image("file:"+ gameModel.getChosenStake().getStakeImageChipUrl()));
        toBeatReward.setText("$$$");
    }

    public void nextRound() {
        closeShop();
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

        openShop();
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
