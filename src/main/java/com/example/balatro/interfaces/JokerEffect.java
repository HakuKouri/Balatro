package com.example.balatro.interfaces;

import com.example.balatro.domain.card.Integer;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.models.GameModel;

import java.util.List;
import java.util.Map;

public interface JokerEffect {
        void apply(GameModel context, Integer self, List<PlayingCard> cards, Map<String, Object> params);
}
