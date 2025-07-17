package com.example.balatro.domain.card;

import com.example.balatro.domain.util.CardViewManager;
import com.example.balatro.enums.TarotEffect;
import com.example.balatro.interfaces.PlayableCard;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tarot extends Card implements PurchasableCard, PlayableCard {
    @Override
    public void onPurchase(GameModel model) {
        model.getRunState().subMoney(model.getShopModel().getItemCardViewManager().getControllerByCard(this).getBuyPrice());
        CardViewManager.transferCardTo(model.getShopModel().getItemCardViewManager(), model.getConsumableManager(), this);
    }

    @Override
    public boolean canPlay(GameModel model) {
        System.out.println("Tarot canPlay called for: " + getCardName() + " | Effect: " + effect);
        return effect != null && effect.canPlay(model);
    }

    public void play(GameModel model, Runnable onFinished) {
        System.out.println("Playing tarot");
        if(effect != null) {
            System.out.println("Effect Apply");
            effect.apply(model);
        }

        if (onFinished != null) {
            onFinished.run();
        }
    }

    private final StringProperty tarotDescription = new SimpleStringProperty("");

    public String getTarotDescription() {
        return tarotDescription.get();
    }

    public StringProperty tarotDescriptionProperty() {
        return tarotDescription;
    }

    public void setTarotDescription(String tarotDescription) {
        this.tarotDescription.set(tarotDescription);
    }

    private TarotEffect effect;
    public void setTarotEffect(TarotEffect effect) {
        this.effect = effect;
    }

    public TarotEffect getTarotEffect() {
        return effect;
    }
    //region Functions

    public void setTarot(Tarot tarot) {
        setCardId(tarot.getCardId());
        setCardName(tarot.getCardName());
        setCardImageUrl(tarot.getCardImageUrl());
        setCardCost(tarot.getCardCost());
        setCardType(tarot.getCardType());
        setTarotDescription(tarot.getTarotDescription());
        setTarotEffect(tarot.getTarotEffect());
    }

    public Tarot copy() {
        Tarot tarot = new Tarot();
        setCardId(tarot.getCardId());
        setCardName(tarot.getCardName());
        setCardImageUrl(tarot.getCardImageUrl());
        setCardCost(tarot.getCardCost());
        setCardType(tarot.getCardType());
        setTarotDescription(tarot.getTarotDescription());
        setTarotEffect(tarot.getTarotEffect());
        return tarot;
    }
    //endregion
}
