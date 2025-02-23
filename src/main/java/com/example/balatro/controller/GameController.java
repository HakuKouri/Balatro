package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class GameController
{
    public Pane pointsScoredPane;
    public Pane handInfo_Pane;
    public Pane runInfo_Pane;
    public AnchorPane holdingHand_AnchorPane;
    public StackPane playedCards_StackPane;
    @FXML
    private AnchorPane smallBlindAnchor;
    @FXML
    private AnchorPane bigBlindAnchor;
    @FXML
    private AnchorPane bossBlindAnchor;
    @FXML
    private Label labelBlind;


    //to beat elements
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


    //TEST
    @FXML
    private ImageView testImageView;

    //FXMLLOADER AND CONTROLLER
    private final FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
    private final FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
    private final FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
    private final FXMLLoader loaderShop = new FXMLLoader(getClass().getResource("/com/example/balatro/shop-part.fxml"));
    private final FXMLLoader loaderReward = new FXMLLoader(getClass().getResource("/com/example/balatro/reward-summary.fxml"));
    private final FXMLLoader loaderPointsScored = new FXMLLoader(getClass().getResource("/com/example/balatro/pointsScored_Pane.fxml"));
    private final FXMLLoader loaderHandInfo = new FXMLLoader(getClass().getResource("/com/example/balatro/handInfo_Pane.fxml"));
    private final FXMLLoader loaderRunInfo = new FXMLLoader(getClass().getResource("/com/example/balatro/runInfo_Pane.fxml"));
    private final FXMLLoader loaderHoldingHand = new FXMLLoader(getClass().getResource("/com/example/balatro/holdingHand_StackPane.fxml"));
    private final FXMLLoader loaderPlayedCards = new FXMLLoader(getClass().getResource("/com/example/balatro/playedCards_StackPane.fxml"));

    private AnchorPane smallBlind = null;
    private AnchorPane bigBlind = null;
    private AnchorPane boss = null;
    private AnchorPane shop = null;
    private AnchorPane reward = null;
    private AnchorPane pointsScored = null;
    private AnchorPane handInfo = null;
    private AnchorPane runInfo = null;
    private AnchorPane holdingHand = null;
    private AnchorPane playedCards = null;

    //GAME MECHANICS VARIABLES
    static boolean blindsToggled = true;
    public static Random rand;
    static List<Blind> allBlindsList;
    private final ArrayList<Blind> gameBlindsList = new ArrayList<>();
    static List<Tag> allTagList;
    static List<Hand> allHandList;

    //MODELS
    GameModel gameModel = new GameModel();
    private BlindPickPanelsController smallController;
    private BlindPickPanelsController bigController;
    private BlindPickPanelsController bossController;
    private ShopPartController shopController;
    private RewardSummarController rewardSummarController;
    private RunInfoController runInfoController;
    private HandInfoController handInfoController;
    private PointsScoredController pointsScoredController;
    private HoldingHandController holdingHandController;
    private PlayedCardsController playedCardsController;

    //GAME VARIABLES

    static int handsize = 8;
    static BigDecimal scoreToReach = new BigDecimal(0);
    static Deck chosenDeck;
    static Stake chosenStake;
    static String sortedBy = "rank";
    static BigDecimal[] chipRequirement = new BigDecimal[]{BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800), BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000), BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};

    private final List<PlayingCard> deckFull = new ArrayList<>();
    private final List<PlayingCard> deckToPlay = new ArrayList<>();
    private final List<Tag> blindTags = new ArrayList<>();
    private final List<Tag> tagQueueList = new ArrayList<>();

    public IntegerProperty pointsScoredProperty = new SimpleIntegerProperty();

    public static GameController getInstance() {
        return Balatro.gameController;
    }

    //UI HANDLER
    public void initialize(){
        Balatro.gameController = this;
        allTagList = Tag.setTagList();
        allHandList = Hand.setHandList();
        allBlindsList = Blind.setBlindList();

        //LOAD / READY PLACEHOLDER
        try {
            pointsScored = loaderPointsScored.load();
            pointsScoredController = loaderPointsScored.getController();
            pointsScoredPane.getChildren().add(pointsScored);

            handInfo = loaderHandInfo.load();
            handInfoController = loaderHandInfo.getController();
            handInfo_Pane.getChildren().add(handInfo);

            runInfo = loaderRunInfo.load();
            runInfoController = loaderRunInfo.getController();
            runInfo_Pane.getChildren().add(runInfo);

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

        setPlayingDeck();
        rand = new Random();
        holdingHandController.hideHandButtons();
        handInfoController.clearHandInfo();
        toggleBlind();

        gameModel.getTagStack().addListener((ListChangeListener<Tag>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    spaceTag.getChildren().addAll(change.getAddedSubList());
                }
                if(change.next()) {
                    spaceTag.getChildren().addAll(change.getAddedSubList());
                }
            }
        });

    }

    private void setBlindPanels() {
        int ante = runInfoController.getAnte();
        int round = runInfoController.getRound();
        smallController.setBlind(gameBlindsList.get((ante-1)*3), allTagList.get(round-ante), 1);
        bigController.setBlind(gameBlindsList.get((ante-1)*3+1), allTagList.get(round-ante), 2);
        bossController.setBlind(gameBlindsList.get((ante-1)*3+2), allTagList.get(round-ante), 3);

        smallController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(allBlindsList.get((ante-1)* 3).getBlindScoreMultiplier().replace("x base",""))));
        bigController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(allBlindsList.get((ante-1)* 3 +1).getBlindScoreMultiplier().replace("x base",""))));
        bossController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(allBlindsList.get((ante-1)* 3+2).getBlindScoreMultiplier().replace("x base",""))));

        toggleBlind();
    }

    private void setDeckImage() throws IOException {
        Image image = new Image("file:" + chosenDeck.getDeckCoverUrl());
        imageViewDeckField.setImage(image);
    }

    public void toggleBlind() {
        int round = runInfoController.getRound();
        blindsToggled = !blindsToggled;
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
        TranslateTransition transitionSmall = new TranslateTransition(Duration.seconds(.5), smallBlindAnchor);
        TranslateTransition transitionBig = new TranslateTransition(Duration.seconds(.5), bigBlindAnchor);
        TranslateTransition transitionBoss = new TranslateTransition(Duration.seconds(.5), bossBlindAnchor);
        if (blindsToggled) {
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

    private void hideBlinds() {
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
    }

    private void showBlinds() {
        TranslateTransition transitionBlindBox = new TranslateTransition(Duration.seconds(.5), blindBox);
    }

    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;
        int bestHandIndex = -1;
        for (Hand hand : allHandList) {
            if(hands.contains(hand.getName())) {
                System.out.println(hand.getName());
                int points = hand.getChips() * hand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    bestHandIndex = allHandList.indexOf(hand);
                }
            }
        }
        if(!holdingHandController.getSelectedCards().isEmpty()) {
            handInfoController.setHandName(allHandList.get(bestHandIndex).getName());
            handInfoController.setHandLevel(allHandList.get(bestHandIndex).getLevel());
            handInfoController.setHandChips(allHandList.get(bestHandIndex).getChips());
            handInfoController.setHandMultiplier(allHandList.get(bestHandIndex).getMulti());
        } else {
            handInfoController.clearHandInfo();
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

    private void openSummary() {
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
                deckFull.add(new PlayingCard(j,i));
            }
        }
        deckToPlay.addAll(deckFull);

        Collections.shuffle(deckToPlay, new Random());
    }

    public void createBlindList() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 0)
                    gameBlindsList.add(allBlindsList.get(0));
                else if(j == 1)
                    gameBlindsList.add(allBlindsList.get(1));
                else
                    gameBlindsList.add(allBlindsList.get(rand.nextInt(allBlindsList.size() - 2 + 1) + 1));
            }
        }
    }

    private void expandBlindTagList() {
        for (int i = 0; i < 2; i++) {
            Tag tag = allTagList.get(rand.nextInt(allTagList.size()));
            if(Integer.parseInt((tag.getMinAnte())) <= runInfoController.getAnte())
                blindTags.add(tag);
            else {
                i--;
            }
        }
    }


    //PLAYING CARD HANDLER
    public void drawCard() {
        deckToPlay.get(0).setOnMouseClicked(mouseEvent -> {
            playingCardClicked((PlayingCard) mouseEvent.getSource());
        });
        deckToPlay.get(0).setClickAble(true);

        holdingHandController.addCardToHand(deckToPlay.get(0));
        deckToPlay.remove(0);
        holdingHandController.moveCards();
    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {drawCard();}
        if(Objects.equals(sortedBy, "rank")) holdingHandController.sortRank();
        else holdingHandController.sortSuit();
    }

    private void playingCardClicked(PlayingCard card) {
        if(!card.isClickAble()) return;

        if(card.getTranslateY() == 0 && holdingHandController.getSelectedCardCounter() < 5) {
            holdingHandController.addCardToSelectedCard(card);
            card.setTranslateY(-20);
            holdingHandController.selectedCardCounterIncement();
        }
        else if(card.getTranslateY() == -20) {
            holdingHandController.removeCardFromSelectedCard(card);
            card.setTranslateY(0);
            holdingHandController.selectedCardCounterDecrement();
        }
        setHandInfo(checkHand.evaluateHands(holdingHandController.getSelectedCards()));
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

            List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(holdingHandController.getSelectedCards() , handInfoController.getHandName());
            for(int i = 0; i < playedCardsController.count(); i++) {

                if(countedCards.contains(playedCardsController.getPlayedCards_StackPane().get(i))) {
                    playedCardsController.getPlayedCards_StackPane().get(i).setTranslateY(-20);
                } else
                    playedCardsController.getPlayedCards_StackPane().get(i).setTranslateY(0);
            }

            handInfoController.setHandChips(allHandList.stream().filter(
                    name -> Objects.equals(name.getName(), handInfoController.getHandName()))
                    .collect(Collectors.toList()).get(0).getChips());
            handInfoController.setHandMultiplier(allHandList.stream().filter(
                    name -> Objects.equals(name.getName(), handInfoController.getHandName()))
                    .collect(Collectors.toList()).get(0).getMulti());

            holdingHandController.clearSelectedCards();

            countPoints();

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
        rand = new Random();
        chosenDeck = gameSetup.getChosenDeck();
        chosenStake = gameSetup.getChosenStake();
        runInfoController.setHands(4);
        runInfoController.setDiscards(3);
        runInfoController.setAnte(1);
        runInfoController.setRound(1);
        runInfoController.setMoney(0);

        pointsScoredController.setStakeImageView("file:" +gameSetup.getChosenStake().getStakeImageChipUrl());

        createBlindList();
        setBlindPanels();
        BlindPickPanelsController.setStageImage(new Image("file:" + chosenStake.getStakeImageChipUrl()));

        try {
            setDeckImage();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startRound(Blind blind, BigDecimal score) {
        holdingHandController.hideHandButtons();
        scoreToReach = score;
        toggleBlind();
        labelBlind.setText(blind.getBlindName());
        drawCards(handsize);
        toBeatScore.setText(String.valueOf(scoreToReach));
        toBeatImage.setImage(new Image("file:"+blind.getBlindImageUrl()));
        toBeatStake.setImage(new Image("file:"+ chosenStake.getStakeImageChipUrl()));
        toBeatReward.setText("$$$");
        handInfoController.clearHandInfo();
    }

    public void nextRound() {
        closeShop();
        runInfoController.setRound(runInfoController.getRound() + 1);
        toggleBlind();
    }

    public void skip(Tag tag) {
        System.out.println(tag.getTagImageUrl());
        gameModel.addTagToStack(tag);
        System.out.println(tag);
        System.out.println(spaceTag.getChildren().stream().findFirst());
        runInfoController.setRound(runInfoController.getRound() + 1);
    }

    private void countPoints() {
        for(int i = 0; i < playedCardsController.count(); i++) {
            if(playedCardsController.getPlayedCards_StackPane().get(i).getTranslateY() == -20) {
                handInfoController.addChips(((PlayingCard)playedCardsController.getPlayedCards_StackPane().get(i)).getValue());
            }
        }

        pointsScoredController.addPoints((long) (handInfoController.getHandChips() * handInfoController.getHandMultiplier()));

        if(scoreReached()) {
            openSummary();
            runInfoController.setRound(runInfoController.getRound() + 1);
            pointsScoredController.clearPoints();
            holdingHandController.clearHandCards();

            if(allBlindsList.get(runInfoController.getRound()-1).getId() > 1) {
                runInfoController.setAnte((runInfoController.getAnte() + 1));
            }
        } else {
            holdingHandController.hideHandButtons();
        }

        handInfoController.clearHandInfo();
    }

    private boolean scoreReached() {
        return BigDecimal.valueOf(pointsScoredProperty.get()).compareTo(scoreToReach) != -1;
    }

    public void addMoney(int reward) {
        runInfoController.setMoney(runInfoController.getMoney() + reward);

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
