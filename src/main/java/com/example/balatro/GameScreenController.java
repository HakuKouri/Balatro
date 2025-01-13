package com.example.balatro;

import com.example.balatro.classes.*;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.RadialGradient;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


    static List<Blind> blindList;
    static List<Tag> tagList;


    static Random rand;
    static Deck deck;
    static Stake stake;
    static int hands;
    static int discards;
    static List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();
    public ImageView imageViewDeckField;
    static int phase = 1;
    static int round = 1;
    static int money = 0;
    static BigInteger[] chipRequirement = new BigInteger[]{BigInteger.valueOf(100), BigInteger.valueOf(300), BigInteger.valueOf(800), BigInteger.valueOf(2000), BigInteger.valueOf(5000), BigInteger.valueOf(11000), BigInteger.valueOf(20000), BigInteger.valueOf(35000), BigInteger.valueOf(50000)};
    static boolean blindsToggled = false;
    private FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private ArrayList<Blind> gameBlindsList = new ArrayList<>();

    public void initialize(){
        Balatro.gameScreenController = this;
        AnchorPane smallBlind = null;
        AnchorPane bigBlind = null;
        AnchorPane boss = null;

        BlindPickPanels smallController = null;
        BlindPickPanels bigController = null;
        BlindPickPanels bossController = null;

        try {
            smallBlind = loaderSmall.load();
            smallController = loaderSmall.getController();
            smallBlindAnchor.getChildren().add(smallBlind);
            smallController.setButtonText("Select");
            smallController.setBossPanel(false);


            bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigBlindAnchor.getChildren().add(bigBlind);
            bigController.setButtonText("Upcoming");
            bigController.setBossPanel(false);
            bigController.setActivity(false);


            boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossAnchor.getChildren().add(boss);
            bossController.setButtonText("Upcoming");
            bossController.setBossPanel(true);
            bossController.setActivity(false);

            blindList = SqlHandler.getAllBlinds();
            tagList = SqlHandler.getAllTags();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNewGame(GameSetup gameSetup) {
        rand = new Random();
        deck = gameSetup.getChosenDeck();
        stake = gameSetup.getChosenStake();
        hands = 3;
        discards = 3;
        playingCardList.clear();
        playingCardList = new StandartDeck().getPlayingCards();
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

    private void setBlindPanels() {
        BlindPickPanels small =  loaderSmall.getController();
        small.setButtonText("Select");
        small.setBossPanel(false);
        small.setActivity(false);
        small.setEarn(3);
        small.setMinScore(chipRequirement[0]);
        small.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        small.setBlindImage(new Image("file:"+gameBlindsList.get(0).getBlindImageUrl()));
        //TODO
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

    private void setDeckImage() throws IOException {
        Image image = new Image("file:" + deck.getDeckCoverUrl());
        imageViewDeckField.setImage(image);
    }

    private void setStakeImage() throws IOException {
        roundScoreStakeImage.setImage(new Image("file:"+stake.getStakeImageChipUrl()));
    }

    private void setBlindName() {
        //labelBlind.setText();
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
        toggleBlind(blindsToggled, 2);
    }

    public void toggleBlind(boolean toggle, int round) {
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


}
