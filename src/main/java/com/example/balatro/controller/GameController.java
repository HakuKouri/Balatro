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
    public AnchorPane shop_Include;
    public AnchorPane holdingHand_Include;
    public AnchorPane reward_Include;
    @FXML
    private AnchorPane playedCards_AnchorPane;
    @FXML
    private AnchorPane deckCover_AnchorPane;
    @FXML
    private ImageView shopImageView;
    @FXML
    private AnchorPane chooseBlind_AnchorPane;
    @FXML
    private AnchorPane shopSign_AnchorPane;
    @FXML
    private AnchorPane pickedBlind_AnchorPane;
    @FXML
    private AnchorPane roundScore_AnchorPane;
    @FXML
    private AnchorPane handInfo_AnchorPane;
    @FXML
    private AnchorPane runInfo_AnchorPane;
    //region FXML IDs
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
    private Label toBeatEffect;
    @FXML
    private GridPane toBeat;
    @FXML
    private Label blindToBeat_Label;
    @FXML
    private AnchorPane blindBox;
    @FXML
    private HBox blindBoxHBox;

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
    private ImageView deckCover_ImageView;
    @FXML
    private VBox spaceTag;
    @FXML
    private StackPane spaceJoker;
    @FXML
    private Label jokerCountLabel;

    @FXML
    private StackPane spaceConsumable;
    @FXML
    private Label consumableCountLabel;

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

    //region FXMLLOADER
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("/com/example/balatro/shop-part.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-summary.fxml"));
    private final FXMLLoader loaderHoldingHand = new FXMLLoader(getClass().getResource("/com/example/balatro/holdingHand_StackPane.fxml"));
    private final FXMLLoader loaderPlayedCards = new FXMLLoader(getClass().getResource("/com/example/balatro/playedCards_StackPane.fxml"));
    private final FXMLLoader loaderBlindBox = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-box.fxml"));

    private final FXMLLoader loaderSmallBlind = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-Box-Panel.fxml"));
    private final FXMLLoader loaderBigBlind = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-Box-Panel.fxml"));
    private final FXMLLoader loaderBossBlind = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-Box-Panel.fxml"));
    //endregion

    //region CONTROLLER
    private ShopPartController shopController;
    private RewardSummarController rewardSummarController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;
    private BlindBoxController blindBoxController;
    private BlindBoxPanelController smallBlindController;
    private BlindBoxPanelController bigBlindController;
    private BlindBoxPanelController bossBlindController;
    //endregion

    private AnchorPane shop = null;
    private AnchorPane reward = null;

    private AnchorPane smallBlindPanel;
    private AnchorPane bigBlindPanel;
    private AnchorPane bossBlindPanel;
    //endregion

    //region INSTANCE
    private static GameController instance;

    public static GameController getInstance() {
        System.out.println("GameController Get Instance");
        return instance;
    }
    //endregion

    //region GAMEMODEL
    private static final GameModel gameModel = Balatro.getGameModel();
    //endregion

    //UI HANDLER
    public void initialize(){
        instance = this;

        gameScreenAnchor.widthProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New GameScreen Anchor Width: " + newValue.doubleValue());
        });
        gameScreenAnchor.setMaxWidth(Balatro.getSettings().getWindowWidth());
        gameScreenAnchor.setMaxHeight(Balatro.getSettings().getWindowHeight());
        System.out.println("Height: " + Balatro.getSettings().getWindowHeight());
        gameScreenAnchor.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("New GameScreen Anchor Height: " + newValue.doubleValue());
        });
        gameModel.getRunBlinds().addAll(gameModel.getAllBlindsList());

        //LOAD / READY PLACEHOLDER
        try {
            //region Blind Box
            smallBlindPanel = loaderSmallBlind.load();
            smallBlindController = loaderSmallBlind.getController();

            bigBlindPanel = loaderBigBlind.load();
            bigBlindController = loaderBigBlind.getController();

            bossBlindPanel = loaderBossBlind.load();
            bossBlindController = loaderBossBlind.getController();

            gameModel.anteProperty().addListener((obs, oldAnte, newAnte) -> {
                System.out.println("ANTE CHANGED");
                smallBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getAnte() - 1) * 3));
                smallBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(smallBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));

                smallBlindController.setTag(gameModel.getRunTags().isEmpty() ? new Tag() : gameModel.getRunTags().get((gameModel.getAnte() -1 ) * 2));
                bigBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getAnte() - 1) * 3 + 1));
                bigBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bigBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));

                bigBlindController.setTag(gameModel.getRunTags().isEmpty() ? new Tag() : gameModel.getRunTags().get((gameModel.getAnte() -1 ) * 2 + 1));
                bossBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getAnte() - 1) * 3 + 2));
                bossBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bossBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));

            });

            smallBlindController.setBossPanel(false);
            bigBlindController.setBossPanel(false);
            bossBlindController.setBossPanel(true);

            blindBoxHBox.getChildren().add(smallBlindPanel);
            blindBoxHBox.getChildren().add(bigBlindPanel);
            blindBoxHBox.getChildren().add(bossBlindPanel);

            gameModel.roundProperty().addListener((obs, oldValue, newValue) -> {
                smallBlindPanel.setDisable(newValue.intValue()%3 != 0);
                bigBlindPanel.setDisable(newValue.intValue()%3 != 1 && newValue.intValue() != 0);
                bossBlindPanel.setDisable(newValue.intValue()%3 != 2 && newValue.intValue() != 0);
            });

            //endregion

            //region Place Holder
            AnchorPane holdingHand = loaderHoldingHand.load();
            holdingHand.maxWidthProperty().bind(holdingHand_AnchorPane.widthProperty());
            holdingHand.maxHeightProperty().bind(holdingHand_AnchorPane.heightProperty());
            holdingHandController = loaderHoldingHand.getController();
            holdingHand_AnchorPane.getChildren().add(holdingHand);

            AnchorPane playedCards = loaderPlayedCards.load();
            playedCards.setMaxWidth(playedCards_AnchorPane.getWidth());
            playedCards.setMaxHeight(playedCards_AnchorPane.getHeight());
            playedCardsController = loaderPlayedCards.getController();
            playedCards_StackPane.getChildren().add(playedCards);

            loaderBlindBox.load();
            blindBoxController = loaderBlindBox.getController();

            shop = loaderShop.load();
            shopController = loaderShop.getController();
            placeHolderShop.getChildren().add(shop);

            reward = loaderReward.load();
            rewardSummarController = loaderReward.getController();
            placeHolderReward.getChildren().add(reward);
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
            animateBox(blindBox, newValue);
        });
        chooseBlind_AnchorPane.visibleProperty().bind(gameModel.blindsVisibilityProperty());

        //endregion

        //region Bind Shop
        gameModel.shopVisibilityProperty().addListener((obs, oldValue, newValue) -> {
            animateBox(placeHolderShop, newValue);
        });
        shopImageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            return Balatro.getSettings().getWindowWidth() * 0.186;
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
        pickedBlind_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
            gameModel.getActiveBlind().getBlindName() != "default",
            gameModel.getActiveBlind().blindNameProperty()
        ));

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

        //region Label card counts Bind
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
            System.out.println(holdingHand_AnchorPane.getHeight());
            holdingHandController.getHeight();
            //gameModel.getActiveJokerObList().addAll(gameModel.getJokerList());
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
        System.out.println("GameController Initialize: " + gameScreenAnchor.getWidth());

        gameModel.setRand(new Random());
        createBlindList();
        createTagList();

        gameModel.getChosenDeck().setDeck(gameSetup.getChosenDeck());
        gameModel.getChosenStake().setStake(gameSetup.getChosenStake());
        gameModel.setHands(4);
        gameModel.setDiscards(3);
        gameModel.setAnte(1);
        gameModel.setRound(0);
        gameModel.setMoney(0);

        Planet.resetUniquePlanets();

        setPlayingDeck();

        gameModel.setShopVisibility(false);
        gameModel.setRewardVisibility(false);
        System.out.println("GameController Initialize: " + gameScreenAnchor.getWidth());

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
        System.out.println(tag.getTagImageUrl());
        tag.setFitHeight(50);
        gameModel.getTagQueue().add(tag);
        System.out.println(tag);
        System.out.println(spaceTag.getChildren().stream().findFirst());
        gameModel.setRound(gameModel.getRound() + 1);
    }

    public void addMoney(int reward) {
        gameModel.setMoney(gameModel.getMoney() + reward);

        gameModel.setShopVisibility(true);
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
        int cardWidth = 140;
        int lastPos = 570;

        int cards = spaceJoker.getChildren().size();
        int pos = 0;
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
