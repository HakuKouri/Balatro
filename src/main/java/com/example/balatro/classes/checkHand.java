package com.example.balatro.classes;

import java.util.*;

public class checkHand {

    public static List<String> evaluateHands(List<PlayingCard> cards) {
        List<String> possibleHands = new ArrayList<>();

        if (cards == null || cards.isEmpty()) {
            return possibleHands; // Keine gültige Hand
        }

        possibleHands.add("High Card");

        // Karten nach Rang sortieren
        cards.sort(Comparator.comparingInt(card -> card.getOrderPosition()));

        // Häufigkeit der Kartenränge zählen
        Map<String, Integer> rankCount = new HashMap<>();
        Map<String, Integer> suitCount = new HashMap<>();
        for (PlayingCard card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        boolean isFlush = suitCount.size() == 1; // Alle Karten haben die gleiche Farbe
        boolean isStraight = isStraight(cards); // Prüfen auf eine gerade Reihenfolge
        boolean isRoyal = isRoyal(cards); // Prüfen auf Royal Flush

        if(cards.size() == 5) {

            // Überprüfen der verschiedenen Handtypen und Hinzufügen der möglichen Hände
            if (isRoyal) {
                possibleHands.add("Royal Flush");
            }
            if (isFlush && isStraight) {
                possibleHands.add("Straight Flush");
            }
            if (rankCount.containsValue(3) && rankCount.containsValue(2)) {
                possibleHands.add("Full House");
                possibleHands.add("Two Pair");
            }
            if (isFlush) {
                possibleHands.add("Flush");
            }
            if (isStraight) {
                possibleHands.add("Straight");
            }
        }
        if (rankCount.containsValue(4)) {
            possibleHands.add("Four of a Kind");
            possibleHands.add("Three of a Kind");
            possibleHands.add("One Pair");
        }

        if (rankCount.containsValue(3)) {
            possibleHands.add("Three of a Kind");
            if(!possibleHands.contains("One Pair"))
                possibleHands.add("One Pair");
        }

        if (rankCount.containsValue(2)) {
            long pairCount = rankCount.values().stream().filter(count -> count == 2).count();
            if (pairCount == 2) {
                possibleHands.add("Two Pair");
            }
            if(!possibleHands.contains("One Pair"))
                possibleHands.add("One Pair");
        }

        return possibleHands;
    }

    private static boolean isStraight(List<PlayingCard> cards) {
        int[] values = cards.stream().mapToInt(PlayingCard::getOrderPosition).toArray();
        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] + 1 != values[i + 1]) {
                if(values[0] != 0 || values[4] != 12)
                    return false;
            }
        }

        return true;
    }

    private static boolean isRoyal(List<PlayingCard> cards) {
        return cards.stream().allMatch(card -> card.getValue() >= 10) && isStraight(cards);
    }

}
