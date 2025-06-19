package com.example.balatro.controller;

import com.almasb.fxgl.ui.UI;
import com.example.balatro.Balatro;
import com.example.balatro.classes.Planet;
import com.example.balatro.classes.PokerHand;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.classes.checkHand;
import com.example.balatro.models.GameModel;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HoldingHandController {

    //region FXML
    @FXML
    private Label handCardsCounterLabel;
    @FXML
    private Button playSelectedCardsButton;
    @FXML
    private Button discardSelectedCardsButton;
    @FXML
    private AnchorPane holdingHand_AnchorPane;
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
                // Wenn das geklickte Element eine PlayingCard ist
                PlayingCard card = (PlayingCard) source;
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
    private void setHandInfo(List<PokerHand> hands) {
        int maxPoints = 0;

        if(hands.isEmpty()) gameModel.getBestHand().setHand(new PokerHand());

        gameModel.getPossiblePokerHand().clear();
        gameModel.getPossiblePokerHand().addAll(hands);

        for (PokerHand pokerHand : gameModel.getPokerHandList()) {
            if(hands.contains(pokerHand)) {
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
        //moveCards();
    }

    public void getHeight() {
        System.out.println(holdingHand_AnchorPane.getHeight());
    }

    public void playSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty() && gameModel.getHands() > 0) {
            gameModel.setHandButtonVisibility(false);
            gameModel.getSelectedCards().sort(Comparator.comparingInt(getHandCards()::indexOf));
            gameModel.getHandCards().removeAll(getSelectedCards());
            GameController.getInstance().playSelectedCards();

            if(Objects.equals(gameModel.getActiveBlind().getBlindName(), "The Serpent"))
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.decrementHands();
        }
    }

    public void discardSelectedCards(ActionEvent actionEvent) {
        if(!gameModel.getSelectedCards().isEmpty() && gameModel.getDiscards() > 0) {
            getHandCards().removeAll(getSelectedCards());
            getSelectedCards().clear();

            if (Objects.equals(gameModel.getActiveBlind().getBlindName(), "The Serpent"))
                drawCardToLimit(3);
            else
                drawCardToLimit();
            gameModel.decrementDiscards();
        }
    }

    public void playPlanet(CardViewController cardViewController) {
        gameModel.setShopVisibility(false);
        Planet planet = (Planet) cardViewController.getCard();

        PokerHand hand = gameModel.getPokerHandList().stream().filter(x -> x.getName().equals(planet.getPlanetPokerHand())).findFirst().get();

        gameModel.getBestHand().setHand(hand);

        Timeline multTimeline = UIController.cardWiggleTimeline(planet);

        multTimeline.setCycleCount(3);
        multTimeline.setOnFinished( event -> {
            hand.addMult(planet.getPlanetMultiplier());
            gameModel.getBestHand().addMult(planet.getPlanetMultiplier());
        });
        Timeline chipsTimeline = UIController.cardWiggleTimeline(planet);
        chipsTimeline.setCycleCount(3);
        chipsTimeline.setOnFinished( event -> {
            hand.addChips(planet.getPlanetChips());
            gameModel.getBestHand().addChips(planet.getPlanetChips());
        });

        Timeline levelTimeline = UIController.cardWiggleTimeline(planet);
        levelTimeline.setCycleCount(3);
        levelTimeline.setOnFinished( event -> {
            hand.addLevel();
            gameModel.getBestHand().addLevel();
        });

        UIController.addToAnimationList(multTimeline);
        UIController.addToAnimationList(chipsTimeline);
        UIController.addToAnimationList(levelTimeline);
        UIController.addToAnimationList(UIController.delayTimeline());

        holdingHand_StackPane.getChildren().add(cardViewController.getCard());

        UIController.playAnimations(() -> {
            holdingHand_StackPane.getChildren().clear();
            gameModel.getBestHand().setHand(new PokerHand());
            gameModel.setShopVisibility(true);
        });
    }


}
