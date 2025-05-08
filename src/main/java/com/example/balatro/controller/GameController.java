package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

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
    private RowConstraints bottomRow;
    @FXML
    private HBox jokerConsumeHBox;
    @FXML
    private Label cardsInDeckLabel;
    @FXML
    private Label toBeatEffect;
    @FXML
    private GridPane toBeat;
    @FXML
    private Label labelBlind;
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
    private ImageView imageViewDeckField;
    @FXML
    private VBox spaceTag;
    @FXML
    private StackPane spaceJoker;
    public Label jokerCountLabel;

    @FXML
    private StackPane spaceConsumable;
    public Label consumableCountLabel;

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
                smallBlindController.setBossPanel(false);
                smallBlindController.setTag(gameModel.getRunTags().isEmpty() ? new Tag() : gameModel.getRunTags().get((gameModel.getAnte() -1 ) * 2));
                bigBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getAnte() - 1) * 3 + 1));
                bigBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bigBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
                bigBlindController.setBossPanel(false);
                bigBlindController.setTag(gameModel.getRunTags().isEmpty() ? new Tag() : gameModel.getRunTags().get((gameModel.getAnte() -1 ) * 2 + 1));
                bossBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getAnte() - 1) * 3 + 2));
                bossBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bossBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
                bossBlindController.setBossPanel(true);
            });

            blindBoxHBox.getChildren().add(smallBlindPanel);
            blindBoxHBox.getChildren().add(bigBlindPanel);
            blindBoxHBox.getChildren().add(bossBlindPanel);

            gameModel.roundProperty().addListener((obs, oldValue, newValue) -> {
                blindBoxHBox.getChildren().get(0).setDisable(gameModel.getRound()%3 != 0);
                blindBoxHBox.getChildren().get(1).setDisable(gameModel.getRound()%3 != 1 && gameModel.getRound() != 0);
                blindBoxHBox.getChildren().get(2).setDisable(gameModel.getRound()%3 != 2 && gameModel.getRound() != 0);
            });
            //endregion

            VBox holdingHand = loaderHoldingHand.load();
            holdingHandController = loaderHoldingHand.getController();
            holdingHand_AnchorPane.getChildren().add(holdingHand);

            AnchorPane playedCards = loaderPlayedCards.load();
            playedCardsController = loaderPlayedCards.getController();
            playedCards_StackPane.getChildren().add(playedCards);

            System.out.println("Gamecontroller BlindBox Load");
            loaderBlindBox.load();
            blindBoxController = loaderBlindBox.getController(); // Hier bekommst du den Controller

            //blindBox.setTranslateY(600);

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

        //region Bind Shop
        placeHolderShop.translateYProperty().bind(Bindings.createIntegerBinding(
                () -> gameModel.isShopVisibility() ? 0 : 560,
                gameModel.shopVisibilityProperty()
        ));
        //endregion

        //region Bind Reward
        placeHolderReward.translateYProperty().bind(Bindings.createIntegerBinding(
                () -> gameModel.isRewardVisibility() ? 0 : 560,
                gameModel.rewardVisibilityProperty()
        ));
        //endregion

        //region Deck CoverBind
        imageViewDeckField.imageProperty().bind(gameModel.getChosenDeck().imageProperty());
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

        bottomRow.prefHeightProperty().bind(Bindings.createIntegerBinding(() ->
                gameModel.isHandButtonVisibility() ? 350 : 220,
                gameModel.handButtonVisibilityProperty()));

        labelBlind.textProperty().bind(gameModel.activeBlindProperty().get().blindNameProperty());

        //region to Beat Bind
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

        //end region

        //TEST BUTTON
        testButton.setOnAction(event -> {
            System.out.println("tag: " + smallBlindController.getTag().getTagName());
        });

    }


    public void toggleBlind() {
        int round = gameModel.getRound();
        gameModel.toggleBlindVisibity();
//        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
//        TranslateTransition transitionSmall = new TranslateTransition(Duration.seconds(.5), smallBlindAnchor);
//        TranslateTransition transitionBig = new TranslateTransition(Duration.seconds(.5), bigBlindAnchor);
//        TranslateTransition transitionBoss = new TranslateTransition(Duration.seconds(.5), bossBlindAnchor);
        if (gameModel.getBlindsVisibility()) {
 //           transitionBlindBox.setToY(0);
//            if(round%3 == 0) transitionSmall.setToY(-50);
//            if(round%3 == 1) transitionBig.setToY(-50);
//            if(round%3 == 2) transitionBoss.setToY(-50);
        } else {
 //           transitionBlindBox.setToY(600);
//            transitionSmall.setToY(0);
//            transitionBig.setToY(0);
//            transitionBoss.setToY(0);
        }
 //       transitionBlindBox.play();
//        transitionSmall.play();
//        transitionBig.play();
//        transitionBoss.play();
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
                    holdingHandController.drawCards();
                    gameModel.handButtonVisibilityProperty().set(true);
                    holdingHandController.moveCards();
                    gameModel.clearSelectedCards();
                    playedCardsController.removeAllCards();
                    //delay(4000,() -> {playedCardsController.removeAllCards();});
                });
            });
        }
    }

    //GAME HANDLER
    public void startNewGame(GameSetup gameSetup) {
        gameModel.setRand(new Random());
        createBlindList();
        createTagList();

        gameModel.setChosenDeck(gameSetup.getChosenDeck());
        gameModel.setChosenStake(gameSetup.getChosenStake());
        gameModel.setHands(4);
        gameModel.setDiscards(3);
        gameModel.setAnte(1);
        gameModel.setRound(0);
        gameModel.setMoney(0);

        Planet.resetUniquePlanets();

        setPlayingDeck();
    }


    public void startRound(BigDecimal score) {
        gameModel.setScoreToReach(score);
        gameModel.blindsVisibilityProperty().set(true);
        holdingHandController.drawCards();
    }

    public void nextRound() {
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
