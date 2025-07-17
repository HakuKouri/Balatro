package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Joker;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.game.PokerHandChecker;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.domain.util.CardViewManager;
import com.example.balatro.enums.JokerTrigger;
import com.example.balatro.models.GameModel;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayedCardsController {

    public StackPane playedCards_StackPane;

    private final GameModel gameModel = Balatro.getGameModel();

    public void initialize() {
        UIController.bindStackPane(gameModel.getPlayedCardsViewManager(), playedCards_StackPane);
    }

    public void addSelectedCards(Runnable onComplete) {
        List<PlayingCard> sortedCards = gameModel.getSelectedCards().stream()
                .sorted(Comparator.comparingDouble(card -> {
                    AnchorPane view = gameModel.getHoldingHandViewManager().getView(card);
                    if (view == null) {
                        System.out.println("⚠️ View not found for card: " + card);
                        return 0;
                    }
                    return view.getTranslateX();
                }))
                .toList();

        for (PlayingCard card : sortedCards) {
            card.setSelected(false);
            CardViewManager.transferCardTo(gameModel.getHoldingHandViewManager(), gameModel.getPlayedCardsViewManager(), card);
        }

        gameModel.getSelectedCards().clear();

        List<PlayingCard> countedCards = PokerHandChecker.getCardsForHand(gameModel.getPlayedCardsViewManager().getCardList(PlayingCard.class), gameModel.getBestHand().getName());
        for(PlayingCard card : gameModel.getPlayedCardsViewManager().getCardList(PlayingCard.class)) {
            card.setSelected(countedCards.contains(card));
        }

        List<PlayingCard> selectedCards = gameModel.getPlayedCardsViewManager().getCardList(PlayingCard.class).stream()
                .filter(PlayingCard::isSelected)
                .collect(Collectors.toList());

        animateSelectedCards(selectedCards, 0, onComplete);
    }

    private void animateSelectedCards(List<PlayingCard> cards, int index, Runnable onComplete) {
        if(index >= cards.size()) {
            for(PlayingCard card : gameModel.getHoldingHandViewManager().getCardList(PlayingCard.class)) {
                if(card.getEnhancement().getEnhancementName().equals("Steel Card")) {
                    gameModel.getBestHand().multMult(1.5);
                    triggerJokers(JokerTrigger.HAND_CARD_TRIGGERED, gameModel.getPlayedCardsViewManager().getCardList(PlayingCard.class));
                }
            }

            triggerJokers(JokerTrigger.ALL_CARDS_SCORED, gameModel.getPlayedCardsViewManager().getCardList(PlayingCard.class));

            UIController.playAnimations(() -> {
                //Rechne Punkte zusammen
                gameModel.addToScoredPoints(BigDecimal.valueOf((long) gameModel.getBestHand().getMulti() * gameModel.getBestHand().getChips()));
                gameModel.getBestHand().setHand(new PokerHand());

                pointsReached();

                if(onComplete != null) {
                    for(PlayingCard card : gameModel.getPlayedCardsViewManager().getCardList(PlayingCard.class)) {
                        card.setSelected(false);
                        Animation animation = UIController.cardMoveToAnimation(gameModel.getPlayedCardsViewManager().getView(card));
                        animation.setDelay(Duration.seconds(.2));
                        UIController.addToAnimationList(animation);
                    }
                    UIController.addToAnimationList(UIController.delayTimeline());
                    UIController.playAnimations(() -> onComplete.run());
                }
            });
            return;
        }

        PlayingCard card = cards.get(index);
        AnchorPane cardPane = gameModel.getPlayedCardsViewManager().getView(card);

        Timeline timeline = UIController.cardWiggleTimeline(cardPane);

        timeline.setCycleCount(3);
        timeline.setDelay(Duration.seconds(0.2));

        timeline.setOnFinished(event -> {
            System.out.println("Card Value: " + card.getValue());
            gameModel.getBestHand().chipsProperty().set(card.getValue() + gameModel.getBestHand().getChips());

            // Joker triggern (fügen ihre Animationen hinzu)
            triggerJokers(JokerTrigger.ON_CARD_SCORED, List.of(card));

            // Danach alle Jokeranimationen sequentiell ausführen, dann nächste Karte
            UIController.playAnimations(() -> {
                animateSelectedCards(cards, index + 1, onComplete);
            });
        });

        timeline.play();
    }

    private void pointsReached() {
        //TODO ADD EDITION, TRIGGER

        if(gameModel.isPointsReached()) {
            gameModel.setRewardVisibility(true);
            gameModel.getHoldingHandViewManager().clear();

            List<PlayingCard> handCards = gameModel.getHoldingHandViewManager().getCardList().stream()
                    .filter(card -> card instanceof PlayingCard)
                    .map(card -> (PlayingCard) card)
                    .toList();

            for(PlayingCard card : handCards) {
                if (card.getEnhancement().getEnhancementName().equals("Gold Card")) {
                    gameModel.getRunState().addMoney(3);
                    triggerJokers(JokerTrigger.HAND_CARD_TRIGGERED, gameModel.getPlayedCards());
                }
            }
            triggerJokers(JokerTrigger.END_OF_ROUND, gameModel.getPlayedCards());

            if(gameModel.getActiveBlind().getBlindId() > 1) {
                gameModel.getRunState().setAnte((gameModel.getRunState().getAnte() + 1));
            }

            gameModel.getCurrentRound().setHands(gameModel.getRunState().getMaxHands());
            gameModel.getCurrentRound().setDiscards(gameModel.getRunState().getMaxDiscards());
        } else {
            gameModel.setHandButtonVisibility(true);
        }
    }

    public void removeAllCards() {
        gameModel.getPlayedCardsViewManager().clear();
    }

    private void triggerJokers(JokerTrigger trigger, List<PlayingCard> playedCards) {
        System.out.println("Jokers getriggert");
        for (Joker joker : gameModel.getJokerManager().getCardList(Joker.class)) {
            System.out.println("Triggert Joker: " + joker.getCardName());
            joker.tryActivate(trigger, gameModel, playedCards);
        }
    }



}
