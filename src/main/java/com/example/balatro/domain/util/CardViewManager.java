package com.example.balatro.domain.util;

import com.example.balatro.domain.card.Card;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.domain.card.Joker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardViewManager {

    // Mapping Model <-> UI
    private final ObservableMap<CardViewController, AnchorPane> viewMap = FXCollections.observableHashMap();
    private final Map<Card, CardViewController> controllerMap = new HashMap<>();

    public void create(Card card, boolean inShop) {
        CardViewController.createCardNode(card, viewMap, inShop);
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
}
