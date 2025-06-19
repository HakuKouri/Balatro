package com.example.balatro.classes;

import com.example.balatro.models.GameModel;

import java.util.*;

public class checkHand {

    public static List<PokerHand> evaluateHands(GameModel model, List<PlayingCard> cards) {
        List<PokerHand> possibleHands = new ArrayList<>();

        if (cards == null || cards.isEmpty()) {
            return possibleHands; // Keine gültige Hand
        }

        // Karten nach Rang sortieren
        cards.sort(Comparator.comparingInt(card -> card.getRankIndex()));

        // Häufigkeit der Kartenränge zählen
        Map<String, Integer> rankCount = new HashMap<>();
        Map<Suit, Integer> suitCount = new HashMap<>();
        for (PlayingCard card : cards) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        boolean isFlush = suitCount.size() == 1; // Alle Karten haben die gleiche Farbe
        boolean isStraight = isStraight(cards); // Prüfen auf eine gerade Reihenfolge
        boolean isRoyal = isRoyal(cards); // Prüfen auf Royal Flush

        if(cards.size() == 5) {

            // Überprüfen der verschiedenen Handtypen und Hinzufügen der möglichen Hände
            if (isStraight && isFlush) {
                possibleHands.add(model.getPokerHand("Royal Flush"));
            }
            if (isFlush && isStraight) {
                possibleHands.add(model.getPokerHand("Straight Flush"));
            }
            if (rankCount.containsValue(3) && rankCount.containsValue(2)) {
                possibleHands.add(model.getPokerHand("Full House"));
                possibleHands.add(model.getPokerHand("Two Pair"));
            }
            if (isFlush) {
                possibleHands.add(model.getPokerHand("Flush"));
            }
            if (isStraight) {
                possibleHands.add(model.getPokerHand("Straight"));
            }
        }
        if (rankCount.containsValue(4)) {
            possibleHands.add(model.getPokerHand("Four of a Kind"));
            possibleHands.add(model.getPokerHand("Three of a Kind"));
            possibleHands.add(model.getPokerHand("One Pair"));
        }

        if (rankCount.containsValue(3)) {
            possibleHands.add(model.getPokerHand("Three of a Kind"));
            if(!possibleHands.contains(model.getPokerHand("One Pair")))
                possibleHands.add(model.getPokerHand("One Pair"));
        }

        if (rankCount.containsValue(2)) {
            long pairCount = rankCount.values().stream().filter(count -> count == 2).count();
            if (pairCount == 2) {
                possibleHands.add(model.getPokerHand("Two Pair"));
            }
            if(!possibleHands.contains(model.getPokerHand("One Pair")))
                possibleHands.add(model.getPokerHand("One Pair"));
        }

        possibleHands.add(model.getPokerHand("High Card"));

        return possibleHands;
    }

    private static boolean isStraight(List<PlayingCard> cards) {
        int[] values = cards.stream().mapToInt(PlayingCard::getRankIndex).toArray();
        for (int i = 0; i < values.length - 1; i++) {
            if (values[i] + 1 != values[i + 1]) {
                    return false;
            }
        }

        return true;
    }

    private static boolean isRoyal(List<PlayingCard> cards) {
        return cards.stream().allMatch(card -> card.getValue() >= 10) && isStraight(cards);
    }

}
