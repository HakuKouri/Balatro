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
import java.util.Map;

public class Joker extends Card {
    private final StringProperty jokerImageUrl = new SimpleStringProperty("");
    private final StringProperty jokerDescription = new SimpleStringProperty("");
    private final StringProperty rarity = new SimpleStringProperty("");
    private final StringProperty unlockRequirement = new SimpleStringProperty("");
    private final StringProperty actTiming = new SimpleStringProperty("");
    private final BooleanProperty unlocked = new SimpleBooleanProperty(false);

    private final List<JokerEffectTrigger> triggers = new ArrayList<>();
    private final StringProperty params = new SimpleStringProperty("");

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
        setUnlocked(joker.isUnlocked());
        triggers.addAll(joker.triggers);
        setParams(joker.getParams());
    }

    // ==== Trigger Check ====
    public void tryActivate(JokerTrigger currentTrigger, GameModel gameModel, List<PlayingCard> playedCards) {
        System.out.println("Joker: " + getName());
        System.out.println("tryActivate called with: " + currentTrigger);
        // Parsen der JSON-Params (Liste von Param-Maps)
        List<Map<String, Object>> paramList = JokerEffectUtil.parseParamList(params.get());

        int effectIndex = 0; // Index für Parametrisierung
        for (JokerEffectTrigger triggerEntry : triggers) {
            if (triggerEntry.getTrigger() == currentTrigger) {
                List<String> effectKeys = triggerEntry.getEffectKeys();
                for (String effectKey : effectKeys) {
                    JokerEffect effect = JokerEffectRegistry.getEffect(effectKey);
                    if (effect != null) {
                        System.out.println("Activating effect: " + effectKey);
                        Map<String, Object> effectParams = effectIndex < paramList.size()
                                ? paramList.get(effectIndex)
                                : Map.of(); // fallback falls params fehlen
                        effect.apply(gameModel, this, playedCards, effectParams);
                    } else {
                        System.out.println("Effect not found for key: " + effectKey);
                    }
                    effectIndex++;
                }
            }
        }
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

    public boolean isUnlocked() {
        return unlocked.get();
    }

    public BooleanProperty unlockedProperty() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked.set(unlocked);
    }

    // ==== Getter / Setter für Trigger & Effect ====
    public List<JokerEffectTrigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<JokerEffectTrigger> triggers) {
        this.triggers.clear();
        this.triggers.addAll(triggers);
    }

    public String getParams() {
        return params.get();
    }

    public StringProperty paramsProperty() {
        return params;
    }

    public void setParams(String params) {
        this.params.set(params);
    }

    private void setup() {
        jokerImageUrlProperty().addListener((obs, oldUrl, newUrl) -> {
            setImage(new Image("file:" + newUrl));
        });

        this.setFitHeight(120);
        this.setPreserveRatio(true);
    }


}


