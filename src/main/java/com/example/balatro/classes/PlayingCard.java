package com.example.balatro.classes;

import com.example.balatro.controller.GameController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.event.EventTarget;
import javafx.event.EventHandler;


public class PlayingCard extends Card
{
    private String[] rankArray = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
    private int[] valueArray = {2,3,4,5,6,7,8,9,10,10,10,10,11};
    private String[] suitArray = {"Hearts", "Clubs", "Diamonds", "Spades"};
    private int orderPosition;
    private String rank;
    private String suit;
    private int value;
    private Seal seal;
    private Enhancement enhancement;
    private Edition edition;
    private boolean clickAble = false;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final BooleanProperty countForPoint = new SimpleBooleanProperty(false);

    public PlayingCard(int rank, int suit) {

        this.rank = rankArray[rank];
        this.suit = suitArray[suit];
        orderPosition = rank;
        value = valueArray[rank];
        seal = new Seal();
        seal.setId(0);
        enhancement = new Enhancement();
        enhancement.setId(0);
        edition = new Edition();
        edition.setId(0);
        Image image = new Image(getClass().getResource("/com/images/DEFAULT/BASIC/8BitDeck"+(rank+1+suit*13)+".png").toExternalForm());
        setImage(image);
        setFitHeight(200);
        setPreserveRatio(true);

        selected.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue)
                this.setTranslateY(-20);
            else
                this.setTranslateY(0);
        });

        getStyleClass().add("card");

        //addEventFilter();


        //region EVENT TEST
//        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            fireEvent(new CardClickedEvent(this));
//
//        });
        //endregion
    }

    //region GETTER SETTER
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

    public int getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        this.orderPosition = orderPosition;
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

    public String[] getRankArray() {
        return rankArray;
    }

    public void setRankArray(String[] rankArray) {
        this.rankArray = rankArray;
    }

    public String[] getSuitArray() {
        return suitArray;
    }

    public void setSuitArray(String[] suitArray) {
        this.suitArray = suitArray;
    }

    public int[] getValueArray() {
        return valueArray;
    }

    public void setValueArray(int[] valueArray) {
        this.valueArray = valueArray;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }

    public int getSuitOrder() {
        for (int i = 0; i < suitArray.length; i++) {
            if (suitArray[i].equals(suit)) {
                return i;
            }
        }
        return -1; // Wenn Suit nicht gefunden wird
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public boolean isCountForPoint() {
        return countForPoint.get();
    }

    public BooleanProperty countForPointProperty() {
        return countForPoint;
    }

    //endregion


    //EVENT TEST
//    public static final EventType<CardClickedEvent> CARD_CLICKED =
//            new EventType<>(Event.ANY, "CARD_CLICKED");
//
//    public static class CardClickedEvent extends Event {
//        public CardClickedEvent(PlayingCard source) {
//            super(source, source, CARD_CLICKED);
//        }
//
//        @Override
//        public PlayingCard getSource() {
//            return (PlayingCard) super.getSource();
//        }
//    }

}
