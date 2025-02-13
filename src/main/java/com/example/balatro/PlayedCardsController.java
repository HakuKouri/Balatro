package com.example.balatro;

import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.PlayedCardsModel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.List;

public class PlayedCardsController {

    public StackPane playedCards;

    PlayedCardsModel model = new PlayedCardsModel();

    public ObservableList<Node> getPlayedCards() {
        return playedCards.getChildren();
    }

    public void addCard(PlayingCard card) {
        playedCards.getChildren().add(card);
        moveCards();
    }

    public void addAllCards(List<PlayingCard> cards) {
        playedCards.getChildren().addAll(cards);
        moveCards();
    }

    public void removeCard(PlayingCard card) {
        playedCards.getChildren().remove(card);
    }

    public void removeAllCards() {
        playedCards.getChildren().clear();
    }

    public void moveCards() {
        int cardsize = 140;
        int lastPos = 570;

        int cards = playedCards.getChildren().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                playedCards.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                playedCards.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardsize/2 + i * cardsize - cards/2*cardsize + i * 5;
                } else {
                    pos = i * cardsize - cards/2*cardsize + i * 5;
                }
            }
            playedCards.getChildren().get(i).setTranslateX(pos);
        }
    }

    public int count() {
        return playedCards.getChildren().size();
    }

}
