package com.example.balatro.classes;

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
}
