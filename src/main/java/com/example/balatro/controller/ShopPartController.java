package com.example.balatro.controller;

import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
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

    private final GameController gameController = GameController.getInstance();
    private final GameModel model = GameController.getGameModel();

    private static List<PlayingCard> playingCardList;
    private static int maxItems = 2;

    public void setupShop() {
        drawItems();
        drawBooster();
        drawVoucher();
    }

    public void reRollShop() {
        ShopArea.getChildren().clear();
        drawItems();
    }

    private void drawItems() {
        
    }

    private void drawVoucher() {
        VoucherArea.getChildren().add(model.getVoucherList().get(model.getRand().nextInt(model.getVoucherList().size())));
    }

    private void removeFromVoucher(Voucher voucher) {
        VoucherArea.getChildren().remove(voucher);
    }

    private void drawBooster() {

        BoosterArea.getChildren().add(model.getBoosterList().get(model.getRand().nextInt(model.getBoosterList().size())));
    }

    private void removeFromBooster(Booster booster) {
        BoosterArea.getChildren().remove(booster);
    }

    public void nextRound() {
        gameController.nextRound();

    }
}
