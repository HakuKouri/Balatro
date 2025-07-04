package com.example.balatro.domain.deck;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SelectableDeck extends ImageView
{
    //TODO Make Deck -> SelectableDeck und erstelle Deck Klasse
    //region ATTRIBUTES
    private final IntegerProperty deckId = new SimpleIntegerProperty();
    private final StringProperty deckName = new SimpleStringProperty();
    private final StringProperty deckCoverUrl = new SimpleStringProperty();
    private final StringProperty deckDescription = new SimpleStringProperty();
    private final StringProperty unlockCondition = new SimpleStringProperty();
    private final IntegerProperty stageCleared = new SimpleIntegerProperty();
    //endregion

    //region GETTER SETTER
    public int getDeckId() {
        return deckId.get();
    }

    public IntegerProperty deckIdProperty() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId.set(deckId);
    }

    public String getDeckName() {
        return deckName.get();
    }

    public StringProperty deckNameProperty() {
        return deckName;
    }

    public void setDeckName(String name) {
        deckName.set(name);
    }

    public String getDeckCoverUrl() {
        return deckCoverUrl.get();
    }

    public StringProperty deckCoverUrlProperty() {
        return deckCoverUrl;
    }

    public void setDeckCoverUrl(String url) {
        setImage(new Image("file:" + url));
        deckCoverUrl.set(url);
    }

    public String getDeckDescription() {
        return deckDescription.get();
    }

    public StringProperty deckDescriptionProperty() {
        return deckDescription;
    }

    public void setDeckDescription(String description) {
        deckDescription.set(description);
    }

    public String getUnlockCondition() {
        return unlockCondition.get();
    }

    public StringProperty unlockConditionProperty() {
        return unlockCondition;
    }

    public void setUnlockCondition(String condition) {
        unlockCondition.set(condition);
    }

    public int getStageCleared() {
        return stageCleared.get();
    }

    public IntegerProperty stageClearedProperty() {
        return stageCleared;
    }

    public void setStageCleared(int clearedStage) {
        stageCleared.set(clearedStage);
    }
    //endregion

    //region FUNCTIONS
    public void setDeck(SelectableDeck selectableDeck) {
        deckCoverUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue, true));
        });
        setDeckId(selectableDeck.getDeckId());
        setDeckName(selectableDeck.getDeckName());
        setDeckCoverUrl(selectableDeck.getDeckCoverUrl());
        setDeckDescription(selectableDeck.getDeckDescription());
        setUnlockCondition(selectableDeck.getUnlockCondition());
        setStageCleared(selectableDeck.getStageCleared());
    }
    //endregion
}
