package com.example.balatro.domain.game;

import com.example.balatro.domain.rules.Stake;
import com.example.balatro.domain.deck.SelectableDeck;

public class GameSetup {
    private SelectableDeck chosenSelectableDeck;
    private Stake chosenStake;

    public GameSetup() {}

    public GameSetup(SelectableDeck chosenDeck, Stake chosenStake) {
        this.chosenSelectableDeck = chosenDeck;
        this.chosenStake = chosenStake;
    }

    public SelectableDeck getChosenDeck() {
        return chosenSelectableDeck;
    }

    public void setChosenDeck(SelectableDeck chosenSelectableDeck) {
        this.chosenSelectableDeck = chosenSelectableDeck;
    }

    public Stake getChosenStake() {
        return chosenStake;
    }

    public void setChosenStake(Stake chosenStake) {
        this.chosenStake = chosenStake;
    }
}
