package com.example.balatro.classes;

import java.util.ArrayList;
import java.util.List;

public class StandartDeck {

    private List<PlayingCard> playingCards = new ArrayList<PlayingCard>();
    private String[] suits = {"diamond", "heart", "spades", "clubs"};


    public StandartDeck() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 13; j++)
                playingCards.add(new PlayingCard(j,i));
    }

    public List<PlayingCard> getPlayingCards() {
        return playingCards;
    }

    public void showDeck() {
        for (PlayingCard playingCard : playingCards) {
            System.out.println(playingCard.getRank() + playingCard.getSuit());
        }
    }
}
