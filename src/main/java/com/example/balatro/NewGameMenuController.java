package com.example.balatro;

import com.example.balatro.classes.Deck;
import com.example.balatro.classes.SqlHandler;
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

    private List<Deck> deckList;
    private int activeDeck = 0;

    public void initialize() {
        deckList = SqlHandler.getAllDecks();

        changeDeck(0);
    }

    private void changeDeck(int deckIndex) {
        labelDeckName.setText(deckList.get(deckIndex).getName());
        System.out.println(deckList.get(deckIndex).getDeckCoverUrl());
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
    }

    private void nextDeck() {
        changeDeck(++activeDeck);
    }

    private void prevDeck() {
        changeDeck(--activeDeck);
    }
}
