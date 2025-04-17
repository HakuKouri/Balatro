package com.example.balatro.controller;

import com.example.balatro.classes.PokerHand;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.checkHand;
import com.example.balatro.models.GameModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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

        //region Event Playing Card CLICKED
        HoldingHand.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();  // Bestimme das geklickte Element

            if (source instanceof PlayingCard) {
                // Wenn das geklickte Element eine PlayingCard ist
                PlayingCard card = (PlayingCard) source;
                if(card.isSelected()) {
                    card.setSelected(false);
                    model.removeCardFromSelectedCards(card);
                }
                else {
                    card.setSelected(true);
                    model.addCardToSelectedCards(card);
                }
                System.out.println("Holding hand card clicked");
                setHandInfo(checkHand.evaluateHands(model.getSelectedCards()));
            }
        });
        //endregion
    }

    public List<PlayingCard> getSelectedCards() {
        return model.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return model.getHandCards();
    }

    public void moveCards() {
        int cardWidth = 140;
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
                    pos = cardWidth/2 + i * cardWidth - cards/2*cardWidth + i * 5;
                } else {
                    pos = i * cardWidth - cards/2*cardWidth + i * 5;
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


    //Drawing Cards
    public void drawCards() {
        while (model.getHandCards().size() < model.handSizeProperty().get() && !model.getDeckToPlay().isEmpty()) {
            PlayingCard cardToDraw = model.getDeckToPlay().get(0);
            cardToDraw.setClickAble(true);
            model.addCardToHandCards(cardToDraw);
            model.getDeckToPlay().remove(0);
        }
        sort();
    }

    public void drawCards(int cardCount) {
        for (int i = 0; i < cardCount  && !model.getDeckToPlay().isEmpty() ; i++) {
            PlayingCard cardToDraw = model.getDeckToPlay().get(0);
            cardToDraw.setClickAble(true);
            model.addCardToHandCards(cardToDraw);
        }
        sort();
    }


    //Selecting Cards
    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;

        if(hands.isEmpty()) model.setBestHand(new PokerHand());

        for (PokerHand pokerHand : model.getAllHandList()) {
            if(hands.contains(pokerHand.getName())) {
                System.out.println(pokerHand.getName());
                int points = pokerHand.getChips() * pokerHand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    model.setBestHand(pokerHand);
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
        model.decrementHands();
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        getHandCards().removeAll(getSelectedCards());
        getSelectedCards().clear();
        if(model.getActiveBlind().getBlindName() == "The Serpent")
            drawCards(3);
        else
            drawCards();
        model.decrementDiscards();
    }

}
