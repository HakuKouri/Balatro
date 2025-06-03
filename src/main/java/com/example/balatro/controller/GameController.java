package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class GameController
{
    //region FXML
    @FXML
    private AnchorPane playedCards_AnchorPane;
    @FXML
    private AnchorPane deckCover_AnchorPane;
    @FXML
    private ImageView shopImageView;

    //region Phase Display
    @FXML
    private AnchorPane chooseBlind_AnchorPane;
    @FXML
    private AnchorPane shopSign_AnchorPane;
    @FXML
    private AnchorPane pickedBlind_AnchorPane;
    //endregion

    @FXML
    private AnchorPane roundScore_AnchorPane;
    @FXML
    private AnchorPane handInfo_AnchorPane;
    @FXML
    private AnchorPane runInfo_AnchorPane;
    @FXML
    private AnchorPane holdingHand_AnchorPane;
    @FXML
    private StackPane playedCards_StackPane;
    @FXML
    private AnchorPane gameScreenAnchor;
    @FXML
    private HBox jokerConsumeHBox;
    @FXML
    private Label cardsInDeckLabel;
    @FXML
    private Label blindToBeat_Label;
    @FXML
    private ImageView stakeImageView;
    @FXML
    private Label pointsScoredLabel;
    @FXML
    private VBox spaceTag;
    @FXML
    private StackPane spaceJoker;
    @FXML
    private Label jokerCountLabel;
    @FXML
    private ImageView deckCover_ImageView;
    @FXML
    private StackPane spaceConsumable;
    @FXML
    private Label consumableCountLabel;

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
    private Label toBeatEffect;
    @FXML
    private GridPane toBeat;
    @FXML
    private ImageView toBeatImage;
    @FXML
    private ImageView toBeatStake;
    @FXML
    private Label toBeatScore;
    @FXML
    private Label toBeatReward;
    //endregion

    //region Placeholder
    @FXML
    private AnchorPane blindBox_AnchorPane;
    @FXML
    private AnchorPane placeHolderShop;
    @FXML
    private AnchorPane placeHolderReward;
    //endregion

    //TEST
    @FXML
    private ImageView testImageView;
    @FXML
    private Button testButton;

    //endregion

    //region FXMLLOADER
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("/com/example/balatro/shop-part.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-summary.fxml"));
    private final FXMLLoader loaderHoldingHand = new FXMLLoader(getClass().getResource("/com/example/balatro/holdingHand.fxml"));
    private final FXMLLoader loaderPlayedCards = new FXMLLoader(getClass().getResource("/com/example/balatro/playedCards_StackPane.fxml"));
    private final FXMLLoader loaderBlindBox = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-box.fxml"));
    //endregion

    //region CONTROLLER
    private ShopPartController shopController;
    private RewardSummaryController rewardSummarController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;
    private BlindBoxController blindBoxController;
    private List<BlindBoxPanelController> blindPanelControllerList;
    //endregion

    //region
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
    private static final GameModel gameModel = Balatro.getGameModel();
    //endregion

    //UI HANDLER
    public void initialize(){
        instance = this;

        double height = Balatro.getSettings().getWindowHeight();
        double width = Balatro.getSettings().getWindowWidth();

        Booster.setImageHeightProperty(height * .26);
        Booster.setImageWidthProperty(width * .09);

        gameScreenAnchor.setMaxWidth(width);
        gameScreenAnchor.setMaxHeight(height);

        gameModel.getRunBlinds().addAll(gameModel.getAllBlindsList());

        //LOAD / READY PLACEHOLDER
        try {
            //region Blind Box
            AnchorPane blindBox = loaderBlindBox.load();
            blindBoxController = loaderBlindBox.getController();

            blindBox_AnchorPane.getChildren().add(blindBox);
            blindPanelControllerList = blindBoxController.setBlindPanels();
            configurePlaceHolder(blindBox_AnchorPane);
            //endregion

            //region Place Holder
            AnchorPane holdingHand = loaderHoldingHand.load();
            holdingHandController = loaderHoldingHand.getController();
            holdingHand_AnchorPane.getChildren().add(holdingHand);

            AnchorPane playedCards = loaderPlayedCards.load();
            playedCardsController = loaderPlayedCards.getController();
            playedCards_StackPane.getChildren().add(playedCards);


            shop = loaderShop.load();
            shopController = loaderShop.getController();
            placeHolderShop.getChildren().add(shop);
            configurePlaceHolder(placeHolderShop);

            reward = loaderReward.load();
            rewardSummarController = loaderReward.getController();
            placeHolderReward.getChildren().add(reward);
            configurePlaceHolder(placeHolderReward);
            //endregion

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        //region Bind Blind Box
        gameModel.blindsVisibilityProperty().addListener((obs, oldValue, newValue) -> {
            animateBox(blindBox_AnchorPane, newValue);
        });
        chooseBlind_AnchorPane.visibleProperty().bind(gameModel.blindsVisibilityProperty());
        //endregion

        //region Bind Shop
        gameModel.shopVisibilityProperty().addListener((obs, oldValue, newValue) -> {
            animateBox(placeHolderShop, newValue);
        });
        shopImageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            return width * 0.186;
        }));
        shopSign_AnchorPane.visibleProperty().bind(gameModel.shopVisibilityProperty());
        //endregion

        //region Bind Reward
        gameModel.rewardVisibilityProperty().addListener((obs, oldValue, newValue) -> {
            animateBox(placeHolderReward, newValue);
        });
        //endregion

        //region Deck CoverBind
        deckCover_ImageView.imageProperty().bind(gameModel.getChosenDeck().imageProperty());
        //endregion

        //region Points Scored Bind
        stakeImageView.imageProperty().bind(gameModel.getChosenStake().imageProperty());

        pointsScoredLabel.textProperty().bind(
                Bindings.createStringBinding( () -> gameModel.getScoredPoints().toString(),
                gameModel.scoredPointsProperty()));
        //endregion

        //region Hand Info Bind
        infoHandName.textProperty().bind(gameModel.getBestHand().nameProperty());
        infoHandLevel.textProperty().bind(
                Bindings.when(gameModel.getBestHand().levelProperty().greaterThan(0))
                        .then(Bindings.concat("lv. ", gameModel.getBestHand().levelProperty().asString()))
                        .otherwise("lv."));
        infoHandChips.textProperty().bind(Bindings.convert(gameModel.getBestHand().chipsProperty()));
        infoHandMulti.textProperty().bind(Bindings.convert(gameModel.getBestHand().multiProperty()));
        //endregion

        //region Joker Space Bind
        gameModel.getActiveJokerObList().addListener((ListChangeListener<? super Joker>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    spaceJoker.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    spaceJoker.getChildren().removeAll(change.getRemoved());
                }
            }
            moveCards();
        });
        //endregion

        //region Run Info Binds
        handsLabel.textProperty().bind(Bindings.createStringBinding(() ->
                String.valueOf(gameModel.getHands()), gameModel.handsProperty()));
        discardsLabel.textProperty().bind(Bindings.createStringBinding(() ->
                String.valueOf(gameModel.getDiscards()), gameModel.discardsProperty()));
        moneyLabel.textProperty().bind(Bindings.createStringBinding(() ->
                "$" + gameModel.getMoney(), gameModel.moneyProperty()));
        anteLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getAnte() + "/8", gameModel.anteProperty()));
        roundLabel.textProperty().bind(Bindings.createStringBinding(() ->
                String.valueOf(gameModel.getRound()), gameModel.roundProperty()));
        //endregion

        /*bottomRow.prefHeightProperty().bind(Bindings.createIntegerBinding(() ->
                gameModel.isHandButtonVisibility() ? 350 : 220,
                gameModel.handButtonVisibilityProperty()));*/

        //region to Beat Bind
        pickedBlind_AnchorPane.visibleProperty().bind(gameModel.pickedBlindVisibilityProperty());
        pickedBlind_AnchorPane.setMaxHeight(height * 0.26);

        toBeatEffect.textProperty().bind(Bindings.createStringBinding(() -> {
            return gameModel.activeBlindProperty().get().getBlindId() < 2 ? "" : gameModel.activeBlindProperty().get().getBlindDescription();
        }));
        blindToBeat_Label.textProperty().bind(gameModel.activeBlindProperty().get().blindNameProperty());

        toBeatScore.textProperty().bind(Bindings.createStringBinding(() ->
                        String.valueOf(gameModel.getScoreToReach()),
                gameModel.scoreToReachProperty()
        ));

        toBeatImage.imageProperty().bind(gameModel.activeBlindProperty().get().imageProperty());

        toBeatStake.imageProperty().bind(gameModel.getChosenStake().imageProperty());

        toBeatReward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, gameModel.getActiveBlind().getBlindReward())),
                gameModel.getActiveBlind().blindRewardProperty()
        ));
        //endregion

        //region Card count Labels Bind
        cardsInDeckLabel.textProperty().bind(Bindings.createStringBinding(() ->
            gameModel.getDeckToPlay().size() + "/" + gameModel.getDeckFull().size(), gameModel.getDeckToPlay()
        ));
        jokerCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getActiveJokerObList().size() + "/" + gameModel.getMaxJokers(), gameModel.getActiveJokerObList()
        ));
        consumableCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getConsumableList().size() + "/" + gameModel.getMaxConsumables(), gameModel.getConsumableList()
        ));
        //endregion

        //TEST BUTTON
        testButton.setOnAction(event -> {
            System.out.println("Card Height: " + Balatro.getSettings().getCardHeight());
            gameModel.getActiveJokerObList().add(gameModel.getAllJokerList().get(0));
            gameModel.getActiveJokerObList().add(gameModel.getAllJokerList().get(3));
            gameModel.getActiveJokerObList().add(gameModel.getAllJokerList().get(4));
            gameModel.getActiveJokerObList().add(gameModel.getAllJokerList().get(5));

            for (Joker joker : gameModel.getActiveJokerObList()) {
                joker.setFitHeight(Balatro.getSettings().getCardHeight());
            }
        });
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

    private void createTagList() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 3; j++) {
                gameModel.getRunTags().add(gameModel.getAllTagList().get(gameModel.getRand().nextInt(gameModel.getAllTagList().size())));
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

            playedCardsController.addSelectedCards( () -> {
                Platform.runLater(() -> {
                    gameModel.handButtonVisibilityProperty().set(true);
                    holdingHandController.moveCards();
                    gameModel.clearSelectedCards();
                    playedCardsController.removeAllCards();
                });
            });
        }
    }

    //GAME HANDLER
    public void startNewGame(GameSetup gameSetup) {
        gameModel.setRand(new Random());
        createBlindList();
        createTagList();

        gameModel.getChosenDeck().setDeck(gameSetup.getChosenDeck());
        gameModel.getChosenStake().setStake(gameSetup.getChosenStake());
        gameModel.setHands(4);
        gameModel.setDiscards(3);
        gameModel.setAnte(1);
        gameModel.setRound(0);
        gameModel.setMoney(4);

        Planet.resetUniquePlanets();

        setPlayingDeck();

        gameModel.setShopVisibility(false);
        gameModel.setRewardVisibility(false);

    }

    public void startRound(BigDecimal score) {
        gameModel.setScoreToReach(score);
        gameModel.blindsVisibilityProperty().set(false);
        holdingHandController.drawCardToLimit();
    }

    public void nextRound() {
        gameModel.setShopVisibility(false);
        gameModel.setBlindsVisibility(true);
    }

    public void skip(Tag tag) {
        tag.setFitHeight(50);
        gameModel.getTagQueue().add(tag);
        gameModel.setRound(gameModel.getRound() + 1);
    }

    public void addMoney(int reward) {
        gameModel.setMoney(gameModel.getMoney() + reward);

        gameModel.setShopVisibility(true);
    }

    public void restockShop() {
        shopController.restockShop();
    }

    //UI
    private void animateBox(Node node, boolean bool) {
        int up = Objects.equals(node.getId(), "blindBox") ? 50 : 0;
        TranslateTransition transition = new TranslateTransition(Duration.seconds(.2), node);

        transition.setToY(bool ? up : Balatro.getSettings().getWindowHeight());
        transition.setInterpolator(Interpolator.LINEAR);

        transition.play();
    }

    public void moveCards() {
        double cardWidth = 140;
        double lastPos = spaceJoker.getWidth();

        int cards = spaceJoker.getChildren().size();
        double pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                spaceJoker.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                spaceJoker.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardWidth/2 + i * cardWidth - cards/2*cardWidth + i * 5;
                } else {
                    pos = i * cardWidth - cards/2*cardWidth + i * 5;
                }
            }
            spaceJoker.getChildren().get(i).setTranslateX(pos);
        }
    }

    private void configurePlaceHolder(AnchorPane anchorPane) {
        anchorPane.setPrefWidth(Balatro.getSettings().getWindowWidth() * .53);
        anchorPane.setPrefHeight(Balatro.getSettings().getWindowHeight() * .72);
        anchorPane.setLayoutX(Balatro.getSettings().getWindowWidth() * .26);
        anchorPane.setLayoutY(Balatro.getSettings().getWindowHeight() * .3);
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
