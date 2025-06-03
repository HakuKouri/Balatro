package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Card;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;

public class CardViewController {

    public ColumnConstraints imageColumn;
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

    public void setData(Card card, boolean showBuy, boolean showSell) {
        //card_AnchorPane.maxWidthProperty().bind(Bindings.createDoubleBinding( () ->  cardImage.fitWidthProperty().get() + 300, cardImage.fitWidthProperty()));
        imageColumn.maxWidthProperty().bind(cardImage.fitWidthProperty());

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
}
