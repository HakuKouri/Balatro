package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Booster;
import com.example.balatro.domain.card.Card;
import com.example.balatro.domain.util.CardGenerator;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.util.CardViewManager;
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
        UIController.bindStackPane(Balatro.getGameModel().getBoosterDrawModel().getPlayingCardsDrawn(), playingCard_StackPane);
        UIController.bindStackPane(Balatro.getGameModel().getBoosterDrawModel().getBoosterDrawnManager(), boosterDraw_StackPane);

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
        System.out.println("drawBoosterCards: " + booster.get().getCardName());
        GameModel gameModel = Balatro.getGameModel();
        for (int i = 0; i < booster.get().getBoosterSizeValue(); i++) {
            System.out.println("Draw " + i + " Card");
            Card card = switch (booster.get().getCardName()) {
                case "Standard Pack" -> CardGenerator.getRandomPlayingCard(gameModel);
                case "Arcana Pack" -> CardGenerator.getRandomTarot(gameModel);
                case "Celestial Pack" -> CardGenerator.getRandomPlanet(gameModel);
                case "Buffoon Pack" -> CardGenerator.getRandomJoker(gameModel);
                case "Spectral Pack" -> CardGenerator.getRandomSpectral(gameModel);
                default -> null;
            };

            if(card != null)
                gameModel.getBoosterDrawModel().getBoosterDrawnManager().createForBooster(card);
            else
                System.out.println("Card is Null");
        }

    }

    private void drawDeckCards() {
        GameModel gameModel = Balatro.getGameModel();

        Set<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < gameModel.getRunState().getMaxHandSize()) {
            generated.add(gameModel.getRand().nextInt(gameModel.getRunState().getPlayingDeck().getFullSize()));
        }

        for(int index : generated) {
            //TODO choose Playing Cards (not clickable)
            PlayingCard playingCard = gameModel.getRunState().getPlayingDeck().getFullDeck().get(index);
            playingCard.setClickAble(true);
            gameModel.getBoosterDrawModel().getPlayingCardsDrawn().add(playingCard);
        }
    }


    public void skipBooster(ActionEvent actionEvent) {
        GameModel gameModel = Balatro.getGameModel();
        gameModel.getBoosterDrawModel().getPlayingCardsDrawn().clear();
        gameModel.getBoosterDrawModel().getBoosterDrawnManager().clear();

        gameModel.setBoosterOpeningVisibility(false);
        gameModel.setShopVisibility(true);
    }
    //endregion
}
