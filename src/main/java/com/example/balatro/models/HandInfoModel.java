package com.example.balatro.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HandInfoModel {
    private StringProperty handName = new SimpleStringProperty();
    private IntegerProperty handLevel = new SimpleIntegerProperty();
    private IntegerProperty handChips = new SimpleIntegerProperty();
    private IntegerProperty handMutliplier = new SimpleIntegerProperty();

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

    public int getHandMutliplier() {
        return handMutliplier.get();
    }

    public IntegerProperty handMutliplierProperty() {
        return handMutliplier;
    }

    public void setHandMutliplier(int handMutliplier) {
        this.handMutliplier.set(handMutliplier);
    }
}
