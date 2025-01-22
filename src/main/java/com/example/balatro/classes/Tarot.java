package com.example.balatro.classes;

public class Tarot extends Card
{
    private String tarotImageUrl;
    private String tarotName;
    private String tarotDescription;

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
