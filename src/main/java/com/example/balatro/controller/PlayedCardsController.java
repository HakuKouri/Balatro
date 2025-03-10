package com.example.balatro.controller;

import com.example.balatro.classes.PlayingCard;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.List;

public class PlayedCardsController {

    public StackPane playedCards_StackPane;

    PlayedCardsModel model = new PlayedCardsModel();

    public ObservableList<Node> getPlayedCards_StackPane() {
        return playedCards_StackPane.getChildren();
    }

    public void addCard(PlayingCard card) {
        playedCards_StackPane.getChildren().add(card);
        moveCards();
    }

    public void addAllCards(List<PlayingCard> cards) {
        playedCards_StackPane.getChildren().addAll(cards);
        moveCards();
    }

    public void removeCard(PlayingCard card) {
        playedCards_StackPane.getChildren().remove(card);
    }

    public void removeAllCards() {
        playedCards_StackPane.getChildren().clear();
    }

    public void moveCards() {
        int cardsize = 140;
        int lastPos = 570;

        int cards = playedCards_StackPane.getChildren().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                playedCards_StackPane.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                playedCards_StackPane.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardsize/2 + i * cardsize - cards/2*cardsize + i * 5;
                } else {
                    pos = i * cardsize - cards/2*cardsize + i * 5;
                }
            }
            playedCards_StackPane.getChildren().get(i).setTranslateX(pos);
        }
    }

    public int count() {
        return playedCards_StackPane.getChildren().size();
    }

}
