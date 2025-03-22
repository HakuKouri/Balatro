package com.example.balatro.controller;

import com.example.balatro.classes.Hand;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.checkHand;
import com.example.balatro.models.GameModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.*;

public class HoldingHandController {

    @FXML
    private StackPane HoldingHand;
    @FXML
    private GridPane handButtonBox;

    GameModel model = GameController.getGameModel();

    public void initialize() {
        model.getHandCards().addListener((ListChangeListener<? super PlayingCard>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    HoldingHand.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    HoldingHand.getChildren().removeAll(change.getRemoved());
                }
            }
        });

        handButtonBox.visibleProperty().bind(model.handButtonVisibilityProperty());
        model.toggleHandButtonVisibilty();
    }

    public List<PlayingCard> getSelectedCards() {
        return model.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return model.getHandCards();
    }

    public void clearHandCards() {
        model.getHandCards().clear();
    }

    public void addCardToHoldingHand(PlayingCard card) {
        model.addCardToHandCards(card);
    }

    public void removeCardFromHoldingHand(PlayingCard card) {
        model.removeCardFromHandCards(card);
    }

    public void addAllToHoldingHand(List<PlayingCard> cards) {
        model.addManyCardsToHandCards(cards);
    }

    public void clearHoldingHand() {
        HoldingHand.getChildren().clear();
        model.clearHandCards();
    }

    public ObservableList<Node> getHoldingHand() {
        return HoldingHand.getChildren();
    }

    public void addCardToHand(PlayingCard card) {
        model.getHandCards().add(card);
    }

    public void removeCardFromHand(PlayingCard card) {
        model.getHandCards().remove(card);
    }

    public void addCardToSelectedCard(PlayingCard card) {
        model.getSelectedCards().add(card);
    }

    public void removeCardFromSelectedCard(PlayingCard card) {
        model.getSelectedCards().remove(card);
    }

    public void moveCards() {
        int cardsize = 140;
        int lastPos = 570;

        int cards = HoldingHand.getChildren().size();
        int pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                HoldingHand.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                HoldingHand.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardsize/2 + i * cardsize - cards/2*cardsize + i * 5;
                } else {
                    pos = i * cardsize - cards/2*cardsize + i * 5;
                }
            }
            HoldingHand.getChildren().get(i).setTranslateX(pos);
        }
    }

    public void moveHoldingHandUp() {
        HoldingHand.setTranslateY(0);
    }

    public void moveHoldingHandDown() {
        HoldingHand.setTranslateY(100);
    }

    public int getHoldingHandSize() {
        return HoldingHand.getChildren().size();
    }

    public int getSelectedCardCounter() {
        return model.getSelectedCardCounter();
    }

    public void selectedCardCounterIncrement() {
        model.setSelectedCardCounter(model.getSelectedCardCounter() + 1);
    }

    public void selectedCardCounterDecrement() {
        model.setSelectedCardCounter(model.getSelectedCardCounter() - 1);
    }

    public void setSelectedCardCounter(int i) {
        model.setSelectedCardCounter(i);
    }


    //Drawing Cards
    public void drawCards() {
        while (model.getHandCards().size() < model.handSizeProperty().get() && !model.getDeckToPlay().isEmpty()) {
            PlayingCard cardToDraw = model.getDeckToPlay().get(0);
            cardToDraw.setOnMouseClicked(mouseEvent -> playingCardClicked((PlayingCard) mouseEvent.getSource()));
            cardToDraw.setClickAble(true);
            addCardToHand(cardToDraw);
            model.getDeckToPlay().remove(0);
        }
        sort();
    }

    public void drawCards(int cardCount) {
        for (int i = 0; i < cardCount  && !model.getDeckToPlay().isEmpty() ; i++) {
            PlayingCard cardToDraw = model.getDeckToPlay().get(0);
            cardToDraw.setOnMouseClicked(mouseEvent -> playingCardClicked((PlayingCard) mouseEvent.getSource()));
            cardToDraw.setClickAble(true);
            addCardToHand(cardToDraw);
        }
        sort();
    }


    //Selecting Cards
    private void playingCardClicked(PlayingCard card) {
        if(!card.isClickAble()) return;

        if(card.getTranslateY() == 0 && model.getSelectedCards().size() < 5) {
            model.addCardToSelectedCards(card);
            card.setTranslateY(-20);
        }
        else if(card.getTranslateY() == -20) {
            model.removeCardFromSelectedCards(card);
            card.setTranslateY(0);
        }

        setHandInfo(checkHand.evaluateHands(model.getSelectedCards()));
    }

    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;

        if(hands.isEmpty()) model.setBestHand(new Hand());

        for (Hand hand : model.getAllHandList()) {
            if(hands.contains(hand.getName())) {
                System.out.println(hand.getName());
                int points = hand.getChips() * hand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    model.setBestHand(hand);
                }
            }
        }
    }


    //Button Funktions
    public void sortRank() {
        model.setSortedByRank(true);
        sort();
    }

    public void sortSuit() {
        model.setSortedByRank(false);
        sort();
    }

    public void sort() {
        List<PlayingCard> tempCardList = new ArrayList<>();
        for(var card : model.getHandCards()) {
            if(card instanceof PlayingCard)
                tempCardList.add(card);
        }

        if(model.isSortedByRank()) {
            tempCardList.sort(Comparator
                    .comparingInt(PlayingCard::getOrderPosition)
                    .thenComparingInt(PlayingCard::getSuitOrder));
        } else {
            tempCardList.sort(Comparator
                    .comparingInt(PlayingCard::getSuitOrder)
                    .thenComparingInt(PlayingCard::getOrderPosition));
        }

        Collections.reverse(tempCardList);

        model.getHandCards().clear();
        model.setHandCards(tempCardList);

        moveCards();
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        getSelectedCards().sort(Comparator.comparingInt(getHandCards()::indexOf));
        getHandCards().removeAll(getSelectedCards());
        GameController.getInstance().playSelectedCards();

        if(model.getActiveBlind().getBlindName() == "The Serpent")
            drawCards(3);
        else
            drawCards();
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        getHandCards().removeAll(getSelectedCards());
        getSelectedCards().clear();
        if(model.getActiveBlind().getBlindName() == "The Serpent")
            drawCards(3);
        else
            drawCards();
    }

}
