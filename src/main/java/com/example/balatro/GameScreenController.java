package com.example.balatro;


import com.example.balatro.classes.*;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public ImageView imageViewDeckField;

    static List<Blind> blindList;
    static List<Tag> tagList;

    static List<Hand> handList;
    static List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();

    static Random rand;
    static Deck deck;
    static Stake stake;

    static int hands;
    static int discards;


    static int ante = 1;
    static int phase = 1;
    static int round = 1;
    static int money = 0;
    static BigInteger[] chipRequirement = new BigInteger[]{BigInteger.valueOf(100), BigInteger.valueOf(300), BigInteger.valueOf(800), BigInteger.valueOf(2000), BigInteger.valueOf(5000), BigInteger.valueOf(11000), BigInteger.valueOf(20000), BigInteger.valueOf(35000), BigInteger.valueOf(50000)};
    static boolean blindsToggled = false;
    private final FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private final FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private BlindPickPanels smallController;
    private BlindPickPanels bigController;
    private BlindPickPanels bossController;

    private ArrayList<Blind> gameBlindsList = new ArrayList<>();

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
            /*smallController.setButtonText("Select");
            smallController.setBossPanel(false);*/

            bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigBlindAnchor.getChildren().add(bigBlind);
            bigController.setGameScreenController(this);
            /*bigController.setButtonText("Upcoming");
            bigController.setBossPanel(false);
            bigController.setActivity(true);*/

            boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossAnchor.getChildren().add(boss);
            bossController.setGameScreenController(this);
            /*bossController.setButtonText("Upcoming");
            bossController.setBossPanel(true);
            bossController.setActivity(true);*/

            blindList = SqlHandler.getAllBlinds();
            tagList = SqlHandler.getAllTags();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setHandList();
        setPlayingDeck();
    }

    private void setPlayingDeck() {
        for(int i = 0; i < 4; i++ ){
            for(int j = 0; j < 13; j++){
                playingCardList.add(new PlayingCard(j,i));
            }
        }
        Collections.shuffle(playingCardList);
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
        handList.add(new Hand("Pair",           10, 2));
        handList.add(new Hand("High Card",      5, 1));
    }

    public void startNewGame(GameSetup gameSetup) {
        rand = new Random();
        deck = gameSetup.getChosenDeck();
        stake = gameSetup.getChosenStake();
        hands = 3;
        discards = 3;
        playingCardList.clear();
        playingCardList = new StandartDeck().getPlayingCards();
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
        smallController.setMinScore(chipRequirement[0]);
        smallController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        smallController.setBlindImage(new Image("file:"+gameBlindsList.get((ante-1)*3).getBlindImageUrl()));
        smallController.setBlind(gameBlindsList.get((ante-1)*3));

        bigBlindAnchor.setTranslateY(590);
        bigController.setButtonText("Select");
        bigController.setBossPanel(false);
        bigController.setActivity(false);
        bigController.setEarn(4);
        bigController.setMinScore(chipRequirement[0]);
        bigController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        bigController.setBlindImage(new Image("file:"+gameBlindsList.get((ante-1)*3+1).getBlindImageUrl()));
        bigController.setBlind(gameBlindsList.get((ante-1)*3+1));

        bossAnchor.setTranslateY(590);
        bossController.setButtonText("Select");
        bossController.setBossPanel(true);
        bossController.setActivity(false);
        bossController.setEarn(5);
        bossController.setMinScore(chipRequirement[0]);
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

    public void toggleBlinds() {
        blindsToggled = !blindsToggled;
        toggleBlind(blindsToggled);
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


    public void startRound(Blind blind, BigInteger score) {
        toggleBlind(false);
        labelBlind.setText(blind.getBlindName());
    }

    public static void skip() {

    }

    public void drawCard() {
        System.out.println("drawCard");
        testImageView.setImage(playingCardList.get(0).getImage());
        ImageView imageView = new ImageView(playingCardList.get(0).getImage());
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        HoldingHand.getChildren().add(imageView);
        playingCardList.remove(0);
        moveCards();
    }

    public void drawCards(int num) {
        for (int i = 0; i < num; i++) {drawCard();}
    }

    public void moveCards() {
        System.out.println(HoldingHand.getChildren());
        int cards = HoldingHand.getChildren().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards%2==0) {
                    pos = 70 + i * 140 - cards/2*140;
            } else {
                pos = i * 140 - cards/2*140;
            }

            HoldingHand.getChildren().get(i).setTranslateX(pos);
        }
    }
}
