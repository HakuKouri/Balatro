package com.example.balatro.classes;

import com.example.balatro.Balatro;
import com.example.balatro.interfaces.JokerEffect;
import com.example.balatro.models.GameModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Joker extends Card
{
    private final StringProperty jokerImageUrl = new SimpleStringProperty("");
    private final StringProperty jokerDescription = new SimpleStringProperty("");
    private final StringProperty rarity = new SimpleStringProperty("");
    private final StringProperty unlockRequirement = new SimpleStringProperty("");
    private final StringProperty actTiming = new SimpleStringProperty("");
    private final BooleanProperty unlocked = new SimpleBooleanProperty(false);

    private final List<JokerEffectTrigger> triggers = new ArrayList<>();

    public Joker() {
        setup();
    }

    public Joker(Joker joker) {
        setup();
        setJokerImageUrl(joker.getJokerImageUrl());
        setJokerDescription(joker.getJokerDescription());
        setRarity(joker.getRarity());
        setUnlockRequirement(joker.getUnlockRequirement());
        setActTiming(joker.getActTiming());
        setUnlocked(joker.getUnlocked());
        triggers.addAll(joker.triggers);
    }

    // ==== Trigger Check ====
    public void tryActivate(JokerTrigger currentTrigger, GameModel gameModel, List<PlayingCard> playedCards) {
//        if (triggers.contains(currentTrigger) && effect != null) {
//            effect.apply(gameModel, this, playedCards);
//        }
    }

    // ==== Getter / Setter für Trigger & Effect ====
    public List<JokerEffectTrigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<JokerEffectTrigger> triggers) {
        this.triggers.clear();
        this.triggers.addAll(triggers);
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

    public String getJokerDescription() {
        return jokerDescription.get();
    }

    public StringProperty jokerDescriptionProperty() {
        return jokerDescription;
    }

    public void setJokerDescription(String jokerDescription) {
        this.jokerDescription.set(jokerDescription);
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

        this.setFitHeight(120);
        this.setPreserveRatio(true);
    }


}


