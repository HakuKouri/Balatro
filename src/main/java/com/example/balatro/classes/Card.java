package com.example.balatro.classes;

import com.example.balatro.Balatro;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView
{
    //region Properties
    protected IntegerProperty cardId = new SimpleIntegerProperty(-1);
    protected StringProperty cardType = new SimpleStringProperty("");
    protected StringProperty cardName = new SimpleStringProperty("");
    protected StringProperty cardImageUrl = new SimpleStringProperty("");
    protected StringProperty cardDescription = new SimpleStringProperty("");
    protected IntegerProperty cardCost = new SimpleIntegerProperty(0);
    protected IntegerProperty maxCost = new SimpleIntegerProperty(0);
    protected IntegerProperty editionCost = new SimpleIntegerProperty(0);
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
            cardCost.add(editionCost).intValue(),
                cardCostProperty(), editionCostProperty())
        );
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

    public int getMaxCost() {
        return maxCost.get();
    }

    public IntegerProperty maxCostProperty() {
        return maxCost;
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
