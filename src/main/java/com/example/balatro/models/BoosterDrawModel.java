package com.example.balatro.models;

import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.util.CardViewManager;

import java.util.List;


public class BoosterDrawModel {

    private final CardViewManager playingCardsDrawnViewManager = new CardViewManager(false, true, false);
    private final CardViewManager boosterDrawnManager = new CardViewManager(true, false, false);

    public List<PlayingCard> getPlayingCardsDrawn() {
        return playingCardsDrawnViewManager.getCardList().stream().map(card -> (PlayingCard) card ).toList();
    }

    public CardViewManager getPlayCardsDrawnViewManager() {
        return playingCardsDrawnViewManager;
    }

    public CardViewManager getBoosterDrawnManager() {
        return boosterDrawnManager;
    }
}
