package com.example.balatro.classes;

import javafx.scene.image.ImageView;

public class Card extends ImageView
{
    protected int id;
    protected String name;
    protected String type;
    protected String cardFrontCoverUrl;
    protected String description;
    protected int cost;

    public int getCardId()
    {
        return id;
    }

    public void setCardId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCardFrontCoverUrl()
    {
        return cardFrontCoverUrl;
    }

    public void setCardFrontCoverUrl(String cardFrontCover)
    {
        this.cardFrontCoverUrl = cardFrontCover;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}
