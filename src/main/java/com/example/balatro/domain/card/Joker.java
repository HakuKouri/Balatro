package com.example.balatro.domain.card;

import com.example.balatro.domain.effects.JokerEffectRegistry;
import com.example.balatro.domain.effects.JokerEffectTrigger;
import com.example.balatro.domain.effects.JokerEffectUtil;
import com.example.balatro.domain.util.CardViewManager;
import com.example.balatro.enums.JokerTrigger;
import com.example.balatro.enums.Suit;
import com.example.balatro.interfaces.JokerEffect;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Joker extends Card implements PurchasableCard {
    @Override
    public void onPurchase(GameModel model) {
        model.getRunState().subMoney(model.getShopModel().getItemCardViewManager().getControllerByCard(this).getBuyPrice());
        CardViewManager.transferCardTo(model.getShopModel().getItemCardViewManager(), model.getJokerManager(), this);
    }

    //region Attributes
    private final StringProperty rarity = new SimpleStringProperty("");
    private final StringProperty unlockRequirement = new SimpleStringProperty("");
    private final StringProperty actTiming = new SimpleStringProperty("");
    private final StringProperty jokerType = new SimpleStringProperty("");
    private final BooleanProperty unlocked = new SimpleBooleanProperty(false);

    private final ObservableSet<Sticker> stickers = FXCollections.observableSet();

    private final List<JokerEffectTrigger> triggers = new ArrayList<>();
    private final StringProperty params = new SimpleStringProperty("");

    //Test Variablen
    private final ObjectProperty<Suit> suitFilter = new SimpleObjectProperty<>(Suit.NO_SUIT);
    private final StringProperty valueFilter = new SimpleStringProperty("");
    private final DoubleProperty multValue = new SimpleDoubleProperty(0);
    private final DoubleProperty chipValue = new SimpleDoubleProperty(0);
    private final DoubleProperty otherValue = new SimpleDoubleProperty(0);
    //endregion

    //region Constructor
    public Joker() {
    }
    //endregion

    //region Getter Setter
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

    public String getJokerType() {
        return jokerType.get();
    }

    public StringProperty jokerTypeProperty() {
        return jokerType;
    }

    public void setJokerType(String jokerType) {
        this.jokerType.set(jokerType);
    }

    public ObservableSet<Sticker> getStickers() {
        return stickers;
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


    //Test Getter Setter
    public Suit getSuitFilter() {
        return suitFilter.get();
    }

    public ObjectProperty<Suit> suitFilterProperty() {
        return suitFilter;
    }

    public void setSuitFilter(Suit suitFilter) {
        this.suitFilter.set(suitFilter);
    }

    public String getValueFilter() {
        return valueFilter.get();
    }

    public StringProperty valueFilterProperty() {
        return valueFilter;
    }

    public void setValueFilter(String valueFilter) {
        this.valueFilter.set(valueFilter);
    }

    public double getMultValue() {
        return multValue.get();
    }

    public DoubleProperty multValueProperty() {
        return multValue;
    }

    public void setMultValue(double multValue) {
        this.multValue.set(multValue);
    }

    public double getChipValue() {
        return chipValue.get();
    }

    public DoubleProperty chipValueProperty() {
        return chipValue;
    }

    public void setChipValue(double chipValue) {
        this.chipValue.set(chipValue);
    }

    public double getOtherValue() {
        return otherValue.get();
    }

    public DoubleProperty otherValueProperty() {
        return otherValue;
    }

    public void setOtherValue(double otherValue) {
        this.otherValue.set(otherValue);
    }
    //endregion

    //region Functions
    public void setJoker(Joker joker) {
        setCardId(joker.getCardId());
        setCardImageUrl(joker.getCardImageUrl());
        setCardName(joker.getCardName());
        setCardDescription(joker.getCardDescription());
        setCardCost(joker.getCardCost());
        setCardType(joker.getCardType());
        setActTiming(joker.getActTiming());
        setRarity(joker.getRarity());
        setUnlockRequirement(joker.getUnlockRequirement());
        setJokerType(joker.getJokerType());
        setTriggers(joker.getTriggers());
        setParams(joker.getParams());
    }

    // ==== Trigger Check ====
    public void tryActivate(JokerTrigger currentTrigger, GameModel gameModel, List<PlayingCard> playedCards) {
        System.out.println("Joker: " + getCardName());
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

    public void addSticker(Sticker sticker) {
        getStickers().add(sticker);
    }

    public Joker copy() {
        Joker joker = new Joker();
        joker.setCardId(getCardId());
        joker.setCardImageUrl(getCardImageUrl());
        joker.setCardName(getCardName());
        joker.setCardDescription(getCardDescription());
        joker.setCardCost(getCardCost());
        joker.setCardType(getCardType());
        joker.setActTiming(getActTiming());
        joker.setRarity(getRarity());
        joker.setUnlockRequirement(getUnlockRequirement());
        joker.setJokerType(getJokerType());
        joker.setTriggers(getTriggers());
        joker.setParams(getParams());
        return joker;
    }
    //endregion

}


