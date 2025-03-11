package com.example.balatro.controller;

import com.example.balatro.classes.PlayingCard;
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


    GameModel model = GameController.getInstance().gameModel;
    private boolean handButtonHidden = false;

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
    }

    public List<PlayingCard> getSelectedCards() {
        return model.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return model.getHandCards();
    }

    public void clearSelectedCards() {
        model.getSelectedCards().clear();
    }

    public void clearHandCards() {
        model.getHandCards().clear();
    }

    public void addCardToHoldingHand(PlayingCard card) {
        HoldingHand.getChildren().add(card);
    }

    public void removeCardFromHoldingHand(PlayingCard card) {
        HoldingHand.getChildren().remove(card);
    }

    public void addAllToHoldingHand(List<PlayingCard> cards) {
        HoldingHand.getChildren().addAll(cards);
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

    public void sortRank() {
        model.setSortedByRank(true);
        List<PlayingCard> cards = new ArrayList<>();
        for(var card : getHoldingHand())
            if(card instanceof PlayingCard)
                cards.add((PlayingCard) card);

        cards.sort(Comparator
                .comparingInt(PlayingCard::getOrderPosition)
                .thenComparingInt(PlayingCard::getSuitOrder));

        Collections.reverse(cards);

        clearHoldingHand();
        addAllToHoldingHand(cards);

        moveCards();
    }

    public void sortSuit() {
        model.setSortedByRank(false);
        List<PlayingCard> cards = new ArrayList<>();
        for(var card : getHoldingHand())
            if(card instanceof PlayingCard)
                cards.add((PlayingCard) card);

        cards.sort(Comparator
                .comparingInt(PlayingCard::getSuitOrder)
                .thenComparingInt(PlayingCard::getOrderPosition));

        Collections.reverse(cards);

        clearHoldingHand();
        addAllToHoldingHand(cards);

        moveCards();
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        HoldingHand.getChildren().removeAll(getSelectedCards());
        model.getHandCards().removeAll(getSelectedCards());
        GameController.getInstance().playCards(getSelectedCards());
        moveCards();
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        HoldingHand.getChildren().removeAll(getSelectedCards());

        GameController.getInstance().drawCards(HoldingHand.getChildren().size());
    }

    /*public void discardSelectedCards(ActionEvent actionEvent) {
        if(holdingHandController.getSelectedCardCounter() != 0) {
            for(int i = 0; i < holdingHandController.getSelectedCardCounter(); i++) {
                holdingHandController.removeCardFromHoldingHand(holdingHandController.getSelectedCards().get(i));
                holdingHandController.removeCardFromHand(holdingHandController.getSelectedCards().get(i));
            }
        }
        holdingHandController.setSelectedCardCounter(0);
        holdingHandController.clearSelectedCards();

        drawCards(8 - holdingHandController.getHandCards().size());
        setHandInfo(new ArrayList<>());
    }*/

    public void hideHandButtons() {
        System.out.println("hide button");
        handButtonHidden = !handButtonHidden;
        if(handButtonHidden) {
            moveHoldingHandDown();
            handButtonBox.setTranslateY(100);
        } else {
            moveHoldingHandUp();
            handButtonBox.setTranslateY(0);
        }
    }


}
