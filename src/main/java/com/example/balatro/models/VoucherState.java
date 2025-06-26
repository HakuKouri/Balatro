package com.example.balatro.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.EnumMap;

public class VoucherState {

    public enum VoucherType {
        OVERSTOCK, OVERSTOCK_PLUS, TAROT_MERCHANT, TAROT_TYCOON, PLANET_MERCHANT, PLANET_TYCOON, MAGIC_TRICK, ILLUSION, HONE, GLOW_UP, CLEARANCE_SALE, LIQUIDATION, REROLL_SURPLUS, REROLL_GLUT, CRYSTAL_BALL, OMEN_GLOBE, TELESCOPE, OBSERVATORY, GRABBER, NACHO_TONG, WASTEFUL, RECYCLOMANCY, SEED_MONEY, MONEY_TREE, BLANK, ANTIMATTER, HIEROGLYPH, PETROGLYPH, DIRECTORS_CUT, RETCON, PAINTBRUSH, PALETTE
    }

    private final EnumMap<VoucherType, BooleanProperty> voucherFlags =
            new EnumMap<>(VoucherType.class);

    public BooleanProperty voucherFlagProperty(VoucherType v) {
        return voucherFlags.computeIfAbsent(v, k -> new SimpleBooleanProperty(false));
    }

    public boolean hasVoucher(VoucherType v) {
        return voucherFlagProperty(v).get();
    }

    public void setVoucher(VoucherType v, boolean value) {
        voucherFlagProperty(v).set(value);
    }

}