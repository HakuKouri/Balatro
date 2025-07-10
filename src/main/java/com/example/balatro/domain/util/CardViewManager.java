package com.example.balatro.domain.util;

import com.example.balatro.domain.card.Card;
import com.example.balatro.controller.CardViewController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.*;

public class CardViewManager {

    //region Attributes
    private final ObservableMap<CardViewController, AnchorPane> viewMap = FXCollections.observableMap(new LinkedHashMap<>());
    private final Map<Card, CardViewController> controllerMap = new HashMap<>();
    private final BooleanProperty singleSelect =  new SimpleBooleanProperty(true);
    //endregion

    //region Constructor
    public CardViewManager(boolean singleSelect) {
        this.singleSelect.set(singleSelect);
    }
    //endregion

    //region Getter Setter
    public boolean isSingleSelect() {
        return singleSelect.get();
    }

    public BooleanProperty singleSelectProperty() {
        return singleSelect;
    }
    //endregion


    //region Functions
    public void create(Card card, boolean inShop) {
        CardViewController.createCardNode(card, viewMap, inShop);
        CardViewController controller = getControllerByCard(card);
        if (controller != null) {
            controllerMap.put(card, controller);
        }
    }

    public void createForBooster(Card card) {
        CardViewController cvc = CardViewController.createCardNode(card, viewMap, false);
        cvc.fromBoosterProperty().set(true);
        CardViewController controller = getControllerByCard(card);
        if (controller != null) {
            controllerMap.put(card, controller);
        }
    }

    public void create(Card card) {
        create(card, false);
    }

    public AnchorPane getView(Card card) {
        CardViewController controller = controllerMap.get(card);
        return controller != null ? viewMap.get(controller) : null;
    }

    public CardViewController getControllerByCard(Card card) {
        return viewMap.keySet().stream()
                .filter(c -> c.getCard().equals(card))
                .findFirst()
                .orElse(null);
    }

    public void remove(Card card) {
        CardViewController controller = controllerMap.remove(card);
        if (controller != null) {
            viewMap.remove(controller);
        }
    }

    public ObservableMap<CardViewController, AnchorPane> getViewMap() {
        return viewMap;
    }

    public void clear() {
        controllerMap.clear();
        viewMap.clear();
    }

    public List<Card> getCardList() {
        return controllerMap.keySet().stream().toList();
    }

    public int size() {
        return controllerMap.size();
    }

    public static void sortMapByX(ObservableMap<CardViewController, AnchorPane> map, StackPane root) {
        List<Map.Entry<CardViewController, AnchorPane>> sorted = map.entrySet().stream().sorted(Comparator.comparingDouble(entry -> entry.getValue().getTranslateX())).toList();

        LinkedHashMap<CardViewController, AnchorPane> newMap = new LinkedHashMap<>();
        sorted.forEach(e -> newMap.put(e.getKey(), e.getValue()));

        map.clear();
        map.putAll(newMap);

        root.getChildren().setAll(map.values());
        map.keySet().stream()
                .filter(CardViewController::isSelected)
                .map(map::get)
                .forEach(Node::toFront);
    }

    public static void transferCardTo(CardViewManager from, CardViewManager to, Card card) {
        from.remove(card);
        to.create(card);
    }
}
