package com.example.balatro.models;

import com.example.balatro.classes.Joker;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardModel {

    private final Map<Joker, Integer> rocketList = new HashMap<>();
    private final IntegerProperty cloud9 = new SimpleIntegerProperty();
    private final int goldJokerReward = 4;
    private final IntegerProperty interestReward = new SimpleIntegerProperty();


    public Map<Joker, Integer> getRocketList() {
        return rocketList;
    }

    public void addRocketToList(Joker rocket) {
        this.rocketList.put(rocket, 1);
    }

    //Cloud9
    public int getCloud9() {
        return cloud9.get();
    }

    public IntegerProperty cloud9Property() {
        return cloud9;
    }

    public void setCloud9(int cloud9) {
        this.cloud9.set(cloud9);
    }

    public int getGoldJokerReward() {
        return goldJokerReward;
    }

    public int getInterestReward() {
        return interestReward.get();
    }

    public IntegerProperty interestRewardProperty() {
        return interestReward;
    }
}
