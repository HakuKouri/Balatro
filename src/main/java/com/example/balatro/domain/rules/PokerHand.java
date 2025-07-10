package com.example.balatro.domain.rules;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Planet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class PokerHand {
    //region Attributes
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty level = new SimpleIntegerProperty();
    private final IntegerProperty chips = new SimpleIntegerProperty();
    private final IntegerProperty multi = new SimpleIntegerProperty();
    private final IntegerProperty played = new SimpleIntegerProperty();
    //endregion

    //region Constructor
    public PokerHand() {

    }

    public PokerHand(String name, int chips, int multi) {
        this.name.set(name);
        this.level.set(1);
        this.chips.set(chips);
        this.multi.set(multi);
        this.played.set(0);
    }
    //endregion

    //region GETTER SETTER
    public void setHand(PokerHand pokerHand) {
        setName(pokerHand.getName());
        setLevel(pokerHand.getLevel());
        setChips(pokerHand.getChips());
        setMulti(pokerHand.getMulti());
        setPlayed(pokerHand.getPlayed());
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public int getChips() {
        return chips.get();
    }

    public IntegerProperty chipsProperty() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips.set(chips);
    }

    public int getMulti() {
        return multi.get();
    }

    public IntegerProperty multiProperty() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi.set(multi);
    }

    public int getPlayed() {
        return played.get();
    }

    public IntegerProperty playedProperty() {
        return played;
    }

    public void setPlayed(int played) {
        this.played.set(played);
    }
    //endregion

    //region Functions
    public void addMult(int multiplier) {
        setMulti(getMulti() + multiplier);
    }

    public void multMult(double i) {
        setMulti((int) (getMulti() * i));
    }

    public static List<PokerHand> setHandList() {
        List<PokerHand> allPokerHandList = new ArrayList<>();
        allPokerHandList.add(new PokerHand("Flush Five",     160, 16));
        allPokerHandList.add(new PokerHand("Flush House",    140, 14));
        allPokerHandList.add(new PokerHand("Five of a Kind", 120, 12));
        allPokerHandList.add(new PokerHand("Royal Flush",    100, 8));
        allPokerHandList.add(new PokerHand("Straight Flush", 100, 8));
        allPokerHandList.add(new PokerHand("Four of a Kind", 60, 7));
        allPokerHandList.add(new PokerHand("Full House",     40, 4));
        allPokerHandList.add(new PokerHand("Flush",          35, 4));
        allPokerHandList.add(new PokerHand("Straight",       30, 4));
        allPokerHandList.add(new PokerHand("Three of a Kind",30, 3));
        allPokerHandList.add(new PokerHand("Two Pair",       20, 2));
        allPokerHandList.add(new PokerHand("One Pair",       10, 2));
        allPokerHandList.add(new PokerHand("High Card",      5, 1));

        return allPokerHandList;
    }


    public void addChips(int planetChips) {
        setChips(getChips() + planetChips);
    }

    public void addLevel() {
        Planet matchingPlanet = Balatro.getGameModel().getAllPlanetList().stream().filter(p -> getName().equals(p.getPlanetPokerHand())).toList().getFirst();
        levelProperty().set(getLevel() + 1);
        multiProperty().set(getMulti() + matchingPlanet.getPlanetMultiplier());
        chipsProperty().set(getChips() + matchingPlanet.getPlanetChips());
    }
    //endregion
}
