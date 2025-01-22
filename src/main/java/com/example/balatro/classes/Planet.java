package com.example.balatro.classes;

public class Planet extends Card
{

    private String planetImageUrl;
    private String planetName;
    private String planetAddition;
    private String planetPokerHand;
    private String planetHandBaseScore;
    private boolean secret;

    public Planet() {
        secret = false;
    }

    public String getPlanetImageUrl()
    {
        return planetImageUrl;
    }

    public void setPlanetImageUrl(String planetImageUrl)
    {
        this.planetImageUrl = planetImageUrl;
    }

    public String getPlanetName()
    {
        return planetName;
    }

    public void setPlanetName(String planetName)
    {
        this.planetName = planetName;
    }

    public String getPlanetAddition()
    {
        return planetAddition;
    }

    public void setPlanetAddition(String planetAddition)
    {
        this.planetAddition = planetAddition;
    }

    public String getPlanetPokerHand()
    {
        return planetPokerHand;
    }

    public void setPlanetPokerHand(String planetPokerHand)
    {
        this.planetPokerHand = planetPokerHand;
    }

    public String getPlanetHandBaseScore()
    {
        return planetHandBaseScore;
    }

    public void setPlanetHandBaseScore(String planetHandBaseScore)
    {
        this.planetHandBaseScore = planetHandBaseScore;
    }

    public boolean isSecret()
    {
        return secret;
    }

    public void setSecret(boolean secret)
    {
        this.secret = secret;
    }
}
