package com.example.balatro.classes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Joker extends Card
{
    private StringProperty jokerImageUrl = new SimpleStringProperty("");
    private StringProperty jokerEffect = new SimpleStringProperty("");
    private StringProperty rarity = new SimpleStringProperty("");
    private StringProperty unlockRequirement = new SimpleStringProperty("");
    private StringProperty actTiming = new SimpleStringProperty("");
    private BooleanProperty unlocked = new SimpleBooleanProperty(false);

    public Joker() {
        setup();
    }

    public Joker(Joker joker) {
        setup();
        setJokerImageUrl(joker.getJokerImageUrl());
        setJokerEffect(joker.getJokerEffect());
        setRarity(joker.getRarity());
        setUnlockRequirement(joker.getUnlockRequirement());
        setActTiming(joker.getActTiming());
        setUnlocked(joker.getUnlocked());
    }


    public String getJokerImageUrl() {
        return jokerImageUrl.get();
    }

    public StringProperty jokerImageUrlProperty() {
        return jokerImageUrl;
    }

    public void setJokerImageUrl(String jokerImageUrl) {
        this.jokerImageUrl.set(jokerImageUrl);
    }

    public String getJokerEffect() {
        return jokerEffect.get();
    }

    public StringProperty jokerEffectProperty() {
        return jokerEffect;
    }

    public void setJokerEffect(String jokerEffect) {
        this.jokerEffect.set(jokerEffect);
    }

    public String getRarity() {
        return rarity.get();
    }

    public StringProperty rarityProperty() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity.set(rarity);
    }

    public String getUnlockRequirement() {
        return unlockRequirement.get();
    }

    public StringProperty unlockRequirementProperty() {
        return unlockRequirement;
    }

    public void setUnlockRequirement(String unlockRequirement) {
        this.unlockRequirement.set(unlockRequirement);
    }

    public String getActTiming() {
        return actTiming.get();
    }

    public StringProperty actTimingProperty() {
        return actTiming;
    }

    public void setActTiming(String actTiming) {
        this.actTiming.set(actTiming);
    }

    public boolean getUnlocked() {
        return unlocked.get();
    }

    public BooleanProperty unlockedProperty() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked.set(unlocked);
    }

    private void setup() {
        jokerImageUrlProperty().addListener((obs, oldUrl, newUrl) -> {
            setImage(new Image("file:" + newUrl));
        });
        this.setFitHeight(290);
        this.setPreserveRatio(true);
    }
}
