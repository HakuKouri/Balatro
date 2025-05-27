package com.example.balatro.classes;

import java.util.List;

public class JokerEffectTrigger {
    private JokerTrigger trigger;        // Enum statt String
    private List<String> effectKeys;     // Effekte als Strings oder evtl. Enum später

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
