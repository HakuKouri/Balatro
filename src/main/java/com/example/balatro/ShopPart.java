package com.example.balatro;

import com.example.balatro.classes.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.Random;

public class ShopPart {

    @FXML
    private AnchorPane shopAnchorPane;
    @FXML
    private StackPane ShopArea;
    @FXML
    private StackPane VoucherArea;
    @FXML
    private StackPane BoosterArea;

    private GameScreenController gameScreenController;

    private static List<Booster> boosterList = SqlHandler.getAllBooster();
    private static List<Voucher> voucherList = SqlHandler.getAllVoucher();
    private static List<Planet> planetList = SqlHandler.getAllPlanets();
    private static List<Joker> jokerList = SqlHandler.getAllJokers();
    private static List<PlayingCard> playingCardList;

    private static int maxItems = 2;

    public void setGameScreenController(GameScreenController gameScreenController) {this.gameScreenController = gameScreenController;}

    public void setupShop() {
        drawItems();
        drawBooster();
        drawVoucher();
    }

    public void rerollShop() {
        ShopArea.getChildren().clear();
        drawItems();
    }

    private void drawItems() {

    }

    private void drawVoucher() {
        VoucherArea.getChildren().add(voucherList.get(GameScreenController.rand.nextInt(voucherList.size())));
    }

    private void removeFromVoucher(Voucher voucher) {
        VoucherArea.getChildren().remove(voucher);
    }

    private void drawBooster() {

        BoosterArea.getChildren().add(boosterList.get(GameScreenController.rand.nextInt(boosterList.size())));
    }

    private void removeFromBooster(Booster booster) {
        BoosterArea.getChildren().remove(booster);
    }

    public void nextRound() {
        gameScreenController.closeShop();
        gameScreenController.toggleBlind(true);
    }
}
