package com.example.balatro.classes;

public class GameSetup {
    private Deck chosenDeck;
    private Stake chosenStake;

    public Deck getChosenDeck() {
        return chosenDeck;
    }

    public void setChosenDeck(Deck chosenDeck) {
        this.chosenDeck = chosenDeck;
    }

    public Stake getChosenStake() {
        return chosenStake;
    }

    public void setChosenStake(Stake chosenStake) {
        this.chosenStake = chosenStake;
    }
}
