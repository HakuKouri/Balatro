package com.example.balatro.interfaces;

import com.example.balatro.models.GameModel;

public interface PlayableCard {
    boolean canPlay(GameModel model);
    void play(GameModel model, Runnable runnable);

}
