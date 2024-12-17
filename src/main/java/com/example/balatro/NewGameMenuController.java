package com.example.balatro;

import com.example.balatro.classes.Deck;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.classes.Stake;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class NewGameMenuController
{
    @FXML
    private StackPane stackPaneDeck;
    @FXML
    private Label labelDeckName;
    @FXML
    private Label labelDeckEffect;
    @FXML
    private ImageView imageStakeChip;
    @FXML
    private Label labelStakeName;
    @FXML
    private Label labelStakeEffect;

    private List<Deck> deckList;
    private List<Stake> stakeList;
    private int activeDeck = 0;
    private int activeStack = 0;

    public void initialize() {
        deckList = SqlHandler.getAllDecks();
        stakeList = SqlHandler.getAllStakes();

        changeDeck(0);
        changeStake(0);
    }

    private void changeDeck(int deckIndex) {
        labelDeckName.setText(deckList.get(deckIndex).getName());
        ImageView deckCover = new ImageView(new Image("file:"+deckList.get(deckIndex).getDeckCoverUrl()));
        deckCover.setFitHeight(120);
        deckCover.setPreserveRatio(true);
        stackPaneDeck.getChildren().add(deckCover);
        if(deckList.get(deckIndex).getStageCleared() > 0) {
            ImageView stakeChip = new ImageView(new Image("file:/com/images/Stickers_Seals/difficult_"+ deckList.get(deckIndex).getStageCleared() +".png"));
            stakeChip.setFitHeight(120);
            stakeChip.setPreserveRatio(true);
            stackPaneDeck.getChildren().add(stakeChip);
        }

        labelDeckEffect.setText(deckList.get(deckIndex).getDescription());
    }

    private void changeStake(int stakeIndex) {
        System.out.printf(stakeList.get(stakeIndex).getStakeImageChipUrl());
        imageStakeChip.setImage(new Image("file:"+ stakeList.get(stakeIndex).getStakeImageChipUrl()));
        labelStakeEffect.setText(stakeList.get(stakeIndex).getStakeEffect());
        labelStakeName.setText(stakeList.get(stakeIndex).getStakeName());
    }

    //FXML FUNCTIONS
    public void nextDeck() { changeDeck(++activeDeck); }
    public void prevDeck() {
        changeDeck(--activeDeck);
    }

    public void nextStake() { changeStake(++activeStack); }
    public void prevStake() { changeStake(--activeStack); }
}
