package com.example.balatro.classes;

import com.example.balatro.controller.CardViewController;
import com.example.balatro.enums.TarotEffect;
import com.example.balatro.interfaces.PlayableCard;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.AnchorPane;

public class Tarot extends Card implements PurchasableCard, PlayableCard {
    @Override
    public void onPurchase(GameModel model, AnchorPane pane) {
        model.getConsumableMap().put(CardViewController.getCardViewController(model.getShopModel().getItemMap(), pane), pane);
    }

    @Override
    public boolean canPlay(GameModel model) {
        return effect != null && effect.canPlay(model);
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


    public void setEffect(TarotEffect effect) {
        this.effect = effect;
    }

    //region Functions
    public void setTarot(Tarot tarot) {
        setCardId(tarot.getCardId());
        setCardName(tarot.getCardName());
        setCardImageUrl(tarot.getCardImageUrl());
        setCardCost(tarot.getCardCost());
        setCardType(tarot.getCardType());
        setTarotDescription(tarot.getTarotDescription());
        setEffect(tarot.getEffect());
    }

    public void play(GameModel model) {
        System.out.println("Playing tarot");
        if(effect != null) {
            System.out.println("Effect Apply");
            effect.apply(model);
        }
    }
    //endregion
}
