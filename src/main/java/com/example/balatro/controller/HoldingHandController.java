package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.*;

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

        UIController.bindStackPane(gameModel.getHoldingHandViewManager(), holdingHand_StackPane);

        //Card Count Label
        handCardsCounterLabel.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getHoldingHandViewManager().getSize() + "/" + gameModel.getRunState().getMaxHandSize(), gameModel.getHoldingHandViewManager().getViewMap()
        ));

        //Hand Control Buttons
        handButtonBox.visibleProperty().bind(gameModel.handButtonVisibilityProperty());
        handButtonBox.managedProperty().bind(gameModel.handButtonVisibilityProperty());
        playSelectedCardsButton.disableProperty().bind(Bindings.isEmpty(gameModel.getSelectedCards()));
        discardSelectedCardsButton.disableProperty().bind(Bindings.isEmpty(gameModel.getSelectedCards()));
    }


    public List<PlayingCard> getSelectedCards() {
        return gameModel.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return  gameModel.getHoldingHandViewManager().getCardList().stream()
                .filter(card -> card instanceof PlayingCard)
                .map(card -> (PlayingCard) card)
                .toList();
    }


    //Drawing Cards
    public void drawCard() {
        PlayingCard cardToDraw = gameModel.getRunState().getPlayingDeck().drawCard();
        cardToDraw.setClickAble(true);
        gameModel.getHoldingHandViewManager().create(cardToDraw);
        sort();
    }

    public void drawCardToLimit() {
        drawCardToLimit(gameModel.getRunState().maxHandSizeProperty().get() - gameModel.getHoldingHandViewManager().getSize());
    }

    public void drawCardToLimit(int cardCount) {
        int draws = Math.min(cardCount, gameModel.getRunState().getPlayingDeck().getPlaySize());
        for (int i = 0; i < draws; i++) {
            drawCard();
        }
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
        Comparator<PlayingCard> comparator = gameModel.isSortedByRank()
                ? Comparator.comparingInt(PlayingCard::getRankIndex).thenComparingInt(PlayingCard::getSuitIndex).reversed()
                : Comparator.comparingInt(PlayingCard::getSuitIndex).thenComparingInt(PlayingCard::getRankIndex);

        List<PlayingCard> sorted = gameModel.getHoldingHandViewManager().getViewMap().keySet().stream()
                .map(cvc -> (PlayingCard) cvc.getCard())
                .sorted(comparator)
                .toList();

        gameModel.getHoldingHandViewManager().clear();
        sorted.forEach(card -> gameModel.getHoldingHandViewManager().create(card));
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty() && gameModel.getCurrentRound().getHands() > 0) {
            gameModel.setHandButtonVisibility(false);
            gameModel.getSelectedCards().sort(Comparator.comparingInt(gameModel.getHoldingHandViewManager().getCardList()::indexOf));

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
            getSelectedCards().forEach(playingCard -> { gameModel.getHoldingHandViewManager().remove(playingCard); });

            getSelectedCards().clear();

            if (Objects.equals(gameModel.getActiveBlind().getBlindName(), "The Serpent"))
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.getCurrentRound().decrementDiscards();
        }
    }
}
