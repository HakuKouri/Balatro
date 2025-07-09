package com.example.balatro.models;

import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.util.CardViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoosterDrawModel {

    private final ObservableList<PlayingCard> playingCardsDrawn = FXCollections.observableArrayList();
    private final CardViewManager boosterDrawnManager = new CardViewManager(true);

    public ObservableList<PlayingCard> getPlayingCardsDrawn() {
        return playingCardsDrawn;
    }

    public CardViewManager getBoosterDrawnManager() {
        return boosterDrawnManager;
    }
}
