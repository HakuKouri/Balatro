package com.example.balatro.classes;

import com.example.balatro.Balatro;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.controller.GameController;
import com.example.balatro.controller.UIController;
import com.example.balatro.interfaces.JokerEffect;
import com.example.balatro.models.GameModel;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JokerEffectRegistry {
    private static final Map<String, JokerEffect> effectMap = new HashMap<>();
    private static final GameModel gameModel = Balatro.getGameModel();

    static {
        // Hier registrierst du alle Effekte über ihre Keys
//        effectMap.put("hearts_mult", (context, self, cards, params) -> {
//            for (PlayingCard c : cards) {
//                if (c.getSuit() == Suit.HEARTS) {
//                    context.addToMultiplier(3);
//                }
//            }
//        });

        effectMap.put("ADD_CARD_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: ADD_CARD_VALUE");
        });

        effectMap.put("ADD_MULT_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: ADD_MULT_VALUE");
            //TODO Joker Trigger Animation

            self.setMultValue(self.getMultValue() + Double.parseDouble(((String)params.get("multiplier")).split(" ")[0].substring(1)));
        });

        effectMap.put("ADD_STONE_CARD", (context, self, cards, params) -> {
            System.out.println("Trigger: ADD_STONE_CARD");
            //TODO Joker Trigger Animation
            PlayingCard stoneCard = PlayingCard.createRandomPlayingCard();
            stoneCard.setEnhancement(gameModel.getAllEnhancementList().get(5));
            gameModel.addCardToDeckFull(stoneCard);
        });

        effectMap.put("BEAN_SET_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: BEAN_SET_VALUE");
            self.setOtherValue(5);
            context.setBeanValue(context.getBeanValue() + 5);
        });

        effectMap.put("BEAN_SUB_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: BEAN_SUB_VALUE");
            self.setOtherValue(self.getOtherValue() - 1);
            context.setBeanValue(context.getBeanValue() - 1);
            if(self.getOtherValue() <= 0) {
                AnchorPane pane = CardViewController.getCardAnchorPane(context.getActiveJokerMap(), self);
//                        context.getActiveJokerMap().values()
//
//
//                        .stream()
//                        .filter(j ->
//                                j.getCard() == self)
//                        .findFirst().get();
                context.getActiveJokerMap().remove(pane);
            }
        });

        effectMap.put("BUS_HAND_CHECK", (context, self, cards, params) -> {
            System.out.println("Trigger: BUS_HAND_CHECK");
        });

        effectMap.put("CARD_COUNT_MONEY_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: CARD_COUNT_MONEY_ADD");
        });

        effectMap.put("CARD_COUNT_MULT_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: CARD_COUNT_MULT_ADD");
        });

        effectMap.put("CHANGE_SUIT", (context, self, cards, params) -> {
            System.out.println("Trigger: CHANGE_SUIT");
        });

        effectMap.put("CHANGE_HAND", (context, self, cards, params) -> {
            System.out.println("Trigger: CHANGE_HAND");
        });

        effectMap.put("CHANGE_RANK", (context, self, cards, params) -> {
            System.out.println("Trigger: CHANGE_RANK");
        });

        effectMap.put("CHIPS_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: CHIPS_ADD");
        });

        effectMap.put("CHIPS_ADD_PER_DISCARD", (context, self, cards, params) -> {
            System.out.println("Trigger: CHIPS_ADD_PER_DISCARD");
        });

        effectMap.put("COUNT_CHIPS_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: COUNT_CHIPS_ADD");
        });

        effectMap.put("DESTROY", (context, self, cards, params) -> {
            System.out.println("Trigger: DESTROY");
        });

        effectMap.put("DESTROY_CHANCE", (context, self, cards, params) -> {
            System.out.println("Trigger: DESTROY_CHANCE");
        });

        effectMap.put("DISCARDS_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: DISCARDS_ADD");
        });

        effectMap.put("DISCARDS_SUB", (context, self, cards, params) -> {
            System.out.println("Trigger: DISCARDS_SUB");
        });

        effectMap.put("DUSK_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DUSK_EFFECT");
        });

        effectMap.put("ENHANCEMENT_MONEY_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: ENHANCEMENT_MONEY_ADD");
        });

        effectMap.put("GAIN_SELL_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: GAIN_SELL_VALUE");
        });

        effectMap.put("HAND_CHIPS_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: HAND_CHIPS_ADD");
        });

        effectMap.put("HAND_MONEY_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: HAND_MONEY_ADD");
        });

        effectMap.put("HAND_MULT_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: HAND_MULT_ADD");
        });

        effectMap.put("HAND_MULT_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: HAND_MULT_MULT");
        });

        effectMap.put("HAND_SIZE_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: HAND_SIZE_ADD");
        });

        effectMap.put("HAND_SIZE_SUB", (context, self, cards, params) -> {
            System.out.println("Trigger: HAND_SIZE_SUB");
        });

        effectMap.put("MULT_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_ADD");
            Timeline timeline = UIController.timeline(self);
            timeline.play();
            System.out.println("Multiplier: " + ((String)params.get("multiplier")).split(" ")[0].substring(1));
            context.getBestHand().addMult(Integer.parseInt(((String)params.get("multiplier")).split(" ")[0].substring(1)));
        });
        effectMap.put("MULT_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_MULT");
            context.getBestHand().multMult(Integer.parseInt(((String)params.get("multiplier")).split(" ")[0].substring(1)));
        });

        effectMap.put("MAIL_DISCARD", (context, self, cards, params) -> {
            System.out.println("Trigger: MAIL_DISCARD");
        });

        effectMap.put("MULT_ADD_ZERO_DISCARDS", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_ADD_ZERO_DISCARDS");
        });

        effectMap.put("MULT_ADD_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_ADD_VALUE");
        });

        effectMap.put("MULT_SUB_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_SUB_VALUE");
        });

        effectMap.put("MULT_SUB", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_SUB");
        });

        effectMap.put("NOVA_MULT_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: NOVA_MULT_ADD");
        });

        effectMap.put("RANK_CHIPS_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_CHIPS_ADD");
        });

        effectMap.put("RANK_CHIPS_MULT_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_CHIPS_MULT_ADD");
        });

        effectMap.put("RANK_MONEY_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_MONEY_ADD");
        });

        effectMap.put("RANK_MULT_ADD", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_MULT_ADD");
        });

        effectMap.put("RANK_MULT_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_MULT_MULT");
        });

        effectMap.put("RANK_SUIT_MULT_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_SUIT_MULT_MULT");
        });

        effectMap.put("RANK_SUIT_CHANGE", (context, self, cards, params) -> {
            System.out.println("Trigger: RANK_SUIT_CHANGE");
        });

        effectMap.put("RESET_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: RESET_MULT");
        });

        effectMap.put("RETRIGGER", (context, self, cards, params) -> {
            System.out.println("Trigger: RETRIGGER");
        });

        effectMap.put("SET_CHIPS", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_CHIPS");
        });

        effectMap.put("SUB_MULT_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: SUB_MULT_VALUE");
        });

        effectMap.put("SUIT_DISCARD_ADD_CHIPS", (context, self, cards, params) -> {
            System.out.println("Trigger: SUIT_DISCARD_ADD_CHIPS");
        });

        effectMap.put("SUIT_MULT_ADD", (context, self, cards, params) -> {
            if(cards.get(0).getSuit() == getSuit(params.get("suit").toString())) {
                AnchorPane anchorPane = CardViewController.getCardAnchorPane(context.getActiveJokerMap(), self);
                Timeline timeline = UIController.timeline(anchorPane);

                timeline.setOnFinished(event -> {
                    String rawMultiplier = (String) params.get("multiplier");
                    int value = Integer.parseInt(rawMultiplier.replace(" Mult", ""));
                    context.getBestHand().addMult(value);
                });

                UIController.addToAnimationList(timeline);
            }
        });

        effectMap.put("SUB_MULT_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: SUB_MULT_VALUE");
        });

        effectMap.put("SET_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_MULT");
        });


        //UNIQUE
        effectMap.put("ACROBAT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: ACROBAT_EFFECT");
            //TODO Joker Animation
            if(context.getHandsPlayed() == 0) {
                context.getBestHand().multMult(3);
            }
        });

        effectMap.put("ANCIENT_JOKER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: ANCIENT_JOKER_EFFECT");
            //TODO Joker Trigger Animation
            int index = context.getRand().nextInt(4);
            switch (index) {
                case 0:
                    self.setSuitFilter(Suit.HEARTS);
                    break;
                case 1:
                    self.setSuitFilter(Suit.CLUBS);
                    break;
                case 2:
                    self.setSuitFilter(Suit.DIAMONDS);
                    break;
                case 3:
                    self.setSuitFilter(Suit.SPADES);
                    break;
            }
        });

        effectMap.put("BARON_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BARON_EFFECT");
            //TODO Einzel Trigger Animation pro König in der Hand
            int countKings = (int) context.getHandCards().stream().filter(x -> x.getRank() == "King").count();
            for (int i = 0; i < countKings; i++) {
                context.getBestHand().multMult(1.5);
            }
        });

        effectMap.put("BASEBALL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BASEBALL_EFFECT");
            //TODO Einzel Trigger Animation pro Joker
            int uncommonCount = (int) context.getActiveJokerMap().keySet().stream().filter(cardViewController -> ((Joker)cardViewController.getCard()).getRarity() == "Uncommon"). count();
            for (int i = 0; i < uncommonCount; i++) {
                context.getBestHand().multMult(1.5);
            }
        });

        effectMap.put("BLACKBOARD_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BLACKBOARD_EFFECT");
            //TODO Joker Animation
            int blackCount = (int) context.getHandCards().stream().filter(card ->
                    card.getSuit() == Suit.SPADES ||
                    card.getSuit() == Suit.CLUBS ||
                    card.getEnhancement().getEnhancementName() == "Wild Card").count();
            if(blackCount == context.getHandCards().size()) {
                context.getBestHand().multMult(3);
            }
        });

        effectMap.put("BLOODSTONE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BLOODSTONE_EFFECT");
            //TODO Joker Trigger Animation
            for (PlayingCard c : cards) {
                if (c.getSuit() == Suit.HEARTS && context.getRand().nextBoolean()) {
                    context.getBestHand().multMult(1.5);
                }
            }
        });

        effectMap.put("BLUEPRINT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BLUEPRINT_EFFECT");
            //TODO Joker Effect
        });

        effectMap.put("BOOTSTRAPS_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BOOTSTRAPS_EFFECT");
            //TODO Bind Desription
            //self.cardDescriptionProperty().bind(Bindings.createStringBinding(() -> String.format(self.getCardDescription(), 2 * context.getMoney() / 5),self.cardDescription,context.moneyProperty()));
            //self.multValueProperty().bind(Bindings.createDoubleBinding(() -> {
            //    return (double) ((context.getMoney() / 5) * 2);
            //}));
        });

        effectMap.put("BRAINSTORM_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BRAINSTORM_EFFECT");
        });

        effectMap.put("BULL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BULL_EFFECT");
        });

        effectMap.put("BURGLAR_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BURGLAR_EFFECT");
            //TODO Joker Animation
            context.handsPlayedProperty().set(context.getHandsPlayed() + 3);
            context.discardsProperty().set(0);
        });

        effectMap.put("BURNT_JOKER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BURNT_JOKER_EFFECT");
            if(context.isFirstDiscard()) {
                //TODO Level Up PokerHand
            }
        });

        effectMap.put("CARD_SHARP_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: CARD_SHARP_EFFECT");
            if (context.getPokerHandsPlayedThisRound().contains(context.getBestHand())) {
                context.getBestHand().multMult(3);
            }
        });

        effectMap.put("CERTIFICATE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: CERTIFICATE_EFFECT");
            //TODO Joker Trigger Effect
            PlayingCard card = new PlayingCard(context.getRand().nextInt(12), context.getRand().nextInt(4));
            card.setSeal(context.getAllSealList().get(context.getRand().nextInt(context.getAllSealList().size())));
            context.addCardToDeckFull(card);
            context.getHandCards().add(card);
        });

        effectMap.put("DAGGER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DAGGER_EFFECT");
            //TODO Joker Trigger Effekt
            List<Node> children = GameController.getInstance().getJokerStackPane().getChildren();
            for (int i = 0; i < children.size(); i++) {
                AnchorPane pane = (AnchorPane) children.get(i);
                CardViewController controller = context.getActiveJokerMap().keySet()
                        .stream()
                        .filter(cvc -> cvc.getCard() == self)
                        .collect(Collectors.toList()).get(0);
                Card card = controller.getCard();

                if ("Ceremonial Dagger".equals(card.getCardName())) {
                    if (i < children.size() - 1);
                    System.out.println("Hat Nachfolger: ");
                    self.setMultValue(self.getMultValue() + controller.getCard().getSellValue());
                    context.getActiveJokerMap().remove(children.get(i+1));
                    break;
                }
            }
        });

        effectMap.put("DIET_COLA_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DIET_COLA_EFFECT");
            //TODO Joker Trigger Animation
            context.getTagQueue().add(context.getAllTagList().get(17));
        });

        effectMap.put("DNA_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DNA_EFFECT");
            //TODO Joker Trigger Animation
            if (context.isFirstHand() && context.getPlayedCards().size() == 1) {
                PlayingCard card = context.getPlayedCards().get(0);
                context.getDeckFull().add(card);
                context.getHandCards().add(card);
            }
        });

        effectMap.put("DRIVERS_LICENSE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DRIVERS_LICENSE_EFFECT");
            //TODO Joker Trigger Animation
            if(context.getDeckFull().stream().filter(card -> card.getEnhancement().getEnhancementName() != "").count() >= 16) {
                context.getBestHand().multMult(3);
            }
        });

        effectMap.put("EIGHTBALL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: EIGHTBALL_EFFECT");
            //TODO Joker Trigger Animation
            for (PlayingCard c : cards) {
                if(c.getValue() == 8 && context.getRand().nextInt(4) == 0) {
                    context.getConsumableList().add(context.getAllTarotList().get(context.getRand().nextInt(context.getAllTarotList().size())));
                }
            }
        });

        effectMap.put("EROSION_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: EROSION_EFFECT");
            //TODO Joker Trigger Animation
            if(context.getDeckFull().size() < 52)
                context.getBestHand().addMult((52 - context.getDeckFull().size()) * 4);
        });

        effectMap.put("FLOWERPOT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: FLOWERPOT_EFFECT");
            //TODO Joker Trigger Animation
            int wildCards = 0;
            Map<Suit, Integer> suitCount = new HashMap<>();
            for (PlayingCard c : cards) {
                suitCount.put(c.getSuit(), 0);
                if(c.getEnhancement().getEnhancementName() == "Wild Card") {
                    wildCards++;
                }
            }
            if(suitCount.size() + wildCards >= 4) {
                context.getBestHand().multMult(3);
            }
        });

        effectMap.put("GIFT_CARD_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: GIFT_CARD_EFFECT");
            //TODO Joker Trigger Animation
            for(CardViewController c : context.getActiveJokerMap().keySet().stream().collect(Collectors.toList())){
                c.getCard().additionalSellValueProperty().add(1);
            }
            for(var c : context.getConsumableList()) {
                c.additionalSellValueProperty().add(1);
            }
        });

        effectMap.put("HALLUCINATION_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: HALLUCINATION_EFFECT");
            //TODO Joker Trigger Animation
            if (context.getRand().nextInt(2) == 0) {
                context.getConsumableList().add(context.getAllTarotList().get(context.getRand().nextInt(context.getAllTarotList().size())));
            }
        });

        effectMap.put("HANGING_CHAD_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: HANGING_CHAD_EFFECT");
            //TODO Retrigger
        });

        effectMap.put("HOLOGRAM_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: HOLOGRAM_EFFECT");
            //TODO Joker Trigger Animation
            self.setMultValue(self.getMultValue() + .25);
        });

        effectMap.put("ICECREAM_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: ICECREAM_EFFECT");
            //TODO Joker Trigger Animation
            //TODO Joker Effekt

        });

        effectMap.put("INVISIBLE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: INVISIBLE_EFFECT");
            //TODO Joker Effekt
        });

        effectMap.put("LOYALTY_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: LOYALTY_EFFECT");
            //TODO Joker Effekt
        });

        effectMap.put("LUCHADOR_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: LUCHADOR_EFFECT");
            context.setBoss_disable_flag(context.getBoss_disable_flag() + 1);
        });

        effectMap.put("MADNESS_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MADNESS_EFFECT");
            //TODO Joker Trigger Effekt
            if(context.getActiveBlind().getBlindId() < 2) {
                List<AnchorPane> list = context.getActiveJokerMap().values()
                        .stream()
                        .filter(c -> CardViewController.getCardViewController(context.getActiveJokerMap(),c).getCard().getCardName() != "Madness").collect(Collectors.toList());
                //TODO Destroy Joker Animation
                context.getActiveJokerMap().remove(list.get(context.getRand().nextInt(list.size())));
                self.setMultValue(self.getMultValue() + .5);
            }
        });

        effectMap.put("MATADOR_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MATADOR_EFFECT");
            context.addMoney(8);
        });

        effectMap.put("MERRY_BUY_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MERRY_BUY_EFFECT");
            context.setMaxDiscards(context.getMaxDiscards() + 3);
            context.setHands(context.getHands() - 1);
        });

        effectMap.put("MERRY_SELL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MERRY_SELL_EFFECT");
            context.setMaxDiscards(context.getMaxDiscards() - 3);
            context.setHands(context.getHands() + 1);
        });

        effectMap.put("MIDAS_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MIDAS_EFFECT");
            //TODO Joker Trigger Effekt
            for (PlayingCard c : cards) {
                if(context.getAll_face_flag() > 0 || c.getValue() >= 10)
                    c.setEnhancement(context.getAllEnhancementList().get(6));
            }
        });

        effectMap.put("MINE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MINE_EFFECT");
            //TODO Joker Trigger Effekt
            //TODO Retrigger
        });

        effectMap.put("MISPRINT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MISPRINT_EFFECT");
            //TODO Joker Trigger Effekt
            context.getBestHand().addMult(context.getRand().nextInt(24));
        });

        effectMap.put("OBELISK_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: OBELISK_EFFECT");
            //TODO Joker Trigger Effekt

        });

        effectMap.put("PERKEO_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: PERKEO_EFFECT");
            //TODO Joker Trigger Effekt
            Card copy = context.getConsumableList().get(context.getRand().nextInt(context.getConsumableList().size()));
            copy.setEdition(context.getAllEditionList().get(4));
            context.getConsumableList().add(copy);
        });

        effectMap.put("PHOTOGRAPH_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: PHOTOGRAPH_EFFECT");
            //TODO Joker Trigger Effekt
            PlayingCard firstFace = context.getPlayedCards().stream().filter(c -> c.getValue() >= 10).findFirst().get();
            if(firstFace == null) return;

            for (PlayingCard c : cards) {
                if(c == firstFace) context.getBestHand().multMult(2);
            }
        });

        effectMap.put("RAISED_FIST_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RAISED_FIST_EFFECT");
            //TODO Joker Trigger Effekt

            PlayingCard lowestCard = context.getHandCards().stream().min(Comparator.comparing(PlayingCard::getValue)).get();
            if(lowestCard == null) return;
            context.getBestHand().addMult(lowestCard.getValue());
        });

        effectMap.put("RESERVED_PARKING_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RESERVED_PARKING_EFFECT");
            //TODO Joker Trigger Effekt

            for (PlayingCard c : context.getHandCards()) {
                int chance = context.getDouble_chance_flag() > 0 ? 2 - 1 : 2;
                if (context.getRand().nextInt(chance) == 0) {
                    context.addMoney(1);
                }
            }
        });

        effectMap.put("RIFF_RAFF_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RIFF_RAFF_EFFECT");
            //TODO Joker Trigger Effekt
            List<Joker> commonJokerList = context.getAllJokerList().stream().filter(j -> j.getRarity() == "Common").collect(Collectors.toList());
            for (int i = 0; i < 2 && context.getActiveJokerMap().size() < context.getMaxJokers(); i++) {
                CardViewController.createCardNode(commonJokerList.get(context.getRand().nextInt(commonJokerList.size())), context.getActiveJokerMap());
            }
        });

        effectMap.put("RUNNER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RUNNER_EFFECT");
            //TODO Joker Trigger Effekt
            if(context.getBestHand().getName() == "Straight") self.setChipValue(self.getChipValue() + 15);
        });

        effectMap.put("SEEING_DOUBLE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SEEING_DOUBLE_EFFECT");
            //TODO Joker Trigger Effekt
            Map<Suit, Integer> suitCount = new HashMap<>();
            for (PlayingCard c : cards) {
                suitCount.put(c.getSuit(), 0);
            }
            if(suitCount.containsKey(Suit.CLUBS) && suitCount.size() > 1) {
                context.getBestHand().multMult(2);
            }
        });

        effectMap.put("SELTZER_TRIGGER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SELTZER_TRIGGER_EFFECT");
            //TODO Retrigger

        });

        effectMap.put("SELTZER_REDUCE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SELTZER_REDUCE_EFFECT");
            //TODO Joker Trigger Effekt
            //TODO andere Variable für Wert
        });

        effectMap.put("SQUARE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SQUARE_EFFECT");
            //TODO Joker Trigger Effekt
            if(context.getPlayedCards().size() == 4)
                self.setChipValue(self.getChipValue() + 4);
        });

        effectMap.put("STUNTMAN_BUY_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: STUNTMAN_BUY_EFFECT");
            //TODO Joker Trigger Effekt
            context.setHandSize(context.getHandSize() - 2);
        });

        effectMap.put("STUNTMAN_SALE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: STUNTMAN_SALE_EFFECT");
            context.setHandSize(context.getHandSize() + 2);
        });

        effectMap.put("SEANCE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SEANCE_EFFECT");
            if (context.getBestHand().getName() == "Straight Flush" && context.getConsumableList().size() < context.getMaxConsumables())
                context.getConsumableList().add(context.getConsumableList().get(context.getRand().nextInt(context.getConsumableList().size())));
        });


        //region FLAGS
        effectMap.put("SET_CLOUD9_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_CLOUD9_FLAG");
            context.setCloud9_flag(context.getCloud9_flag() + 1);
        });

        effectMap.put("UNSET_CLOUD9_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_CLOUD9_FLAG");
            context.setCloud9_flag(context.getCloud9_flag() - 1);
        });

        effectMap.put("SET_DOUBLE_CHANCE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_DOUBLE_CHANCE_FLAG");
            context.setDouble_chance_flag(context.getDouble_chance_flag() + 1);
        });

        effectMap.put("UNSET_DOUBLE_CHANCE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_DOUBLE_CHANCE_FLAG");
            context.setDouble_chance_flag(context.getDouble_chance_flag() - 1);
        });

        effectMap.put("SET_MOON_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_MOON_FLAG");
            context.setMoon_flag(context.getMoon_flag() + 1);
        });

        effectMap.put("UNSET_MOON_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_MOON_FLAG");
            context.setMoon_flag(context.getMoon_flag() - 1);
        });

        effectMap.put("SET_ASTRONOMER_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_ASTRONOMER_FLAG");
            context.setAstronomer_flag(context.getAstronomer_flag() + 1);
        });

        effectMap.put("UNSET_ASTRONOMER_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_ASTRONOMER_FLAG");
            context.setAstronomer_flag(context.getAstronomer_flag() - 1);
        });

        effectMap.put("SET_BONES_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_BONES_FLAG");
            context.setBones_flag(context.getBones_flag() + 1);
        });

        effectMap.put("UNSET_BONES_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_BONES_FLAG");
            context.setBones_flag(context.getBones_flag() - 1);
        });

        effectMap.put("SET_BOSS_DISABLE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_BOSS_DISABLE_FLAG");
            context.setBoss_disable_flag(context.getBoss_disable_flag() + 1);
        });

        effectMap.put("UNSET_BOSS_DISABLE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_BOSS_DISABLE_FLAG");
            context.setBoss_disable_flag(context.getBoss_disable_flag() - 1);
        });

        effectMap.put("GRATI_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: GRATI_SET_FLAG");
            context.setGrati_flag(context.getGrati_flag() + 1);
        });

        effectMap.put("GRATI_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: GRATI_UNSET_FLAG");
            context.setGrati_flag(context.getGrati_flag() - 1);
        });

        effectMap.put("FOUR_FINGER_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FOUR_FINGER_SET_FLAG");
            context.setFour_finger_flag(context.getFour_finger_flag() + 1);
        });

        effectMap.put("FOUR_FINGER_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FOUR_FINGER_UNSET_FLAG");
            context.setFour_finger_flag(context.getFour_finger_flag() - 1);
        });

        effectMap.put("FREE_ROLL_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FREE_ROLL_SET_FLAG");
            context.setFree_roll_flag(context.getFree_roll_flag() + 1);
        });

        effectMap.put("FREE_ROLL_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FREE_ROLL_UNSET_FLAG");
            context.setFree_roll_flag(context.getFree_roll_flag() - 1);
        });

        effectMap.put("DEPT_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: DEPT_SET_FLAG");
            context.setDebt_flag(context.getDebt_flag() + 1);
        });

        effectMap.put("DEPT_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: DEPT_UNSET_FLAG");
            context.setDebt_flag(context.getDebt_flag() - 1);
        });

        effectMap.put("ALL_COUNT_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_COUNT_SET_FLAG");
            context.setAll_count_flag(context.getAll_count_flag() + 1);
        });

        effectMap.put("ALL_COUNT_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_COUNT_UNSET_FLAG");
            context.setAll_count_flag(context.getAll_count_flag() - 1);
        });

        effectMap.put("ALL_FACE_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_FACE_SET_FLAG");
            context.setAll_face_flag(context.getAll_face_flag() + 1);
        });

        effectMap.put("ALL_FACE_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_FACE_UNSET_FLAG");
            context.setAll_face_flag(context.getAll_face_flag() - 1);
        });
        //endregion

        // weitere Effekte hier registrieren...
    }

    private static Suit getSuit(String suitString) {
        switch (suitString) {
            case "HEARTS": return Suit.HEARTS;
            case "SPADES": return Suit.SPADES;
            case "CLUBS": return Suit.CLUBS;
            case "DIAMONDS": return Suit.DIAMONDS;
        }
        return Suit.NO_SUIT;
    }

    public static JokerEffect getEffect(String effectKey) {
        return effectMap.get(effectKey);
    }

    private static void createCardNode(Card card, ObservableMap<AnchorPane, CardViewController> map) {
        try {
            FXMLLoader loader = new FXMLLoader(JokerEffectRegistry.class.getResource("/com/example/balatro/card.fxml"));
            AnchorPane cardPane = loader.load();

            CardViewController controller = loader.getController();
            controller.setData(card);
            controller.setInShop(true);

            map.put(cardPane,controller);
        } catch (IOException e) {
            e.printStackTrace();
            //return new Label("Error loading card");
        }
    }
}
