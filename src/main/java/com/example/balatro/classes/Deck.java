package com.example.balatro.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Deck extends ImageView
{
    private final IntegerProperty deckId = new SimpleIntegerProperty();
    private final StringProperty deckName = new SimpleStringProperty();
    private final StringProperty deckCoverUrl = new SimpleStringProperty();
    private final StringProperty deckDescription = new SimpleStringProperty();
    private final StringProperty unlockCondition = new SimpleStringProperty();
    private final IntegerProperty stageCleared = new SimpleIntegerProperty();


    public void setDeck(Deck deck) {
        setDeckId(deck.getDeckId());
        setDeckName(deck.getDeckName());
        setDeckCoverUrl(deck.getDeckCoverUrl());
        setDeckDescription(deck.getDeckDescription());
        setUnlockCondition(deck.getUnlockCondition());
        setStageCleared(deck.getStageCleared());
    }


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
}
