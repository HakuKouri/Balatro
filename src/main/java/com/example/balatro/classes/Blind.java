package com.example.balatro.classes;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Blind extends ImageView
{
    private final IntegerProperty blindId = new SimpleIntegerProperty();
    private final StringProperty blindImageUrl = new SimpleStringProperty();
    private final StringProperty blindName = new SimpleStringProperty();
    private final StringProperty blindDescription = new SimpleStringProperty();
    private final StringProperty blindMinimumAnte = new SimpleStringProperty();
    private final StringProperty blindScoreMultiplier = new SimpleStringProperty();
    private final IntegerProperty blindReward = new SimpleIntegerProperty();
    private final BooleanProperty blindSkipped = new SimpleBooleanProperty();
    private final ObjectProperty<Color> colorScheme = new SimpleObjectProperty<>();
    private final BooleanProperty rewarded = new SimpleBooleanProperty();

    public Blind() {
        blindImageUrl.addListener((obs, oldVal, newVal) -> {
            this.setImage(new Image("file:" + newVal,true));
        });
        blindId.set(-1);
        blindImageUrl.set("");
        blindName.set("default");
        blindDescription.set("");
        blindMinimumAnte.set("-1");
        blindScoreMultiplier.set("0");
        blindReward.set(0);
        blindSkipped.set(false);
        colorScheme.set(new Color(0,0,0,0));
    }

    //region GETTER SETTER
    public void setBlind(Blind blind) {
        blindId.set(blind.getBlindId());
        blindImageUrl.set(blind.getBlindImageUrl());
        blindName.set(blind.getBlindName());
        blindDescription.set(blind.getBlindDescription());
        blindMinimumAnte.set(blind.getBlindMinimumAnte());
        blindScoreMultiplier.set(blind.getBlindScoreMultiplier());
        blindReward.set(blind.getBlindReward());
        blindSkipped.set(blind.isBlindSkipped());
        colorScheme.set(blind.getColorScheme());
    }

    public int getBlindId() {
        return blindId.get();
    }

    public IntegerProperty blindIdProperty() {
        return blindId;
    }

    public void setBlindId(int blindId) {
        this.blindId.set(blindId);
    }

    public String getBlindImageUrl() {
        return blindImageUrl.get();
    }

    public StringProperty blindImageUrlProperty() {
        return blindImageUrl;
    }

    public void setBlindImageUrl(String blindImageUrl) {
        this.blindImageUrl.set(blindImageUrl);
    }

    public String getBlindName() {
        return blindName.get();
    }

    public StringProperty blindNameProperty() {
        return blindName;
    }

    public void setBlindName(String blindName) {
        this.blindName.set(blindName);
    }

    public String getBlindDescription() {
        return blindDescription.get();
    }

    public StringProperty blindDescriptionProperty() {
        return blindDescription;
    }

    public void setBlindDescription(String blindDescription) {
        this.blindDescription.set(blindDescription);
    }

    public String getBlindMinimumAnte() {
        return blindMinimumAnte.get();
    }

    public StringProperty blindMinimumAnteProperty() {
        return blindMinimumAnte;
    }

    public void setBlindMinimumAnte(String blindMinimumAnte) {
        this.blindMinimumAnte.set(blindMinimumAnte);
    }

    public String getBlindScoreMultiplier() {
        return blindScoreMultiplier.get();
    }

    public StringProperty blindScoreMultiplierProperty() {
        return blindScoreMultiplier;
    }

    public void setBlindScoreMultiplier(String blindScoreMultiplier) {
        this.blindScoreMultiplier.set(blindScoreMultiplier);
    }

    public int getBlindReward() {
        return blindReward.get();
    }

    public IntegerProperty blindRewardProperty() {
        return blindReward;
    }

    public void setBlindReward(int blindReward) {
        this.blindReward.set(blindReward);
    }

    public boolean isBlindSkipped() {
        return blindSkipped.get();
    }

    public BooleanProperty blindSkippedProperty() {
        return blindSkipped;
    }

    public void setBlindSkipped(boolean blindSkipped) {
        this.blindSkipped.set(blindSkipped);
    }

    public Color getColorScheme() {
        return colorScheme.get();
    }

    public ObjectProperty<Color> colorSchemeProperty() {
        return colorScheme;
    }

    public void setColorScheme(Color colorScheme) {
        this.colorScheme.set(colorScheme);
    }

    public boolean isRewarded() {
        return rewarded.get();
    }

    public BooleanProperty rewardedProperty() {
        return rewarded;
    }

    //endregion

    //region Funktionen
    public void copyFrom(Blind newBlind) {
        System.out.println("Copy Done");
        setBlindId(newBlind.getBlindId());
        setBlindImageUrl(newBlind.getBlindImageUrl());
        setBlindName(newBlind.getBlindName());
        setBlindDescription(newBlind.getBlindDescription());
        setBlindMinimumAnte(newBlind.getBlindMinimumAnte());
        setBlindScoreMultiplier(newBlind.getBlindScoreMultiplier());
        setBlindReward(newBlind.getBlindReward());
        setBlindSkipped(newBlind.isBlindSkipped());
        setColorScheme(newBlind.getColorScheme());
    }
    //endregion
}
