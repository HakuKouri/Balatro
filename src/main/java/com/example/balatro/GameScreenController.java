package com.example.balatro;

import com.example.balatro.classes.*;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameScreenController
{
    @FXML
    private ImageView roundScoreStakeImage;
    @FXML
    private AnchorPane smallBlindAnchor;
    @FXML
    private AnchorPane bigBlindAnchor;
    @FXML
    private AnchorPane bossAnchor;
    @FXML
    private Label labelBlind;
    @FXML
    private StackPane HoldingHand;
    @FXML
    private ImageView testImageView;
    @FXML
    private HBox hbPlayedCards;
    @FXML
    private GridPane handButtonBox;
    @FXML
    private Label infoHand;
    @FXML
    private Label infoHandLevel;
    @FXML
    private Label infoHandChips;
    @FXML
    private Label infoHandMulti;
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
    private Label pointsScored;
    @FXML
    private AnchorPane gameScreenAnchor;


    public ImageView imageViewDeckField;

    private final FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private BlindPickPanels smallController;
    private BlindPickPanels bigController;
    private BlindPickPanels bossController;

    static boolean blindsToggled = false;

    static Random rand;
    static int ante = 1;
    static int phase = 1;
    static int round = 1;
    static int money = 0;
    static int hands;
    static int handsize = 8;
    static int discards;
    static int baseChips = 0;
    static int baseMulti = 0;
    static BigDecimal scoreToReach = new BigDecimal(0);
    static int scored = 0;
    static Deck deck;
    static Stake stake;
    static int selectedCardCounter = 0;
    static boolean handButtonHidden = false;

    static List<Blind> blindList;
    private ArrayList<Blind> gameBlindsList = new ArrayList<>();

    static List<Tag> tagList;
    static List<Hand> handList;

    static BigDecimal[] chipRequirement = new BigDecimal[]{BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800), BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000), BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};

    private List<PlayingCard> deckList = new ArrayList<>();
    static List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();
    private List<PlayingCard> selectedCards = new ArrayList<>();
    private List<PlayingCard> handCards = new ArrayList<>();

    public void initialize(){
        Balatro.gameScreenController = this;
        AnchorPane smallBlind = null;
        AnchorPane bigBlind = null;
        AnchorPane boss = null;


        try {
            smallBlind = loaderSmall.load();
            smallController = loaderSmall.getController();
            smallBlindAnchor.getChildren().add(smallBlind);
            smallController.setGameScreenController(this);

            bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigBlindAnchor.getChildren().add(bigBlind);
            bigController.setGameScreenController(this);

            boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossAnchor.getChildren().add(boss);
            bossController.setGameScreenController(this);

            blindList = SqlHandler.getAllBlinds();
            tagList = SqlHandler.getAllTags();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setHandList();
        setPlayingDeck();
        rand = new Random();
        hideHandButtons();
    }

    private void setPlayingDeck() {
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                deckList.add(new PlayingCard(j,i));
            }
        }
        playingCardList.addAll(deckList);

        Collections.shuffle(playingCardList, new Random());
        for(int i = 0; i < 52; i++) {
            System.out.println(deckList.get(i).getRank() + " "+ deckList.get(i).getSuit() + " | " + playingCardList.get(i).getRank() + " " + playingCardList.get(i).getSuit());
        }
    }

    private void setHandList() {
        handList = new ArrayList<>();
        handList.add(new Hand("Flush Five",     160, 16));
        handList.add(new Hand("Flush House",    140, 14));
        handList.add(new Hand("Five of a Kind", 120, 12));
        handList.add(new Hand("Royal Flush",    100, 8));
        handList.add(new Hand("Straight Flush", 100, 8));
        handList.add(new Hand("Four of a Kind", 60, 7));
        handList.add(new Hand("Full House",     40, 4));
        handList.add(new Hand("Flush",          35, 4));
        handList.add(new Hand("Straight",       30, 4));
        handList.add(new Hand("Three of a Kind",30, 3));
        handList.add(new Hand("Two Pair",       20, 2));
        handList.add(new Hand("One Pair",       10, 2));
        handList.add(new Hand("High Card",      5, 1));
    }

    public void startNewGame(GameSetup gameSetup) {
        rand = new Random();
        deck = gameSetup.getChosenDeck();
        stake = gameSetup.getChosenStake();
        hands = 3;
        discards = 3;
        ante = 1;
        phase = 1;
        round = 1;
        money = 0;

        createBlindList();
        setBlindPanels();

        try {
            setDeckImage();
            setStakeImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBlindList() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 0)
                    gameBlindsList.add(blindList.get(0));
                else if(j == 1)
                    gameBlindsList.add(blindList.get(1));
                else
                    gameBlindsList.add(blindList.get(rand.nextInt(blindList.size() - 2 + 1) + 1));
            }
        }
    }

    private void setBlindPanels() {
        smallBlindAnchor.setTranslateY(590);
        smallController.setButtonText("Select");
        smallController.setBossPanel(false);
        smallController.setActivity(false);
        smallController.setEarn(3);
        smallController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(blindList.get((ante-1)* 3).getBlindScoreMultiplier().replace("x base",""))));
        smallController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        smallController.setBlindImage(new Image("file:"+gameBlindsList.get((ante-1)*3).getBlindImageUrl()));
        smallController.setBlind(gameBlindsList.get((ante-1)*3));

        bigBlindAnchor.setTranslateY(590);
        bigController.setButtonText("Select");
        bigController.setBossPanel(false);
        bigController.setActivity(false);
        bigController.setEarn(4);
        bigController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(blindList.get((ante-1)* 3 +1).getBlindScoreMultiplier().replace("x base",""))));
        bigController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        bigController.setBlindImage(new Image("file:"+gameBlindsList.get((ante-1)*3+1).getBlindImageUrl()));
        bigController.setBlind(gameBlindsList.get((ante-1)*3+1));

        bossAnchor.setTranslateY(590);
        bossController.setButtonText("Select");
        bossController.setBossPanel(true);
        bossController.setActivity(false);
        bossController.setEarn(5);
        bossController.setMinScore(chipRequirement[ante].multiply(new BigDecimal(blindList.get((ante-1)* 3+2).getBlindScoreMultiplier().replace("x base",""))));
        bossController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        bossController.setBlindImage(new Image("file:"+gameBlindsList.get((ante-1)*3+2).getBlindImageUrl()));
        bossController.setBlind(gameBlindsList.get((ante-1)*3+2));

        toggleBlind(true);
    }

    private void setDeckImage() throws IOException {
        Image image = new Image("file:" + deck.getDeckCoverUrl());
        imageViewDeckField.setImage(image);
    }

    private void setStakeImage() throws IOException {
        roundScoreStakeImage.setImage(new Image("file:"+stake.getStakeImageChipUrl()));
    }

    private void nextPhase() {
        phase++;
        if(phase > 2){ phase = 0; }
    }

    private void skipBlind(Tag tag) {
        nextPhase();
    }

    public void toggleBlind(boolean toggle) {
        TranslateTransition transitionSmall = new TranslateTransition(Duration.seconds(.5), smallBlindAnchor);
        TranslateTransition transitionBig = new TranslateTransition(Duration.seconds(.5), bigBlindAnchor);
        TranslateTransition transitionBoss = new TranslateTransition(Duration.seconds(.5), bossAnchor);
        if (toggle) {
            if(round%3 == 1) transitionSmall.setToY(-50);
            else transitionSmall.setToY(0);
            if(round%3 == 2)
            transitionBig.setToY(-50);
            else transitionBig.setToY(0);
            if(round%3 == 0)
            transitionBoss.setToY(-50);
            else transitionBoss.setToY(0);
        } else {
            transitionSmall.setToY(590);
            transitionBig.setToY(590);
            transitionBoss.setToY(590);

        }
        transitionSmall.play();
        transitionBig.play();
        transitionBoss.play();
    }

    public void startRound(Blind blind, BigDecimal score) {
        hideHandButtons();
        scoreToReach = score;
        toggleBlind(false);
        labelBlind.setText(blind.getBlindName());
        drawCards(8);
        toBeatScore.setText(String.valueOf(scoreToReach));
        toBeatImage.setImage(new Image("file:"+blind.getBlindImageUrl()));
        toBeatStake.setImage(new Image("file:"+stake.getStakeImageChipUrl()));
        toBeatReward.setText("$$$");
    }

    public static void skip() {

    }

    public void drawCard() {
        testImageView.setImage(playingCardList.get(0).getImage());
        playingCardList.get(0).setOnMouseClicked(mouseEvent -> {
            playingCardClicked((PlayingCard) mouseEvent.getSource());
        });

        HoldingHand.getChildren().add(playingCardList.get(0));
        handCards.add(playingCardList.get(0));
        playingCardList.remove(0);
        moveCards();
    }

    private void playingCardClicked(PlayingCard card) {

        if(!card.isClickAble()) return;

        if(card.getTranslateY() == 0 && selectedCardCounter < 5) {
            selectedCards.add(card);
            card.setTranslateY(-20);

            selectedCardCounter++;
            setHandInfo(checkHand.evaluateHands(selectedCards));
        }
        else if(card.getTranslateY() == -20) {
            selectedCards.remove(card);
            card.setTranslateY(0);
            selectedCardCounter--;
            if(selectedCardCounter > 0)
                setHandInfo(checkHand.evaluateHands(selectedCards));
        }
    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {drawCard();}
    }

    public void moveCards() {
        int cardsize = 140;
        int lastPos = 570;

        int cards = HoldingHand.getChildren().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                HoldingHand.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                HoldingHand.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardsize/2 + i * cardsize - cards/2*cardsize;
                } else {
                    pos = i * cardsize - cards/2*cardsize;
                }
            }
            HoldingHand.getChildren().get(i).setTranslateX(pos);
        }
    }

    public void playSelectetCards(ActionEvent actionEvent) {

        hideHandButtons();
        if(selectedCardCounter != 0) {
            hbPlayedCards.getChildren().addAll(selectedCards);

            List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(selectedCards , infoHand.getText());
            for(int i = 0; i < hbPlayedCards.getChildren().size(); i++) {
                hbPlayedCards.getChildren().get(i).setTranslateX(0);
                if(countedCards.contains(hbPlayedCards.getChildren().get(i))) {
                    hbPlayedCards.getChildren().get(i).setTranslateY(-20);
                } else
                    hbPlayedCards.getChildren().get(i).setTranslateY(0);
            }

            baseChips = handList.stream().filter(name -> name.getName() == infoHand.getText()).collect(Collectors.toList()).get(0).getChips();
            baseMulti = handList.stream().filter(name -> name.getName() == infoHand.getText()).collect(Collectors.toList()).get(0).getMulti();

            selectedCards.clear();
            moveCards();
        }

        countPoints();

        delay(2000,() ->hbPlayedCards.getChildren().clear());

        drawCards(8 - HoldingHand.getChildren().size());
        selectedCardCounter = 0;
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }

    private void countPoints() {
        for(int i = 0; i < hbPlayedCards.getChildren().size(); i++) {
            System.out.println(hbPlayedCards.getChildren().get(i).getTranslateY());
            if(hbPlayedCards.getChildren().get(i).getTranslateY() == -20) {
                System.out.println(((PlayingCard)hbPlayedCards.getChildren().get(i)).getValue());
                baseChips += ((PlayingCard)hbPlayedCards.getChildren().get(i)).getValue();
            }
        }

        scored += baseChips * baseMulti;
        pointsScored.setText(String.valueOf(scored));
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        if(selectedCardCounter != 0) {
            for(int i = 0; i < selectedCardCounter; i++) {
                HoldingHand.getChildren().remove(selectedCards.get(i));
                handCards.remove(selectedCards.get(i));
            }
        }
        selectedCardCounter = 0;
        selectedCards.clear();

        drawCards(8 - handCards.size());
        setHandInfo(new ArrayList<>());
    }

    public void hideHandButtons() {
        handButtonHidden = !handButtonHidden;
        if(handButtonHidden) {
            HoldingHand.setTranslateY(100);
            handButtonBox.setTranslateY(100);
        } else {
            HoldingHand.setTranslateY(0);
            handButtonBox.setTranslateY(0);
        }
    }

    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;
        int bestHandIndex = -1;
        for (Hand hand : handList) {
            if(hands.contains(hand.getName())) {
                System.out.println(hand.getName());
                int points = hand.getChips() * hand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    bestHandIndex = handList.indexOf(hand);
                }
            }
        }
        if(bestHandIndex != -1) {
            infoHand.setText(handList.get(bestHandIndex).getName());
            infoHandLevel.setText("lvl. " + handList.get(bestHandIndex).getLevel());
            infoHandChips.setText(""+handList.get(bestHandIndex).getChips());
            infoHandMulti.setText(""+handList.get(bestHandIndex).getMulti());
        } else {
            infoHand.setText("");
            infoHandLevel.setText("");
            infoHandChips.setText("0");
            infoHandMulti.setText("0");
        }

    }


}
