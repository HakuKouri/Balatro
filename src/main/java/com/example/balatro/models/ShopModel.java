package com.example.balatro.models;

import com.example.balatro.controller.CardViewController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;

public class ShopModel {

    //region VARIABLES
    private final IntegerProperty maxItems = new SimpleIntegerProperty(2);
    private final DoubleProperty shopPrices =  new SimpleDoubleProperty(1);
    private final DoubleProperty editionChanceMultiplier = new SimpleDoubleProperty(1);
    private final IntegerProperty rerollPrice  = new SimpleIntegerProperty(5);
    private final IntegerProperty maxInterest = new SimpleIntegerProperty(5);

    private final ObservableMap<CardViewController, AnchorPane> itemMap = FXCollections.observableMap(new LinkedHashMap<>());
    private final ObservableMap<CardViewController, AnchorPane> boosterMap = FXCollections.observableMap(new LinkedHashMap<>());
    private final ObservableMap<CardViewController, AnchorPane> voucherMap = FXCollections.observableMap(new LinkedHashMap<>());
    //endregion

    //region CONSTRUCTOR

    //endregion

    //region GETTER SETTER
    public int getMaxItems() {
        return maxItems.get();
    }

    public IntegerProperty maxItemsProperty() {
        return maxItems;
    }

    public double getShopPrices() {
        return shopPrices.get();
    }

    public DoubleProperty shopPricesProperty() {
        return shopPrices;
    }

    public double getEditionChanceMultiplier() {
        return editionChanceMultiplier.get();
    }

    public DoubleProperty editionChanceMultiplierProperty() {
        return editionChanceMultiplier;
    }

    public int getRerollPrice() {
        return rerollPrice.get();
    }

    public IntegerProperty rerollPriceProperty() {
        return rerollPrice;
    }

    public int getMaxInterest() {
        return maxInterest.get();
    }

    public IntegerProperty maxInterestProperty() {
        return maxInterest;
    }

    public ObservableMap<CardViewController, AnchorPane> getItemMap() {
        return itemMap;
    }

    public ObservableMap<CardViewController, AnchorPane> getBoosterMap() {
        return boosterMap;
    }

    public ObservableMap<CardViewController, AnchorPane> getVoucherMap() {
        return voucherMap;
    }


    //endregion
}
