package com.example.balatro.classes;

import javafx.beans.property.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Booster extends Card {
    private static final DoubleProperty imageHeightProperty = new SimpleDoubleProperty();
    private static final DoubleProperty imageWidthProperty = new SimpleDoubleProperty();

    private StringProperty boosterCost = new SimpleStringProperty("");
    private StringProperty boosterSize = new SimpleStringProperty("");
    private StringProperty boosterEffect = new SimpleStringProperty("");

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

    //Functions
    public void setBooster(Booster booster) {
        setCardId(booster.getCardId());
        setCardImageUrl(booster.getCardImageUrl());
        setCardName(booster.getCardName());
        setCardCost(booster.getCardCost());
        setCardType(booster.getCardType());
        setBoosterSize(booster.getBoosterSize());
        setBoosterEffect(booster.getBoosterEffect());
    }
}
