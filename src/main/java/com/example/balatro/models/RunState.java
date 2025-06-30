package com.example.balatro.models;

import com.example.balatro.classes.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class RunState {
    //region VARIABLES
    private final ObjectProperty<Deck> chosenDeck = new SimpleObjectProperty<>(new Deck());
    private final ObjectProperty<Stake> chosenStake = new SimpleObjectProperty<>(new Stake());

    private final ObservableList<PlayingCard> deckFull = FXCollections.observableArrayList();
    private final ObservableList<Blind> bossBlindsBeaten = FXCollections.observableArrayList();

    private final List<Voucher> broughtVoucher = new ArrayList<>();
    private final IntegerProperty maxHandSize = new SimpleIntegerProperty(8);
    private final IntegerProperty maxHands = new SimpleIntegerProperty(4);
    private final IntegerProperty maxDiscards = new SimpleIntegerProperty(3);

    private final IntegerProperty money = new SimpleIntegerProperty(3);
    private final IntegerProperty ante = new SimpleIntegerProperty(0);
    private final IntegerProperty round = new SimpleIntegerProperty(-1);
    private final IntegerProperty handsPlayed = new SimpleIntegerProperty(0);
    private final IntegerProperty handsDiscarded = new SimpleIntegerProperty(0);

    private final IntegerProperty maxJokers = new SimpleIntegerProperty(5);
    private final IntegerProperty maxConsumables = new SimpleIntegerProperty(2);
    //endregion

    //region CONSTRUCTOR

    //endregion

    //region GETTER SETTER
    public Deck getChosenDeck() {
        return chosenDeck.get();
    }

    public ObjectProperty<Deck> chosenDeckProperty() {
        return chosenDeck;
    }

    public void setChosenDeck(Deck chosenDeck) {
        this.chosenDeck.get().setDeck(chosenDeck);
    }

    //Chosen Stake
    public Stake getChosenStake() {
        return chosenStake.get();
    }

    public ObjectProperty<Stake> chosenStakeProperty() {
        return chosenStake;
    }

    public ObservableList<PlayingCard> getDeckFull() {
        return deckFull;
    }

    public void setDeckFull(List<PlayingCard> deck) {
        deckFull.clear();
        deckFull.addAll(deck);
    }

    public void addCardToDeckFull(PlayingCard card) {
        deckFull.add(card);
    }

    public void removeCardFromDeckFull(PlayingCard card) {
        deckFull.remove(card);
    }

    public ObservableList<Blind> getBossBlindsBeaten() {
        return bossBlindsBeaten;
    }

    public List<Voucher> getBroughtVoucher() {
        return broughtVoucher;
    }

    public int getMaxHandSize() {
        return maxHandSize.get();
    }

    public IntegerProperty maxHandSizeProperty() {
        return maxHandSize;
    }

    public void setMaxHandSize(int size) {
        maxHandSize.set(size);
    }

    public void increaseHandSizeBy(int add) {
        maxHandSize.set(maxHandSize.get() + add);
    }

    public void decreaseHandSizeBy(int sub) {
        maxHandSize.set(maxHandSize.get() + sub);
    }


    public int getMaxHands() {
        return maxHands.get();
    }

    public IntegerProperty maxHandsProperty() {
        return maxHands;
    }

    public void setMaxHands(int maxHands) {
        this.maxHands.set(maxHands);
    }

    public int getMaxDiscards() {
        return maxDiscards.get();
    }

    public IntegerProperty maxDiscardsProperty() {
        return maxDiscards;
    }

    public void setMaxDiscards(int maxDiscards) {
        this.maxDiscards.set(maxDiscards);
    }

    //Money
    public int getMoney() {
        return money.get();
    }

    public IntegerProperty moneyProperty() {
        return money;
    }

    public void setMoney(int money) {
        this.money.set(money);
    }

    public void addMoney(int money) {
        setMoney(getMoney() + money);
    }

    public void subMoney(int money) { setMoney(getMoney() - money); }

    //Aktueller Ante

    public int getAnte() {
        return ante.get();
    }

    public IntegerProperty anteProperty() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante.set(ante);
    }

    public void incrementAnte() {
        setAnte(getMaxAnte() + 1);
    }

    public void decrementAnte() {
        setAnte(getAnte() - 1);
    }

    //Aktuelle Round
    public int getRound() {
        return round.get();
    }

    public IntegerProperty roundProperty() {
        return round;
    }

    public void setRound(int round) {
        this.round.set(round);
    }

    //Max Ante
    public int getMaxAnte() {
        return 8;
    }

    //Counter für Played Hands
    public int getHandsPlayed() {
        return handsPlayed.get();
    }

    public IntegerProperty handsPlayedProperty() {
        return handsPlayed;
    }

    public void setHandsPlayed(int handsPlayed) {
        this.handsPlayed.set(handsPlayed);
    }

    public void incrementHandsPlayed() {
        setHandsPlayed(getHandsPlayed() + 1);
    }

    //Counter für Discarded Hands
    public int getHandsDiscarded() {
        return handsDiscarded.get();
    }

    public IntegerProperty handsDiscardedProperty() {
        return handsDiscarded;
    }

    public void setHandsDiscarded(int handsDiscarded) {
        this.handsDiscarded.set(handsDiscarded);
    }

    public void incrementHandsDiscarded() {
        setHandsDiscarded(getHandsDiscarded() + 1);
    }

    public int getMaxJokers() {
        return maxJokers.get();
    }

    public IntegerProperty maxJokersProperty() {
        return maxJokers;
    }

    public void setMaxJokers(int maxJokers) {
        this.maxJokers.set(maxJokers);
    }

    public int getMaxConsumables() {
        return maxConsumables.get();
    }

    public IntegerProperty maxConsumablesProperty() {
        return maxConsumables;
    }

    public void setMaxConsumables(int maxConsumables) {
        this.maxConsumables.set(maxConsumables);
    }

    //endregion
}
