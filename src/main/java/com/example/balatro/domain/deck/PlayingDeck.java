package com.example.balatro.domain.deck;

import com.example.balatro.domain.card.PlayingCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class PlayingDeck {

    private final ObservableList<PlayingCard> fullDeck = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> playDeck = FXCollections.observableArrayList();

    //region Getter Setter
    public ObservableList<PlayingCard> getFullDeck() {
        return fullDeck;
    }

    public ObservableList<PlayingCard> getPlayDeck() {
        return playDeck;
    }

    public int getFullSize() {
        return fullDeck.size();
    }

    public int getPlaySize() {
        return playDeck.size();
    }
    //endregion

    //region Functions
    //Full Deck
    public void setFullDeck(List<PlayingCard> fullDeck) {
        System.out.println("set full deck");
        System.out.println("set full deck size: " + fullDeck.size());
        this.fullDeck.addAll(fullDeck);
        System.out.println("set this full deck size: " + this.fullDeck.size());
    }

    public List<PlayingCard> drawFromFullDeck(int cardCount, Random random) {
        Set<Integer> set = new HashSet<>();
        while (set.size() < cardCount) {
            set.add(random.nextInt(fullDeck.size()));
        }
        List<PlayingCard> cards = new ArrayList<>();
        for (Integer i : set) {
            cards.add(fullDeck.get(i));
        }
        return cards;
    }

    public void addCard(PlayingCard card)
    {
        fullDeck.add(card);
    }

    //Play Deck
    public void shuffleDeck()
    {
        playDeck.setAll(fullDeck);
        Collections.shuffle(playDeck);
    }

    public PlayingCard drawCard()
    {
        if (playDeck.isEmpty()) return null;
        return playDeck.removeFirst();
    }

    public void removeCards(List<PlayingCard> cards) {
        for (PlayingCard card : cards) {
            fullDeck.remove(card);
        }
    }
    public void removeCard(PlayingCard card)
    {
        fullDeck.remove(card);
    }


    //endregion
}
