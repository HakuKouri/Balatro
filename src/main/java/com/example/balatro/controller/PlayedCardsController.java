package com.example.balatro.controller;

import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.GameModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.math.BigDecimal;
import java.util.List;

public class PlayedCardsController {

    public StackPane playedCards_StackPane;

    GameModel model = GameController.instance.gameModel;

    public void initialize() {
        model.getPlayedCards().addListener((ListChangeListener<? super PlayingCard>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    playedCards_StackPane.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    playedCards_StackPane.getChildren().removeAll(change.getRemoved());
                }
            }
        });
    }

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
        countPoints();
    }

    private void countPoints() {
        /*
        for(int i = 0; i < model.getPlayedCards().size(); i++) {
            PlayingCard card = model.getPlayedCards().get(i);
            if(playedCards_StackPane.getChildren()..get(i).getTranslateY() == -20) {
                model.addToScoredPoints(BigDecimal.valueOf(((PlayingCard)playedCardsController.getPlayedCards_StackPane().get(i)).getValue()));
            }
        }*/
        for(Node card : playedCards_StackPane.getChildren()) {
            if(card.getTranslateY() == -20) {
                model.addToScoredPoints(BigDecimal.valueOf(((PlayingCard)card).getValue()));
            }
        }

        model.addToScoredPoints(BigDecimal.valueOf((long) model.getBestHand().getChips() * model.getBestHand().getMulti()));

        if(model.isPointsReached()) {
            GameController.getInstance().openSummary();
            model.setRound(model.getRound() + 1);
            model.setScoredPoints(BigDecimal.valueOf(0));
            model.clearHandCards();

            if(model.getAllBlindsList().get(model.getRound()-1).getBlindId() > 1) {
                model.setAnte((model.getAnte() + 1));
            }
        } else {
            model.toggleHandButtonVisibilty();
        }
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
