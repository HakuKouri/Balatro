package com.example.balatro.domain.effects;

import com.example.balatro.enums.JokerTrigger;

import java.util.List;

public class JokerEffectTrigger {
    private final JokerTrigger trigger;        // Enum statt String
    private final List<String> effectKeys;     // Effekte als Strings oder evtl. Enum später

    public JokerEffectTrigger(JokerTrigger trigger, List<String> effectKeys) {
        this.trigger = trigger;
        this.effectKeys = effectKeys;
    }

    // Getter
    public JokerTrigger getTrigger() {
        return trigger;
    }

    public List<String> getEffectKeys() {
        return effectKeys;
    }
}
