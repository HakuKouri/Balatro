package com.example.balatro.classes;

import com.example.balatro.interfaces.JokerEffect;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.HashMap;
import java.util.Map;

public class JokerEffectRegistry {
    private static final Map<String, JokerEffect> effectMap = new HashMap<>();

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
        });

        effectMap.put("ADD_STONE_CARD", (context, self, cards, params) -> {
            System.out.println("Trigger: ADD_STONE_CARD");
        });

        effectMap.put("BEAN_SET_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: BEAN_SET_VALUE");
        });

        effectMap.put("BEAN_SUB_VALUE", (context, self, cards, params) -> {
            System.out.println("Trigger: BEAN_SUB_VALUE");
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
            System.out.println("Multiplier: " + ((String)params.get("multiplier")).split(" ")[0].substring(1));
            context.getBestHand().addMult(Integer.parseInt(((String)params.get("multiplier")).split(" ")[0].substring(1)));
        });
        effectMap.put("MULT_MULT", (context, self, cards, params) -> {
            System.out.println("Trigger: MULT_MULT");
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
            System.out.println("Trigger: SUIT_MULT_ADD");
            System.out.println("Applying SUIT_MULT_ADD with params: " + params);
            System.out.println("Multiplier before: " + context.getBestHand().getMulti());

            if(cards.get(0).getSuit() == getSuit(params.get("suit").toString())) {
                System.out.println("SUIT MATCHES");

                String rawMultiplier = (String) params.get("multiplier");
                int value = Integer.parseInt(rawMultiplier.replace(" Mult", ""));
                context.getBestHand().addMult(value);
            }

            System.out.println("Multiplier after: " + context.getBestHand().getMulti());
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
        });

        effectMap.put("ANCIENT_JOKER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: ANCIENT_JOKER_EFFECT");
        });

        effectMap.put("BARON_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BARON_EFFECT");
        });

        effectMap.put("BASEBALL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BASEBALL_EFFECT");
        });

        effectMap.put("BLACKBOARD_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BLACKBOARD_EFFECT");
        });

        effectMap.put("BLOODSTONE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BLOODSTONE_EFFECT");
        });

        effectMap.put("BLUEPRINT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BLUEPRINT_EFFECT");
        });

        effectMap.put("BOOTSTRAPS_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BOOTSTRAPS_EFFECT");
        });

        effectMap.put("BRAINSTORM_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BRAINSTORM_EFFECT");
        });

        effectMap.put("BULL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BULL_EFFECT");
        });

        effectMap.put("BURGLAR_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BURGLAR_EFFECT");
        });

        effectMap.put("BURNT_JOKER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: BURNT_JOKER_EFFECT");
        });

        effectMap.put("CARD_SHARP_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: CARD_SHARP_EFFECT");
        });

        effectMap.put("CERTIFICATE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: CERTIFICATE_EFFECT");
        });

        effectMap.put("DAGGER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DAGGER_EFFECT");
        });

        effectMap.put("DIET_COLA_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DIET_COLA_EFFECT");
        });

        effectMap.put("DNA_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DNA_EFFECT");
        });

        effectMap.put("DRIVERS_LICENSE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: DRIVERS_LICENSE_EFFECT");
        });

        effectMap.put("EIGHTBALL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: EIGHTBALL_EFFECT");
        });

        effectMap.put("EROSION_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: EROSION_EFFECT");
        });

        effectMap.put("FLOWERPOT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: FLOWERPOT_EFFECT");
        });

        effectMap.put("GIFT_CARD_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: GIFT_CARD_EFFECT");
        });

        effectMap.put("HALLUCINATION_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: HALLUCINATION_EFFECT");
        });

        effectMap.put("HANGING_CHAD_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: HANGING_CHAD_EFFECT");
        });

        effectMap.put("HOLOGRAM_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: HOLOGRAM_EFFECT");
        });

        effectMap.put("ICECREAM_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: ICECREAM_EFFECT");
        });

        effectMap.put("INVISIBLE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: INVISIBLE_EFFECT");
        });

        effectMap.put("LOYALTY_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: LOYALTY_EFFECT");
        });

        effectMap.put("LUCHADOR_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: LUCHADOR_EFFECT");
        });

        effectMap.put("MATADOR_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MATADOR_EFFECT");
        });

        effectMap.put("MERRY_BUY_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MERRY_BUY_EFFECT");
        });

        effectMap.put("MERRY_SELL_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MERRY_SELL_EFFECT");
        });

        effectMap.put("MIDAS_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MIDAS_EFFECT");
        });

        effectMap.put("MINE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MINE_EFFECT");
        });

        effectMap.put("MISPRINT_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MISPRINT_EFFECT");
        });

        effectMap.put("MADNESS_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: MADNESS_EFFECT");
        });

        effectMap.put("OBELISK_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: OBELISK_EFFECT");
        });

        effectMap.put("PERKEO_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: PERKEO_EFFECT");
        });

        effectMap.put("PHOTOGRAPH_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: PHOTOGRAPH_EFFECT");
        });

        effectMap.put("RESERVED_PARKING_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RESERVED_PARKING_EFFECT");
        });

        effectMap.put("RAISED_FIST_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RAISED_FIST_EFFECT");
        });

        effectMap.put("RIFF_RAFF_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RIFF_RAFF_EFFECT");
        });

        effectMap.put("RUNNER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: RUNNER_EFFECT");
        });

        effectMap.put("STUNTMAN_BUY_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: STUNTMAN_BUY_EFFECT");
        });

        effectMap.put("STUNTMAN_SALE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: STUNTMAN_SALE_EFFECT");
        });

        effectMap.put("SQUARE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SQUARE_EFFECT");
        });

        effectMap.put("SEANCE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SEANCE_EFFECT");
        });

        effectMap.put("SEEING_DOUBLE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SEEING_DOUBLE_EFFECT");
        });

        effectMap.put("SELTZER_TRIGGER_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SELTZER_TRIGGER_EFFECT");
        });

        effectMap.put("SELTZER_REDUCE_EFFECT", (context, self, cards, params) -> {
            System.out.println("Trigger: SELTZER_REDUCE_EFFECT");
        });


        //FLAGS
        effectMap.put("SET_CLOUD9_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_CLOUD9_FLAG");
        });

        effectMap.put("UNSET_CLOUD9_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_CLOUD9_FLAG");
        });

        effectMap.put("SET_DOUBLE_CHANCE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_DOUBLE_CHANCE_FLAG");
        });

        effectMap.put("UNSET_DOUBLE_CHANCE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_DOUBLE_CHANCE_FLAG");
        });

        effectMap.put("SET_MOON_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_MOON_FLAG");
        });

        effectMap.put("UNSET_MOON_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_MOON_FLAG");
        });

        effectMap.put("SET_ASTRONOMER_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_ASTRONOMER_FLAG");
        });

        effectMap.put("UNSET_ASTRONOMER_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_ASTRONOMER_FLAG");
        });

        effectMap.put("SET_BONES_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_BONES_FLAG");
        });

        effectMap.put("UNSET_BONES_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_BONES_FLAG");
        });

        effectMap.put("SET_BOSS_DISABLE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: SET_BOSS_DISABLE_FLAG");
        });

        effectMap.put("UNSET_BOSS_DISABLE_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: UNSET_BOSS_DISABLE_FLAG");
        });

        effectMap.put("GRATI_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: GRATI_SET_FLAG");
        });

        effectMap.put("GRATI_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: GRATI_UNSET_FLAG");
        });

        effectMap.put("FOUR_FINGER_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FOUR_FINGER_SET_FLAG");
        });

        effectMap.put("FOUR_FINGER_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ");
        });

        effectMap.put("FREE_ROLL_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FREE_ROLL_SET_FLAG");
        });

        effectMap.put("FREE_ROLL_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: FREE_ROLL_UNSET_FLAG");
        });

        effectMap.put("DEPT_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: DEPT_SET_FLAG");
        });

        effectMap.put("DEPT_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: DEPT_UNSET_FLAG");
        });

        effectMap.put("ALL_COUNT_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_COUNT_SET_FLAG");
        });

        effectMap.put("ALL_COUNT_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_COUNT_UNSET_FLAG");
        });

        effectMap.put("ALL_FACE_SET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_FACE_SET_FLAG");
        });

        effectMap.put("ALL_FACE_UNSET_FLAG", (context, self, cards, params) -> {
            System.out.println("Trigger: ALL_FACE_UNSET_FLAG");
        });

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
}
