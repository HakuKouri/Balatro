package com.example.balatro.classes;

import javafx.scene.image.Image;

public class Card
{
    protected int id;
    protected String name;
    protected String type;
    protected Image cardFrontCover;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public Image getCardFrontCover()
    {
        return cardFrontCover;
    }

    public void setCardFrontCover(Image cardFrontCover)
    {
        this.cardFrontCover = cardFrontCover;
    }


}
