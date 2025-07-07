package com.example.balatro.domain.card;

import com.example.balatro.Balatro;
import com.example.balatro.domain.rewards.VoucherHandler;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.interfaces.PurchasableCard;
import com.example.balatro.models.GameModel;
import javafx.beans.property.*;
import javafx.scene.layout.AnchorPane;

public class Voucher extends Card implements PurchasableCard {

    @Override
    public void onPurchase(GameModel model) {
        //TODO
        VoucherHandler.setVoucherFlag(this);
        model.getShopModel().getVoucherCardViewManager().remove(this);
    }

    //region Properties
    private final StringProperty voucherEffect = new SimpleStringProperty("");
    private final StringProperty voucherUpgradeFrom = new SimpleStringProperty("");
    private final StringProperty voucherUnlockCondition = new SimpleStringProperty("");
    private final StringProperty voucherNotes = new SimpleStringProperty("");
    private final BooleanProperty available =  new SimpleBooleanProperty(false);
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

    public boolean isAvailable() {
        return available.get();
    }

    public BooleanProperty availableProperty() {
        return available;
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
        availableProperty().set(voucher.isAvailable());
    }

    public static void resetVoucher() {
        for (Voucher voucher : Balatro.getGameModel().getAllVoucherList()) {
            voucher.availableProperty().set(voucher.getCardId() >= 17);
        }
    }
    //endregion
}
