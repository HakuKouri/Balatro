package com.example.balatro.domain.card;

import com.example.balatro.Balatro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Seal extends ImageView
{
    //region Attributes
    private final IntegerProperty sealID = new SimpleIntegerProperty();
    private final StringProperty sealImageUrl = new SimpleStringProperty("");
    private final StringProperty sealName = new SimpleStringProperty("");
    private final StringProperty sealEffect = new SimpleStringProperty("");
    //endregion

    //region Constructor
    public Seal() {
        sealImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue));
            setPreserveRatio(true);
            fitHeightProperty().bind(Balatro.getSettings().cardHeightProperty());
        });
    }
    //endregion

    //region Getter Setter
    public int getSealId() {
        return sealID.get();
    }

    public IntegerProperty sealIdProperty() {
        return sealID;
    }

    public void setSealID(int sealID) {
        this.sealID.set(sealID);
    }

    public String getSealImageUrl() {
        return sealImageUrl.get();
    }

    public StringProperty sealImageUrlProperty() {
        return sealImageUrl;
    }

    public void setSealImageUrl(String sealImageUrl) {
        this.sealImageUrl.set(sealImageUrl);
    }

    public String getSealName() {
        return sealName.get();
    }

    public StringProperty sealNameProperty() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName.set(sealName);
    }

    public String getSealEffect() {
        return sealEffect.get();
    }

    public StringProperty sealEffectProperty() {
        return sealEffect;
    }

    public void setSealEffect(String sealEffect) {
        this.sealEffect.set(sealEffect);
    }

    //endregion

    //region Functions
    @Override
    public String toString() {
        return sealID + " | " + sealImageUrl + " | " + sealName + " | " + sealEffect;
    }

    public void setSeal(Seal seal) {
        setSealID(seal.getSealId());
        setSealImageUrl(seal.getSealImageUrl());
        setSealName(seal.getSealName());
        setSealEffect(seal.getSealEffect());
    }

    public Seal copy() {
        Seal seal = new Seal();
        seal.setSealID(sealID.get());
        seal.setSealImageUrl(sealImageUrl.get());
        seal.setSealName(sealName.get());
        seal.setSealEffect(sealEffect.get());
        return seal;
    }

    //endregion
}
