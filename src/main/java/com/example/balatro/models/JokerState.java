package com.example.balatro.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.EnumMap;

public class JokerState {

    public enum JokerType { CLOUD9_FLAG, DOUBLE_CHANCE_FLAG, MOON_FLAG, ASTRONOMER_FLAG, BONES_FLAG, BOSS_DISABLE_FLAG, GRATI_FLAG, FOUR_FINGER_FLAG, FREE_ROLL_FLAG, DEBT_FLAG, ALL_COUNT_FLAG, ALL_FACE_FLAG, }

    private final EnumMap<JokerType, IntegerProperty> jokerFlags =
            new EnumMap<>(JokerType.class);

    public IntegerProperty jokerFlagProperty(JokerType v) {
        return jokerFlags.computeIfAbsent(v, k -> new SimpleIntegerProperty(0));
    }
    public int hasJoker(JokerType v) { return jokerFlagProperty(v).get(); }
    public void setJoker(JokerType v, int value) { jokerFlagProperty(v).set(value); }
    public void incrementJoker(JokerType v) { jokerFlagProperty(v).set(jokerFlags.get(v).get() + 1); }
    public void decrementJoker(JokerType v) { jokerFlagProperty(v).set(jokerFlags.get(v).get() - 1); }

}
