package com.example.balatro.interfaces;

import com.example.balatro.classes.Joker;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.GameModel;

import java.util.List;

public interface JokerEffect {
    void apply(GameModel gameModel, Joker self, List<PlayingCard> playedCards);
}