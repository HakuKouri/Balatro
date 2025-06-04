package com.example.balatro.classes;

import com.example.balatro.Balatro;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Planet extends Card
{
    private static Map<String, Boolean> uniquePlanetPlayed = new HashMap<>() {{
        put("Pluto", false);
        put("Mercury", false);
        put("Uranus", false);
        put("Venus", false);
        put("Saturn", false);
        put("Jupiter", false);
        put("Earth", false);
        put("Mars", false);
        put("Neptune", false);
        put("Planet X", false);
        put("Ceres", false);
        put("Eris", false);
    }};

    private final StringProperty planetAddition = new SimpleStringProperty("");
    private final StringProperty planetPokerHand = new SimpleStringProperty("");
    private final StringProperty planetHandBaseScore = new SimpleStringProperty("");
    private final BooleanProperty secret = new SimpleBooleanProperty(false);

    //Constructor
    public Planet() {
    }

    //region Statics
    public static void planetPlayed(String planetName) {
        uniquePlanetPlayed.put(planetName, true);
    }

    public static int getUniquePlanetsPlayedCount() {
        return uniquePlanetPlayed.size();
    }

    public static void resetUniquePlanets() {
        uniquePlanetPlayed.replaceAll((k,v) ->  false);
    }
    //endregion

    //region Getter Setter
    public String getPlanetAddition() {
        return planetAddition.get();
    }

    public StringProperty planetAdditionProperty() {
        return planetAddition;
    }

    public void setPlanetAddition(String planetAddition) {
        this.planetAddition.set(planetAddition);
    }

    public String getPlanetPokerHand() {
        return planetPokerHand.get();
    }

    public StringProperty planetPokerHandProperty() {
        return planetPokerHand;
    }

    public void setPlanetPokerHand(String planetPokerHand) {
        this.planetPokerHand.set(planetPokerHand);
    }

    public String getPlanetHandBaseScore() {
        return planetHandBaseScore.get();
    }

    public StringProperty planetHandBaseScoreProperty() {
        return planetHandBaseScore;
    }

    public void setPlanetHandBaseScore(String planetHandBaseScore) {
        this.planetHandBaseScore.set(planetHandBaseScore);
    }

    public boolean isSecret() {
        return secret.get();
    }

    public BooleanProperty secretProperty() {
        return secret;
    }

    public void setSecret(boolean secret) {
        this.secret.set(secret);
    }
    //endregion

    //region Functions
    public void setPlanet(Planet planet) {
        setCardId(planet.getCardId());
        setCardImageUrl(planet.getCardImageUrl());
        setCardName(planet.getCardName());
        setCardCost(planet.getCardCost());
        setPlanetAddition(planet.getPlanetAddition());
        setPlanetPokerHand(planet.getPlanetPokerHand());
        setPlanetHandBaseScore(planet.getPlanetHandBaseScore());
        setSecret(planet.isSecret());
    }
    //endregion
}
