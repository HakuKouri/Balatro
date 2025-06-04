package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.*;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShopPartController {

    @FXML
    private AnchorPane rotatedLabel_AnchorPane;
    @FXML
    private Label rotatedLabel;
    @FXML
    private AnchorPane shopAnchorPane;
    @FXML
    private StackPane shopArea;
    @FXML
    private StackPane voucherArea;
    @FXML
    private StackPane boosterArea;

    private final GameController gameController = GameController.getInstance();
    private final GameModel gameModel = Balatro.getGameModel();

    private final ObservableList<Card> shopList = FXCollections.observableArrayList();
    private final ObservableList<Booster> boosterList = FXCollections.observableArrayList();
    private final ObservableList<Voucher> voucherList = FXCollections.observableArrayList();

    private final ObservableList<Node> cardNodes = FXCollections.observableArrayList();
    private final List<CardViewController> controllerList = new ArrayList<>();

    private int maxItems = 2;
    private int maxBoosters = 2;

    public void initialize() {
        cardNodes.addListener((ListChangeListener<? super Node>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    shopArea.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    shopArea.getChildren().removeAll(change.getRemoved());
                }
            }
            moveItems(shopArea, 200);
        });
        boosterList.addListener((ListChangeListener<? super Booster>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    //System.out.println("Booster added");
                    boosterArea.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    //System.out.println("Booster removed");
                    boosterArea.getChildren().removeAll(change.getRemoved());
                }
            }
            moveItems(boosterArea, Booster.getImageWidthProperty());
        });
        voucherList.addListener((ListChangeListener<? super Voucher>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    voucherArea.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    voucherArea.getChildren().removeAll(change.getRemoved());
                }
            }
            moveItems(voucherArea, 200);
        });

        shopArea.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();  // Bestimme das geklickte Element

            if(source instanceof ImageView) {
                AnchorPane cardPane = (AnchorPane) source.getParent().getParent().getParent();
                CardViewController controller = controllerList.get(shopArea.getChildren().indexOf(source.getParent().getParent().getParent()));

                controller.setIsSelected(!controller.isIsSelected());
                cardPane.translateYProperty().bind(Bindings.createDoubleBinding(() -> controller.isIsSelected() ? -50.0 : 0.0));

            }
        });
    }

    public void restockShop() {
//        System.out.println("restockShop");
        drawItems();
        drawBooster();
        drawVoucher();
    }

    public void reRollShop() {
//        System.out.println("reRollShop");
        shopList.clear();
        drawItems();
    }

    private void drawItems() {
        cardNodes.clear();
        for (int i = 0; i < maxItems; i++) {
            cardNodes.add(createCardNode(getRandomCard(),true,true));
        }
//        System.out.println("Shop List Items: " + shopList.size());
//        System.out.println("Shop Area Children: " + shopArea.getChildren().size());
//        for (Card card : shopList) {
//            System.out.println(card);
//            if (card instanceof Joker) {
//                System.out.println("Joker Card: " + ((Joker)card).getName());
//            }
//            if (card instanceof Tarot) {
//                System.out.println("Tarot Card: " + ((Tarot)card).getName());
//            }
//            if (card instanceof Planet) {
//                System.out.println("Planet Card: " + ((Planet)card).getName());
//            }
//        }
//        System.out.println("______________________________________________________");
    }

    private Card getRandomCard() {
        Card card = new Card();

        double jokerWeight = 20;
        double tarotWeight = gameModel.isTarotTycoonVoucher() ? 32 : gameModel.isTarotMerchantVoucher() ? 9.6 : 20;
//        System.out.println("Tarot: " + tarotWeight);
        double planetWeight = gameModel.isPlanetTycoonVoucher() ? 32 : gameModel.isPlanetMerchantVoucher() ? 9.6 : 20;
//        System.out.println("Planet: " + planetWeight);
        double playingCardWeight = gameModel.isMagicTrickVoucher() ? 4 : 0;
//        System.out.println("PlayingCard: " + playingCardWeight);
        double spectralCardWeight = gameModel.getChosenDeck().getDeckName() == "Ghost Deck" ? 2 : 0 ;
//        System.out.println("Spectral: " + spectralCardWeight);

        double maxWeight = jokerWeight + tarotWeight + planetWeight + playingCardWeight + spectralCardWeight;
//        System.out.println("Max weight: " + maxWeight);

        double jokerPercentage = jokerWeight * 100 / maxWeight ;
//        System.out.println("Joker percentage: " + jokerPercentage);
        double tarotPercentage = tarotWeight * 100 / maxWeight ;
//        System.out.println("Tarot percentage: " + tarotPercentage);
        double planetPercentage = planetWeight * 100 / maxWeight ;
//        System.out.println("Planet percentage: " + planetPercentage);
        double playingCardPercentage = playingCardWeight != 0 ? playingCardWeight * 100 / maxWeight  : 0;
//        System.out.println("Playing Card percentage: " + playingCardPercentage);
        double spectralCardPercentage = spectralCardWeight != 0 ? spectralCardWeight * 100 / maxWeight  : 0;
//        System.out.println("Spectral Card percentage: " + spectralCardPercentage);

        double itemChance = gameModel.getRand().nextInt(100) +1;
//        System.out.println("itemChance: " + itemChance);
//        System.out.println("joker chance: " + jokerPercentage);
//        System.out.println("tarot chance: " + (jokerPercentage + tarotPercentage));
//        System.out.println("planet chance: " + (jokerPercentage +  tarotPercentage + planetPercentage));

//        System.out.println("Joker boolean: " + (itemChance < jokerPercentage));
//        System.out.println("Tarot boolean: " + (itemChance < (jokerPercentage + tarotPercentage)));
//        System.out.println("Planet boolean: " + (itemChance < (jokerPercentage + tarotPercentage + playingCardPercentage)));

        if (itemChance < jokerPercentage) {
//            System.out.println("Joker will be picked");
            card = getRandomJoker();
        }
        else if (itemChance < (jokerPercentage + tarotPercentage)) {
//            System.out.println("Tarot will be picked");
            card = getRandomTarot();
        }
        else if (itemChance < (jokerPercentage + tarotPercentage + planetPercentage)) {
//            System.out.println("Planet will be picked");
            card = getRandomPlanet();
        }
        else if (playingCardWeight != 0 && itemChance < jokerPercentage + tarotPercentage + planetPercentage + playingCardPercentage) {
//            System.out.println("Playing Card will be picked");
            card = getRandomPlayingCard();
        }
        else if (spectralCardWeight != 0 && itemChance < jokerPercentage + tarotPercentage + planetPercentage + playingCardPercentage + spectralCardPercentage) {
//            System.out.println("Spectral Card will be picked");
            card = getRandomSpectral();
        }

        return card;
    }

    //region Joker
    private Card getRandomJoker() {
        Joker joker = new Joker();

        int jokerChance = gameModel.getRand().nextInt(100);

        if (jokerChance < 70) joker.setJoker(getJokerFilteredByRarity("Common"));
        else if (jokerChance < 95) joker.setJoker(getJokerFilteredByRarity("Uncommon"));
        else joker.setJoker(getJokerFilteredByRarity("Rare"));

        return joker;
    }

    private Joker getJokerFilteredByRarity(String rarity) {
        List<Joker> jokerList = gameModel.getAllJokerList().stream().filter(x -> Objects.equals(x.getRarity(), rarity)).collect(Collectors.toList());
        return jokerList.get(gameModel.getRand().nextInt(jokerList.size()));
    }
    //endregion

    private Card getRandomTarot() {
        Tarot tarot = new Tarot();
        int tarotChance = gameModel.getRand().nextInt(21);
        tarot.setTarot(gameModel.getAllTarotList().get(tarotChance));
        return tarot;
    }

    private Card getRandomPlanet() {
        Planet planet = new Planet();
        int planetChance = gameModel.getRand().nextInt(12);
        planet.setPlanet(gameModel.getAllPlanetList().get(planetChance));
        return planet;
    }

    private Card getRandomSpectral() {
        Spectral spectral = new Spectral();
        int spectralChance = gameModel.getRand().nextInt(18);

        return spectral;
    }

    private Card getRandomPlayingCard() {
        PlayingCard playingCard = new PlayingCard(1,1);
        return playingCard;
    }

    private void drawVoucher() {
        voucherArea.getChildren().add(gameModel.getAllVoucherList().get(gameModel.getRand().nextInt(gameModel.getAllVoucherList().size())));
    }

    private void removeFromVoucher(Voucher voucher) {
        voucherArea.getChildren().remove(voucher);
    }

    private void drawBooster() {
        //System.out.println("drawBooster");
        boosterList.clear();
        for (int i = 0; i < maxBoosters; i++) {
            boosterList.add(getRandomBooster());
            //System.out.println(boosterList.size());
        }
        //System.out.println("bossterArea Children: " + boosterArea.getChildren().size());
        //System.out.println("bossterArea Height: " + boosterArea.getHeight());
    }

    public void nextRound() {
        gameController.nextRound();
    }

    public Booster getRandomBooster() {
        //System.out.println("getRandomBooster");
        Booster booster = new Booster();

        int boosterChance = gameModel.getRand().nextInt(2242);
        //System.out.println("boosterChance: " + boosterChance);

        if (boosterChance < 100) booster.setBooster(gameModel.getAllBoosterList().get(0));
        else if (boosterChance < 200) booster.setBooster(gameModel.getAllBoosterList().get(1));
        else if (boosterChance < 300) booster.setBooster(gameModel.getAllBoosterList().get(2));
        else if (boosterChance < 400) booster.setBooster(gameModel.getAllBoosterList().get(3));
        else if (boosterChance < 500) booster.setBooster(gameModel.getAllBoosterList().get(4));
        else if (boosterChance < 600) booster.setBooster(gameModel.getAllBoosterList().get(5));
        else if (boosterChance < 625) booster.setBooster(gameModel.getAllBoosterList().get(6));
        else if (boosterChance < 650) booster.setBooster(gameModel.getAllBoosterList().get(7));
        else if (boosterChance < 750) booster.setBooster(gameModel.getAllBoosterList().get(8));
        else if (boosterChance < 850) booster.setBooster(gameModel.getAllBoosterList().get(9));
        else if (boosterChance < 950) booster.setBooster(gameModel.getAllBoosterList().get(10));
        else if (boosterChance < 1050) booster.setBooster( gameModel.getAllBoosterList().get(11));
        else if (boosterChance < 1150) booster.setBooster( gameModel.getAllBoosterList().get(12));
        else if (boosterChance < 1250) booster.setBooster( gameModel.getAllBoosterList().get(13));
        else if (boosterChance < 1275) booster.setBooster( gameModel.getAllBoosterList().get(14));
        else if (boosterChance < 1300) booster.setBooster( gameModel.getAllBoosterList().get(15));
        else if (boosterChance < 1400) booster.setBooster( gameModel.getAllBoosterList().get(16));
        else if (boosterChance < 1500) booster.setBooster( gameModel.getAllBoosterList().get(17));
        else if (boosterChance < 1600) booster.setBooster( gameModel.getAllBoosterList().get(18));
        else if (boosterChance < 1700) booster.setBooster( gameModel.getAllBoosterList().get(19));
        else if (boosterChance < 1800) booster.setBooster( gameModel.getAllBoosterList().get(20));
        else if (boosterChance < 1900) booster.setBooster( gameModel.getAllBoosterList().get(21));
        else if (boosterChance < 1925) booster.setBooster( gameModel.getAllBoosterList().get(22));
        else if (boosterChance < 1950) booster.setBooster( gameModel.getAllBoosterList().get(23));
        else if (boosterChance < 2010) booster.setBooster( gameModel.getAllBoosterList().get(24));
        else if (boosterChance < 2070) booster.setBooster( gameModel.getAllBoosterList().get(25));
        else if (boosterChance < 2130) booster.setBooster( gameModel.getAllBoosterList().get(26));
        else if (boosterChance < 2145) booster.setBooster( gameModel.getAllBoosterList().get(27));
        else if (boosterChance < 2175) booster.setBooster( gameModel.getAllBoosterList().get(28));
        else if (boosterChance < 2205) booster.setBooster( gameModel.getAllBoosterList().get(29));
        else if (boosterChance < 2235) booster.setBooster( gameModel.getAllBoosterList().get(30));
        else booster.setBooster(gameModel.getAllBoosterList().get(31));

        //System.out.println("Booster: " + booster.getBoosterName());
        return booster;
    }

    private void moveItems(StackPane stackPane, double width) {
        int cards = stackPane.getChildren().size();
        double pos = 0;

        for(int i = 0; i < cards; i++) {
                if(cards%2==0) {
                    pos = width/2 + i * width - cards/2*width + i * 10;
                } else {
                    pos = i * width - cards/2*width + i * 10;
                }
        stackPane.getChildren().get(i).setTranslateX(pos);
        }
    }

    private Node createCardNode(Card card, boolean showBuy, boolean showSell) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/balatro/card.fxml"));
            AnchorPane cardPane = loader.load();

            CardViewController controller = loader.getController();
            controllerList.add(controller);
            controller.setData(card, showBuy, showSell);
            controller.setInShop(true);

            return cardPane;
        } catch (IOException e) {
            e.printStackTrace();
            return new Label("Error loading card");
        }
    }
}
