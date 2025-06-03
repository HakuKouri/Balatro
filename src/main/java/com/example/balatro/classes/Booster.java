package com.example.balatro.classes;

import javafx.beans.property.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Booster extends ImageView {
    private static final DoubleProperty imageHeightProperty = new SimpleDoubleProperty();
    private static final DoubleProperty imageWidthProperty = new SimpleDoubleProperty();

    private int boosterId;
    private StringProperty boosterImageUrl = new SimpleStringProperty("");
    private StringProperty boosterName = new SimpleStringProperty("");
    private StringProperty boosterCost = new SimpleStringProperty("");
    private StringProperty boosterSize = new SimpleStringProperty("");
    private StringProperty boosterEffect = new SimpleStringProperty("");

    //Constructor
    public Booster() {
        boosterImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue, true));
        });
        setFitHeight(getImageHeightProperty());
        setPreserveRatio(true);

        setBoosterId(-1);
        setBoosterImageUrl("");
        setBoosterName("default");
        setBoosterCost("$0");
        setBoosterSize("default");
        setBoosterEffect("default");
    }

    public Booster(Booster booster) {
        boosterImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue, true));
        });
        setFitHeight(getImageHeightProperty());
        setPreserveRatio(true);


        this.boosterId = booster.boosterId;
        this.boosterImageUrl = booster.boosterImageUrl;
        this.boosterName = booster.boosterName;
        this.boosterCost = booster.boosterCost;
        this.boosterSize = booster.boosterSize;
        this.boosterEffect = booster.boosterEffect;
    }

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

    public int getBoosterId() {
        return boosterId;
    }

    public void setBoosterId(int boosterId) {
        this.boosterId = boosterId;
    }

    public String getBoosterImageUrl() {
        return boosterImageUrl.get();
    }

    public StringProperty boosterImageUrlProperty() {
        return boosterImageUrl;
    }

    public void setBoosterImageUrl(String boosterImageUrl) {
        this.boosterImageUrl.set(boosterImageUrl);
    }

    public String getBoosterName() {
        return boosterName.get();
    }

    public StringProperty boosterNameProperty() {
        return boosterName;
    }

    public void setBoosterName(String boosterName) {
        this.boosterName.set(boosterName);
    }

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
        setBoosterId(booster.boosterId);
        setBoosterImageUrl(booster.getBoosterImageUrl());
        setBoosterName(booster.getBoosterName());
        setBoosterCost(booster.getBoosterCost());
        setBoosterSize(booster.getBoosterSize());
        setBoosterEffect(booster.getBoosterEffect());
    }
}
