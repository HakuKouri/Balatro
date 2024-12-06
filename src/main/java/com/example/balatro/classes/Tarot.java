package com.example.balatro.classes;

public class Tarot
{
    private int id;
    private String tarotImageUrl;
    private String tarotName;
    private String tarotDescription;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTarotImageUrl()
    {
        return tarotImageUrl;
    }

    public void setTarotImageUrl(String tarotImageUrl)
    {
        this.tarotImageUrl = tarotImageUrl;
    }

    public String getTarotName()
    {
        return tarotName;
    }

    public void setTarotName(String tarotName)
    {
        this.tarotName = tarotName;
    }

    public String getTarotDescription()
    {
        return tarotDescription;
    }

    public void setTarotDescription(String tarotDescription)
    {
        this.tarotDescription = tarotDescription;
    }
}
