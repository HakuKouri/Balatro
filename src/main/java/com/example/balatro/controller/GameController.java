package com.example.balatro.controller;

import com.almasb.fxgl.trade.Shop;
import com.example.balatro.Balatro;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.game.GameSetup;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.rewards.VoucherHandler;
import com.example.balatro.domain.util.CardViewManager;
import com.example.balatro.domain.util.FxmlUtil;
import com.example.balatro.enums.JokerTrigger;
import com.example.balatro.enums.SlideDirection;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.VoucherState;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.*;

public class GameController
{
    //region FXML

    //Phase Display
    @FXML private AnchorPane chooseBlind_AnchorPane, shopSign_AnchorPane, pickedBlind_AnchorPane;
    @FXML private Button rerollBossBlind_Button;
    @FXML private ImageView shopImageView;

    //Spaces
    @FXML private AnchorPane gameScreenAnchor, holdingHand_AnchorPane, playedCards_AnchorPane;
    @FXML private StackPane spaceJoker, spaceConsumable;
    @FXML private ImageView deckCover_ImageView;
    @FXML private VBox spaceTag;
    @FXML private Label jokerCountLabel, consumableCountLabel, cardsInDeckLabel;

    //Handinfo
    @FXML private Label infoHandName, infoHandLevel, infoHandChips, infoHandMulti;

    //Run Info
    @FXML private Label handsLabel, discardsLabel, moneyLabel, anteLabel, roundLabel;

    //to beat elements
    @FXML private ImageView toBeatImage, toBeatStake;
    @FXML private Label toBeatName, toBeatEffect, toBeatScore, toBeatReward;

    //scored Points
    @FXML private ImageView stakeImageView;
    @FXML private Label pointsScoredLabel;

    //Placeholder
    @FXML
    private AnchorPane placeHolderBlinds, placeHolderShop, placeHolderReward, placeHolderBoosterOpening;

    //Test Elements
    @FXML
    private ImageView testImageView;
    @FXML
    private Button testButton;
    //endregion

    //region Attributes
    //Controller
    private ShopController shopController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;
    private BlindBoxController blindBoxController;
    private BoosterOpeningController boosterOpeningController;

    //Game Controller & Model
    private static GameController instance;
    public static GameController getInstance() {
        return instance;
    }
    private static final GameModel gameModel = Balatro.getGameModel();

    //endregion

    public void initialize(){
        instance = this;
        VoucherHandler.initializeVoucherHandler(gameModel);

        loadFXMLParts();

        double height = Balatro.getSettings().getWindowHeight();
        double width = Balatro.getSettings().getWindowWidth();

        bindUi();

        Booster.setImageHeightProperty(height * .26);
        Booster.setImageWidthProperty(width * .09);

        gameScreenAnchor.setMaxWidth(width);
        gameScreenAnchor.setMaxHeight(height);

        //Shop
        shopImageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            return width * 0.186;
        }));

        //Deck CoverBind
        deckCover_ImageView.imageProperty().bind(gameModel.getRunState().getChosenDeck().imageProperty());

        pickedBlind_AnchorPane.setMaxHeight(height * 0.26);

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


        //TEST BUTTON
        //JOKER 15
        testButton.setOnAction(event -> {

            for (int i = 0; i < gameModel.getAllJokerList().size() && i < 6; i++) {
                Joker joker = gameModel.getAllJokerList().get(i);
                joker.addSticker(gameModel.getStickerList().get(10));
                gameModel.getJokerManager().create(joker);
            }

            for (Joker joker : gameModel.getActiveJokerList()) {
                System.out.println(joker.getCardName());
                triggerJokers(JokerTrigger.ON_BUY,new ArrayList<>());
            }
        });
    }

    //region Setup
    private void loadFXMLParts() {
        //Holding Hand
        Pair<HoldingHandController, AnchorPane> holdingHand = FxmlUtil.loadWithPane("/com/example/balatro/holdingHand.fxml");
        holdingHandController = holdingHand.getKey();
        holdingHand_AnchorPane.getChildren().add(holdingHand.getValue());

        //Played Cards
        Pair<PlayedCardsController, AnchorPane> playedCards = FxmlUtil.loadWithPane("/com/example/balatro/playedCards_StackPane.fxml");
        playedCardsController = playedCards.getKey();
        playedCards_AnchorPane.getChildren().add(playedCards.getValue());

        //Blind Box
        Pair<BlindBoxController, AnchorPane> blindBox = FxmlUtil.loadWithPane("/com/example/balatro/blind-box.fxml");
        blindBoxController = blindBox.getKey();
        placeHolderBlinds.getChildren().add(blindBox.getValue());
        UIController.configurePlaceHolder(placeHolderBlinds);

        //Booster Opener
        Pair<BoosterOpeningController, AnchorPane> boosterOpening = FxmlUtil.loadWithPane("/com/example/balatro/boosterOpening.fxml");
        boosterOpeningController = boosterOpening.getKey();
        boosterOpeningController.setGameModel(gameModel);
        placeHolderBoosterOpening.getChildren().add(boosterOpening.getValue());
        UIController.configurePlaceHolder(placeHolderBoosterOpening);

        //Shop
        //region Placeholder
        Pair<ShopController, AnchorPane> shop = FxmlUtil.loadWithPane("/com/example/balatro/shop.fxml");
        shopController = shop.getKey();
        placeHolderShop.getChildren().add(shop.getValue());
        UIController.configurePlaceHolder(placeHolderShop);
        shopController.setOnNextRoundCallback(this::nextRound);

        //Reward
        Pair<RewardSummaryController, AnchorPane> reward = FxmlUtil.loadWithPane("/com/example/balatro/reward-summary.fxml");
        placeHolderReward.getChildren().add(reward.getValue());
        UIController.configurePlaceHolder(placeHolderReward);
    }

    private void bindUi() {
        UIController.setupUiController();

        //Binding der AnchorPanes mit Visibility und Animation
        UIController.bindAnimatedVisibility(gameModel.shopVisibilityProperty(), placeHolderShop, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.rewardVisibilityProperty(), placeHolderReward, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.blindsVisibilityProperty(), placeHolderBlinds, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.boosterOpeningVisibilityProperty(), placeHolderBoosterOpening, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.blindsVisibilityProperty(), chooseBlind_AnchorPane, SlideDirection.UP);
        UIController.bindAnimatedVisibility(gameModel.pickedBlindVisibilityProperty(), pickedBlind_AnchorPane, SlideDirection.UP);
        UIController.bindAnimatedVisibility(gameModel.shopVisibilityProperty(), shopSign_AnchorPane, SlideDirection.UP);

        //Blind Box
        rerollBossBlind_Button.visibleProperty().bind(Bindings.createBooleanBinding(() -> gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.DIRECTORS_CUT), gameModel.blindsVisibilityProperty()));
        rerollBossBlind_Button.disableProperty().bind(Bindings.createBooleanBinding(() -> gameModel.getRunState().getMoney() < 10, gameModel.getRunState().moneyProperty()));

        //Binding von Stack Panes für Inhalt und Click Events
        UIController.bindStackPane(gameModel.getJokerManager(), spaceJoker);
        UIController.bindStackPane(gameModel.getConsumableManager(), spaceConsumable);

        //Binding der Gameinfo Labels
        UIController.bindBlindToBeatInfo(toBeatName, toBeatEffect,toBeatImage,toBeatStake,toBeatScore,toBeatReward,gameModel);
        UIController.bindScoredPointsInfo(stakeImageView, pointsScoredLabel, gameModel);
        UIController.bindHandInfo(infoHandName,infoHandLevel,infoHandChips,infoHandMulti,gameModel);
        UIController.bindRunInfo(handsLabel,discardsLabel,moneyLabel,anteLabel,roundLabel,gameModel);

        //region Card count Labels Bind
        cardsInDeckLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getRunState().getPlayingDeck().getPlaySize() + "/" + gameModel.getRunState().getPlayingDeck().getFullSize(), gameModel.getRunState().getPlayingDeck().getPlayDeck(), gameModel.getRunState().getPlayingDeck().getFullDeck()
        ));
        jokerCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getJokerManager().getSize() + "/" + gameModel.getRunState().getMaxJokers(), gameModel.getJokerManager().sizeProperty(),gameModel.getRunState().maxJokersProperty()
        ));
        consumableCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getConsumableManager().getSize() + "/" + gameModel.getRunState().getMaxConsumables(), gameModel.getConsumableManager().sizeProperty()
        ));
        //endregion
    }
    //endregion

    //SETTING UP GAME
    private void setPlayingDeck() {
        List<PlayingCard> cards = new ArrayList<>();
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                PlayingCard card = new PlayingCard(j,i);
                card.setSeal(gameModel.getRandomSeal());
                System.out.println(card.getSeal().getSealName());
                System.out.println("Card: " + card.getSuit() + " " + card.getRank());
                cards.add(card);
            }
        }
        gameModel.getRunState().getPlayingDeck().setFullDeck(cards);
    }

    //PLAYING CARD HANDLER
    public void playSelectedCards() {
        List<PlayingCard> selectedCards = gameModel.getSelectedCards();
        for (PlayingCard card : selectedCards) {
            card.setClickAble(false);
        }

        triggerJokers(JokerTrigger.AFTER_HAND_PLAYED, gameModel.getPlayedCards());

        playedCardsController.addSelectedCards(() -> {
            Platform.runLater(() -> {
                gameModel.clearSelectedCards();
                gameModel.getPlayedCardsViewManager().clear();
            });
        });
    }

    //GAME HANDLER
    public void startNewGame(GameSetup gameSetup) {
        gameModel.setRand(new Random());

        gameModel.getRunState().getChosenDeck().setDeck(gameSetup.getChosenDeck());
        gameModel.getRunState().getChosenStake().setStake(gameSetup.getChosenStake());
        gameModel.getRunState().setMaxHands(4);
        gameModel.getRunState().setMaxDiscards(3);
        gameModel.getRunState().setAnte(1);
        gameModel.getRunState().setRound(0);
        gameModel.getRunState().setMoney(4);

        Planet.resetUniquePlanets();

        setPlayingDeck();

        gameModel.setShopVisibility(false);
        gameModel.setRewardVisibility(false);
        gameModel.setBoosterOpeningVisibility(false);
    }

    public void startRound(BigDecimal score) {
        gameModel.setScoreToReach(score);
        gameModel.blindsVisibilityProperty().set(false);
        gameModel.getRunState().getPlayingDeck().shuffleDeck();
        holdingHandController.drawCardToLimit();
    }

    public void nextRound() {
        gameModel.setShopVisibility(false);
        gameModel.setBlindsVisibility(true);
    }

    public void skip(Tag tag) {
        tag.setFitHeight(50);
        gameModel.getTagQueue().add(tag);
        gameModel.getRunState().setRound(gameModel.getRunState().getRound() + 1);
    }

    public void addMoney(int reward) {
        gameModel.getRunState().setMoney(gameModel.getRunState().getMoney() + reward);
    }

    public void restockShop() {
        shopController.restockShop();
    }

    public void rerollBossBlind(ActionEvent actionEvent) {
        blindBoxController.rerollBoss();
    }

    public void useCardFromConsumable(Card card) {
        gameModel.handButtonVisibilityProperty().set(false);
        CardViewManager.transferCardTo(gameModel.getConsumableManager(), gameModel.getPlayedCardsViewManager(), card);
        if(card instanceof Planet planet) {
            planet.play(gameModel, () -> {
                gameModel.handButtonVisibilityProperty().set(true);
                gameModel.getPlayedCardsViewManager().clear();
            });
        } else if(card instanceof Tarot tarot) {
            tarot.play(gameModel, () -> {
                gameModel.handButtonVisibilityProperty().set(true);
                gameModel.getPlayedCardsViewManager().clear();
            });
        } else if(card instanceof Spectral spectral) {
            spectral.play(gameModel, () -> {
                gameModel.handButtonVisibilityProperty().set(true);
                gameModel.getPlayedCardsViewManager().clear();
            });
        }
    }

    public void useCardFromShop(Card card) {
        gameModel.shopVisibilityProperty().set(false);
        CardViewManager.transferCardTo(gameModel.getShopModel().getItemCardViewManager(), gameModel.getPlayedCardsViewManager(), card);
        if(card instanceof Planet planet) {
            planet.play(gameModel, () -> {
                gameModel.shopVisibilityProperty().set(true);
                gameModel.getPlayedCardsViewManager().clear();
            });
        } else if(card instanceof Tarot tarot) {
            tarot.play(gameModel, () -> {
                gameModel.shopVisibilityProperty().set(true);
                gameModel.getPlayedCardsViewManager().clear();
            });
        } else if(card instanceof Spectral spectral) {
            spectral.play(gameModel, () -> {
                gameModel.shopVisibilityProperty().set(true);
                gameModel.getPlayedCardsViewManager().clear();
            });
        }
    }

    public void useCardFromBooster(Card card) {
        boosterOpeningController.useCard(card);
    }

    public void selectCardFromBooster(Card card) {
        if(card instanceof Joker joker) {
            if (gameModel.getJokerManager().getSize() < gameModel.getRunState().getMaxJokers()) {
                CardViewManager.transferCardTo(gameModel.getBoosterDrawModel().getBoosterDrawnManager(), gameModel.getJokerManager(), card);
            } else {
                //TODO JOKER SPACE FULL ANIMATION
                System.out.println("Joker Space Full");
            }
        } else if (card instanceof PlayingCard playingCard) {
            gameModel.getRunState().getPlayingDeck().addCard(playingCard);
            gameModel.getBoosterDrawModel().getBoosterDrawnManager().remove(playingCard);
        }
    }

    public void buyItem(Card card) {
        if (card instanceof PurchasableCard) {
            ((PurchasableCard) card).onPurchase(gameModel);
        }
    }

    public void buyAndUse(Card card) {
        System.out.println("Buy & Use");
        gameModel.getRunState().subMoney(card.getBuyPrice());
        useCardFromShop(card);
    }

    public void sellItem(Card card) {
        addMoney((int) card.getSellValue());
        if(card instanceof Joker joker) {
            gameModel.getJokerManager().remove(joker);
        } else {
            gameModel.getConsumableManager().remove(card);
        }
    }

    public void playBooster(Booster booster) {
        gameModel.setShopVisibility(false);
        gameModel.setBoosterOpeningVisibility(true);
        boosterOpeningController.useBooster(booster);
    }

    private void triggerJokers(JokerTrigger trigger, List<PlayingCard> playedCards) {
        for (Joker joker : gameModel.getActiveJokerList()) {
            joker.tryActivate(trigger, gameModel, playedCards);
        }
    }

}


