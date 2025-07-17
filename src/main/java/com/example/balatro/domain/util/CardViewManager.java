package com.example.balatro.domain.util;

import com.example.balatro.domain.card.Card;
import com.example.balatro.controller.CardViewController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.*;

public class CardViewManager {

    //region Attributes
    private final ObservableMap<CardViewController, AnchorPane> viewMap = FXCollections.observableMap(new LinkedHashMap<>());
    private final Map<Card, CardViewController> controllerMap = new LinkedHashMap<>();
    private final BooleanProperty singleSelect =  new SimpleBooleanProperty(true);
    private final BooleanProperty draggable =  new SimpleBooleanProperty(true);
    private final BooleanProperty bringToFrontOnClick =  new SimpleBooleanProperty(true);
    private final IntegerProperty size = new SimpleIntegerProperty();
    //endregion

    //region Constructor
    public CardViewManager(boolean singleSelect, boolean draggable, boolean bringToFrontOnClick) {
        this.singleSelect.set(singleSelect);
        this.draggable.set(draggable);
        this.bringToFrontOnClick.set(bringToFrontOnClick);
        viewMap.addListener((MapChangeListener<? super CardViewController, ? super AnchorPane>) change -> {
            size.set(viewMap.size());
        });
    }
    //endregion

    //region Getter Setter
    public boolean isSingleSelect() {
        return singleSelect.get();
    }

    public BooleanProperty singleSelectProperty() {
        return singleSelect;
    }

    public boolean isBringToFrontOnClick() {
        return bringToFrontOnClick.get();
    }

    public BooleanProperty bringToFrontOnClickProperty() {
        return bringToFrontOnClick;
    }

    public boolean isDraggable() {
        return draggable.get();
    }

    public BooleanProperty draggableProperty() {
        return draggable;
    }

    //endregion


    //region Functions
    public void create(Card card, boolean inShop) {
        CardViewController controller = CardViewController.createCardNode(card, viewMap, inShop);
        controllerMap.put(card, controller);
    }

    public void createForBooster(Card card) {
        CardViewController controller = CardViewController.createCardNode(card, viewMap, false);
        //cvc.fromBoosterProperty().set(true);
        controller.fromBoosterProperty().set(true);
        //CardViewController controller = getControllerByCard(card);
        if (controller != null) {
            controllerMap.put(card, controller);
        }
    }

    public void create(Card card) {
        create(card, false);
    }

    public void create(ObservableList<Card> cards) {
        for (Card card : cards) {
            create(card, false);
        }
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

    public <T extends Card> List<T> getCardList(Class<T> clazz) {
        return controllerMap.keySet().stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    public int getSize() {
        return size.get();
    }

    public IntegerProperty sizeProperty() {
        return size;
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
