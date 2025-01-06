package com.example.balatro.classes;

public class PlayingCard extends Card
{
    int rank;
    String suit;
    Seal seal;
    Enhancement enhancement;
    Edition edition;

    public PlayingCard(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        seal = new Seal();
        seal.setId(0);
        enhancement = new Enhancement();
        enhancement.setId(0);
        edition = new Edition();
        edition.setId(0);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public Seal getSeal() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal = seal;
    }

    public Enhancement getEnhancement() {
        return enhancement;
    }

    public void setEnhancement(Enhancement enhancement) {
        this.enhancement = enhancement;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }
}
