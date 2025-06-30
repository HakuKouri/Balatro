package com.example.balatro.classes;

import com.example.balatro.Balatro;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Tarot extends Card implements PurchasableCard {
    @Override
    public void onPurchase(GameModel model, AnchorPane pane) {
        model.getConsumableMap().put(CardViewController.getCardViewController(model.getShopModel().getItemMap(), pane), pane);
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

    //region Functions
    public void setTarot(Tarot tarot) {
        setCardId(tarot.getCardId());
        setCardName(tarot.getCardName());
        setCardImageUrl(tarot.getCardImageUrl());
        setCardCost(tarot.getCardCost());
        setCardType(tarot.getCardType());
        setTarotDescription(tarot.getTarotDescription());
    }


    //endregion
}
