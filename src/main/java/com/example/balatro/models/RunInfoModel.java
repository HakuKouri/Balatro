package com.example.balatro.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RunInfoModel {
    //region PROPERTIES
    public IntegerProperty hands = new SimpleIntegerProperty();
    public IntegerProperty discards = new SimpleIntegerProperty();
    public IntegerProperty money = new SimpleIntegerProperty();
    public IntegerProperty ante = new SimpleIntegerProperty();
    public IntegerProperty round = new SimpleIntegerProperty();
    //endregion

    public RunInfoModel() {
        ante.set(1);
        round.set(1);
        money.set(0);
        hands.set(4);
        discards.set(3);
    }

    //region GETTER SETTER
    public int getHands() {
        return hands.get();
    }

    public IntegerProperty handsProperty() {
        return hands;
    }

    public void setHands(int hands) {
        this.hands.set(hands);
    }

    public int getDiscards() {
        return discards.get();
    }

    public IntegerProperty discardsProperty() {
        return discards;
    }

    public void setDiscards(int discards) {
        this.discards.set(discards);
    }

    public int getMoney() {
        return money.get();
    }

    public IntegerProperty moneyProperty() {
        return money;
    }

    public void setMoney(int money) {
        this.money.set(money);
    }

    public int getAnte() {
        return ante.get();
    }

    public IntegerProperty anteProperty() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante.set(ante);
    }

    public int getRound() {
        return round.get();
    }

    public IntegerProperty roundProperty() {
        return round;
    }

    public void setRound(int round) {
        this.round.set(round);
    }
    //endregion
}
