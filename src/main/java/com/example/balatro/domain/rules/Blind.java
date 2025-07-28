package com.example.balatro.domain.rules;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Blind extends ImageView
{
    private final IntegerProperty blindId = new SimpleIntegerProperty(-1);
    private final StringProperty blindImageUrl = new SimpleStringProperty("");
    private final StringProperty blindName = new SimpleStringProperty("");
    private final StringProperty blindDescription = new SimpleStringProperty("");
    private final IntegerProperty blindMinimumAnte = new SimpleIntegerProperty(0);
    private final DoubleProperty blindScoreMultiplier = new SimpleDoubleProperty(0);
    private final IntegerProperty blindReward = new SimpleIntegerProperty(0);
    private final BooleanProperty blindSkipped = new SimpleBooleanProperty(false);
    private final ObjectProperty<Color> colorScheme = new SimpleObjectProperty<>(new Color(0,0,0,0));
    private final BooleanProperty rewarded = new SimpleBooleanProperty(true);

    public Blind() {
        blindImageUrl.addListener((obs, oldVal, newVal) -> {
            this.setImage(new Image("file:" + newVal,true));
        });
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
        rewarded.set(blind.isRewarded());
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

    public int getBlindMinimumAnte() {
        return blindMinimumAnte.get();
    }

    public IntegerProperty blindMinimumAnteProperty() {
        return blindMinimumAnte;
    }

    public void setBlindMinimumAnte(int blindMinimumAnte) {
        this.blindMinimumAnte.set(blindMinimumAnte);
    }

    public double getBlindScoreMultiplier() {
        return blindScoreMultiplier.get();
    }

    public DoubleProperty blindScoreMultiplierProperty() {
        return blindScoreMultiplier;
    }

    public void setBlindScoreMultiplier(double blindScoreMultiplier) {
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

    //endregion
}
