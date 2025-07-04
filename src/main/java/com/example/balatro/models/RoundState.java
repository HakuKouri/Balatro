package com.example.balatro.models;

import com.example.balatro.domain.rules.PokerHand;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoundState {
    //region VARIABLES
    private final BooleanProperty firstHand = new SimpleBooleanProperty(true);
    private final BooleanProperty firstDiscard = new SimpleBooleanProperty(true);

    private final IntegerProperty hands = new SimpleIntegerProperty(4);
    private final IntegerProperty discards = new SimpleIntegerProperty(3);

    private final ObservableList<PokerHand> playedPokerHandsThisRound = FXCollections.observableArrayList();
    //endregion

    //region CONSTRUCTOR

    //endregion

    //region GETTER SETTER
    public boolean isFirstHand() {
        return firstHand.get();
    }

    public BooleanProperty firstHandProperty() {
        return firstHand;
    }

    public boolean isFirstDiscard() {
        return firstDiscard.get();
    }

    public BooleanProperty firstDiscardProperty() {
        return firstDiscard;
    }

    public int getHands() {
        return hands.get();
    }

    public IntegerProperty handsProperty() {
        return hands;
    }

    public void setHands(int hands) {
        this.hands.set(hands);
    }

    public void decrementHands() {
        setHands(getHands() - 1);
    }

    public int getDiscards() {
        return discards.get();
    }

    public IntegerProperty discardsProperty() {
        return discards;
    }

    public void setDiscards(int discards) {
        this.discards.set(discards);
    }

    public void decrementDiscards() {
        setDiscards(getDiscards() - 1);
    }

    public ObservableList<PokerHand> getPokerHandsPlayedThisRound() {
        return playedPokerHandsThisRound;
    }
    //endregion

    //region Functions

    //endregion

}
