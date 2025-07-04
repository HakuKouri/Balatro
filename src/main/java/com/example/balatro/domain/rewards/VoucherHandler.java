package com.example.balatro.domain.rewards;

import com.example.balatro.domain.card.Card;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.VoucherState;

public class VoucherHandler {

    private static GameModel gameModel;

    public static void initializeVoucherHandler(GameModel model) {
        gameModel = model;
    }

    public static void setVoucherFlag(Card card) {
        switch (card.getCardName()){
            case "Overstock": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OVERSTOCK, true);
                gameModel.getShopModel().maxItemsProperty().set(3);
                activeVoucherUpgrade(0);
                break;
            case "Clearance Sale": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.CLEARANCE_SALE, true);
                gameModel.getShopModel().shopPricesProperty().set(0.75);
                activeVoucherUpgrade(1);
                break;
            case "Hone": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.HONE, true);
                gameModel.getShopModel().editionChanceMultiplierProperty().set(2);
                activeVoucherUpgrade(2);
                break;
            case "Reroll Surplus": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.REROLL_SURPLUS, true);
                gameModel.getShopModel().rerollPriceProperty().set(3);
                activeVoucherUpgrade(3);
                break;
            case "Crystal Ball": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.CRYSTAL_BALL, true);
                gameModel.getRunState().maxConsumablesProperty().set(3);
                activeVoucherUpgrade(4);
                break;
            case "Telescope": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.TELESCOPE, true);
                //TODO BOOSTER PACK OPENER
                activeVoucherUpgrade(5);
                break;
            case "Grabber": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.GRABBER, true);
                gameModel.getRunState().maxHandsProperty().set(gameModel.getRunState().getMaxHands() + 1);
                activeVoucherUpgrade(6);
                break;
            case "Wasteful": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.WASTEFUL, true);
                gameModel.getRunState().setMaxDiscards(gameModel.getRunState().getMaxDiscards() + 1);
                activeVoucherUpgrade(7);
                break;
            case "Tarot Merchant": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.TAROT_MERCHANT, true);
                activeVoucherUpgrade(8);
                break;
            case "Planet Merchant": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PLANET_MERCHANT, true);
                activeVoucherUpgrade(9);
                break;
            case "Seed Money": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.SEED_MONEY, true);
                gameModel.getShopModel().maxInterestProperty().set(10);
                activeVoucherUpgrade(10);
                break;
            case "Blank": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.BLANK, true);
                activeVoucherUpgrade(11);
                break;
            case "Magic Trick": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.MAGIC_TRICK, true);
                activeVoucherUpgrade(12);
                break;
            case "Hieroglyph": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.HIEROGLYPH, true);
                gameModel.getRunState().anteProperty().set(gameModel.getRunState().getAnte() - 1);
                gameModel.getRunState().maxHandsProperty().set(gameModel.getRunState().getMaxHands() - 1);
                activeVoucherUpgrade(13);
                break;
            case "Director's Cut": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.DIRECTORS_CUT, true);
                //TODO REROLL BOSS BUTTON EINFÜGEN + ANTE RESET
                activeVoucherUpgrade(14);
                break;
            case "Paint Brush": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PAINTBRUSH, true);
                gameModel.getRunState().setMaxHandSize(gameModel.getRunState().getMaxHandSize() + 1);
                activeVoucherUpgrade(15);
                break;
            case "Overstock Plus": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OVERSTOCK_PLUS, true);
                gameModel.getShopModel().maxItemsProperty().set(4);
                upgradeBrought(16);
                break;
            case "Liquidation": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.LIQUIDATION, true);
                gameModel.getShopModel().shopPricesProperty().set(0.5);
                upgradeBrought(17);
                break;
            case "Glow Up": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.GLOW_UP, true);
                gameModel.getShopModel().editionChanceMultiplierProperty().set(2);
                upgradeBrought(18);
                break;
            case "Reroll Glut": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.REROLL_GLUT, true);
                gameModel.getShopModel().rerollPriceProperty().set(1);
                upgradeBrought(19);
                break;
            case "Omen Globe": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OMEN_GLOBE, true);
                upgradeBrought(20);
                break;
            case "Observatory": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.OBSERVATORY, true);
                upgradeBrought(21);
                break;
            case "Nacho Tong": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.NACHO_TONG, true);
                gameModel.getRunState().maxHandsProperty().set(gameModel.getRunState().getMaxHands() + 1);
                upgradeBrought(22);
                break;
            case "Recyclomancy": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.RECYCLOMANCY, true);
                gameModel.getRunState().setMaxDiscards(gameModel.getRunState().getMaxDiscards() + 1);
                upgradeBrought(23);
                break;
            case "Tarot Tycoon": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.TAROT_TYCOON, true);
                upgradeBrought(24);
                break;
            case "Planet Tycoon": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PLANET_TYCOON, true);
                upgradeBrought(25);
                break;
            case "Money Tree": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.MONEY_TREE, true);
                gameModel.getShopModel().maxInterestProperty().set(20);
                upgradeBrought(26);
                break;
            case "Antimatter": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.ANTIMATTER, true);
                gameModel.getRunState().maxJokersProperty().set(gameModel.getRunState().getMaxJokers() + 1);
                upgradeBrought(27);
                break;
            case "Illusion": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.ILLUSION, true);
                upgradeBrought(28);
                break;
            case "Petroglyph": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PETROGLYPH, true);
                gameModel.getRunState().anteProperty().set(gameModel.getRunState().getAnte() - 1);
                gameModel.getRunState().setMaxDiscards(gameModel.getRunState().getMaxDiscards() - 1);
                upgradeBrought(29);
                break;
            case "Retcon": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.RETCON, true);
                upgradeBrought(30);
                break;
            case "Palette": gameModel.getVoucherState().setVoucher(VoucherState.VoucherType.PALETTE, true);
                gameModel.getRunState().maxHandSizeProperty().set(gameModel.getRunState().getMaxHandSize() + 1);
                upgradeBrought(31);
                break;
        }
    }

    public static void activeVoucherUpgrade(int index) {
        gameModel.getAllVoucherList().get(index).availableProperty().set(false);
        gameModel.getAllVoucherList().get(index + 16).availableProperty().set(true);
    }

    public static void upgradeBrought(int index) {
        gameModel.getAllVoucherList().get(index).availableProperty().set(false);
    }
}
