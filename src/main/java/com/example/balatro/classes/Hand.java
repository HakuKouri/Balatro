package com.example.balatro.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty level = new SimpleIntegerProperty();
    private IntegerProperty chips = new SimpleIntegerProperty();
    private IntegerProperty multi = new SimpleIntegerProperty();
    private IntegerProperty played = new SimpleIntegerProperty();

    public Hand() {

    }

    public Hand(String name, int chips, int multi) {
        this.name.set(name);
        this.level.set(1);
        this.chips.set(chips);
        this.multi.set(multi);
        this.played.set(0);
    }

    //region GETTER SETTER
    public void setHand(Hand hand) {
        setName(hand.getName());
        setLevel(hand.getLevel());
        setChips(hand.getChips());
        setMulti(hand.getMulti());
        setPlayed(hand.getPlayed());
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

    public static List<Hand> setHandList() {
        List<Hand> allHandList = new ArrayList<>();
        allHandList.add(new Hand("Flush Five",     160, 16));
        allHandList.add(new Hand("Flush House",    140, 14));
        allHandList.add(new Hand("Five of a Kind", 120, 12));
        allHandList.add(new Hand("Royal Flush",    100, 8));
        allHandList.add(new Hand("Straight Flush", 100, 8));
        allHandList.add(new Hand("Four of a Kind", 60, 7));
        allHandList.add(new Hand("Full House",     40, 4));
        allHandList.add(new Hand("Flush",          35, 4));
        allHandList.add(new Hand("Straight",       30, 4));
        allHandList.add(new Hand("Three of a Kind",30, 3));
        allHandList.add(new Hand("Two Pair",       20, 2));
        allHandList.add(new Hand("One Pair",       10, 2));
        allHandList.add(new Hand("High Card",      5, 1));

        return allHandList;
    }

}
