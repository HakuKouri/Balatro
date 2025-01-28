package com.example.balatro;

import com.example.balatro.classes.Booster;
import com.example.balatro.classes.Joker;
import com.example.balatro.classes.SqlHandler;
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
    private StackPane Voucher;
    @FXML
    private StackPane Booster;

    private GameScreenController gameScreenController;

    private static List<Booster> boosterList = SqlHandler.getAllBooster();
    private static List<Joker> jokerList = SqlHandler.getAllJokers();

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
