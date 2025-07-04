package com.example.balatro.domain.card;

import com.example.balatro.Balatro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sticker extends ImageView {

    //region Attribute
    private final IntegerProperty stickerId = new SimpleIntegerProperty(0);
    private final StringProperty stickerName = new SimpleStringProperty("");
    private final StringProperty stickerImageUrl = new SimpleStringProperty("");
    private final StringProperty stickerDescription = new SimpleStringProperty("");
    //endregion

    //region Constructor
    public Sticker() {
        stickerImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue));
            setPreserveRatio(true);
            fitHeightProperty().bind(Balatro.getSettings().cardHeightProperty());
        });
    }
    //endregion

    //region Getter Setter
    public int getStickerId() {
        return stickerId.get();
    }

    public IntegerProperty stickerIdProperty() {
        return stickerId;
    }

    public void setStickerId(int stickerId) {
        this.stickerId.set(stickerId);
    }

    public String getStickerName() {
        return stickerName.get();
    }

    public StringProperty stickerNameProperty() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName.set(stickerName);
    }

    public String getStickerImageUrl() {
        return stickerImageUrl.get();
    }

    public StringProperty stickerImageUrlProperty() {
        return stickerImageUrl;
    }

    public void setStickerImageUrl(String stickerImageUrl) {
        this.stickerImageUrl.set(stickerImageUrl);
    }

    public String getStickerDescription() {
        return stickerDescription.get();
    }

    public StringProperty stickerDescriptionProperty() {
        return stickerDescription;
    }

    public void setStickerDescription(String stickerDescription) {
        this.stickerDescription.set(stickerDescription);
    }

    //endregion

    //region Functions
    public void setSticker(Sticker sticker) {
        setStickerId(sticker.getStickerId());
        setStickerName(sticker.getStickerName());
        setStickerImageUrl(sticker.getStickerImageUrl());
        setStickerDescription(sticker.getStickerDescription());

    }
    //endregion
}
