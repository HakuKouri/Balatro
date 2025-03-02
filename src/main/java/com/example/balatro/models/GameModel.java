package com.example.balatro.models;

import com.example.balatro.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

    //LISTS
    private final List<Tag> allTagList = SqlHandler.getAllTags();
    private final List<Hand> allHandList = Hand.setHandList();
    private final List<Blind> allBlindsList = SqlHandler.getAllBlinds();
    private final List<PlayingCard> deckFull = new ArrayList<>();
    private final List<PlayingCard> deckToPlay = new ArrayList<>();
    private final List<Tag> blindTags = new ArrayList<>();
    private final List<Tag> tagQueueList = new ArrayList<>();
    private ObservableList<Tag> tagStack = FXCollections.observableArrayList();

    //GAME SETTINGS VAR
    private Deck chosenDeck = new Deck();
    private Stake chosenStake = new Stake();

    static BigDecimal scoreToReach = new BigDecimal(0);
    static BigDecimal[] chipRequirement = new BigDecimal[]{BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800), BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000), BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};
    public BigDecimal pointsScoredProperty = new BigDecimal(0);

    final int maxAnte = 8;
    private int ante = 0;
    private int round = 1;
    private int money = 0;
    private int handsPlayed = 0;
    private int handsDiscarded = 0;

    int handsize = 8;

    public int getMaxAnte() {
        return maxAnte;
    }

    public ObservableList<Tag> getTagStack() {
        return tagStack;
    }

    public void addTagToStack(Tag tag) {
        tagStack.add(tag);
    }

    public void removeTagFromStack(Tag tag) {
        tagStack.remove(tag);
    }


    //GETTER SETTER
    public Deck getChosenDeck() {
        return chosenDeck;
    }

    public void setChosenDeck(Deck chosenDeck) {
        this.chosenDeck = chosenDeck;
    }

    public Stake getChosenStake() {
        return chosenStake;
    }

    public void setChosenStake(Stake chosenStake) {
        this.chosenStake = chosenStake;
    }

    public List<PlayingCard> getDeckFull() {
        return deckFull;
    }

    public List<PlayingCard> getDeckToPlay() {
        return deckToPlay;
    }

    public boolean scoreReached() {
        return pointsScoredProperty.compareTo(scoreToReach) != -1;
    }

    public BigDecimal getScoreToReach() {
        return scoreToReach;
    }

    public void setScoreToReach(BigDecimal scoreToReach) {
        GameModel.scoreToReach = scoreToReach;
    }

    public List<Tag> getBlindTags() {
        return blindTags;
    }

    public List<Tag> getTagQueueList() {
        return tagQueueList;
    }

    public void setTagStack(ObservableList<Tag> tagStack) {
        this.tagStack = tagStack;
    }

    public BigDecimal getPointsScoredProperty() {
        return pointsScoredProperty;
    }

    public void setPointsScoredProperty(BigDecimal pointsScoredProperty) {
        this.pointsScoredProperty = pointsScoredProperty;
    }

    public int getAnte() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante = ante;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHandsPlayed() {
        return handsPlayed;
    }

    public void setHandsPlayed(int handsPlayed) {
        this.handsPlayed = handsPlayed;
    }

    public int getHandsDiscarded() {
        return handsDiscarded;
    }

    public void setHandsDiscarded(int handsDiscarded) {
        this.handsDiscarded = handsDiscarded;
    }

    public int getHandsize() {
        return handsize;
    }

    public void setHandsize(int handsize) {
        this.handsize = handsize;
    }

    public List<Hand> getAllHandList() {
        return allHandList;
    }

    public List<Tag> getAllTagList() {
        return allTagList;
    }

    public List<Blind> getAllBlindsList() {
        return allBlindsList;
    }

    public BigDecimal[] getChipRequirement() {
        return chipRequirement;
    }

    public void setChipRequirement(BigDecimal[] chipRequirement) {
        GameModel.chipRequirement = chipRequirement;
    }
}
