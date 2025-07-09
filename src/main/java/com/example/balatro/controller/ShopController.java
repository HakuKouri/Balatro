package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Booster;
import com.example.balatro.domain.card.Card;
import com.example.balatro.domain.util.CardGenerator;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.VoucherState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ShopController {

    //region FXML
    @FXML
    private Label rotatedLabel;
    @FXML
    private AnchorPane shopAnchorPane;
    @FXML
    private StackPane shopArea;
    @FXML
    private StackPane voucherArea;
    @FXML
    private StackPane boosterArea;
    //endregion

    //region ATTRIBUTES
    private final GameModel gameModel = Balatro.getGameModel();
    private Runnable onNextRoundCallback;

    private final IntegerProperty maxItems = new SimpleIntegerProperty(2);
    private final IntegerProperty maxBoosters = new SimpleIntegerProperty(2);
    //endregion

    //region GETTER SETTER
    public void setOnNextRoundCallback(Runnable callback) {
        this.onNextRoundCallback = callback;
    }

    public ObservableMap<CardViewController, AnchorPane> getItemMap() {
        return gameModel.getShopModel().getItemCardViewManager().getViewMap();
    }

    public ObservableMap<CardViewController, AnchorPane> getBoosterMap() {
        return gameModel.getShopModel().getBoosterCardViewManager().getViewMap();
    }

    public ObservableMap<CardViewController, AnchorPane> getVoucherMap() {
        return gameModel.getShopModel().getVoucherCardViewManager().getViewMap();
    }

    public int getMaxItems() {
        return maxItems.get();
    }

    public IntegerProperty maxItemsProperty() {
        return maxItems;
    }

    public int getMaxBoosters() {
        return maxBoosters.get();
    }

    public IntegerProperty maxBoostersProperty() {
        return maxBoosters;
    }

    //endregion

    public void initialize() {
        UIController.bindStackPane(gameModel.getShopModel().getItemCardViewManager(), shopArea);
        UIController.bindStackPane(gameModel.getShopModel().getBoosterCardViewManager(), boosterArea);
        UIController.bindStackPane(gameModel.getShopModel().getVoucherCardViewManager(), voucherArea);
    }

    //region FUNCTIONS
    public void restockShop() {
        drawItems();
        drawBooster();
        drawVoucher();
    }

    public void reRollShop() {
        drawItems();
    }

    private void drawItems() {
        getItemMap().clear();
        for (int i = 0; i < getMaxItems(); i++) {
            gameModel.getShopModel().getItemCardViewManager().create(getRandomCard(), true);
        }
    }

    private Card getRandomCard() {
        Card card = new Card();

        double jokerWeight = 20;
        double tarotWeight = gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.TAROT_TYCOON) ? 32 : gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.TAROT_MERCHANT) ? 9.6 : 4;
        double planetWeight = gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.PLANET_TYCOON) ? 32 : gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.PLANET_MERCHANT) ? 9.6 : 4;
        double playingCardWeight = gameModel.getVoucherState().hasVoucher(VoucherState.VoucherType.MAGIC_TRICK) ? 4 : 0;
        double spectralCardWeight = "Ghost Deck".equals(gameModel.getRunState().getChosenDeck().getDeckName()) ? 2 : 0 ;

        double maxWeight = jokerWeight + tarotWeight + planetWeight + playingCardWeight + spectralCardWeight;

        double jokerPercentage = jokerWeight * 100 / maxWeight ;
        double tarotPercentage = tarotWeight * 100 / maxWeight ;
        double planetPercentage = planetWeight * 100 / maxWeight ;
        double playingCardPercentage = playingCardWeight != 0 ? playingCardWeight * 100 / maxWeight  : 0;
        double spectralCardPercentage = spectralCardWeight != 0 ? spectralCardWeight * 100 / maxWeight  : 0;
        double itemChance = gameModel.getRand().nextInt(100) +1;

        if (itemChance < jokerPercentage) {
            card = CardGenerator.getRandomJoker(gameModel);
        }
        else if (itemChance < (jokerPercentage + tarotPercentage)) {
            card = CardGenerator.getRandomTarot(gameModel);
        }
        else if (itemChance < (jokerPercentage + tarotPercentage + planetPercentage)) {
            card = CardGenerator.getRandomPlanet(gameModel);
        }
        else if (playingCardWeight != 0 && itemChance < jokerPercentage + tarotPercentage + planetPercentage + playingCardPercentage) {
            card = CardGenerator.getRandomPlayingCard(gameModel);
        }
        else if (spectralCardWeight != 0 && itemChance < jokerPercentage + tarotPercentage + planetPercentage + playingCardPercentage + spectralCardPercentage) {
            card = CardGenerator.getRandomSpectral(gameModel);
        }

        return card;
    }

    private void drawVoucher() {
        getVoucherMap().clear();
        gameModel.getShopModel().getVoucherCardViewManager().create(CardGenerator.getRandomVoucher(gameModel), true);
    }

    public void addVoucher() {
        gameModel.getShopModel().getVoucherCardViewManager().create(CardGenerator.getRandomVoucher(gameModel), true);
    }

    private void drawBooster() {
        getBoosterMap().clear();
        for (int i = 0; i < getMaxBoosters(); i++) {
            Booster booster = CardGenerator.getRandomBooster(gameModel);
            System.out.println("Random Booster: " +  booster.getCardName());
            System.out.println("Booster Image: " + booster.getImage());
            System.out.println("Booster Image url: " + booster.getCardImageUrl());
            gameModel.getShopModel().getBoosterCardViewManager().create(booster, true);
        }
    }

    @FXML
    private void handleNextRoundButtonClick() {
        if (onNextRoundCallback != null) {
            onNextRoundCallback.run();
        }
    }

    //endregion
}
