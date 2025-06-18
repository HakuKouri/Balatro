package com.example.balatro.classes;

import com.example.balatro.Balatro;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class PlayingCard extends Card
{
    private String[] rankArray = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
    private int[] valueArray = {2,3,4,5,6,7,8,9,10,10,10,10,11};

    private StringProperty rank = new SimpleStringProperty("");
    private IntegerProperty rankIndex = new SimpleIntegerProperty(0);
    private ObjectProperty<Suit> suit =  new SimpleObjectProperty<>(Suit.NO_SUIT);
    private IntegerProperty suitIndex = new SimpleIntegerProperty(0);
    private IntegerProperty value = new SimpleIntegerProperty(0);
    private ObjectProperty<Seal> seal = new SimpleObjectProperty<>(new Seal());
    private ObjectProperty<Enhancement> enhancement = new SimpleObjectProperty<>(new Enhancement());
    private ObjectProperty<Edition> edition =  new SimpleObjectProperty<>(new Edition());

    private boolean clickAble = false;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final BooleanProperty countForPoint = new SimpleBooleanProperty(false);
    private final BooleanProperty cardDisabled = new SimpleBooleanProperty(false);

    public PlayingCard(int rank, int suit) {
        setCardType("PlayingCard");
        rankProperty().bind(Bindings.createStringBinding(() -> rankArray[rankIndex.get()], rankProperty()));
        valueProperty().bind(Bindings.createIntegerBinding(() -> valueArray[suitIndex.get()], valueProperty()));
        suitProperty().bind(Bindings.createObjectBinding(() -> Suit.values()[getSuitIndex()], suitProperty()));

        setRankIndex(rank);
        setSuitIndex(suit);

        imageProperty().bind(Bindings.createObjectBinding(() -> {
            String path = "/com/images/DEFAULT/BASIC/8BitDeck" + (getRankIndex() + 1 + getSuitIndex() * 13) + ".png";
            URL url = getClass().getResource(path);
            if (url != null) {
                return new Image(url.toExternalForm());
            } else {
                System.err.println("Image not found: " + path);
                return null; // oder ein Platzhalterbild
            }
        }, rankIndexProperty(), suitIndexProperty()));

        getSeal().setId(-1);
        getEnhancement().setId(-1);
        getEdition().setId(-1);

        setFitHeight(Balatro.getSettings().getCardHeight());
        setPreserveRatio(true);

        selected.addListener((observable, oldValue, newValue) -> {
            // Only if completed
            if (newValue)
                this.setTranslateY(-20);
            else
                this.setTranslateY(0);
        });

        getStyleClass().add("card");
    }

    //region GETTER SETTER
    public int getRankIndex() {
        return rankIndex.get();
    }

    public IntegerProperty rankIndexProperty() {
        return rankIndex;
    }

    public void setRankIndex(int rankIndex) {
        this.rankIndex.set(rankIndex);
    }

    public String getRank() {
        return rank.get();
    }

    public StringProperty rankProperty() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank.set(rank);
    }

    public int getSuitIndex() {
        return suitIndex.get();
    }

    public IntegerProperty suitIndexProperty() {
        return suitIndex;
    }

    public void setSuitIndex(int suitIndex) {
        this.suitIndex.set(suitIndex);
    }

    public Suit getSuit() {
        return suit.get();
    }

    public ObjectProperty<Suit> suitProperty() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit.set(suit);
    }

    public Seal getSeal() {
        return seal.get();
    }

    public ObjectProperty<Seal> sealProperty() {
        return seal;
    }

    public void setSeal(Seal seal) {
        this.seal.set(seal);
    }

    public Enhancement getEnhancement() {
        return enhancement.get();
    }

    public ObjectProperty<Enhancement> enhancementProperty() {
        return enhancement;
    }

    public void setEnhancement(Enhancement enhancement) {
        this.enhancement.set(enhancement);
    }

    public Edition getEdition() {
        return edition.get();
    }

    public ObjectProperty<Edition> editionProperty() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition.set(edition);
    }

    public String[] getRankArray() {
        return rankArray;
    }

    public void setRankArray(String[] rankArray) {
        this.rankArray = rankArray;
    }

    public int[] getValueArray() {
        return valueArray;
    }

    public void setValueArray(int[] valueArray) {
        this.valueArray = valueArray;
    }

    public int getValue() {
        return value.get();
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }

    public int getSuitOrder() {
        for (int i = 0; i < Suit.values().length; i++) {
            if (Suit.values()[i].equals(suit)) {
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

    public boolean isCardDisabled() {
        return cardDisabled.get();
    }

    public BooleanProperty cardDisabledProperty() {
        return cardDisabled;
    }

    //endregion

    //region Funktionen

    public static PlayingCard createRandomPlayingCard() {
        return new PlayingCard(
                Balatro.getGameModel().getRand().nextInt(13),
                Balatro.getGameModel().getRand().nextInt(4));
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
