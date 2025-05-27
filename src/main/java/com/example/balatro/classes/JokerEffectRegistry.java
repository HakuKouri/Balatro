package com.example.balatro.classes;

import com.example.balatro.interfaces.JokerEffect;

import java.util.HashMap;
import java.util.Map;

public class JokerEffectRegistry {
    private static final Map<String, JokerEffect> effectMap = new HashMap<>();

    static {
        // Hier registrierst du alle Effekte über ihre Keys
        effectMap.put("hearts_mult", (context, self, cards) -> {
            for (PlayingCard c : cards) {
                if (c.getSuit() == Suit.HEARTS) {
                    context.addToMultiplier(3);
                }
            }
        });

        effectMap.put("flat_15_mult_end_round", (context, self, cards) -> {
            context.addToMultiplier(15);
        });

        // weitere Effekte hier registrieren...
    }

    public static JokerEffect getEffect(String effectKey) {
        return effectMap.get(effectKey);
    }
}
