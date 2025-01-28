package com.example.balatro.classes;

import javafx.scene.image.ImageView;

public class Booster extends ImageView {

    private int boosterId;
    private String boosterImageUrl;
    private String boosterName;
    private String boosterCost;
    private String boosterSize;
    private String boosterEffect;

    public int getBoosterId() {
        return boosterId;
    }

    public void setBoosterId(int boosterId) {
        this.boosterId = boosterId;
    }

    public String getboosterImageUrl() {
        return boosterImageUrl;
    }

    public void setboosterImageUrl(String boosterImageUrl) {
        this.boosterImageUrl = boosterImageUrl;
    }

    public String getBoosterName() {
        return boosterName;
    }

    public void setBoosterName(String boosterName) {
        this.boosterName = boosterName;
    }

    public String getBoosterCost() {
        return boosterCost;
    }

    public void setBoosterCost(String boosterCost) {
        this.boosterCost = boosterCost;
    }

    public String getBoosterSize() {
        return boosterSize;
    }

    public void setBoosterSize(String boosterSize) {
        this.boosterSize = boosterSize;
    }

    public String getBoosterEffect() {
        return boosterEffect;
    }

    public void setBoosterEffect(String boosterEffect) {
        this.boosterEffect = boosterEffect;
    }
}
