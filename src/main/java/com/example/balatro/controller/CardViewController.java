package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.util.FxmlUtil;
import com.example.balatro.models.GameModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CardViewController {
    //region FXML
    @FXML
    private AnchorPane card_AnchorPane, price_AnchorPane, buy_AnchorPane, buyUseSell_AnchorPane, use_AnchorPane, useSelect_AnchorPane;
    @FXML
    private Label priceLabel, buyLabel, buyUseSell_Label, use_Label, useSelect_Label;
    @FXML
    private ImageView cardImage;
    @FXML
    private StackPane image_StackPane;
    //endregion

    //region Attributes
    private final ObjectProperty<Card> card = new SimpleObjectProperty<>(new Card());
    private final BooleanProperty inShop = new SimpleBooleanProperty(false);
    private final BooleanProperty fromBooster = new SimpleBooleanProperty(false);
    private final StringProperty cardType = new SimpleStringProperty("");
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final IntegerProperty buyPrice = new SimpleIntegerProperty(0);
    private Map<CardViewController, AnchorPane> inMap;
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

    public boolean isInShop() {
        return inShop.get();
    }

    public BooleanProperty inShopProperty() {
        return inShop;
    }

    public boolean isFromBooster() {
        return fromBooster.get();
    }

    public BooleanProperty fromBoosterProperty() {
        return fromBooster;
    }

    public String getCardType() {
        return cardType.get();
    }

    public StringProperty cardTypeProperty() {
        return cardType;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public int getBuyPrice() {
        return buyPrice.get();
    }

    public IntegerProperty buyPriceProperty() {
        return buyPrice;
    }

    public Map<CardViewController, AnchorPane> getInMap() {
        return inMap;
    }

    public void setInMap(Map<CardViewController, AnchorPane> inMap) {
        this.inMap = inMap;
    }
    //endregion

    public void initialize() {
        //TODO Booster Drawn Attribute label visibility anpassen
        bindVisibility();
        bindDisabled();
        bindMouseClickEvent();
        bindText();

        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                card_AnchorPane.getStyleClass().add("card-selected");
            } else
                card_AnchorPane.getStyleClass().remove("card-selected");
        });

        cardProperty().addListener((obs, oldCard, newCard) -> {
            cardImage.imageProperty().unbind();
            cardImage.imageProperty().bind(newCard.imageProperty());
            buyPrice.bind(Bindings.createIntegerBinding(() ->
                            Math.max((int) Math.round(getCard().getMaxCost() * Balatro.getGameModel().getShopModel().getShopPrices()),1) ,
                    getCard().maxCostProperty(),
                    Balatro.getGameModel().getShopModel().shopPricesProperty()));
        });

        card_AnchorPane.setMaxWidth(Balatro.getSettings().getWindowWidth() * .09);

        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(getCard() instanceof  Planet planet) {
                System.out.println("Planet can play: " + planet.canPlay());
            }
            if(getCard() instanceof Tarot tarot)
                System.out.println("Tarot can play: " + tarot.canPlay(Balatro.getGameModel()));
        });
    }

    //region Funktionen

    private void bindMouseClickEvent() {
        buyLabel.setOnMouseClicked(event -> {
            System.out.println("BuyLabel clicked");
            GameController.getInstance().buyItem(getCard());
        });

        buyUseSell_AnchorPane.setOnMouseClicked(event -> {
            System.out.println("BuyUseSell clicked");
            if(isInShop())
                GameController.getInstance().buyAndUse(getCard());
            else
                GameController.getInstance().sellItem(getCard());
        });

        use_AnchorPane.setOnMouseClicked(event -> {
            System.out.println("use_clicked");
            GameController.getInstance().useCardFromConsumable(getCard());
        });

        useSelect_AnchorPane.setOnMouseClicked(event -> {
            System.out.println("useSelect_clicked");
            GameModel model = Balatro.getGameModel();
            if(getCard() instanceof Planet planet) {
                GameController.getInstance().useCardFromBooster(planet);
            } else if(getCard() instanceof Tarot tarot) {
                GameController.getInstance().useCardFromBooster(tarot);
            } else if(getCard() instanceof Spectral spectral) {
                GameController.getInstance().useCardFromBooster(spectral);
            } else if(getCard() instanceof PlayingCard playingCard) {
                GameController.getInstance().selectCardFromBooster(playingCard);
            } else if(getCard() instanceof Joker joker) {
                GameController.getInstance().selectCardFromBooster(joker);
            }
        });
    }

    private void bindDisabled() {
        buy_AnchorPane.disableProperty().bind(Bindings.createBooleanBinding(() ->
                Balatro.getGameModel().getRunState().getMoney() < buyPrice.get(), Balatro.getGameModel().getRunState().moneyProperty(), buyPriceProperty()));

        buyUseSell_AnchorPane.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                    boolean cannotPlay = !(getCard() instanceof Tarot tarot && tarot.canPlay(Balatro.getGameModel()));
                    if (isInShop())
                        return Balatro.getGameModel().getRunState().getMoney() < buyPrice.get() || cannotPlay;
                    else return false;
                },
                selectedProperty(),               // falls Auswahl wechselt
                cardProperty(),                   // falls Karte sich ändert
                buyPriceProperty(),              // Preisänderung
                Balatro.getGameModel().getRunState().moneyProperty(),  // Geld
                Balatro.getGameModel().getSelectedCards(),             // Auswahlkarten
                Balatro.getGameModel().getJokerManager().getViewMap(),            // Joker (für bestimmte Tarot-Effekte)
                Balatro.getGameModel().getConsumableManager().getViewMap()              // Consumables (z. B. The Fool)
        ));

        use_AnchorPane.disableProperty().bind(Bindings.createBooleanBinding(() -> {
                    if (getCard() instanceof Tarot) return !((Tarot) getCard()).canPlay(Balatro.getGameModel());
                    return true;
                },
                selectedProperty(),               // falls Auswahl wechselt
                cardProperty(),                   // falls Karte sich ändert
                buyPriceProperty(),              // Preisänderung
                Balatro.getGameModel().getRunState().moneyProperty(),  // Geld
                Balatro.getGameModel().getSelectedCards(),             // Auswahlkarten
                Balatro.getGameModel().getJokerManager().getViewMap(),            // Joker (für bestimmte Tarot-Effekte)
                Balatro.getGameModel().getConsumableManager().getViewMap()              // Consumables (z. B. The Fool)
        ));

        useSelect_AnchorPane.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            if(getCard() instanceof Tarot tarot) return !(tarot.canPlay(Balatro.getGameModel()));
            if(getCard() instanceof Spectral spectral) return !(spectral.canPlay(Balatro.getGameModel()));
            if(getCard() instanceof Planet) return false;
            if(getCard() instanceof PlayingCard) return false;
            if(getCard() instanceof Joker joker) return Balatro.getGameModel().getJokerManager().getSize() >= Balatro.getGameModel().getRunState().getMaxJokers();
            return true;
        }, cardProperty(),
                Balatro.getGameModel().getSelectedCards(),// wichtig für Kartenlogik
                Balatro.getGameModel().getJokerManager().sizeProperty(),
                Balatro.getGameModel().getConsumableManager().sizeProperty(),
                Balatro.getGameModel().getRunState().moneyProperty(),
                Balatro.getGameModel().getRunState().maxConsumablesProperty(),
                Balatro.getGameModel().getRunState().maxJokersProperty()
                ));
    }

    private void bindVisibility() {
        price_AnchorPane.visibleProperty().bind(inShopProperty());

        buyUseSell_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && ((isTarot() || isPlanet()) || (!isInShop() && isJoker())) && !isFromBooster(), selectedProperty())
        );

        use_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && ((isTarot() || isPlanet()) && !isInShop()) && !isFromBooster(),
                selectedProperty(), cardTypeProperty(), cardTypeProperty(), inShopProperty()
        ));
        use_AnchorPane.managedProperty().bind(use_AnchorPane.visibleProperty());

        buy_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && isInShop(),
                selectedProperty(), inShopProperty()
        ));

        useSelect_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                isSelected() && isFromBooster(), selectedProperty(), fromBoosterProperty()));
    }

    private void bindText() {
        buyUseSell_Label.textProperty().bind(Bindings.createStringBinding(() ->
                        isInShop() ? buyAndUse : String.format(sell, (int)Math.max(getCard().getSellValue(), 1)),
                inShopProperty(), getCard().sellValueProperty()));

        priceLabel.textProperty().bind(Bindings.createStringBinding(() ->
                "$ " + buyPrice.get(), buyPriceProperty()));

        useSelect_Label.textProperty().bind(Bindings.createStringBinding(() -> {
            if(getCard() instanceof Tarot ||  getCard() instanceof Spectral || getCard() instanceof Planet) {
                return "USE";
            } else {
                return "SELECT";
            }
        }, cardProperty()));

    }

    public void renderCardVisuals() {
        if (getCard() == null) {
            System.out.println("Card is null");
            return;
        }
        image_StackPane.getChildren().removeIf(child -> !"cardImage".equals(child.getId()));

        if(getCard() instanceof Joker joker) {
            for(Sticker sticker : joker.getStickers()) {
                image_StackPane.getChildren().add(sticker);
            }
        }

        if(getCard() instanceof PlayingCard playingCard) {
            System.out.println(playingCard.getSeal().toString());
            if(playingCard.getSeal().getSealId() > 0)
                image_StackPane.getChildren().add(playingCard.getSeal());
        }
    }

    private boolean isJoker() {
        return "Joker".equals(getCard().getCardType());
    }

    private boolean isTarot() {
        return "Tarot".equals(getCard().getCardType());
    }

    private boolean isPlanet() {
        return "Planet".equals(getCard().getCardType());
    }

    public static CardViewController createCardNode(Card card, ObservableMap<CardViewController, AnchorPane> map, boolean inShop) {
        Pair<CardViewController, AnchorPane> cardView = FxmlUtil.loadWithPane("/com/example/balatro/card.fxml");
        CardViewController controller = cardView.getKey();
        AnchorPane cardPane = cardView.getValue();

        cardPane.getStyleClass().add("card");
        controller.setInMap(map);
        controller.setCard(card);
        controller.inShopProperty().set(inShop);

        map.put(controller, cardPane);

        return controller;
    }

    public static CardViewController createCardNode(Card card) {
        Pair<CardViewController, AnchorPane> cardView = FxmlUtil.loadWithPane("/com/example/balatro/card.fxml");
        CardViewController controller = cardView.getKey();
        AnchorPane cardPane = cardView.getValue();

        cardPane.getStyleClass().add("card");
        controller.setCard(card);
        controller.inShopProperty().set(false);

        return controller;
    }

    public void setCard(Card card) {
        this.cardProperty().set(card);
        getCard().setupBindings();
        renderCardVisuals();
    }

    public void setMaxWidth(double width) {
        card_AnchorPane.setMaxWidth(width);
    }
}
