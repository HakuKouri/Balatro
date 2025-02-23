package com.example.balatro.models;

import com.example.balatro.classes.PlayingCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class HoldingHandModel {

    private final ObservableList<PlayingCard> handCards = FXCollections.observableArrayList();
    private final List<PlayingCard> selectedCards = new ArrayList<>();
    int selectedCardCounter = 0;
    boolean sortedByrankd = true;

    public ObservableList<PlayingCard> getList() {
        return handCards;
    }

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

    public void clearHandCards() {
        handCards.clear();
    }

    public boolean isSortedByrankd() {
        return sortedByrankd;
    }

    public void setSortedByrankd(boolean sortedByrankd) {
        this.sortedByrankd = sortedByrankd;
    }
}
