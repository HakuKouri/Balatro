package com.example.balatro.controller;

import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.PokerHand;
import com.example.balatro.classes.PokerHandChecker;
import com.example.balatro.models.GameModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
            moveCards();
        });
    }

    public void addSelectedCards() {
        model.getPlayedCards().addAll(model.getSelectedCards());
        model.getSelectedCards().clear();
        moveCards();
        List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(model.getPlayedCards(), model.getBestHand().getName());
        for(PlayingCard card : model.getPlayedCards()) {
            card.setSelected(countedCards.contains(card));
        }

        countPoints();
    }

    private void countPoints() {
        List<PlayingCard> selectedCards = model.getPlayedCards().stream()
                .filter(PlayingCard::isSelected)
                .collect(Collectors.toList());

        animateSelectedCards(selectedCards, 0);

        //TODO ADD EDITION, TRIGGER

        System.out.println("Points Reached?: " + model.isPointsReached());

        if(model.isPointsReached()) {
            model.setRewardVisibility(true);
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

    private void animateSelectedCards(List<PlayingCard> cards, int index) {
        if(index >= cards.size()) {
            model.addToScoredPoints(BigDecimal.valueOf((long) model.getBestHand().getMulti() * model.getBestHand().getChips()));
            model.bestHandProperty().set(new PokerHand());
            return;
        }

        PlayingCard card = cards.get(index);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), new KeyValue(card.rotateProperty(), 0)),
                new KeyFrame(Duration.millis(1), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis(2), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis(3), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis(4), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis(5), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis(6), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis(7), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis(8), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis(9), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis(10), new KeyValue(card.rotateProperty(), 0)));
        timeline.setCycleCount(3);
        timeline.setDelay(Duration.seconds(0.2));

        timeline.setOnFinished(event -> {
            model.getBestHand().chipsProperty().set(card.getValue() + model.getBestHand().getChips());
            animateSelectedCards(cards, index + 1);
        });

        timeline.play();
    }

    public void removeAllCards() {
        model.getPlayedCards().clear();
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
}
