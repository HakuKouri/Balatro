package com.example.balatro;

<<<<<<< HEAD
import com.example.balatro.classes.GameSetup;

public class GameScreenController
{
    public static void startNewGame(GameSetup gameSetup) {

    }
=======
import com.example.balatro.classes.Deck;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.Stake;
import com.example.balatro.classes.StandartDeck;

import java.util.ArrayList;
import java.util.List;

public class GameScreenController
{

    Deck deck;
    Stake stake;
    int hands;
    int discards;
    List<PlayingCard> playingCardList = new ArrayList<PlayingCard>();


    public void startNewGame() {
        deck = new Deck();
        stake = new Stake();
        hands = 3;
        discards = 3;
        playingCardList.clear();
        playingCardList = new StandartDeck().getPlayingCards();
    }



>>>>>>> game-code
}
