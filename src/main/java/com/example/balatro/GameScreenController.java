package com.example.balatro;

<<<<<<< HEAD
<<<<<<< HEAD
import com.example.balatro.classes.GameSetup;

public class GameScreenController
{
    public static void startNewGame(GameSetup gameSetup) {

    }
=======
import com.example.balatro.classes.Deck;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.Stake;
import com.example.balatro.classes.StandartDeck;

=======
import com.example.balatro.classes.*;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;
import java.math.BigInteger;
>>>>>>> 7003cb2527a7daf0ec1b4e7b1498fda0ca950cce
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

    static List<Hand> handList;
    static List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();

    static Random rand;
    static Deck deck;
    static Stake stake;
    static int hands;
    static int discards;

    public ImageView imageViewDeckField;
    static int phase = 1;
    static int round = 1;
    static int money = 0;
    static BigInteger[] chipRequirement = new BigInteger[]{BigInteger.valueOf(100), BigInteger.valueOf(300), BigInteger.valueOf(800), BigInteger.valueOf(2000), BigInteger.valueOf(5000), BigInteger.valueOf(11000), BigInteger.valueOf(20000), BigInteger.valueOf(35000), BigInteger.valueOf(50000)};
    static boolean blindsToggled = false;
    private FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
    private BlindPickPanels smallController = null;
    private BlindPickPanels bigController = null;
    private BlindPickPanels bossController = null;


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

        setHandList();
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
        smallController.setButtonText("Upcoming");
        smallController.setBossPanel(false);
        smallController.setActivity(false);
        smallController.setEarn(3);
        smallController.setMinScore(chipRequirement[0]);
        smallController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        smallController.setBlindImage(new Image("file:"+gameBlindsList.get(0).getBlindImageUrl()));

        smallController.setButtonText("Select");
        smallController.setBossPanel(false);
        smallController.setActivity(false);
        smallController.setEarn(4);
        smallController.setMinScore(chipRequirement[0]);
        smallController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        smallController.setBlindImage(new Image("file:"+gameBlindsList.get(0).getBlindImageUrl()));

        smallController.setButtonText("Select");
        smallController.setBossPanel(false);
        smallController.setActivity(false);
        smallController.setEarn(5);
        smallController.setMinScore(chipRequirement[0]);
        smallController.setStakeImage(new Image("file:"+stake.getStakeImageChipUrl()));
        smallController.setBlindImage(new Image("file:"+gameBlindsList.get(0).getBlindImageUrl()));
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



<<<<<<< HEAD
>>>>>>> game-code
=======
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


>>>>>>> 7003cb2527a7daf0ec1b4e7b1498fda0ca950cce
}
