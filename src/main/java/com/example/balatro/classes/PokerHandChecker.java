package com.example.balatro.classes;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHandChecker {

    // Methode, die eine Liste von Karten und eine Pokerhand überprüft und passende Karten zurückgibt
    public static List<PlayingCard> getCardsForHand(List<PlayingCard> cards, String handType) {
        switch (handType) {
            case "Four of a Kind":
                return getFourOfAKind(cards);
            case "Three of a Kind":
                return getThreeOfAKind(cards);
            case "Two Pair":
                return getTwoPair(cards);
            case "One Pair":
                return getOnePair(cards);
            case "High Card":
                return getHighCard(cards);
            default:
                throw new IllegalArgumentException("Unbekannte Pokerhand: " + handType);
        }
    }

    // Findet die Karten für "Four of a Kind"
    private static List<PlayingCard> getFourOfAKind(List<PlayingCard> cards) {
        Map<Integer, List<PlayingCard>> valueMap = groupCardsByValue(cards);
        for (Map.Entry<Integer, List<PlayingCard>> entry : valueMap.entrySet()) {
            if (entry.getValue().size() == 4) {
                return entry.getValue();
            }
        }
        return Collections.emptyList(); // Wenn kein "Four of a Kind" gefunden wird
    }

    // Findet die Karten für "Three of a Kind"
    private static List<PlayingCard> getThreeOfAKind(List<PlayingCard> cards) {
        Map<Integer, List<PlayingCard>> valueMap = groupCardsByValue(cards);
        for (Map.Entry<Integer, List<PlayingCard>> entry : valueMap.entrySet()) {
            if (entry.getValue().size() == 3) {
                return entry.getValue();
            }
        }
        return Collections.emptyList(); // Wenn kein "Three of a Kind" gefunden wird
    }

    // Findet die Karten für "Two Pair"
    private static List<PlayingCard> getTwoPair(List<PlayingCard> cards) {
        Map<Integer, List<PlayingCard>> valueMap = groupCardsByValue(cards);
        List<PlayingCard> pairs = new ArrayList<>();
        for (Map.Entry<Integer, List<PlayingCard>> entry : valueMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                pairs.addAll(entry.getValue());
            }
        }
        if (pairs.size() == 4) {
            return pairs; // Es gibt genau zwei Paare
        }
        return Collections.emptyList(); // Wenn kein "Two Pair" gefunden wird
    }

    // Findet die Karten für "One Pair"
    private static List<PlayingCard> getOnePair(List<PlayingCard> cards) {
        Map<Integer, List<PlayingCard>> valueMap = groupCardsByValue(cards);
        for (Map.Entry<Integer, List<PlayingCard>> entry : valueMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                return entry.getValue();
            }
        }
        return Collections.emptyList(); // Wenn kein "One Pair" gefunden wird
    }

    // Findet die höchste Karte (High Card)
    private static List<PlayingCard> getHighCard(List<PlayingCard> cards) {
        return cards.stream()
                .sorted((card1, card2) -> Integer.compare(card2.getValue(), card1.getValue()))
                .limit(1)
                .collect(Collectors.toList());
    }

    // Hilfsmethode, die Karten nach ihrem Wert gruppiert
    private static Map<Integer, List<PlayingCard>> groupCardsByValue(List<PlayingCard> cards) {
        Map<Integer, List<PlayingCard>> valueMap = new HashMap<>();
        for (PlayingCard card : cards) {
            valueMap.putIfAbsent(card.getValue(), new ArrayList<>());
            valueMap.get(card.getValue()).add(card);
        }
        return valueMap;
    }

}
