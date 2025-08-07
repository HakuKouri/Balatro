package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.util.CardGenerator;
import com.example.balatro.models.GameModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.lang.Integer;
import java.util.LinkedHashSet;
import java.util.List;
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
    private int choosePossibility = 0;
    private GameModel gameModel;
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
        choosePossibility = booster.getBoosterChoiceValue();
    }
    //endregion

    //region FUNCTIONS
    public void initialize() {
        UIController.bindStackPane(Balatro.getGameModel().getBoosterDrawModel().getPlayCardsDrawnViewManager(), playingCard_StackPane);
        UIController.bindStackPane(Balatro.getGameModel().getBoosterDrawModel().getBoosterDrawnManager(), boosterDraw_StackPane);

        boosterName_Label.textProperty().bind(booster.get().cardNameProperty());
    }

    public void useBooster(Booster booster) {
        setBooster(booster);

        drawBoosterCards();
        if("Arcana Pack".equals(booster.getCardName()) || "Spectral Pack".equals(booster.getCardName())) {
            drawDeckCards();
        }
    }

    private void drawBoosterCards() {
        System.out.println("drawBoosterCards: " + booster.get().getCardName());
        for (int i = 0; i < booster.get().getBoosterSizeValue(); i++) {
            System.out.println("Draw " + i + " Card");
            switch (booster.get().getCardName()) {
                case "Standard Pack" ->  gameModel.getBoosterDrawModel().getBoosterDrawnManager().createForBooster(CardGenerator.getRandomPlayingCard(gameModel));
                case "Arcana Pack" ->  gameModel.getBoosterDrawModel().getBoosterDrawnManager().createForBooster(CardGenerator.getRandomTarot(gameModel));
                case "Celestial Pack" ->  gameModel.getBoosterDrawModel().getBoosterDrawnManager().createForBooster(CardGenerator.getRandomPlanet(gameModel));
                case "Buffoon Pack" ->  gameModel.getBoosterDrawModel().getBoosterDrawnManager().createForBooster(CardGenerator.getRandomJoker(gameModel));
                case "Spectral Pack" ->  gameModel.getBoosterDrawModel().getBoosterDrawnManager().createForBooster(CardGenerator.getRandomSpectral(gameModel));
            };
        }

    }

    private void drawDeckCards() {
        Set<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < gameModel.getRunState().getMaxHandSize()) {
            generated.add(gameModel.getRand().nextInt(gameModel.getRunState().getPlayingDeck().getFullSize()));
        }

        for(int index : generated) {
            //TODO choose Playing Cards (not clickable)
            PlayingCard playingCard = gameModel.getRunState().getPlayingDeck().getFullDeck().get(index);
            playingCard.setClickAble(true);
            gameModel.getBoosterDrawModel().getPlayCardsDrawnViewManager().create(playingCard);
        }
    }

    public void skipBooster(ActionEvent actionEvent) {
        //TODO FIRE BOOSTER SKIP EVENT
        closeBoosterOpener();
    }

    public void useCard(Card card) {
        choosePossibility--;
        if(card instanceof Planet planet) {
            List<Planet> nonPlayedPlanets = gameModel.getBoosterDrawModel().getBoosterDrawnManager().getCardList(Planet.class).stream().filter(p -> p != planet ).toList();
            for(Planet p : nonPlayedPlanets) {
                gameModel.getBoosterDrawModel().getBoosterDrawnManager().getView(p).setManaged(false);
            }

            planet.play(gameModel, () -> {
                for(Planet p : nonPlayedPlanets) {
                    gameModel.getBoosterDrawModel().getBoosterDrawnManager().getView(p).setManaged(true);
                }
                if(choosePossibility == 0) {
                    closeBoosterOpener();
                }
            });
        }
        if(card instanceof Tarot tarot) {
            List<Tarot> nonPlayedTarot = gameModel.getBoosterDrawModel().getBoosterDrawnManager().getCardList(Tarot.class).stream().filter(t -> t != tarot ).toList();
            for(Tarot t : nonPlayedTarot) {
                gameModel.getBoosterDrawModel().getBoosterDrawnManager().getView(t).setManaged(false);
            }

            tarot.play(gameModel, () -> {
                for(Tarot t : nonPlayedTarot) {
                    gameModel.getBoosterDrawModel().getBoosterDrawnManager().getView(t).setManaged(true);
                }
                if(choosePossibility == 0) {
                    closeBoosterOpener();
                }
            });
        }
    }

    public void closeBoosterOpener() {
        gameModel.getBoosterDrawModel().getPlayCardsDrawnViewManager().clear();
        gameModel.getBoosterDrawModel().getBoosterDrawnManager().clear();

        gameModel.setBoosterOpeningVisibility(false);
        gameModel.setShopVisibility(true);
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
    //endregion
}
