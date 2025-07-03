package com.example.balatro.classes;

import com.example.balatro.controller.GameController;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.*;

import javafx.scene.layout.AnchorPane;

public class Booster extends Card implements PurchasableCard {

    @Override
    public void onPurchase(GameModel model, AnchorPane pane) {
        GameController.getInstance().playBooster(this);
        model.getShopModel().getBoosterMap().remove(this);
    }

    private static final DoubleProperty imageHeightProperty = new SimpleDoubleProperty();
    private static final DoubleProperty imageWidthProperty = new SimpleDoubleProperty();

    private final StringProperty boosterCost = new SimpleStringProperty("");
    private final StringProperty boosterSize = new SimpleStringProperty("");
    private final StringProperty boosterEffect = new SimpleStringProperty("");
    private final IntegerProperty boosterSizeValue = new SimpleIntegerProperty(0);
    private final IntegerProperty boosterChoiceValue = new SimpleIntegerProperty(0);

    //Constructor

    //Getter Setter
    public static double getImageHeightProperty() {
        return imageHeightProperty.get();
    }

    public static DoubleProperty imageHeightPropertyProperty() {
        return imageHeightProperty;
    }

    public static void setImageHeightProperty(double imageHeightProperty) {
        Booster.imageHeightProperty.set(imageHeightProperty);
    }

    public static double getImageWidthProperty() { return imageWidthProperty.get(); }

    public static DoubleProperty imageWidthPropertyProperty() {return imageWidthProperty; }

    public static void setImageWidthProperty(double imageWidthProperty) { Booster.imageWidthProperty.set(imageWidthProperty); }

    public String getBoosterCost() {
        return boosterCost.get();
    }

    public StringProperty boosterCostProperty() {
        return boosterCost;
    }

    public void setBoosterCost(String boosterCost) {
        this.boosterCost.set(boosterCost);
    }

    public String getBoosterSize() {
        return boosterSize.get();
    }

    public StringProperty boosterSizeProperty() {
        return boosterSize;
    }

    public void setBoosterSize(String boosterSize) {
        this.boosterSize.set(boosterSize);
    }

    public String getBoosterEffect() {
        return boosterEffect.get();
    }

    public StringProperty boosterEffectProperty() {
        return boosterEffect;
    }

    public void setBoosterEffect(String boosterEffect) {
        this.boosterEffect.set(boosterEffect);
    }

    public int getBoosterSizeValue() {
        return boosterSizeValue.get();
    }

    public IntegerProperty boosterSizeValueProperty() {
        return boosterSizeValue;
    }

    public void setBoosterSizeValue(int boosterSizeValue) {
        this.boosterSizeValue.set(boosterSizeValue);
    }

    public int getBoosterChoiceValue() {
        return boosterChoiceValue.get();
    }

    public IntegerProperty boosterChoiceValueProperty() {
        return boosterChoiceValue;
    }

    public void setBoosterChoiceValue(int boosterChoiceValue) {
        this.boosterChoiceValue.set(boosterChoiceValue);
    }

    //Functions
    public void setBooster(Booster booster) {
        setCardId(booster.getCardId());
        setCardImageUrl(booster.getCardImageUrl());
        setCardName(booster.getCardName());
        setCardCost(booster.getCardCost());
        setCardType(booster.getCardType());
        setBoosterSize(booster.getBoosterSize());
        setBoosterEffect(booster.getBoosterEffect());
        setBoosterSizeValue(booster.getBoosterSizeValue());
        setBoosterChoiceValue(booster.getBoosterChoiceValue());
    }


}
