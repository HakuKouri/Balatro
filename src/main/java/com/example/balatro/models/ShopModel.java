package com.example.balatro.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ShopModel {

    //region VARIABLES
    private final IntegerProperty maxItems = new SimpleIntegerProperty(2);
    private final DoubleProperty shopPrices =  new SimpleDoubleProperty(1);
    private final DoubleProperty editionChanceMultiplier = new SimpleDoubleProperty(1);
    private final IntegerProperty rerollPrice  = new SimpleIntegerProperty(5);
    private final IntegerProperty maxInterest = new SimpleIntegerProperty(5);
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
    //endregion
}
