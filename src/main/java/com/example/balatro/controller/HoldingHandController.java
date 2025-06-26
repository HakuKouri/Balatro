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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.stream.Collectors;

public class HoldingHandController {

    //region FXML
    public AnchorPane button_anchorpane;
    @FXML
    private Label handCardsCounterLabel;
    @FXML
    private Button playSelectedCardsButton;
    @FXML
    private Button discardSelectedCardsButton;
    @FXML
    private AnchorPane holdHand_AnchorPane;
    @FXML
    private StackPane holdingHand_StackPane;
    @FXML
    private RowConstraints handButton_Column;
    @FXML
    private GridPane handButtonBox;
    //endregion

    GameModel gameModel = Balatro.getGameModel();

    public void initialize() {
        //Add Cards to Stack Pane
        gameModel.getHandCards().addListener((ListChangeListener<? super PlayingCard>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    holdingHand_StackPane.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    holdingHand_StackPane.getChildren().removeAll(change.getRemoved());
                }
            }
        });

        //region Event Playing Card CLICKED
        holdingHand_StackPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();  // Bestimme das geklickte Element

            if (source instanceof PlayingCard) {
                handMouseClick((PlayingCard) source);
              }
        });
        //endregion

        //Card Count Label
        handCardsCounterLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getHandCards().size() + "/" + gameModel.getMaxHandSize(), gameModel.getHandCards()
        ));

        //Hand Control Buttons
        handButtonBox.visibleProperty().bind(gameModel.handButtonVisibilityProperty());
        handButtonBox.managedProperty().bind(gameModel.handButtonVisibilityProperty());
        playSelectedCardsButton.disableProperty().bind(Bindings.isEmpty(gameModel.getSelectedCards()));
        discardSelectedCardsButton.disableProperty().bind(Bindings.isEmpty(gameModel.getSelectedCards()));
    }

    private void handMouseClick(PlayingCard card) {
        // Wenn das geklickte Element eine PlayingCard ist
        if(card.isSelected()) {
            card.setSelected(false);
            gameModel.removeCardFromSelectedCards(card);
        }
        else if(gameModel.getSelectedCards().size() < 5){
            card.setSelected(true);
            gameModel.addCardToSelectedCards(card);
        }
        setHandInfo(checkHand.evaluateHands(gameModel, gameModel.getSelectedCards()));

    }

    public List<PlayingCard> getSelectedCards() {
        return gameModel.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return gameModel.getHandCards();
    }


    //Drawing Cards
    public void drawCard() {
        PlayingCard cardToDraw = gameModel.getCurrentRound().getDeckToPlay().get(0);
        cardToDraw.setClickAble(true);
        gameModel.getHandCards().add(cardToDraw);
        gameModel.getCurrentRound().getDeckToPlay().remove(0);
        sort();
    }

    public void drawCardToLimit() {
        drawCardToLimit(gameModel.maxHandSizeProperty().get() - gameModel.getHandCards().size());
    }

    public void drawCardToLimit(int cardCount) {
        int draws = Math.min(cardCount, gameModel.getCurrentRound().getDeckToPlay().size());
        for (int i = 0; i < draws; i++) {
            drawCard();
        }
    }

    //Selecting Cards
    private void setHandInfo(List<PokerHand> hands) {
        if(hands.isEmpty()) {
            gameModel.getBestHand().setHand(new PokerHand());
            gameModel.getPossiblePokerHand().clear();
            return;
        }
        int maxPoints = 0;

        PokerHand bestHand = null;
        gameModel.getPossiblePokerHand().setAll(hands);

        for (PokerHand pokerHand : gameModel.getPokerHandList()) {
            if(hands.contains(pokerHand)) {
                System.out.println(pokerHand.getName());
                int points = pokerHand.getChips() * pokerHand.getMulti();
                System.out.println("Possible Points: " + points);
                if(maxPoints < points) {
                    maxPoints = points;
                    bestHand = pokerHand;
                }
            }
        }

        if(bestHand != null) {
            gameModel.getBestHand().setHand(bestHand);
        }
        System.out.println("Best Hand: " + gameModel.getBestHand().getName());
    }

    //Button Functions
    public void sortRank() {
        gameModel.setSortedByRank(true);
        sort();
    }

    public void sortSuit() {
        gameModel.setSortedByRank(false);
        sort();
    }

    public void sort() {
        List<PlayingCard> tempCardList = gameModel.getHandCards()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if(gameModel.isSortedByRank()) {
            tempCardList.sort(Comparator
                    .comparingInt(PlayingCard::getRankIndex)
                    .thenComparingInt(PlayingCard::getSuitOrder));
        } else {
            tempCardList.sort(Comparator
                    .comparingInt(PlayingCard::getSuitOrder)
                    .thenComparingInt(PlayingCard::getRankIndex));
        }

        Collections.reverse(tempCardList);

        gameModel.getHandCards().clear();
        gameModel.getHandCards().addAll(tempCardList);

        UIController.moveCards(holdingHand_StackPane);
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty() && gameModel.getCurrentRound().getHands() > 0) {
            gameModel.setHandButtonVisibility(false);
            gameModel.getSelectedCards().sort(Comparator.comparingInt(getHandCards()::indexOf));
            gameModel.getHandCards().removeAll(getSelectedCards());
            GameController.getInstance().playSelectedCards();

            if(Objects.equals(gameModel.getActiveBlind().getBlindName(), "The Serpent"))
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.getCurrentRound().decrementHands();
        }
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty() && gameModel.getCurrentRound().getDiscards() > -1000) {
            getHandCards().removeAll(getSelectedCards());
            getSelectedCards().clear();

            if (Objects.equals(gameModel.getActiveBlind().getBlindName(), "The Serpent"))
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.getCurrentRound().decrementDiscards();
        }
    }
}
