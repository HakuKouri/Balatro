package com.example.balatro.controller;

import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.PokerHandChecker;
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

    private final GameModel model = GameController.getGameModel();

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

    public void addSelectedCards() {
        model.getPlayedCards().addAll(model.getSelectedCards());
        model.getSelectedCards().clear();
        moveCards();
        List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(model.getPlayedCards(), model.getBestHand().getName());
        for(PlayingCard card : model.getPlayedCards()) {
            if(countedCards.contains(card)) {
                card.setTranslateY(-20);
            } else
                card.setTranslateY(0);
        }

        countPoints();

    }

    private void countPoints() {
        for(PlayingCard card : model.getPlayedCards()) {
            if(card.getTranslateY() == -20) {
                model.addToScoredPoints(BigDecimal.valueOf((card).getValue()));
            }
        }

        model.addToScoredPoints(BigDecimal.valueOf((long) model.getBestHand().getChips() * model.getBestHand().getMulti()));
        System.out.println("Points Reached?: " + model.isPointsReached());
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

    public void removeAllCards() {
        playedCards_StackPane.getChildren().clear();
    }

    public void moveCards() {
        int cardWidth = 140;
        int lastPos = 570;

        int cards = model.getPlayedCards().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                playedCards_StackPane.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                playedCards_StackPane.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardWidth/2 + i * cardWidth - cards/2*cardWidth + i * 5;
                } else {
                    pos = i * cardWidth - cards/2*cardWidth + i * 5;
                }
            }
            model.getPlayedCards().get(i).setTranslateX(pos);
        }

    }

    public int count() {
        return playedCards_StackPane.getChildren().size();
    }

}
