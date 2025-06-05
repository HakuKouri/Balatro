package com.example.balatro.classes;

import com.example.balatro.Balatro;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Tarot extends Card
{
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
