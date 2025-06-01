package com.example.balatro.interfaces;

import com.example.balatro.classes.Joker;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.GameModel;

import java.util.List;
import java.util.Map;

public interface JokerEffect {
        void apply(GameModel context, Joker self, List<PlayingCard> cards, Map<String, Object> params);
}
