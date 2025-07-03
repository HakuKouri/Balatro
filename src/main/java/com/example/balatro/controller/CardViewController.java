package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
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
    private Label priceLabel;
    @FXML
    private AnchorPane buy_AnchorPane;
    @FXML
    private Label buyLabel;
    @FXML
    private AnchorPane buyUseSell_AnchorPane;
    @FXML
    private Label buyUseSell_Label;
    @FXML
    private AnchorPane use_AnchorPane;
    @FXML
    private Label use_Label;
    @FXML
    private AnchorPane card_AnchorPane;
    @FXML
    private ImageView cardImage;
    //endregion

    //region Properties
    private final ObjectProperty<Card> card = new SimpleObjectProperty<>(new Card());
    private final BooleanProperty inShop = new SimpleBooleanProperty(false);
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

    public String getBuyAndUse() {
        return buyAndUse;
    }

    public String getSell() {
        return sell;
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

        buyLabel.setOnMouseClicked(event -> {
            System.out.println("BuyLabel clicked");
            GameController.getInstance().buyItem(card_AnchorPane,this);
        });

        buyUseSell_AnchorPane.setOnMouseClicked(event -> {
            System.out.println("BuyUseSell clicked");
            if(isInShop())
                GameController.getInstance().buyAndUse(this);
            else
                GameController.getInstance().sellItem(card_AnchorPane, this, getInMap());
        });

        use_AnchorPane.setOnMouseClicked(event -> {
            System.out.println("use_clicked");
            GameController.getInstance().useItem(card_AnchorPane,this);
        });

        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(card.get() instanceof Tarot)
                System.out.println(((Tarot)card.get()).canPlay(Balatro.getGameModel()));
        });
    }

    //Funktionen
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
                Balatro.getGameModel().getActiveJokerMap(),            // Joker (für bestimmte Tarot-Effekte)
                Balatro.getGameModel().getConsumableMap()              // Consumables (z. B. The Fool)
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
                Balatro.getGameModel().getActiveJokerMap(),            // Joker (für bestimmte Tarot-Effekte)
                Balatro.getGameModel().getConsumableMap()              // Consumables (z. B. The Fool)
        ));
    }

    private void bindVisibility() {
        price_AnchorPane.visibleProperty().bind(inShopProperty());

        buyUseSell_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && ((isTarot() || isPlanet()) || (!isInShop() && isJoker())), selectedProperty())
        );

        use_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && (isTarot() || isPlanet()) && !isInShop(),
                selectedProperty(), cardTypeProperty(), cardTypeProperty(), inShopProperty()
        ));
        use_AnchorPane.managedProperty().bind(use_AnchorPane.visibleProperty());

        buy_AnchorPane.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        isSelected() && isInShop(),
                selectedProperty(), inShopProperty()
        ));
    }

    public void initializeCardData(Card card) {
        card_AnchorPane.maxWidthProperty().bind(Bindings.createDoubleBinding(() -> Balatro.getSettings().getWindowWidth() * .09, Balatro.getSettings().windowWidthProperty()));
        this.card.setValue(card);

        if (card.getCardImageUrl() != null) {
            cardImage.setImage(card.getImage());
        }

        buyPrice.bind(Bindings.createIntegerBinding(() ->
                        Math.max((int) Math.round(card.getMaxCost() * Balatro.getGameModel().getShopModel().getShopPrices()),1) ,
                card.maxCostProperty(),
                Balatro.getGameModel().getShopModel().shopPricesProperty()));

        buyUseSell_Label.textProperty().bind(Bindings.createStringBinding(() ->
                isInShop() ? buyAndUse : String.format(sell, Math.max(Math.round(card.getSellValue()),1)),
        inShopProperty(), card.sellValueProperty()));

        priceLabel.textProperty().bind(Bindings.createStringBinding(() ->
                "$ " + buyPrice.get(), buyPriceProperty()));
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

    public static void createCardNode(Card card, ObservableMap<CardViewController, AnchorPane> map) {
        createCardNode(card,map,false);
    }

    public static void createCardNode(Card card, ObservableMap<CardViewController, AnchorPane> map, boolean inShop) {
        try {
            FXMLLoader loader = new FXMLLoader(CardViewController.class.getResource("/com/example/balatro/card.fxml"));
            AnchorPane cardPane = loader.load();
            CardViewController controller = loader.getController();

            cardPane.getStyleClass().add("card");
            controller.setInMap(map);
            controller.initializeCardData(card);
            controller.inShopProperty().set(inShop);

            map.put(controller,cardPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CardViewController getCardViewController(Map<CardViewController,AnchorPane> map, AnchorPane pane) {
        return map.entrySet().stream()
                .filter(e -> e.getValue().equals(pane))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No CardViewController found"));
    }

    public static AnchorPane getCardAnchorPane(Map<CardViewController,AnchorPane> map, Card card) {
        return map.entrySet().stream()
                .filter( e -> e.getKey().getCard().equals(card))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseGet(() -> {
                    System.out.println("Card not found in map: " + card.getCardName());
                    return null;
                });
    }

}
