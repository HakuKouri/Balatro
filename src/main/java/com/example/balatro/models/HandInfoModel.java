package com.example.balatro.models;

import javafx.beans.property.*;

public class HandInfoModel {
    private StringProperty handName = new SimpleStringProperty();
    private IntegerProperty handLevel = new SimpleIntegerProperty();
    private IntegerProperty handChips = new SimpleIntegerProperty();
    private DoubleProperty handMultiplier = new SimpleDoubleProperty();

    public String getHandName() {
        return handName.get();
    }

    public StringProperty handNameProperty() {
        return handName;
    }

    public void setHandName(String handName) {
        this.handName.set(handName);
    }

    public int getHandLevel() {
        return handLevel.get();
    }

    public IntegerProperty handLevelProperty() {
        return handLevel;
    }

    public void setHandLevel(int handLevel) {
        this.handLevel.set(handLevel);
    }

    public int getHandChips() {
        return handChips.get();
    }

    public IntegerProperty handChipsProperty() {
        return handChips;
    }

    public void setHandChips(int handChips) {
        this.handChips.set(handChips);
    }

    public double getHandMultiplier() {
        return handMultiplier.get();
    }

    public DoubleProperty handMultiplierProperty() {
        return handMultiplier;
    }

    public void setHandMultiplier(int handMultiplier) {
        this.handMultiplier.set(handMultiplier);
    }
}
