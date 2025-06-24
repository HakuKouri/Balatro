package com.example.balatro.classes;

import com.example.balatro.Balatro;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView
{
    //region Properties
    protected final IntegerProperty cardId = new SimpleIntegerProperty(-1);
    protected final StringProperty cardType = new SimpleStringProperty("");
    protected final StringProperty cardName = new SimpleStringProperty("");
    protected final StringProperty cardImageUrl = new SimpleStringProperty("");
    protected final StringProperty cardDescription = new SimpleStringProperty("");
    protected final IntegerProperty cardCost = new SimpleIntegerProperty(0);
    protected final DoubleProperty maxCost = new SimpleDoubleProperty(0);
    protected final ObjectProperty<Edition> edition = new SimpleObjectProperty<>(new Edition());
    protected final IntegerProperty editionCost = new SimpleIntegerProperty(0);
    protected final IntegerProperty buyPrice = new SimpleIntegerProperty(0);
    private final DoubleProperty sellValue = new SimpleDoubleProperty(0);
    private final DoubleProperty additionalSellValue = new SimpleDoubleProperty(0);

    //endregion

    //region Constructor
    public Card() {
        cardImageUrl.addListener((observable, oldValue, newValue) -> {
            setImage(new Image("file:" + newValue));

            setFitHeight(Balatro.getSettings().getCardHeight());
            setPreserveRatio(true);
            //setFitWidth(getImage().getWidth());
        });

        maxCostProperty().bind(Bindings.createIntegerBinding(() ->
                        getCardCost() + getEditionCost(),
                cardCostProperty(), editionCostProperty())
        );



        sellValueProperty().bind(Bindings.createDoubleBinding(() -> {
            return Math.floor(getMaxCost() / 2.0 + getAdditionalSellValue());
        }, maxCostProperty(), additionalSellValueProperty()));
    }

    //endregion

    //region Getter Setter
    public int getCardId() {
        return cardId.get();
    }

    public IntegerProperty cardIdProperty() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId.set(cardId);
    }

    public String getCardType() {
        return cardType.get();
    }

    public StringProperty cardTypeProperty() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType.set(cardType);
    }

    public String getCardName() {
        return cardName.get();
    }

    public StringProperty cardNameProperty() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName.set(cardName);
    }

    public String getCardImageUrl() {
        return cardImageUrl.get();
    }

    public StringProperty cardImageUrlProperty() {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl.set(cardImageUrl);
    }

    public String getCardDescription() {
        return cardDescription.get();
    }

    public StringProperty cardDescriptionProperty() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription.set(cardDescription);
    }

    public int getCardCost() {
        return cardCost.get();
    }

    public IntegerProperty cardCostProperty() {
        return cardCost;
    }

    public void setCardCost(int cost) {
        this.cardCost.set(cost);
    }

    public double getMaxCost() {
        return maxCost.get();
    }

    public DoubleProperty maxCostProperty() {
        return maxCost;
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

    public int getEditionCost() {
        return editionCost.get();
    }

    public IntegerProperty editionCostProperty() {
        return editionCost;
    }

    public void setEditionCost(int editionCost) {
        this.editionCost.set(editionCost);
    }

    public int getBuyPrice() {
        return buyPrice.get();
    }

    public IntegerProperty buyPriceProperty() {
        return buyPrice;
    }

    public double getSellValue() {
        return sellValue.get();
    }

    public DoubleProperty sellValueProperty() {
        return sellValue;
    }

    public double getAdditionalSellValue() {
        return additionalSellValue.get();
    }

    public DoubleProperty additionalSellValueProperty() {
        return additionalSellValue;
    }


    //endregion

    //region Functions
    public void setCard(Card card) {
        setCardId(card.getCardId());
        setCardType(card.getCardType());
        setCardName(card.getCardName());
        setCardImageUrl(card.getCardImageUrl());
        setCardDescription(card.getCardDescription());
        setCardCost(card.getCardCost());
        setEditionCost(card.getEditionCost());
    }

}
