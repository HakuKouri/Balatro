package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Blind;
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
import java.util.stream.Collectors;

public class PlayedCardsController {

    public StackPane playedCards_StackPane;

    private final GameModel gameModel = Balatro.getGameModel();

    private int gameSpeed = 1;

    public void initialize() {
        gameModel.getPlayedCards().addListener((ListChangeListener<? super PlayingCard>) change -> {
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

    public void addSelectedCards(Runnable onComplete) {
        gameModel.getPlayedCards().addAll(gameModel.getSelectedCards());
        gameModel.getSelectedCards().clear();
        moveCards();

        List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(gameModel.getPlayedCards(), gameModel.getBestHand().getName());
        for(PlayingCard card : gameModel.getPlayedCards()) {
            card.setSelected(countedCards.contains(card));
        }

        List<PlayingCard> selectedCards = gameModel.getPlayedCards().stream()
                .filter(PlayingCard::isSelected)
                .collect(Collectors.toList());

        animateSelectedCards(selectedCards, 0, onComplete);
        //countPoints();
    }

    private void countPoints() {
        //TODO ADD EDITION, TRIGGER

        System.out.println("Points Reached? (Played Cards): " + gameModel.isPointsReached());

        if(gameModel.isPointsReached()) {
            gameModel.setRewardVisibility(true);
            gameModel.setScoredPoints(BigDecimal.valueOf(0));
            gameModel.getHandCards().clear();

            if(gameModel.getActiveBlind().getBlindId() > 1) {
                gameModel.setAnte((gameModel.getAnte() + 1));
            }

            gameModel.getActiveBlind().setBlind(new Blind());
        } else {
            gameModel.setHandButtonVisibility(true);
        }
        System.out.println("Handsize: " + gameModel.getHandCards().size());
    }

    private void animateSelectedCards(List<PlayingCard> cards, int index, Runnable onComplete) {
        if(index >= cards.size()) {
            gameModel.addToScoredPoints(BigDecimal.valueOf((long) gameModel.getBestHand().getMulti() * gameModel.getBestHand().getChips()));
            gameModel.getBestHand().setHand(new PokerHand());
            countPoints();
            if(onComplete != null) onComplete.run();
            return;
        }

        PlayingCard card = cards.get(index);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), new KeyValue(card.rotateProperty(), 0)),
                new KeyFrame(Duration.millis((double) 4 /gameSpeed), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 8 /gameSpeed), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 12 /gameSpeed), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 16 /gameSpeed), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 20 /gameSpeed), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 24 /gameSpeed), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 28 /gameSpeed), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 32 /gameSpeed), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 36 /gameSpeed), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 40 /gameSpeed), new KeyValue(card.rotateProperty(), 0)));
        timeline.setCycleCount(3);
        timeline.setDelay(Duration.seconds(0.2));

        timeline.setOnFinished(event -> {
            gameModel.getBestHand().chipsProperty().set(card.getValue() + gameModel.getBestHand().getChips());
            animateSelectedCards(cards, index + 1, onComplete);
        });

        timeline.play();
    }

    public void removeAllCards() {
        gameModel.getPlayedCards().clear();
    }

    public void moveCards() {
        int cardWidth = 140;
        int lastPos = 570;

        int cards = gameModel.getPlayedCards().size();
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
            gameModel.getPlayedCards().get(i).setTranslateX(pos);
        }
    }
}
