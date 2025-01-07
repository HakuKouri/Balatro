package com.example.balatro;

import com.example.balatro.classes.*;

import java.util.ArrayList;
import java.util.List;

public class GameScreenController
{

    static Deck deck;
    static Stake stake;
    static int hands;
    static int discards;
    static List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();


    public static void startNewGame(GameSetup gameSetup) {
        deck = gameSetup.getChosenDeck();
        stake = gameSetup.getChosenStake();
        hands = 3;
        discards = 3;
        playingCardList.clear();
        playingCardList = new StandartDeck().getPlayingCards();
    }


}
