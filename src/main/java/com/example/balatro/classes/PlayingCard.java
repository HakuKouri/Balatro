package com.example.balatro.classes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PlayingCard extends Card
{
    String[] rankArray = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","As"};
    String[] suitArray = {"Hearts", "Clubs", "Diamonds", "Spades"};
    int[] valueArray = {2,3,4,5,6,7,8,9,10,10,10,10,11};
    String rank;
    String suit;
    int value;
    Seal seal;
    Enhancement enhancement;
    Edition edition;
    Image image;

    public PlayingCard(int rank, int suit) {
        this.rank = rankArray[rank];
        this.suit = suitArray[suit];
        value = valueArray[rank];
        seal = new Seal();
        seal.setId(0);
        enhancement = new Enhancement();
        enhancement.setId(0);
        edition = new Edition();
        edition.setId(0);
        image = new Image(getClass().getResource("/com/images/DEFAULT/BASIC/8BitDeck"+(rank+1+suit*13)+".png").toExternalForm());
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
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

    public Image getImage() { return image; }
}
