package com.example.balatro.classes;

public class
Deck
{
    private int id;
    private String deckCoverUrl;
    private String name;
    private String description;
    private String unlockCondition;
    private int stageCleared;

    public String getDeckCoverUrl()
    {
        return deckCoverUrl;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setDeckCoverUrl(String deckCover)
    {
        this.deckCoverUrl = deckCover;
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
