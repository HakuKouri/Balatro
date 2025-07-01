package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.enums.SlideDirection;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.VoucherState;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
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
import java.util.stream.Collectors;

public class GameController
{
    //region FXML
    public RowConstraints holdingHand_GrowRow;
    @FXML
    private AnchorPane playedCards_AnchorPane;
    @FXML
    private ImageView shopImageView;

    //region Phase Display
    @FXML
    private AnchorPane chooseBlind_AnchorPane;
    @FXML
    private Button rerollBossBlind_Button;
    @FXML
    private AnchorPane shopSign_AnchorPane;
    @FXML
    private AnchorPane pickedBlind_AnchorPane;
    //endregion

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
    private AnchorPane placeHolderBlinds;
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
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("/com/example/balatro/shop.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-summary.fxml"));
    private final FXMLLoader loaderHoldingHand = new FXMLLoader(getClass().getResource("/com/example/balatro/holdingHand.fxml"));
    private final FXMLLoader loaderPlayedCards = new FXMLLoader(getClass().getResource("/com/example/balatro/playedCards_StackPane.fxml"));
    private final FXMLLoader loaderBlindBox = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-box.fxml"));
    //endregion

    //region CONTROLLER
    private ShopController shopController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;
    private BlindBoxController blindBoxController;
    //endregion

    //region Placeholder
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

    public StackPane getJokerStackPane() {
        return spaceJoker;
    }

    //UI HANDLER
    public void initialize(){
        instance = this;

        double height = Balatro.getSettings().getWindowHeight();
        double width = Balatro.getSettings().getWindowWidth();

        Booster.setImageHeightProperty(height * .26);
        Booster.setImageWidthProperty(width * .09);

        gameScreenAnchor.setMaxWidth(width);
        gameScreenAnchor.setMaxHeight(height);

        loadFXMLParts();


        UIController.setupUiController();

        //Binding der AnchorPanes mit Visibility und Animation
        UIController.bindAnimatedVisibility(gameModel.shopVisibilityProperty(), placeHolderShop, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.rewardVisibilityProperty(), placeHolderReward, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.blindsVisibilityProperty(), placeHolderBlinds, SlideDirection.DOWN);
        UIController.bindAnimatedVisibility(gameModel.blindsVisibilityProperty(), chooseBlind_AnchorPane, SlideDirection.UP);
        UIController.bindAnimatedVisibility(gameModel.pickedBlindVisibilityProperty(), pickedBlind_AnchorPane, SlideDirection.UP);
        UIController.bindAnimatedVisibility(gameModel.shopVisibilityProperty(), shopSign_AnchorPane, SlideDirection.UP);


        //Blind Box
        rerollBossBlind_Button.visibleProperty().bind(Bindings.createBooleanBinding(() -> gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.DIRECTORS_CUT), gameModel.blindsVisibilityProperty()));
        rerollBossBlind_Button.disableProperty().bind(Bindings.createBooleanBinding(() -> gameModel.getRunState().getMoney() < 10, gameModel.getRunState().moneyProperty()));


        //Binding von Stack Panes für Inhalt und Click Events
        UIController.bindStackPane(gameModel.getActiveJokerMap(), spaceJoker);
        UIController.bindStackPane(gameModel.getConsumableMap(), spaceConsumable);
        UIController.addCardClickEvent(spaceJoker,gameModel.getActiveJokerMap());
        UIController.addCardClickEvent(spaceConsumable,gameModel.getConsumableMap());


        //Binding der Gameinfo Labels
        UIController.bindBlindToBeatInfo(blindToBeat_Label, toBeatEffect,toBeatImage,toBeatStake,toBeatScore,toBeatReward,gameModel);
        UIController.bindScoredPointsInfo(stakeImageView, pointsScoredLabel, gameModel);
        UIController.bindHandInfo(infoHandName,infoHandLevel,infoHandChips,infoHandMulti,gameModel);
        UIController.bindRunInfo(handsLabel,discardsLabel,moneyLabel,anteLabel,roundLabel,gameModel);


        //region Card count Labels Bind
        cardsInDeckLabel.textProperty().bind(Bindings.createStringBinding(() ->
            gameModel.getCurrentRound().getDeckToPlay().size() + "/" + gameModel.getRunState().getDeckFull().size(), gameModel.getCurrentRound().getDeckToPlay()
        ));
        jokerCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getActiveJokerObList().size() + "/" + gameModel.getRunState().getMaxJokers(), gameModel.getActiveJokerObList()
        ));
        consumableCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getConsumableList().size() + "/" + gameModel.getRunState().getMaxConsumables(), gameModel.getConsumableList()
        ));
        //endregion

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

        if(false) {
            CardViewController.createCardNode(gameModel.getAllJokerList().get(79), gameModel.getActiveJokerMap());
            CardViewController.createCardNode(gameModel.getAllJokerList().get(1), gameModel.getActiveJokerMap());
            CardViewController.createCardNode(gameModel.getAllJokerList().get(2), gameModel.getActiveJokerMap());
            CardViewController.createCardNode(gameModel.getAllJokerList().get(3), gameModel.getActiveJokerMap());
            CardViewController.createCardNode(gameModel.getAllJokerList().get(4), gameModel.getActiveJokerMap());
            CardViewController.createCardNode(gameModel.getAllJokerList().get(5), gameModel.getActiveJokerMap());

            for (AnchorPane pane : gameModel.getActiveJokerMap().values()) {
                CardViewController controller = gameModel.getActiveJokerMap().keySet()
                        .stream()
                        .filter(cardViewController -> gameModel.getActiveJokerMap().get(cardViewController) == pane).collect(Collectors.toList()).get(0);
                System.out.println("Card instance: " + (controller.getCard() instanceof Joker));
                pane.setOnMouseClicked(mouseEvent -> {
                    System.out.println(controller.getCard());

                    controller.selectedProperty().set(!controller.isSelected());
                });
            }
            UIController.moveCards(spaceJoker);
        }

        //TEST BUTTON
        //JOKER 15
        testButton.setOnAction(event -> {
            for (int i = 0; i < gameModel.getAllJokerList().size() && i < 6; i++) {
                CardViewController.createCardNode(gameModel.getAllJokerList().get(i), gameModel.getActiveJokerMap());

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

            //Shop
            shop = loaderShop.load();
            shopController = loaderShop.getController();
            placeHolderShop.getChildren().add(shop);
            UIController.configurePlaceHolder(placeHolderShop);
            shopController.setOnNextRoundCallback(this::nextRound);

            //Reward
            reward = loaderReward.load();
            placeHolderReward.getChildren().add(reward);
            UIController.configurePlaceHolder(placeHolderReward);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //SETTING UP GAME
    private void setPlayingDeck() {
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                gameModel.getRunState().getDeckFull().add(new PlayingCard(j,i));
            }
        }
        gameModel.getCurrentRound().getDeckToPlay().setAll(gameModel.getRunState().getDeckFull());
        Collections.shuffle(gameModel.getCurrentRound().getDeckToPlay(), new Random());
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
        gameModel.getRunState().setRound(gameModel.getRunState().getRound() + 1);
    }

    public void addMoney(int reward) {
        gameModel.getRunState().setMoney(gameModel.getRunState().getMoney() + reward);
    }

    public void restockShop() {
        shopController.restockShop();
    }

    public void buyItem(AnchorPane pane, CardViewController controller) {
        System.out.printf("Buy item: ");
        System.out.println(controller.getCard());

        controller.selectedProperty().set(false);
        controller.inShopProperty().set(false);
        gameModel.getRunState().subMoney(controller.getBuyPrice());


        Card card = controller.getCard();
        if (card instanceof PurchasableCard) {
            ((PurchasableCard) card).onPurchase(gameModel, pane);
        }

        //TODO
        if(controller.getCard().getCardType() == "Voucher") {
            System.out.printf("Voucher");
            setVoucherFlag(controller.getCard());
            shopController.getVoucherMap().remove(controller);
        } else if(controller.getCard().getCardType() == "Booster") {
            System.out.printf("Booster");
            shopController.getBoosterMap().remove(controller);
            //gameModel.setShopVisibility(false);
            //do stuff
            //gameModel.setShopVisibility(true);
        }

        System.out.println();
    }

    private void setVoucherFlag(Card card) {
        switch (card.getCardName()){
            case "Overstock": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OVERSTOCK, true);
                gameModel.getShopModel().maxItemsProperty().set(3);
                activeVoucherUpgrade(0);
                break;
            case "Clearance Sale": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.CLEARANCE_SALE, true);
                gameModel.getShopModel().shopPricesProperty().set(0.75);
                activeVoucherUpgrade(1);
                break;
            case "Hone": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.HONE, true);
                gameModel.getShopModel().editionChanceMultiplierProperty().set(2);
                activeVoucherUpgrade(2);
                break;
            case "Reroll Surplus": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.REROLL_SURPLUS, true);
                gameModel.getShopModel().rerollPriceProperty().set(3);
                activeVoucherUpgrade(3);
                break;
            case "Crystal Ball": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.CRYSTAL_BALL, true);
                gameModel.getRunState().maxConsumablesProperty().set(3);
                activeVoucherUpgrade(4);
                break;
            case "Telescope": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.TELESCOPE, true);
            //TODO BOOSTER PACK OPENER
                activeVoucherUpgrade(5);
                break;
            case "Grabber": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.GRABBER, true);
                gameModel.getRunState().maxHandsProperty().set(gameModel.getRunState().getMaxHands() + 1);
                activeVoucherUpgrade(6);
                break;
            case "Wasteful": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.WASTEFUL, true);
                gameModel.getRunState().setMaxDiscards(gameModel.getRunState().getMaxDiscards() + 1);
                activeVoucherUpgrade(7);
                break;
            case "Tarot Merchant": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.TAROT_MERCHANT, true);
                activeVoucherUpgrade(8);
                break;
            case "Planet Merchant": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PLANET_MERCHANT, true);
                activeVoucherUpgrade(9);
                break;
            case "Seed Money": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.SEED_MONEY, true);
                gameModel.getShopModel().maxInterestProperty().set(10);
                activeVoucherUpgrade(10);
                break;
            case "Blank": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.BLANK, true);
                activeVoucherUpgrade(11);
                break;
            case "Magic Trick": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.MAGIC_TRICK, true);
                activeVoucherUpgrade(12);
                break;
            case "Hieroglyph": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.HIEROGLYPH, true);
                gameModel.getRunState().anteProperty().set(gameModel.getRunState().getAnte() - 1);
                gameModel.getRunState().maxHandsProperty().set(gameModel.getRunState().getMaxHands() - 1);
                activeVoucherUpgrade(13);
                break;
            case "Director's Cut": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.DIRECTORS_CUT, true);
                //TODO REROLL BOSS BUTTON EINFÜGEN + ANTE RESET
                activeVoucherUpgrade(14);
                break;
            case "Paint Brush": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PAINTBRUSH, true);
                gameModel.getRunState().setMaxHandSize(gameModel.getRunState().getMaxHandSize() + 1);
                activeVoucherUpgrade(15);
                break;
            case "Overstock Plus": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OVERSTOCK_PLUS, true);
                gameModel.getShopModel().maxItemsProperty().set(4);
                upgradeBrought(16);
                break;
            case "Liquidation": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.LIQUIDATION, true);
                gameModel.getShopModel().shopPricesProperty().set(0.5);
                upgradeBrought(17);
                break;
            case "Glow Up": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.GLOW_UP, true);
                gameModel.getShopModel().editionChanceMultiplierProperty().set(2);
                upgradeBrought(18);
                break;
            case "Reroll Glut": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.REROLL_GLUT, true);
                gameModel.getShopModel().rerollPriceProperty().set(1);
                upgradeBrought(19);
                break;
            case "Omen Globe": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OMEN_GLOBE, true);
                upgradeBrought(20);
                break;
            case "Observatory": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OBSERVATORY, true);
                upgradeBrought(21);
                break;
            case "Nacho Tong": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.NACHO_TONG, true);
                gameModel.getRunState().maxHandsProperty().set(gameModel.getRunState().getMaxHands() + 1);
                upgradeBrought(22);
                break;
            case "Recyclomancy": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.RECYCLOMANCY, true);
                gameModel.getRunState().setMaxDiscards(gameModel.getRunState().getMaxDiscards() + 1);
                upgradeBrought(23);
                break;
            case "Tarot Tycoon": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.TAROT_TYCOON, true);
                upgradeBrought(24);
                break;
            case "Planet Tycoon": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PLANET_TYCOON, true);
                upgradeBrought(25);
                break;
            case "Money Tree": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.MONEY_TREE, true);
                gameModel.getShopModel().maxInterestProperty().set(20);
                upgradeBrought(26);
                break;
            case "Antimatter": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.ANTIMATTER, true);
                gameModel.getRunState().maxJokersProperty().set(gameModel.getRunState().getMaxJokers() + 1);
                upgradeBrought(27);
                break;
            case "Illusion": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.ILLUSION, true);
                upgradeBrought(28);
                break;
            case "Petroglyph": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PETROGLYPH, true);
                gameModel.getRunState().anteProperty().set(gameModel.getRunState().getAnte() - 1);
                gameModel.getRunState().setMaxDiscards(gameModel.getRunState().getMaxDiscards() - 1);
                upgradeBrought(29);
                break;
            case "Retcon": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.RETCON, true);
                upgradeBrought(30);
                break;
            case "Palette": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PALETTE, true);
                gameModel.getRunState().maxHandSizeProperty().set(gameModel.getRunState().getMaxHandSize() + 1);
                upgradeBrought(31);
                break;
        }
    }

    private void activeVoucherUpgrade(int index) {
        gameModel.getAllVoucherList().get(index).availableProperty().set(false);
        gameModel.getAllVoucherList().get(index + 16).availableProperty().set(true);
    }

    private void upgradeBrought(int index) {
        gameModel.getAllVoucherList().get(index).availableProperty().set(false);
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

    public void buyAndUse(CardViewController cardViewController) {
        System.out.println("Buy & Use");
        Card card = cardViewController.getCard();
        if(card.getCardType().equals("Booster")) {

        } else if(card.getCardType().equals("Planet")) {
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
        else if(cardViewController.getCard().getCardType().equals("Spectral")) {}
    }

    public void playPlanet(CardViewController cardViewController, Map<CardViewController, AnchorPane> map) {
        gameModel.setShopVisibility(false);
        Planet planet = (Planet) cardViewController.getCard();

        PokerHand hand = gameModel.getPokerHandList().stream().filter(x -> x.getName().equals(planet.getPlanetPokerHand())).findFirst().get();

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

    private void triggerJokers(JokerTrigger trigger, List<PlayingCard> playedCards) {
        for (Joker joker : gameModel.getActiveJokerList()) {
            joker.tryActivate(trigger, gameModel, playedCards);
        }
    }


    public void shuffleDeck() {
        gameModel.getCurrentRound().getDeckToPlay().setAll(gameModel.getRunState().getDeckFull());
        Collections.shuffle(gameModel.getCurrentRound().getDeckToPlay(), new Random());
    }
}
