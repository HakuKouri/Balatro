package com.example.balatro.classes;

import java.util.ArrayList;
import java.util.List;

public class Planet extends Card
{
    private static final List<Planet> uniquePlanetPlayed = new ArrayList<Planet>();

    private int planetId;
    private String planetImageUrl;
    private String planetName;
    private String planetAddition;
    private String planetPokerHand;
    private String planetHandBaseScore;
    private boolean secret;

    //Constructor
    public Planet() {
        secret = false;
    }

    //Statics
    public static void addUniquePlanet(Planet planet) {
        if(!uniquePlanetPlayed.contains(planet))
            uniquePlanetPlayed.add(planet);
    }

    public static int getUniquePlanetsPlayedCount() {
        return uniquePlanetPlayed.size();
    }

    public static void resetUniquePlanets() {
        uniquePlanetPlayed.clear();
    }

    //GETTER SETTER
    public int getPlanetId() {
        return planetId;
    }

    public void setPlanetId(int planetId) {
        this.planetId = planetId;
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
