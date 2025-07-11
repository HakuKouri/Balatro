package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.game.GameSetup;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.rewards.VoucherHandler;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.enums.JokerTrigger;
import com.example.balatro.enums.SlideDirection;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.VoucherState;
import javafx.animation.Animation;
import javafx.animation.Timeline;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class GameController
{
    //region FXML
    @FXML
    private RowConstraints holdingHand_GrowRow;
    @FXML
    private AnchorPane playedCards_AnchorPane;
    @FXML
    private ImageView shopImageView;

    //Phase Display
    @FXML
    private AnchorPane chooseBlind_AnchorPane;
    @FXML
    private Button rerollBossBlind_Button;
    @FXML
    private AnchorPane shopSign_AnchorPane;
    @FXML
    private AnchorPane pickedBlind_AnchorPane;

    @FXML
    private AnchorPane holdingHand_AnchorPane;
    @FXML
    private AnchorPane gameScreenAnchor;
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
    @FXML
    private StackPane boosterCards_StackPane;

    //Handinfo
    @FXML
    private Label infoHandName;
    @FXML
    private Label infoHandLevel;
    @FXML
    private Label infoHandChips;
    @FXML
    private Label infoHandMulti;

    //Run Info
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

    //to beat elements
    @FXML
    private Label toBeatEffect;
    @FXML
    private ImageView toBeatImage;
    @FXML
    private ImageView toBeatStake;
    @FXML
    private Label toBeatScore;
    @FXML
    private Label toBeatReward;

    //Placeholder
    @FXML
    private AnchorPane placeHolderBlinds;
    @FXML
    private AnchorPane placeHolderShop;
    @FXML
    private AnchorPane placeHolderReward;
    @FXML
    private AnchorPane placeHolderBoosterOpening;

    //Test Elements
    @FXML
    private ImageView testImageView;
    @FXML
    private Button testButton;
    //endregion

    //region Attributes
    //FXML LOADER
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("/com/example/balatro/shop.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-summary.fxml"));
    private final FXMLLoader loaderHoldingHand = new FXMLLoader(getClass().getResource("/com/example/balatro/holdingHand.fxml"));
    private final FXMLLoader loaderPlayedCards = new FXMLLoader(getClass().getResource("/com/example/balatro/playedCards_StackPane.fxml"));
    private final FXMLLoader loaderBlindBox = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-box.fxml"));
    private final FXMLLoader loaderBoosterOpening = new FXMLLoader(getClass().getResource("/com/example/balatro/boosterOpening.fxml"));

    //Controller
    private ShopController shopController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;
    private BlindBoxController blindBoxController;
    private BoosterOpeningController boosterOpeningController;

    //Gamecontroller Instance
    private static GameController instance;

    public static GameController getInstance() {
        return instance;
    }

    //Game Model instance
    private static final GameModel gameModel = Balatro.getGameModel();

    public StackPane getJokerStackPane() {
        return spaceJoker;
    }
    //endregion

    //UI HANDLER
    public void initialize(){
        instance = this;
        VoucherHandler.initializeVoucherHandler(gameModel);

        double height = Balatro.getSettings().getWindowHeight();
        double width = Balatro.getSettings().getWindowWidth();

        loadFXMLParts();
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

    private void loadFXMLParts() {
        //Place Holder
        try {
            //Holding Hand
            AnchorPane holdingHand = loaderHoldingHand.load();
            holdingHandController = loaderHoldingHand.getController();
            holdingHand_AnchorPane.getChildren().add(holdingHand);

            //Played Cards
            AnchorPane playedCards = loaderPlayedCards.load();
            playedCardsController = loaderPlayedCards.getController();
            playedCards_AnchorPane.getChildren().add(playedCards);

            //Blind Box
            AnchorPane blindBox = loaderBlindBox.load();
            blindBoxController = loaderBlindBox.getController();
            placeHolderBlinds.getChildren().add(blindBox);
            UIController.configurePlaceHolder(placeHolderBlinds);

            AnchorPane boosterOpening = loaderBoosterOpening.load();
            boosterOpeningController = loaderBoosterOpening.getController();
            placeHolderBoosterOpening.getChildren().add(boosterOpening);
            UIController.configurePlaceHolder(placeHolderBoosterOpening);

            //Shop
            //region Placeholder
            AnchorPane shop = loaderShop.load();
            shopController = loaderShop.getController();
            placeHolderShop.getChildren().add(shop);
            UIController.configurePlaceHolder(placeHolderShop);
            shopController.setOnNextRoundCallback(this::nextRound);

            //Reward
            AnchorPane reward = loaderReward.load();
            placeHolderReward.getChildren().add(reward);
            UIController.configurePlaceHolder(placeHolderReward);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        UIController.bindBlindToBeatInfo(blindToBeat_Label, toBeatEffect,toBeatImage,toBeatStake,toBeatScore,toBeatReward,gameModel);
        UIController.bindScoredPointsInfo(stakeImageView, pointsScoredLabel, gameModel);
        UIController.bindHandInfo(infoHandName,infoHandLevel,infoHandChips,infoHandMulti,gameModel);
        UIController.bindRunInfo(handsLabel,discardsLabel,moneyLabel,anteLabel,roundLabel,gameModel);

        //region Card count Labels Bind
        cardsInDeckLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getRunState().getPlayingDeck().getPlaySize() + "/" + gameModel.getRunState().getPlayingDeck().getFullSize(), gameModel.getRunState().playingDeckProperty(), gameModel.getRunState().getPlayingDeck().getFullDeck()
        ));
        jokerCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getJokerManager().getSize() + "/" + gameModel.getRunState().getMaxJokers(), gameModel.getActiveJokerMap(),gameModel.getRunState().maxJokersProperty()
        ));
        consumableCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getConsumableManager().getSize() + "/" + gameModel.getRunState().getMaxConsumables(), gameModel.getConsumableManager().getViewMap()
        ));
        //endregion
    }

    //SETTING UP GAME
    private void setPlayingDeck() {
        List<PlayingCard> cards = new ArrayList<>();
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                PlayingCard card = new PlayingCard(j,i);
                card.setSeal(gameModel.getRandomSeal());
                System.out.println(card.getSeal().getSealName());
                cards.add(card);
            }
        }
        gameModel.getRunState().getPlayingDeck().setFullDeck(cards);
    }


    //PLAYING CARD HANDLER
    public void playSelectedCards() {
        List<PlayingCard> selectedCards = gameModel.getSelectedCards();
        for (PlayingCard card : selectedCards) {
            card.setTranslateX(0);
            card.setClickAble(false);
        }

        triggerJokers(JokerTrigger.AFTER_HAND_PLAYED, gameModel.getPlayedCards());

        playedCardsController.addSelectedCards(() -> {
            Platform.runLater(() -> {
                gameModel.handButtonVisibilityProperty().set(true);
                gameModel.clearSelectedCards();

                playedCardsController.removeAllCards();
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

    public void buyItem(Card card) {
        System.out.println("Buy item: ");
        System.out.println(card.getCardName());

        if (card instanceof PurchasableCard) {
            ((PurchasableCard) card).onPurchase(gameModel);
        }
    }

    public void playTarot(CardViewController cardViewController) {
        Card card = cardViewController.getCard();
        if(card instanceof Tarot) {
            ((Tarot) card).play(gameModel);
        }
    }

    public void rerollBossBlind(ActionEvent actionEvent) {
        blindBoxController.rerollBoss();
    }

    public void buyAndUse(CardViewController cardViewController) {
        System.out.println("Buy & Use");
        Card card = cardViewController.getCard();
        if(card.getCardType().equals("Planet")) {
            gameModel.setShopVisibility(false);
            playPlanet(cardViewController, shopController.getItemMap());
            shopController.getItemMap().remove(cardViewController);
        } else if(card.getCardType().equals("Tarot")) {
            playTarot(cardViewController);
        }
    }

    public void sellItem(AnchorPane anchorPane, CardViewController cardViewController, Map<CardViewController, AnchorPane> map) {
        addMoney((int) cardViewController.getCard().getSellValue());
        map.remove(cardViewController, anchorPane);
    }

    public void useCard(AnchorPane anchorPane, CardViewController cardViewController, Map<CardViewController, AnchorPane> map) {
        if(cardViewController.getCard().getCardType().equals("Tarot")) {
            playTarot(cardViewController);
        }
        else if(cardViewController.getCard().getCardType().equals("Planet")) {
            playPlanet(cardViewController, map);
        }
        else if(cardViewController.getCard().getCardType().equals("Spectral")) {
            System.out.println("Spectral Card");
        }
    }

    public void playPlanet(CardViewController cardViewController, Map<CardViewController, AnchorPane> map) {
        gameModel.setShopVisibility(false);
        Planet planet = (Planet) cardViewController.getCard();

        PokerHand hand = gameModel.getPokerHandList().stream().filter(x -> x.getName().equals(planet.getPlanetPokerHand())).toList().getFirst();

        gameModel.getBestHand().setHand(hand);

        Animation moveAnimation = UIController.cardMoveToAnimation(map.get(cardViewController),"" ,"middle");

        Timeline multTimeline = UIController.cardWiggleTimeline(planet);
        multTimeline.setCycleCount(3);
        multTimeline.setOnFinished( event -> {
            hand.addMult(planet.getPlanetMultiplier());
            gameModel.getBestHand().addMult(planet.getPlanetMultiplier());
        });

        Timeline chipsTimeline = UIController.cardWiggleTimeline(planet);
        chipsTimeline.setCycleCount(3);
        chipsTimeline.setOnFinished( event -> {
            hand.addChips(planet.getPlanetChips());
            gameModel.getBestHand().addChips(planet.getPlanetChips());
        });

        Timeline levelTimeline = UIController.cardWiggleTimeline(planet);
        levelTimeline.setCycleCount(3);
        levelTimeline.setOnFinished( event -> {
            hand.addLevel();
            gameModel.getBestHand().addLevel();
        });

        UIController.addToAnimationList(moveAnimation);
        UIController.addToAnimationList(multTimeline);
        UIController.addToAnimationList(chipsTimeline);
        UIController.addToAnimationList(levelTimeline);
        UIController.addToAnimationList(UIController.delayTimeline());

        UIController.playAnimations(() -> {
            map.remove(cardViewController);
            gameModel.getBestHand().setHand(new PokerHand());
            gameModel.setShopVisibility(true);
        });
    }

    public void playBooster(Booster booster) {
        gameModel.setShopVisibility(false);
        gameModel.setBoosterOpeningVisibility(true);
        boosterOpeningController.useBooster(booster, gameModel);
    }

    private void triggerJokers(JokerTrigger trigger, List<PlayingCard> playedCards) {
        for (Joker joker : gameModel.getActiveJokerList()) {
            joker.tryActivate(trigger, gameModel, playedCards);
        }
    }

    public void useItem(AnchorPane cardAnchorPane, CardViewController cardViewController) {

    }

    //BACKGROUND HANDLER
//    public static void delay(long millis, Runnable continuation) {
//        Task<Void> sleeper = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                try { Thread.sleep(millis); }
//                catch (InterruptedException ignored) { }
//                return null;
//            }
//        };
//        sleeper.setOnSucceeded(event -> continuation.run());
//        new Thread(sleeper).start();
//    }
}


