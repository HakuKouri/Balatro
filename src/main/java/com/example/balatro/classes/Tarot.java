package com.example.balatro.classes;

import com.example.balatro.Balatro;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Tarot extends Card
{
    private final StringProperty tarotImageUrl = new SimpleStringProperty("");
    private final StringProperty tarotName = new SimpleStringProperty("");
    private final StringProperty tarotDescription = new SimpleStringProperty("");

    public Tarot() {
        tarotImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue, true));
        });

        setFitHeight(Balatro.getSettings().getCardHeight());
        setPreserveRatio(true);
    }

    public String getTarotImageUrl() {
        return tarotImageUrl.get();
    }

    public StringProperty tarotImageUrlProperty() {
        return tarotImageUrl;
    }

    public void setTarotImageUrl(String tarotImageUrl) {
        this.tarotImageUrl.set(tarotImageUrl);
    }

    public String getTarotName() {
        return tarotName.get();
    }

    public StringProperty tarotNameProperty() {
        return tarotName;
    }

    public void setTarotName(String tarotName) {
        this.tarotName.set(tarotName);
    }

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
        setTarotImageUrl(tarot.getTarotImageUrl());
        setTarotName(tarot.getTarotName());
        setTarotDescription(tarot.getTarotDescription());
    }


    //endregion
}
