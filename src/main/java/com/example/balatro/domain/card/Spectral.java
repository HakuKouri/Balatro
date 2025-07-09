package com.example.balatro.domain.card;

import com.example.balatro.Balatro;
import com.example.balatro.domain.util.CardViewManager;
import com.example.balatro.enums.SpectralEffect;
import com.example.balatro.interfaces.PlayableCard;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Spectral extends Card implements PurchasableCard, PlayableCard {

    //region @Override
    @Override
    public void onPurchase(GameModel model) {
        model.getRunState().subMoney(model.getShopModel().getItemCardViewManager().getControllerByCard(this).getBuyPrice());
        CardViewManager.transferCardTo(model.getShopModel().getItemCardViewManager(), model.getConsumableManager(), this);
    }

    @Override
    public boolean canPlay(GameModel model) {
        return effect != null && effect.canPlay(model);
    }
    //endregion

    //region Attributes
    private final StringProperty spectralImageUrl = new SimpleStringProperty("");
    private final StringProperty spectralName = new SimpleStringProperty("");
    private final StringProperty spectralDescription = new SimpleStringProperty("");

    private SpectralEffect effect;
    //endregion

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

    public String getSpectralDescription() {
        return spectralDescription.get();
    }

    public StringProperty spectralDescriptionProperty() {
        return spectralDescription;
    }

    public void setSpectralDescription(String spectralDescription) {
        this.spectralDescription.set(spectralDescription);
    }

    public void setEffect(SpectralEffect effect) {
        this.effect = effect;
    }


    //endregion

    //region Functions
    public void setSpectral(Spectral spectral) {
        setCardId(spectral.getCardId());
        setCardName(spectral.getCardName());
        setCardImageUrl(spectral.getCardImageUrl());
        setCardType(spectral.getCardType());
        setCardDescription(spectral.getCardDescription());
        setEffect(spectral.getEffect());
    }

    public void play(GameModel model) {
        System.out.println("Playing Spectral");
        if(effect != null) {
            System.out.println("Effect Apply");
            effect.apply(model);
        }
    }

    //endregion
}
