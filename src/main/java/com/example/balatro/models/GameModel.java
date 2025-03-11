package com.example.balatro.models;

import com.example.balatro.classes.*;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameModel {

    //region LISTS
    private final List<Tag> allTagList = SqlHandler.getAllTags();
    private final List<Hand> allHandList = Hand.setHandList();
    private final List<Blind> allBlindsList = SqlHandler.getAllBlinds();
    private final List<PlayingCard> deckFull = new ArrayList<>();
    private final List<PlayingCard> deckToPlay = new ArrayList<>();
    private final List<Blind> runBlinds = new ArrayList<>();
    private final List<Tag> blindTags = new ArrayList<>();
    private final ObservableList<Tag> tagQueue = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> handCards = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> selectedCards = FXCollections.observableArrayList();
    //endregion

    //region GAME SETTINGS VAR
    private final ObjectProperty<Deck> chosenDeck = new SimpleObjectProperty<>(new Deck());
    private final ObjectProperty<Stake> chosenStake = new SimpleObjectProperty<>();
    private Random rand;
    //endregion

    //region HAND POINTS VAR
    private final ObjectProperty<Hand> bestHand = new SimpleObjectProperty<>(new Hand());
    private final StringProperty handName = new SimpleStringProperty();
    private final IntegerProperty handLevel = new SimpleIntegerProperty();
    private final IntegerProperty handChips = new SimpleIntegerProperty();
    private final DoubleProperty handMultiplier = new SimpleDoubleProperty();
    //endregion

    //region HOLDING HAND VAR
    private final IntegerProperty selectedCardCounter = new SimpleIntegerProperty(0);
    private final BooleanProperty sortedByRank = new SimpleBooleanProperty(true);
    private final IntegerProperty handSize = new SimpleIntegerProperty(8);
    private final StringProperty stakeChipImageUrl = new SimpleStringProperty("file:src/main/resources/com/images/Stakechips/stake_chip_1.png");
    //endregion

    //region RUN INFO VAR
    private final IntegerProperty maxHands = new SimpleIntegerProperty(4);
    private final IntegerProperty hands = new SimpleIntegerProperty(4);
    private final IntegerProperty maxDiscards = new SimpleIntegerProperty(3);
    private final IntegerProperty discards = new SimpleIntegerProperty(3);
    private final IntegerProperty money = new SimpleIntegerProperty(3);
    private final IntegerProperty ante = new SimpleIntegerProperty(0);
    private final IntegerProperty round = new SimpleIntegerProperty(1);
    private final IntegerProperty handsPlayed = new SimpleIntegerProperty(0);
    private final IntegerProperty handsDiscarded = new SimpleIntegerProperty(0);
    //endregion

    //region UI VAR
    private final BooleanProperty blindsVisiblity = new SimpleBooleanProperty(false);
    private final BooleanProperty shopVisibility = new SimpleBooleanProperty(false);
    private final BooleanProperty rewardVisibility = new SimpleBooleanProperty(false);
    //endregion

    //region POINTS VAR
    private final BooleanProperty pointsReached = new SimpleBooleanProperty(false);
    private final ObjectProperty<BigDecimal> scoredPoints = new SimpleObjectProperty<>(new BigDecimal(0));
    private final ObjectProperty<BigDecimal> scoreToReach = new SimpleObjectProperty<>(new BigDecimal(0));
    private final BigDecimal[] chipRequirement = new BigDecimal[]{
            BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800),
            BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000),
            BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};
    public ObjectProperty<BigDecimal> pointsScoredProperty = new SimpleObjectProperty<>();
    //endregion


    //GETTER SETTER
    //region All Data Lists
    public List<Hand> getAllHandList() {
        return allHandList;
    }

    public List<Tag> getAllTagList() {
        return allTagList;
    }

    public List<Blind> getAllBlindsList() {
        return allBlindsList;
    }
    //endregion

    //region Full Deck
    public List<PlayingCard> getDeckFull() {
        return deckFull;
    }

    public void setDeckFull(List<PlayingCard> deck) {
        deckFull.clear();
        deckFull.addAll(deck);
    }

    public void addCardToDeckFull(PlayingCard card) {
        deckFull.add(card);
    }

    public void removeCardFromDeckFull(PlayingCard card) {
        deckFull.remove(card);
    }
    //endregion

    //region Deck to Play with
    public List<PlayingCard> getDeckToPlay() {
        return deckToPlay;
    }

    public void setDeckToPlay(List<PlayingCard> deck) {
        deckToPlay.clear();
        deckToPlay.addAll(deck);
    }

    public void shuffleDeck() {
        Collections.shuffle(deckToPlay);
    }

    public void addCardToDeckToPlay(PlayingCard card) {
        deckToPlay.add(card);
    }

    public void addCardToDeckToPlay(PlayingCard card, int index) {
        deckToPlay.add(index, card);
    }
    //endregion

    //region Blinds
    public List<Blind> getRunBlinds() {
        return runBlinds;
    }

    public void setRunBlinds(List<Blind> blindList) {
        runBlinds.clear();
        runBlinds.addAll(blindList);
    }
    //endregion

    //region Tags for the Run
    public List<Tag> getBlindTags() {
        return blindTags;
    }

    public void setBlindTags(List<Tag> tags) {
        blindTags.clear();
        blindTags.addAll(tags);
    }
    //endregion

    //region Tag Queue
    public ObservableList<Tag> getTagQueue() {
        return tagQueue;
    }

    public Tag getNextTagFromQueue() {
        return tagQueue.get(0);
    }

    public void setTagQueue(List<Tag> queueTags) {
        tagQueue.clear();
        tagQueue.addAll(queueTags);
    }

    public void addTagToTagQueue(Tag tag) {
        tagQueue.add(tag);
    }

    public void removeTagFromTagQueue(Tag tag) {
        tagQueue.remove(tag);
    }

    public void removeNextTagFromTagQueue() {
        tagQueue.remove(0);
    }
    //endregion

//    private final ObservableList<Tag> tagStack = FXCollections.observableArrayList();
    //TODO


    //region GAME SETTINGS VAR
    //Chosen Deck
    public Deck getChosenDeck() {
        return chosenDeck.get();
    }

    public ObjectProperty<Deck> chosenDeckProperty() {
        return chosenDeck;
    }

    public void setChosenDeck(Deck chosenDeck) {
        this.chosenDeck.set(chosenDeck);
    }

    //Chosen Stake
    public Stake getChosenStake() {
        return chosenStake.get();
    }

    public ObjectProperty<Stake> chosenStakeProperty() {
        return chosenStake;
    }

    public void setChosenStake(Stake chosenStake) {
        this.chosenStake.set(chosenStake);
    }

    //Random Seed
    public Random getRand() {
        return rand;
    }

    public void setRand(Random random) {
        rand = random;
    }
    //endregion

    //region HAND POINTS VAR
    //Best Hand
    public Hand getBestHand() {
        return bestHand.get();
    }

    public ObjectProperty<Hand> bestHandProperty() {
        return bestHand;
    }

    public void setBestHand(Hand bestHand) {
        this.bestHand.set(bestHand);
    }

    //region Hand Infos as String
    public ObservableValue<String> getBestHandName() {
        return new SimpleStringProperty(getBestHand().getName());
    }
    public ObservableValue<String> getBestHandLevel() {
        return new SimpleStringProperty(String.format("lv. %d", getBestHand().getLevel()));
    }
    public ObservableValue<String> getBestHandChips() {
        return new SimpleStringProperty(String.valueOf(getBestHand().getChips()));
    }
    public ObservableValue<String> getBestHandMult() {
        return new SimpleStringProperty(String.valueOf(getBestHand().getMulti()));
    }
    //endregion

    //Poker Hand Name
    public String getHandName() {
        return handName.get();
    }

    public StringProperty handNameProperty() {
        return handName;
    }

    public void setHandName(String handName) {
        this.handName.set(handName);
    }

    //Poker Hand Level
    public int getHandLevel() {
        return handLevel.get();
    }

    public IntegerProperty handLevelProperty() {
        return handLevel;
    }

    public void setHandLevel(int handLevel) {
        this.handLevel.set(handLevel);
    }

    //Poker Hand Chips
    public int getHandChips() {
        return handChips.get();
    }

    public IntegerProperty handChipsProperty() {
        return handChips;
    }

    public void setHandChips(int handChips) {
        this.handChips.set(handChips);
    }

    //Poker Hand Multiplyer
    public double getHandMultiplier() {
        return handMultiplier.get();
    }

    public DoubleProperty handMultiplierProperty() {
        return handMultiplier;
    }

    public void setHandMultiplier(int handMultiplier) {
        this.handMultiplier.set(handMultiplier);
    }
    //endregion

    //region HOLDING HAND VAR
    //Handcards
    public ObservableList<PlayingCard> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<PlayingCard> cards) {
        handCards.clear();
        handCards.addAll(cards);
    }

    public void addCardToHandCards(PlayingCard card) {
        handCards.add(card);
    }

    public void clearHandCards() {
        handCards.clear();
    }

    //Selected Cards
    public ObservableList<PlayingCard> getSelectedCards() {
        return selectedCards;
    }

    public void setSelectedCards(List<PlayingCard> cards) {
        selectedCards.clear();
        selectedCards.addAll(cards);
    }

    public void addCardToSelectedCards(PlayingCard card) {
        selectedCards.add(card);
    }

    public void removeCardFromSelectedCards(PlayingCard card) {
        selectedCards.remove(card);
    }

    //Selected Cards Counter
    public int getSelectedCardCounter() {
        return selectedCardCounter.get();
    }

    public IntegerProperty selectedCardCounterProperty() {
        return selectedCardCounter;
    }

    public void setSelectedCardCounter(int selectedCardCounter) {
        this.selectedCardCounter.set(selectedCardCounter);
    }

    //Sort Holding Hand Cards
    public boolean isSortedByRank() {
        return sortedByRank.get();
    }

    public BooleanProperty sortedByRankProperty() {
        return sortedByRank;
    }

    public void setSortedByRank(boolean bool) {
        sortedByRank.set(bool);
    }

    public void toggleSortedByRank() {
        setSortedByRank(!isSortedByRank());
    }

    //Hand Size
    public IntegerProperty handSizeProperty() {
        return handSize;
    }

    public void setHandSize(int size) {
        handSize.set(size);
    }

    public void increaseHandSizeBy(int add) {
        handSize.set(handSize.get() + add);
    }

    public void decreaseHandSizeBy(int sub) {
        handSize.set(handSize.get() + sub);
    }
    //endregion

    //region RUN INFO VAR
    //Hands per Round
    public int getMaxHands() {
        return maxHands.get();
    }

    public IntegerProperty maxHandsProperty() {
        return maxHands;
    }

    public void setMaxHands(int maxHands) {
        this.maxHands.set(maxHands);
    }

    //Hands left this Round
    public int getHands() {
        return hands.get();
    }

    public IntegerProperty handsProperty() {
        return hands;
    }

    public void setHands(int hands) {
        this.hands.set(hands);
    }

    //Discard per Round
    public int getMaxDiscards() {
        return maxDiscards.get();
    }

    public IntegerProperty maxDiscardsProperty() {
        return maxDiscards;
    }

    public void setMaxDiscards(int maxDiscards) {
        this.maxDiscards.set(maxDiscards);
    }

    //Discards Left this Round
    public int getDiscards() {
        return discards.get();
    }

    public IntegerProperty discardsProperty() {
        return discards;
    }

    public void setDiscards(int discards) {
        this.discards.set(discards);
    }

    //Money
    public int getMoney() {
        return money.get();
    }

    public IntegerProperty moneyProperty() {
        return money;
    }

    public void setMoney(int money) {
        this.money.set(money);
    }

    public void addMoney(int money) {
        setMoney(getMoney() + money);
    }

    //Aktueller Ante
    public int getAnte() {
        return ante.get();
    }

    public IntegerProperty anteProperty() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante.set(ante);
    }

    public void incrementAnte() {
        setAnte(getMaxAnte() + 1);
    }

    public void decrementAnte() {
        setAnte(getAnte() - 1);
    }

    //Aktuelle Round
    public int getRound() {
        return round.get();
    }

    public IntegerProperty roundProperty() {
        return round;
    }

    public void setRound(int round) {
        this.round.set(round);
    }

    //Max Ante
    public int getMaxAnte() {
        return 8;
    }

    //Counter für Played Hands
    public int getHandsPlayed() {
        return handsPlayed.get();
    }

    public IntegerProperty handsPlayedProperty() {
        return handsPlayed;
    }

    public void setHandsPlayed(int handsPlayed) {
        this.handsPlayed.set(handsPlayed);
    }

    public void incrementHandsPlayed() {
        setHandsPlayed(getHandsPlayed() + 1);
    }

    //Counter für Discarded Hands
    public int getHandsDiscarded() {
        return handsDiscarded.get();
    }

    public IntegerProperty handsDiscardedProperty() {
        return handsDiscarded;
    }

    public void setHandsDiscarded(int handsDiscarded) {
        this.handsDiscarded.set(handsDiscarded);
    }

    public void incrementHandsDiscarded() {
        setHandsDiscarded(getHandsDiscarded() + 1);
    }



    //endregion

    //region UI VAR
    //Blinds Visibility
    public boolean isBlindsVisiblity() {
        return blindsVisiblity.get();
    }

    public BooleanProperty blindsVisiblityProperty() {
        return blindsVisiblity;
    }

    public void setBlindsVisiblity(boolean blindsVisiblity) {
        this.blindsVisiblity.set(blindsVisiblity);
    }

    public void toggleBlindVisibity() {
        setBlindsVisiblity(!isBlindsVisiblity());
    }

    //Shop Visibility
    public boolean isShopVisibility() {
        return shopVisibility.get();
    }

    public BooleanProperty shopVisibilityProperty() {
        return shopVisibility;
    }

    public void setShopVisibility(boolean shopVisibility) {
        this.shopVisibility.set(shopVisibility);
    }

    public void toggleShopVisibility() {
        setShopVisibility(!isShopVisibility());
    }


    public boolean isRewardVisibility() {
        return rewardVisibility.get();
    }

    public BooleanProperty rewardVisibilityProperty() {
        return rewardVisibility;
    }

    public void setRewardVisibility(boolean rewardVisibility) {
        this.rewardVisibility.set(rewardVisibility);
    }

    public void toggleRewardVisibility() {
        setRewardVisibility(!isRewardVisibility());
    }

    //Difficulty Stake Image
    public String getStakeChipImageUrl() {
        return stakeChipImageUrl.get();
    }

    public StringProperty stakeChipImageUrlProperty() {
        return stakeChipImageUrl;
    }

    public void setStakeChipImageUrl(String stakeChipImageUrl) {
        this.stakeChipImageUrl.set(stakeChipImageUrl);
    }


    //endregion
    //Scored Points
    public BigDecimal getScoredPoints() {
        return scoredPoints.get();
    }

    public ObjectProperty<BigDecimal> scoredPointsProperty() {
        return scoredPoints;
    }

    public void setScoredPoints(BigDecimal points) {
        scoredPoints.set(points);
    }

    public void addToScoredPoints(BigDecimal scoredPoints) {
        setScoredPoints(getScoredPoints().add(scoredPoints));
    }


    //Score To Reach
    public BigDecimal getScoreToReach() {
        return scoreToReach.get();
    }

    public void setScoreToReach(BigDecimal scoreToReach) {
        this.scoreToReach.set(scoreToReach);
    }

    //Points Reached
    public boolean isPointsReached() {
        return pointsReached.get();
    }

    public BooleanProperty pointsReachedProperty() {
        return pointsReached;
    }

    //Chip Requirement
    public BigDecimal[] getChipRequirement() {
    return chipRequirement;
}

    public BigDecimal getChipRequirementByIndex(int index) {
        return getChipRequirement()[index];
    }


}
