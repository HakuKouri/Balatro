package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Booster;
import com.example.balatro.classes.Card;
import com.example.balatro.classes.CardGenerator;
import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class BoosterOpeningController {

    //region FXML
    @FXML
    private Label boosterName_Label;
    @FXML
    private Label choose_Label;
    @FXML
    private Button skip_Button;
    @FXML
    private StackPane boosterDraw_StackPane;
    @FXML
    private StackPane playingCard_StackPane;
    //endregion

    //region ATTRIBUTES
    private final ObjectProperty<Booster> booster = new SimpleObjectProperty<>(new Booster());

    private final ObservableList<PlayingCard> playingCardsDrawn = FXCollections.observableArrayList();
    private final ObservableMap<CardViewController, AnchorPane> boosterDrawnMap = FXCollections.observableMap(new LinkedHashMap<>());
    //endregion

    //region GETTER SETTER
    public Booster getBooster() {
        return booster.get();
    }

    public ObjectProperty<Booster> boosterProperty() {
        return booster;
    }

    public void setBooster(Booster booster) {
        this.booster.get().setBooster(booster);
    }
    //endregion

    //region FUNCTIONS
    public void initialize() {
        UIController.bindStackPane(playingCardsDrawn, playingCard_StackPane);
        UIController.bindStackPane(boosterDrawnMap, boosterDraw_StackPane);

        boosterName_Label.textProperty().bind(booster.get().cardNameProperty());
    }

    public void useBooster(Booster booster, GameModel gameModel) {
        setBooster(booster);

        drawBoosterCards();
        if("Arcana Pack".equals(booster.getCardName()) || "Spectral Pack".equals(booster.getCardName())) {
            drawDeckCards();
        }

    }

    private void drawBoosterCards() {
        GameModel gameModel = Balatro.getGameModel();
        for (int i = 0; i < booster.get().getBoosterSizeValue(); i++) {
            Card card = null;

            switch (booster.get().getCardName()) {
                case "Standard Pack":
                    card = CardGenerator.getRandomPlayingCard(gameModel);
                    break;
                case "Arcana Pack":
                    card = CardGenerator.getRandomTarot(gameModel);
                    break;
                case "Celestial Pack":
                    card = CardGenerator.getRandomPlanet(gameModel);
                    break;
                case "Buffoon Pack":
                    card = CardGenerator.getRandomJoker(gameModel);
                    break;
                case "Spectral Pack":
                    card = CardGenerator.getRandomSpectral(gameModel);
                    break;
            }

            if(card != null)
                CardViewController.createCardNode(card, boosterDrawnMap);
            else
                System.out.println("Card is Null");
        }

    }

    private void drawDeckCards() {
        GameModel gameModel = Balatro.getGameModel();

        Set<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < gameModel.getRunState().getMaxHandSize()) {
            generated.add(gameModel.getRand().nextInt(gameModel.getRunState().getDeckFull().size()));
        }

        for(int index : generated) {
            //TODO choose Playing Cards (not clickable)
            PlayingCard playingCard = gameModel.getRunState().getDeckFull().get(index);
            playingCard.setClickAble(true);
            playingCardsDrawn.add(playingCard);
        }
    }


    public void skipBooster(ActionEvent actionEvent) {
        playingCardsDrawn.clear();
        boosterDrawnMap.clear();

        Balatro.getGameModel().setBoosterOpeningVisibility(false);
        Balatro.getGameModel().setShopVisibility(true);
    }
    //endregion
}
