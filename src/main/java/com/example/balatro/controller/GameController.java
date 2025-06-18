package com.example.balatro.controller;

import com.almasb.fxgl.ui.UI;
import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.animation.Animation;
import javafx.animation.Timeline;
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
import java.util.stream.Collectors;

public class GameController
{
    public RowConstraints holdingHand_GrowRow;
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
            playedCards_AnchorPane.getChildren().add(playedCards);

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

        UIController.setupUiController();
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
            UIController.animateBox(blindBox_AnchorPane, newValue);
        });
        chooseBlind_AnchorPane.visibleProperty().bind(gameModel.blindsVisibilityProperty());
        //endregion

        //region Bind Shop
        gameModel.shopVisibilityProperty().addListener((obs, oldValue, newValue) -> {
            UIController.animateBox(placeHolderShop, newValue);
        });
        shopImageView.fitWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            return width * 0.186;
        }));
        shopSign_AnchorPane.visibleProperty().bind(gameModel.shopVisibilityProperty());
        //endregion

        //region Bind Reward
        gameModel.rewardVisibilityProperty().addListener((obs, oldValue, newValue) -> {
            UIController.animateBox(placeHolderReward, newValue);
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

        UIController.bindStackPane(gameModel.getActiveJokerMap(), spaceJoker);
        UIController.bindStackPane(gameModel.getConsumableMap(), spaceConsumable);


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

        //holdingHand_GrowRow.percentHeightProperty().bind(Bindings.createDoubleBinding(() -> gameModel.isHandButtonVisibility() ? 20.0 : 30.0, gameModel.handButtonVisibilityProperty()));

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
        testButton.setOnAction(event -> {
            for (int i = 0; i < gameModel.getAllJokerList().size() && i < 7; i++) {
                CardViewController.createCardNode(gameModel.getAllJokerList().get(i), gameModel.getActiveJokerMap());
            }
        });

        UIController.addCardClickEvent(spaceJoker,gameModel.getActiveJokerMap());
        UIController.addCardClickEvent(spaceConsumable,gameModel.getConsumableMap());
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
        for (PlayingCard card : selectedCards) {
            card.setTranslateX(0);
            card.setClickAble(false);
        }

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
    }

    public void restockShop() {
        shopController.restockShop();
    }

    public void buyItem(AnchorPane pane, CardViewController controller) {
        System.out.printf("Buy item: ");
        System.out.println(controller.getCard());
        if(controller.getCard().getCardType() == "Joker") {
            controller.selectedProperty().set(false);
            controller.inShopProperty().set(false);
            gameModel.getActiveJokerMap().put(controller, pane);
            shopController.getItemMap().remove(controller);
        } else if(controller.getCard().getCardType() == "Tarot") {
            System.out.printf("Tarot");
            controller.selectedProperty().set(false);
            controller.inShopProperty().set(false);
            gameModel.getConsumableMap().put(controller, pane);
            shopController.getItemMap().remove(controller);
        } else if(controller.getCard().getCardType() == "Planet") {
            System.out.printf("Planet");
            controller.selectedProperty().set(false);
            controller.inShopProperty().set(false);
            gameModel.getConsumableMap().put(controller, pane);
            shopController.getItemMap().remove(controller);
        } else if(controller.getCard().getCardType() == "Spectral") {
            System.out.printf("Spectral");
            controller.selectedProperty().set(false);
            controller.inShopProperty().set(false);
            gameModel.getConsumableMap().put(controller, pane);
            shopController.getItemMap().remove(controller);
        } else if(controller.getCard().getCardType() == "PlayingCard") {
            System.out.printf("PlayingCard");
            controller.selectedProperty().set(false);
            controller.inShopProperty().set(false);
            gameModel.addCardToDeckFull((PlayingCard) controller.getCard());
            shopController.getItemMap().remove(controller);
        } else if(controller.getCard().getCardType() == "Voucher") {
            System.out.printf("Voucher");
            setVoucherFlag(controller.getCard());
            shopController.getVoucherMap().remove(controller);
        } else if(controller.getCard().getCardType() == "Booster") {
            System.out.printf("Booster");
            gameModel.setShopVisibility(false);
            //do stuff
            //gameModel.setShopVisibility(true);
        }

        System.out.println();
    }

    private void setVoucherFlag(Card card) {
        switch (card.getCardName()){
            case "Overstock": gameModel.overstockVoucherProperty().set(true);
                break;
            case "Clearance Sale": gameModel.clearanceSaleVoucherProperty().set(true);
                break;
            case "Hone": gameModel.honeVoucherProperty().set(true);
                break;
            case "Reroll Surplus": gameModel.rerollSurplusVoucherProperty().set(true);
                break;
            case "Crystal Ball": gameModel.crystalBallVoucherProperty().set(true);
                break;
            case "Telescope": gameModel.telescopeVoucherProperty().set(true);
                break;
            case "Grabber": gameModel.grabberVoucherProperty().set(true);
                break;
            case "Wasteful": gameModel.wastefulVoucherProperty().set(true);
                break;
            case "Tarot Merchant": gameModel.tarotMerchantVoucherProperty().set(true);
                break;
            case "Planet Merchant": gameModel.planetMerchantVoucherProperty().set(true);
                break;
            case "Seed Money": gameModel.seedMoneyVoucherProperty().set(true);
                break;
            case "Blank": gameModel.blankVoucherProperty().set(true);
                break;
            case "Magic Trick": gameModel.magicTrickVoucherProperty().set(true);
                break;
            case "Hieroglyph": gameModel.hieroglyphVoucherProperty().set(true);
                break;
            case "Director's Cut": gameModel.directorsCutVoucherProperty().set(true);
                break;
            case "Paint Brush": gameModel.paintBrushVoucherProperty().set(true);
                break;
            case "Overstock Plus": gameModel.overstockVoucherPlusProperty().set(true);
                break;
            case "Liquidation": gameModel.liquidationVoucherProperty().set(true);
                break;
            case "Glow Up": gameModel.glowUpVoucherProperty().set(true);
                break;
            case "Reroll Glut": gameModel.rerollGlutVoucherProperty().set(true);
                break;
            case "Omen Globe": gameModel.omenGlobeVoucherProperty().set(true);
                break;
            case "Observatory": gameModel.observatoryVoucherProperty().set(true);
                break;
            case "Nacho Tong": gameModel.nachoTongVoucherProperty().set(true);
                break;
            case "Recyclomancy": gameModel.recyclomancyVoucherProperty().set(true);
                break;
            case "Tarot Tycoon": gameModel.tarotTycoonVoucherProperty().set(true);
                break;
            case "Planet Tycoon": gameModel.planetTycoonVoucherProperty().set(true);
                break;
            case "Money Tree": gameModel.moneyTreeVoucherProperty().set(true);
                break;
            case "Antimatter": gameModel.antimatterVoucherProperty().set(true);
                break;
            case "Illusion": gameModel.illusionVoucherProperty().set(true);
                break;
            case "Petroglyph": gameModel.petroglyphVoucherProperty().set(true);
                break;
            case "Retcon": gameModel.retconVoucherProperty().set(true);
                break;
            case "Palette": gameModel.paletteVoucherProperty().set(true);
                break;

        }
    }

    public void playTarot(CardViewController cardViewController) {
        switch (cardViewController.getCard().getCardId()) {
            case 1:
                if(gameModel.getConsumableMap().size() < gameModel.getMaxConsumables())
                    CardViewController.createCardNode(gameModel.getLastConsumableUsed(), gameModel.getConsumableMap());
                break;
            case 2:
                if(gameModel.getSelectedCards().size() < 3 && !gameModel.getSelectedCards().isEmpty())
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(7));
                    }
                break;
            case 3:
                for (int i = gameModel.getConsumableMap().size(); i < gameModel.getMaxConsumables(); i++) {
                    CardViewController.createCardNode(gameModel.getAllPlanetList().get(gameModel.getRand().nextInt(gameModel.getAllPlanetList().size())), gameModel.getConsumableMap());
                }
                break;
            case 4:
                if(gameModel.getSelectedCards().size() < 3 && !gameModel.getSelectedCards().isEmpty())
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(1));
                    }
                break;
            case 5:
                for (int i = gameModel.getConsumableMap().size(); i < gameModel.getMaxConsumables(); i++) {
                    CardViewController.createCardNode(gameModel.getAllTarotList().get(gameModel.getRand().nextInt(gameModel.getAllTarotList().size())), gameModel.getConsumableMap());
                }
                break;
            case 6:
                if(gameModel.getSelectedCards().size() < 3 && !gameModel.getSelectedCards().isEmpty())
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(0));
                    }
                break;
            case 7:
                if(gameModel.getSelectedCards().size() == 1)
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(2));
                    }
                break;
            case 8:
                if(gameModel.getSelectedCards().size() == 1)
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(4));
                    }
                break;
            case 9:
                if(gameModel.getSelectedCards().size() == 1)
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(3));
                    }
                break;
            case 10:
                gameModel.addMoney(gameModel.getMoney());
                break;
            case 11://TODO WHEEL OF FORTUNE
                break;
            case 12:
                if(gameModel.getSelectedCards().size() < 3 && !gameModel.getSelectedCards().isEmpty())
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        //TODO CARD VALUE IMAGE ANPASSEN
                        card.setValue(card.getValue() + 1);
                        if(card.getValue() == 12)
                            card.setValue(2);
                    }
                break;
            case 13:
                if(gameModel.getSelectedCards().size() < 3 && !gameModel.getSelectedCards().isEmpty())
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        gameModel.removeCardFromDeckFull(card);
                        //TODO REMOVE CARDS FROM HOLDING ODER PLAYED
                    }
                break;
            case 14:
                if(gameModel.getSelectedCards().size() < 3 && !gameModel.getSelectedCards().isEmpty()) {
                    gameModel.getSelectedCards().get(0).setCard(gameModel.getSelectedCards().get(1));
                    //TODO IMAGE CHANGE
                }
                break;
            case 15:
                gameModel.addMoney(gameModel.getActiveJokerMap().keySet().stream().mapToInt(i -> (int) i.getCard().getSellValue()).sum());
                break;
            case 16:
                //GOLD
                if(gameModel.getSelectedCards().size() == 1)
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(6));
                    }
                break;
            case 17:
                //STONE
                if(gameModel.getSelectedCards().size() == 1)
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setEnhancement(gameModel.getAllEnhancementList().get(5));
                    }
                break;
            case 18:
                //DIAMONDS
                if(gameModel.getSelectedCards().size() < 3) {
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setSuit(Suit.DIAMONDS);
                    }
                }
                break;
            case 19:
                if(gameModel.getSelectedCards().size() < 3) {
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setSuit(Suit.CLUBS);
                    }
                }
                break;
            case 20:
                if(gameModel.getSelectedCards().size() < 3) {
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setSuit(Suit.HEARTS);
                    }
                }
                break;
            case 21:
                if(gameModel.getActiveJokerMap().size() < gameModel.getMaxJokers()) {
                    CardViewController.createCardNode(gameModel.getAllJokerList().get(gameModel.getRand().nextInt(gameModel.getAllJokerList().size())), gameModel.getActiveJokerMap());
                }
                break;
            case 22:
                if (gameModel.getSelectedCards().size() < 3) {
                    for (PlayingCard card : gameModel.getSelectedCards()) {
                        card.setSuit(Suit.SPADES);
                    }
                }
                break;
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

    public void buyAndUse(CardViewController cardViewController) {
        System.out.println("Buy & Use");
        Card card = cardViewController.getCard();
        if(card.getCardType().equals("Booster")) {

        } else if(card.getCardType().equals("Planet")) {
            gameModel.setShopVisibility(false);
            holdingHandController.playPlanet(cardViewController);
            shopController.getItemMap().remove(cardViewController);
        } else if(card.getCardType().equals("Tarot")) {

        }
    }

    public void sellItem(AnchorPane anchorPane, CardViewController cardViewController, Map<CardViewController, AnchorPane> map) {
        addMoney((int) cardViewController.getCard().getSellValue());
        map.remove(cardViewController, anchorPane);
    }

    public void useCard(AnchorPane anchorPane, CardViewController cardViewController, Map<CardViewController, AnchorPane> map) {
        if(cardViewController.getCard().getCardType().equals("Tarot")) {}
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

        Animation moveAnimation = UIController.cardMoveToAnimation(map.get(cardViewController), "middle");

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
}
