package com.example.balatro.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Stake extends ImageView
{
    private final IntegerProperty stakeId = new SimpleIntegerProperty();
    private final StringProperty stakeImageStickerUrl = new SimpleStringProperty();
    private final StringProperty stakeImageChipUrl = new SimpleStringProperty();
    private final StringProperty stakeName = new SimpleStringProperty();
    private final StringProperty stakeEffect = new SimpleStringProperty();
    private final StringProperty stakeUnlockCondition = new SimpleStringProperty();


    public void setStake(Stake chosenStake) {
        setStakeId(chosenStake.getStakeId());
        setStakeName(chosenStake.getStakeName());
        setStakeImageStickerUrl(chosenStake.getStakeImageStickerUrl());
        setStakeImageChipUrl(chosenStake.getStakeImageChipUrl());
        setStakeEffect(chosenStake.getStakeEffect());
        setStakeUnlockCondition(chosenStake.getStakeUnlockCondition());
    }

    public int getStakeId() {
        return stakeId.get();
    }

    public IntegerProperty stakeIdProperty() {
        return stakeId;
    }

    public void setStakeId(int stakeId) {
        this.stakeId.set(stakeId);
    }

    public String getStakeImageStickerUrl() {
        return stakeImageStickerUrl.get();
    }

    public StringProperty stakeImageStickerUrlProperty() {
        return stakeImageStickerUrl;
    }

    public void setStakeImageStickerUrl(String stakeImageStickerUrl) {
        this.stakeImageStickerUrl.set(stakeImageStickerUrl);
    }

    public String getStakeImageChipUrl() {
        return stakeImageChipUrl.get();
    }

    public StringProperty stakeImageChipUrlProperty() {
        return stakeImageChipUrl;
    }

    public void setStakeImageChipUrl(String stakeImageChipUrl) {
        setImage(new Image("file:" + stakeImageChipUrl));
        this.stakeImageChipUrl.set(stakeImageChipUrl);
    }

    public String getStakeName() {
        return stakeName.get();
    }

    public StringProperty stakeNameProperty() {
        return stakeName;
    }

    public void setStakeName(String stakeName) {
        this.stakeName.set(stakeName);
    }

    public String getStakeEffect() {
        return stakeEffect.get();
    }

    public StringProperty stakeEffectProperty() {
        return stakeEffect;
    }

    public void setStakeEffect(String stakeEffect) {
        this.stakeEffect.set(stakeEffect);
    }

    public String getStakeUnlockCondition() {
        return stakeUnlockCondition.get();
    }

    public StringProperty stakeUnlockConditionProperty() {
        return stakeUnlockCondition;
    }

    public void setStakeUnlockCondition(String stakeUnlockCondition) {
        this.stakeUnlockCondition.set(stakeUnlockCondition);
    }
}
