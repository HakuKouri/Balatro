package com.example.balatro.controller;

import com.example.balatro.classes.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class ShopPartController {

    @FXML
    private AnchorPane shopAnchorPane;
    @FXML
    private StackPane ShopArea;
    @FXML
    private StackPane VoucherArea;
    @FXML
    private StackPane BoosterArea;

    private GameController gameController;

    private static List<Booster> boosterList = SqlHandler.getAllBooster();
    private static List<Voucher> voucherList = SqlHandler.getAllVoucher();
    private static List<Planet> planetList = SqlHandler.getAllPlanets();
    private static List<Joker> jokerList = SqlHandler.getAllJokers();
    private static List<PlayingCard> playingCardList;

    private static int maxItems = 2;

    public void setGameScreenController(GameController gameController) {this.gameController = gameController;}

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
        VoucherArea.getChildren().add(voucherList.get(GameController.getInstance().getRand().nextInt(voucherList.size())));
    }

    private void removeFromVoucher(Voucher voucher) {
        VoucherArea.getChildren().remove(voucher);
    }

    private void drawBooster() {

        BoosterArea.getChildren().add(boosterList.get(GameController.getInstance().getRand().nextInt(boosterList.size())));
    }

    private void removeFromBooster(Booster booster) {
        BoosterArea.getChildren().remove(booster);
    }

    public void nextRound() {
        gameController.nextRound();

    }
}
