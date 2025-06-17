package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Card;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Map;

public class CardViewController {
    //region FXML
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
    //endregion

    //region Properties
    private final ObjectProperty<Card> card = new SimpleObjectProperty<>(new Card());
    private final BooleanProperty inShop = new SimpleBooleanProperty(false);
    private final StringProperty cardType = new SimpleStringProperty("");
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    //endregion

    //region const Strings
    private final String buyAndUse = "Buy\n& Use";
    private final String sell = "Sell $%d";
    //endregion


    //region Getter Setter
    public Card getCard() {
        return card.get();
    }

    public ObjectProperty<Card> cardProperty() {
        return card;
    }

    public void setCard(Card card) {
        this.card.set(card);
    }

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

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    //endregion

    public void initialize() {
        price_AnchorPane.visibleProperty().bind(inShopProperty());
        buy_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && isInShop(),
                selectedProperty(), inShopProperty()
        ));
        sell_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && (getCard().getCardType().equals("Tarot") || getCard().getCardType().equals("Planet")),
                selectedProperty(), cardTypeProperty(), cardTypeProperty()
        ));

        buyLabel.disableProperty().bind(Bindings.createBooleanBinding(() ->
                Balatro.getGameModel().getMoney() < getCard().getMaxCost(),Balatro.getGameModel().moneyProperty(),getCard().maxCostProperty()));
        sellLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return isInShop() && getCardType() == "Tarot" ? String.format(buyAndUse, card.get().getMaxCost()) : String.format(sell, (int)card.get().getSellValue());
        }));
    }

    public void setData(Card card) {
        card_AnchorPane.maxWidthProperty().bind(Bindings.createDoubleBinding(() -> Balatro.getSettings().getWindowWidth() * .09, Balatro.getSettings().windowWidthProperty()));
        this.card.set(card);
        priceLabel.setText( "$ " + (int)card.getMaxCost());

        if (card.getCardImageUrl() != null) {
            cardImage.setImage(card.getImage());
            //cardImage.setFitHeight(Balatro.getSettings().getCardHeight());
            //cardImage.setPreserveRatio(true);
        }
    }

    public String getImageUrl() {
        return cardImage.getImage().getUrl();
    }

    public static void createCardNode(Card card, ObservableMap<CardViewController, AnchorPane> map) {
        createCardNode(card,map,false);
    }

    public static void createCardNode(Card card, ObservableMap<CardViewController, AnchorPane> map, boolean inShop) {
        try {
            FXMLLoader loader = new FXMLLoader(CardViewController.class.getResource("/com/example/balatro/card.fxml"));
            AnchorPane cardPane = loader.load();
            CardViewController controller = loader.getController();

            controller.setData(card);
            controller.setInShop(inShop);
            map.put(controller,cardPane);
        } catch (IOException e) {
            e.printStackTrace();
            //return new Label("Error loading card");
        }
    }

    public static CardViewController getCardViewController(Map<CardViewController,AnchorPane> map, AnchorPane pane) {
        System.out.println("getCardViewController");
        return map.keySet().stream().filter(cardViewController -> map.get(cardViewController).equals(pane)).findFirst().get();
    }

    public static AnchorPane getCardAnchorPane(Map<CardViewController,AnchorPane> map, Card card) {
        CardViewController controller = map.keySet().stream().filter( cardViewController -> cardViewController.getCard() == card).findFirst().get();
        return map.get(controller);
    }




}
