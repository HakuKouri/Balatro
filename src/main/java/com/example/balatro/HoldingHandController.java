package com.example.balatro;

import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.HoldingHandModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.List;

public class HoldingHandController {

    @FXML
    private StackPane HoldingHand;

    HoldingHandModel model = new HoldingHandModel();


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

    public void selectedCardCounterIncement() {
        model.setSelectedCardCounter(model.getSelectedCardCounter() + 1);
    }

    public void selectedCardCounterDecrement() {
        model.setSelectedCardCounter(model.getSelectedCardCounter() - 1);
    }

    public void setSelectedCardCounter(int i) {
        model.setSelectedCardCounter(i);
    }
}
