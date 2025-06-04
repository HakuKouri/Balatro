package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Card;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;

public class CardViewController {

    @FXML
    private ColumnConstraints imageColumn;
    @FXML
    private AnchorPane price_AnchorPane;
    @FXML
    private AnchorPane buy_AnchorPane;
    @FXML
    private AnchorPane sell_AnchorPane;
    @FXML
    private AnchorPane card_AnchorPane;
    @FXML
    private ImageView cardImage;

    @FXML
    private Label priceLabel;

    @FXML
    private Label buyLabel;

    @FXML
    private Label sellLabel;

    //region
    private final BooleanProperty inShop = new SimpleBooleanProperty(false);
    private final StringProperty cardType = new SimpleStringProperty("");
    private final BooleanProperty isSelected = new SimpleBooleanProperty(false);
    //endregion

    //region Getter Setter
    public boolean isInShop() {
        return inShop.get();
    }

    public BooleanProperty inShopProperty() {
        return inShop;
    }

    public void setInShop(boolean inShop) {
        this.inShop.set(inShop);
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

    public boolean isIsSelected() {
        return isSelected.get();
    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }

    //endregion

    public void initialize() {
        price_AnchorPane.visibleProperty().bind(inShopProperty());
        buy_AnchorPane.visibleProperty().bind(isSelectedProperty());
        sell_AnchorPane.visibleProperty().bind(isSelectedProperty());


    }

    public void setData(Card card, boolean showBuy, boolean showSell) {
        //card_AnchorPane.maxWidthProperty().bind(Bindings.createDoubleBinding( () ->  cardImage.fitWidthProperty().get() + 300, cardImage.fitWidthProperty()));
        card_AnchorPane.maxWidthProperty().bind(Bindings.createDoubleBinding(() -> Balatro.getSettings().getWindowWidth() * .1296, Balatro.getSettings().windowWidthProperty()));

        if (card.getCardImageUrl() != null) {
            cardImage.setImage(card.getImage());
            cardImage.setFitHeight(Balatro.getSettings().getCardHeight());
            cardImage.setPreserveRatio(true);
            System.out.println("Card Width: " + cardImage.getFitWidth());
            cardImage.setOnMouseClicked(event -> {
                System.out.println("Card Width: " + cardImage.getFitWidth());
                System.out.println("Card Width: " + cardImage.getImage().getWidth());
                System.out.println("Card Height: " + cardImage.getFitHeight());
                System.out.println("Card Height: " + cardImage.getImage().getHeight());
            });
        }

        priceLabel.setText("$" + card.getCardCost());

        buyLabel.setVisible(showBuy);
        sellLabel.setVisible(showSell);
    }

    public String getImageUrl() {
        return cardImage.getImage().getUrl();
    }
}
