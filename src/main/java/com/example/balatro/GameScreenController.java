package com.example.balatro;

import com.example.balatro.classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    static Deck deck;
    static Stake stake;
    static int hands;
    static int discards;
    static List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();
    public ImageView imageViewDeckField;
    static int phase = 0;


    public void initialize(){
        Balatro.gameScreenController = this;
        AnchorPane smallBlind = null;
        AnchorPane bigBlind = null;
        AnchorPane boss = null;

        BlindPickPanels smallController = null;
        BlindPickPanels bigController = null;
        BlindPickPanels bossController = null;

        try {
            FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
            smallBlind = loaderSmall.load();
            smallController = loaderSmall.getController();
            smallBlindAnchor.getChildren().add(smallBlind);
            smallController.setButtonText("Select");
            smallController.setBossPanel(false);

            FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
            bigBlind = loaderBig.load();
            bigController = loaderBig.getController();
            bigBlindAnchor.getChildren().add(bigBlind);
            bigController.setButtonText("Upcoming");
            bigController.setBossPanel(false);
            bigController.setActivity(false);

            FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("blindPickPanels.fxml"));
            boss = loaderBoss.load();
            bossController = loaderBoss.getController();
            bossAnchor.getChildren().add(boss);
            bossController.setButtonText("Upcoming");
            bossController.setBossPanel(true);
            bossController.setActivity(false);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void startNewGame(GameSetup gameSetup) {
        deck = gameSetup.getChosenDeck();
        stake = gameSetup.getChosenStake();
        hands = 3;
        discards = 3;
        playingCardList.clear();
        playingCardList = new StandartDeck().getPlayingCards();
        phase = 0;

        try {
            setDeckImage();
            setStakeImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void OnButtonClicked(){

    }


}
