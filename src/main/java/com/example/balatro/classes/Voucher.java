package com.example.balatro.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Voucher extends Card
{
    //region Properties
    private final StringProperty voucherEffect = new SimpleStringProperty("");
    private final StringProperty voucherUpgradeFrom = new SimpleStringProperty("");
    private final StringProperty voucherUnlockCondition = new SimpleStringProperty("");
    private final StringProperty voucherNotes = new SimpleStringProperty("");
    //endregion

    //region Constructor
    public Voucher() {

    }
    //endregion

    //region Getter Setter
    public String getVoucherEffect() {
        return voucherEffect.get();
    }

    public StringProperty voucherEffectProperty() {
        return voucherEffect;
    }

    public void setVoucherEffect(String voucherEffect) {
        this.voucherEffect.set(voucherEffect);
    }

    public String getVoucherUpgradeFrom() {
        return voucherUpgradeFrom.get();
    }

    public StringProperty voucherUpgradeFromProperty() {
        return voucherUpgradeFrom;
    }

    public void setVoucherUpgradeFrom(String voucherUpgradeFrom) {
        this.voucherUpgradeFrom.set(voucherUpgradeFrom);
    }

    public String getVoucherUnlockCondition() {
        return voucherUnlockCondition.get();
    }

    public StringProperty voucherUnlockConditionProperty() {
        return voucherUnlockCondition;
    }

    public void setVoucherUnlockCondition(String voucherUnlockCondition) {
        this.voucherUnlockCondition.set(voucherUnlockCondition);
    }

    public String getVoucherNotes() {
        return voucherNotes.get();
    }

    public StringProperty voucherNotesProperty() {
        return voucherNotes;
    }

    public void setVoucherNotes(String voucherNotes) {
        this.voucherNotes.set(voucherNotes);
    }
    //endregion

    //region Functions
    public void setVoucher(Voucher voucher) {
        setCardId(voucher.getCardId());
        setCardImageUrl(voucher.getCardImageUrl());
        setCardName(voucher.getCardName());
        setCardCost(voucher.getCardCost());
        setCardType(voucher.getCardType());
        setVoucherEffect(voucher.getVoucherEffect());
        setVoucherUpgradeFrom(voucher.getVoucherUpgradeFrom());
        setVoucherUnlockCondition(voucher.getVoucherUnlockCondition());
        setVoucherNotes(voucher.getVoucherNotes());
        setCardType(voucher.getCardType());
    }
    //endregion
}
