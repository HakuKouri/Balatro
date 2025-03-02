package com.example.balatro.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Deck
{
    private int id;
    private StringProperty deckCoverUrl = new SimpleStringProperty();
    private String name;
    private String description;
    private String unlockCondition;
    private int stageCleared;



    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDeckCoverUrl() {
        return deckCoverUrl.get();
    }

    public StringProperty deckCoverUrlProperty() {
        return deckCoverUrl;
    }

    public void setDeckCoverUrl(String deckCoverUrl) {
        this.deckCoverUrl.set(deckCoverUrl);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUnlockCondition()
    {
        return unlockCondition;
    }

    public void setUnlockCondition(String unlockCondition)
    {
        this.unlockCondition = unlockCondition;
    }

    public int getStageCleared()
    {
        return stageCleared;
    }

    public void setStageCleared(int stageCleared)
    {
        this.stageCleared = stageCleared;
    }
}
