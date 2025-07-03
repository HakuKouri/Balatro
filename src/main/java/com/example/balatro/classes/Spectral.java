package com.example.balatro.classes;

import com.example.balatro.Balatro;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Spectral extends Card implements PurchasableCard {

    @Override
    public void onPurchase(GameModel model, AnchorPane pane) {
        model.getConsumableMap().put(CardViewController.getCardViewController(model.getShopModel().getItemMap(), pane), pane);
    }
    private final StringProperty spectralImageUrl = new SimpleStringProperty("");
    private final StringProperty spectralName = new SimpleStringProperty("");
    private final StringProperty spectralEffect = new SimpleStringProperty("");

    //region Constructor
    public Spectral() {
        spectralImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue, true));
        });

        setFitHeight(Balatro.getSettings().getCardHeight());
        setPreserveRatio(true);
    }
    //endregion


    //region Getter Setter
    public String getSpectralImageUrl() {
        return spectralImageUrl.get();
    }

    public StringProperty spectralImageUrlProperty() {
        return spectralImageUrl;
    }

    public void setSpectralImageUrl(String spectralImageUrl) {
        this.spectralImageUrl.set(spectralImageUrl);
    }

    public String getSpectralName() {
        return spectralName.get();
    }

    public StringProperty spectralNameProperty() {
        return spectralName;
    }

    public void setSpectralName(String spectralName) {
        this.spectralName.set(spectralName);
    }

    public String getSpectralEffect() {
        return spectralEffect.get();
    }

    public StringProperty spectralEffectProperty() {
        return spectralEffect;
    }

    public void setSpectralEffect(String spectralEffect) {
        this.spectralEffect.set(spectralEffect);
    }
    //endregion

    //region Functions
    public void setSpectral(Spectral spectral) {
        setCardId(spectral.getCardId());
        setCardName(spectral.getCardName());
        setCardImageUrl(spectral.getCardImageUrl());
        setCardType(spectral.getCardType());
        setCardDescription(spectral.getCardDescription());
    }
    //endregion
}
