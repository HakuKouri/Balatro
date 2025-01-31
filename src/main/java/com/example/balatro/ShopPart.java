package com.example.balatro;

import com.example.balatro.classes.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class ShopPart {

    @FXML
    private AnchorPane shopAnchorPane;
    @FXML
    private StackPane Shop;
    @FXML
    private StackPane VoucherArea;
    @FXML
    private StackPane Booster;

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

    private void rerollShop() {
        Shop.getChildren().clear();
        drawItems();
    }

    private void drawItems() {

    }

    private void drawVoucher() {

    }

    private void drawBooster() {

    }

    private void nextRound() {
        gameScreenController.toggleBlind(true);
    }
}
