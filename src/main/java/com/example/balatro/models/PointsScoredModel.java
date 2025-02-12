package com.example.balatro.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PointsScoredModel {

    private LongProperty scoredPoints = new SimpleLongProperty();
    private StringProperty stakeChipImageUrl = new SimpleStringProperty("file:src/main/resources/com/images/Stakechips/stake_chip_1.png");

    public long getScoredPoints() {
        return scoredPoints.get();
    }

    public LongProperty scoredPointsProperty() {
        return scoredPoints;
    }

    public void setScoredPoints(long scoredPoints) {
        this.scoredPoints.set(scoredPoints);
    }

    public String getStakeChipImageUrl() {
        return stakeChipImageUrl.get();
    }

    public StringProperty stakeChipImageUrlProperty() {
        return stakeChipImageUrl;
    }

    public void setStakeChipImageUrl(String stakeChipImageUrl) {
        this.stakeChipImageUrl.set(stakeChipImageUrl);
    }
}
