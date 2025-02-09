package com.example.balatro.classes;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private String name;
    private int level;
    private int chips;
    private int multi;
    private int played;

    public Hand(String name, int chips, int multi) {
        this.name = name;
        this.level = 1;
        this.chips = chips;
        this.multi = multi;
        this.played = 0;
    }

    //region GETTER SETTER
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
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
