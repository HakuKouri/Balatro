package com.example.balatro.controller;

import com.almasb.fxgl.trade.Shop;
import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class ShopPartController {

    public AnchorPane rotatedLabel_AnchorPane;
    public Label rotatedLabel;
    @FXML
    private AnchorPane shopAnchorPane;
    @FXML
    private StackPane shopArea;
    @FXML
    private StackPane voucherArea;
    @FXML
    private StackPane boosterArea;

    private final GameController gameController = GameController.getInstance();
    private final GameModel gameModel = Balatro.getGameModel();

    private final ObservableList<Card> shopList = FXCollections.observableArrayList();
    private final ObservableList<Booster> boosterList = FXCollections.observableArrayList();
    private final ObservableList<Voucher> voucherList = FXCollections.observableArrayList();


    private int maxItems = 2;
    private int maxBoosters = 2;

    public void initialize() {
        shopList.addListener((ListChangeListener<? super Card>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    shopArea.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    shopArea.getChildren().removeAll(change.getRemoved());
                }
            }
            moveItems(shopArea, 200);
        });
        boosterList.addListener((ListChangeListener<? super Booster>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Booster added");
                    boosterArea.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    System.out.println("Booster removed");
                    boosterArea.getChildren().removeAll(change.getRemoved());
                }
            }
            moveItems(boosterArea, Booster.getImageWidthProperty());
        });
        voucherList.addListener((ListChangeListener<? super Voucher>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    voucherArea.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    voucherArea.getChildren().removeAll(change.getRemoved());
                }
            }
            moveItems(voucherArea, 200);
        });

//        rotatedLabel_AnchorPane.heightProperty().addListener((obs, oldValue, newValue) -> {
//            rotatedLabel.setLayoutY(newValue.doubleValue() * .05);
//        });


    }

    public void restockShop() {
        System.out.println("restockShop");
        drawItems();
        drawBooster();
        drawVoucher();
    }

    public void reRollShop() {
        shopList.clear();
        drawItems();
    }

    private void drawItems() {
        shopList.clear();
        for (int i = 0; i < maxItems; i++) {
            shopList.add(getRandomCard());
        }
    }

    private Card getRandomCard() {
        int boosterChance = gameModel.getRand().nextInt(2242);
        Card card = new Card();


        return card;
    }

    private void drawVoucher() {
        voucherArea.getChildren().add(gameModel.getVoucherList().get(gameModel.getRand().nextInt(gameModel.getVoucherList().size())));
    }

    private void removeFromVoucher(Voucher voucher) {
        voucherArea.getChildren().remove(voucher);
    }

    private void drawBooster() {
        System.out.println("drawBooster");
        boosterList.clear();
        for (int i = 0; i < maxBoosters; i++) {
            boosterList.add(getRandomBooster());
            System.out.println(boosterList.size());
        }
        System.out.println("bossterArea Children: " + boosterArea.getChildren().size());
        System.out.println("bossterArea Height: " + boosterArea.getHeight());
    }

    public void nextRound() {
        gameController.nextRound();
    }

    public Booster getRandomBooster() {
        System.out.println("getRandomBooster");
        Booster booster = new Booster();

        int boosterChance = gameModel.getRand().nextInt(2242);
        System.out.println("boosterChance: " + boosterChance);

        if (boosterChance < 100) booster.setBooster(gameModel.getBoosterList().get(0));
        else if (boosterChance < 200) booster.setBooster(gameModel.getBoosterList().get(1));
        else if (boosterChance < 300) booster.setBooster(gameModel.getBoosterList().get(2));
        else if (boosterChance < 400) booster.setBooster(gameModel.getBoosterList().get(3));
        else if (boosterChance < 500) booster.setBooster(gameModel.getBoosterList().get(4));
        else if (boosterChance < 600) booster.setBooster(gameModel.getBoosterList().get(5));
        else if (boosterChance < 625) booster.setBooster(gameModel.getBoosterList().get(6));
        else if (boosterChance < 650) booster.setBooster(gameModel.getBoosterList().get(7));
        else if (boosterChance < 750) booster.setBooster(gameModel.getBoosterList().get(8));
        else if (boosterChance < 850) booster.setBooster(gameModel.getBoosterList().get(9));
        else if (boosterChance < 950) booster.setBooster(gameModel.getBoosterList().get(10));
        else if (boosterChance < 1050) booster.setBooster( gameModel.getBoosterList().get(11));
        else if (boosterChance < 1150) booster.setBooster( gameModel.getBoosterList().get(12));
        else if (boosterChance < 1250) booster.setBooster( gameModel.getBoosterList().get(13));
        else if (boosterChance < 1275) booster.setBooster( gameModel.getBoosterList().get(14));
        else if (boosterChance < 1300) booster.setBooster( gameModel.getBoosterList().get(15));
        else if (boosterChance < 1400) booster.setBooster( gameModel.getBoosterList().get(16));
        else if (boosterChance < 1500) booster.setBooster( gameModel.getBoosterList().get(17));
        else if (boosterChance < 1600) booster.setBooster( gameModel.getBoosterList().get(18));
        else if (boosterChance < 1700) booster.setBooster( gameModel.getBoosterList().get(19));
        else if (boosterChance < 1800) booster.setBooster( gameModel.getBoosterList().get(20));
        else if (boosterChance < 1900) booster.setBooster( gameModel.getBoosterList().get(21));
        else if (boosterChance < 1925) booster.setBooster( gameModel.getBoosterList().get(22));
        else if (boosterChance < 1950) booster.setBooster( gameModel.getBoosterList().get(23));
        else if (boosterChance < 2010) booster.setBooster( gameModel.getBoosterList().get(24));
        else if (boosterChance < 2070) booster.setBooster( gameModel.getBoosterList().get(25));
        else if (boosterChance < 2130) booster.setBooster( gameModel.getBoosterList().get(26));
        else if (boosterChance < 2145) booster.setBooster( gameModel.getBoosterList().get(27));
        else if (boosterChance < 2175) booster.setBooster( gameModel.getBoosterList().get(28));
        else if (boosterChance < 2205) booster.setBooster( gameModel.getBoosterList().get(29));
        else if (boosterChance < 2235) booster.setBooster( gameModel.getBoosterList().get(30));
        else booster.setBooster(gameModel.getBoosterList().get(31));

        System.out.println("Booster: " + booster.getBoosterName());
        return booster;
    }

    private void moveItems(StackPane stackPane, double width) {
        int cards = stackPane.getChildren().size();
        double pos = 0;

        for(int i = 0; i < cards; i++) {
                if(cards%2==0) {
                    pos = width/2 + i * width - cards/2*width + i * 5;
                } else {
                    pos = i * width - cards/2*width + i * 5;
                }
        stackPane.getChildren().get(i).setTranslateX(pos);
        }
    }
}
