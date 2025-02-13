package com.example.balatro.models;

import com.example.balatro.classes.PlayingCard;

import java.util.ArrayList;
import java.util.List;

public class HoldingHandModel {

    private final List<PlayingCard> handCards = new ArrayList<>();
    private final List<PlayingCard> selectedCards = new ArrayList<>();
    int selectedCardCounter = 0;

    public int getSelectedCardCounter() {
        return selectedCardCounter;
    }

    public void setSelectedCardCounter(int selectedCardCounter) {
        this.selectedCardCounter = selectedCardCounter;
    }

    public List<PlayingCard> getHandCards() {
        return handCards;
    }

    public List<PlayingCard> getSelectedCards() {
        return selectedCards;
    }


}
