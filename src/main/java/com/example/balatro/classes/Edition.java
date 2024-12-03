package com.example.balatro.classes;

public class Edition
{
    private int id;
    private String cardImageUrl;
    private String name;
    private String effect;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCardImageUrl()
    {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl)
    {
        this.cardImageUrl = cardImageUrl;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEffect()
    {
        return effect;
    }

    public void setEffect(String effect)
    {
        this.effect = effect;
    }
}
