package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.PokerHand;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.checkHand;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.*;

public class HoldingHandController {

    public Label handCardsCounterLabel;
    public Button playSelectedCardsButton;
    public Button discardSelectedCardsButton;
    @FXML
    private StackPane HoldingHand;
    @FXML
    private GridPane handButtonBox;

    GameModel gameModel = Balatro.getGameModel();

    public void initialize() {
        //Add Cards to Stack Pane
        gameModel.getHandCards().addListener((ListChangeListener<? super PlayingCard>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    HoldingHand.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    System.out.println("Hand Card removed");
                    HoldingHand.getChildren().removeAll(change.getRemoved());
                }
            }
        });

        //region Event Playing Card CLICKED
        HoldingHand.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();  // Bestimme das geklickte Element

            if (source instanceof PlayingCard) {
                // Wenn das geklickte Element eine PlayingCard ist
                PlayingCard card = (PlayingCard) source;
                if(card.isSelected()) {
                    card.setSelected(false);
                    gameModel.removeCardFromSelectedCards(card);
                }
                else if(gameModel.getSelectedCards().size() <5){
                    card.setSelected(true);
                    gameModel.addCardToSelectedCards(card);
                }
                setHandInfo(checkHand.evaluateHands(gameModel.getSelectedCards()));
            }
        });
        //endregion

        //Card Count Label
        handCardsCounterLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getHandCards().size() + "/" + gameModel.getHandSize(), gameModel.getHandCards()
        ));

        //Hand Control Buttons
        handButtonBox.visibleProperty().bind(gameModel.handButtonVisibilityProperty());
        playSelectedCardsButton.disableProperty().bind(Bindings.isEmpty(gameModel.getSelectedCards()));
        discardSelectedCardsButton.disableProperty().bind(Bindings.isEmpty(gameModel.getSelectedCards()));
    }

    public List<PlayingCard> getSelectedCards() {
        return gameModel.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return gameModel.getHandCards();
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

    //Drawing Cards
    public void drawCard() {
        PlayingCard cardToDraw = gameModel.getDeckToPlay().get(0);
        cardToDraw.setClickAble(true);
        gameModel.getHandCards().add(cardToDraw);
        gameModel.getDeckToPlay().remove(0);
        sort();
    }
    public void drawCardToLimit() {
        while (gameModel.getHandCards().size() < gameModel.handSizeProperty().get() && !gameModel.getDeckToPlay().isEmpty()) {
            drawCard();
        }
    }

    public void drawCardToLimit(int cardCount) {
        for (int i = 0; i < cardCount  && !gameModel.getDeckToPlay().isEmpty() ; i++) {
            drawCard();
        }
    }

    //Selecting Cards
    private void setHandInfo(List<String> hands) {
        int maxPoints = 0;

        if(hands.isEmpty()) gameModel.getBestHand().setHand(new PokerHand());

        for (PokerHand pokerHand : gameModel.getAllHandList()) {
            if(hands.contains(pokerHand.getName())) {
                System.out.println(pokerHand.getName());
                int points = pokerHand.getChips() * pokerHand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    gameModel.getBestHand().setHand(pokerHand);
                }
            }
        }
        System.out.println("Best Hand: " + gameModel.getBestHand().getName());
    }

    //Button Funktions
    public void sortRank() {
        gameModel.setSortedByRank(true);
        sort();
    }

    public void sortSuit() {
        gameModel.setSortedByRank(false);
        sort();
    }

    public void sort() {
        List<PlayingCard> tempCardList = new ArrayList<>();
        for(var card : gameModel.getHandCards()) {
            if(card != null)
                tempCardList.add(card);
        }

        if(gameModel.isSortedByRank()) {
            tempCardList.sort(Comparator
                    .comparingInt(PlayingCard::getOrderPosition)
                    .thenComparingInt(PlayingCard::getSuitOrder));
        } else {
            tempCardList.sort(Comparator
                    .comparingInt(PlayingCard::getSuitOrder)
                    .thenComparingInt(PlayingCard::getOrderPosition));
        }

        Collections.reverse(tempCardList);

        gameModel.getHandCards().clear();
        gameModel.getHandCards().addAll(tempCardList);

        moveCards();
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty()) {
            gameModel.setHandButtonVisibility(false);
            getSelectedCards().sort(Comparator.comparingInt(getHandCards()::indexOf));
            getHandCards().removeAll(getSelectedCards());
            GameController.getInstance().playSelectedCards();

            if(gameModel.getActiveBlind().getBlindName() == "The Serpent")
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.decrementHands();
        }
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty()) {
            getHandCards().removeAll(getSelectedCards());
            getSelectedCards().clear();

            if (gameModel.getActiveBlind().getBlindName() == "The Serpent")
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.decrementDiscards();
        }
    }

}
