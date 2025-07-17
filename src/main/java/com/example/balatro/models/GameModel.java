package com.example.balatro.models;

import com.example.balatro.controller.CardViewController;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.domain.util.CardViewManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.*;

public class GameModel {
    //region ATTRIBUTES

    //region Lists of All Data (Tag, Blind, Joker, Tarot. Planet, Booster, Voucher, PokerHand)
    private final List<Tag> allTagList = SqlHandler.getAllTags();
    private final List<Blind> allBlindsList = SqlHandler.getAllBlinds();
    private final List<Joker> allJokerList = SqlHandler.getAllJokers();
    private final List<Tarot> allTarotList = SqlHandler.getAllTarots();
    private final List<Planet> allPlanetList = SqlHandler.getAllPlanets();
    private final List<Booster> allBoosterList = SqlHandler.getAllBooster();
    private final List<Voucher> allVoucherList = SqlHandler.getAllVoucher();
    private final List<Spectral> allSpectralList = SqlHandler.getAllSpectrals();
    private final List<PokerHand> allPokerHandList = SqlHandler.getAllPokerHands();
    private final List<Edition> allEditionList = SqlHandler.getAllEditions();
    private final List<Enhancement> allEnhancementList = SqlHandler.getAllEnhancements();
    private final List<Seal> allSealList = SqlHandler.getAllSeals();
    private final List<Sticker> stickerList = SqlHandler.getAllStickers();
    //endregion

    //region Models
    private final JokerState jokerState = new JokerState();
    public JokerState getJokerState() {
        return jokerState;
    }

    private final VoucherState voucherState = new VoucherState();
    public VoucherState getVoucherState() {
        return voucherState;
    }

    private final RunState runState = new RunState();
    public RunState getRunState() {
        return runState;
    }

    private final RoundState currentRound = new RoundState();
    public RoundState getCurrentRound() {
        return currentRound;
    }

    private final ShopModel shopModel = new ShopModel();
    public ShopModel getShopModel() {
        return shopModel;
    }

    private final BoosterDrawModel boosterDrawModel = new BoosterDrawModel();
    public BoosterDrawModel getBoosterDrawModel() {
        return boosterDrawModel;
    }

    private final RewardModel rewardModel = new RewardModel();
    public RewardModel getRewardModel() {return rewardModel;}
    //endregion

    //region Run Lists
    private final ObservableList<Tag> tagQueue = FXCollections.observableArrayList();
    private final CardViewManager holdingHandViewManager = new CardViewManager(false, true, false);
    private final ObservableList<PlayingCard> selectedCards = FXCollections.observableArrayList();

    private final CardViewManager playCardsManager = new CardViewManager(false, false, false);
    private final ObservableList<PlayingCard> playedCards = FXCollections.observableArrayList();
    private final ObservableList<PokerHand> possiblePokerHand = FXCollections.observableArrayList();
    private final MapProperty<Joker, IntegerProperty> rocketJokers = new SimpleMapProperty<>();
    private final List<PokerHand> pokerHandList = getAllPokerHandList();
    //endregion

    //region GAME SETTINGS VAR
    private Random rand;
    private final ObjectProperty<Blind> activeBlind = new SimpleObjectProperty<>(new Blind());
    //endregion

    //region HAND POINTS VAR
    private final ObjectProperty<PokerHand> bestHand = new SimpleObjectProperty<>(new PokerHand());
    //endregion

    //region HOLDING HAND VAR
    private final BooleanProperty sortedByRank = new SimpleBooleanProperty(true);
    //endregion

    //region ACTIVE JOKERS
    private final CardViewManager jokerManager = new CardViewManager(true, true, true);
    private final ObservableMap<CardViewController, AnchorPane> activeJokerMap = FXCollections.observableMap(new LinkedHashMap<>());
    //endregion

    //region Collected Consumables
    private final CardViewManager consumableManager = new CardViewManager(true, true, true);
    //endregion

    //region
    private final CardViewManager playedCardsViewManager = new CardViewManager(true, false,false);
    //endregion

    //region RUN INFO VAR
    private Card lastConsumableUsed;
    //endregion

    //region Background Run Variables
    private final IntegerProperty beanValue = new SimpleIntegerProperty(0);
    //endregion

    //region UI VAR
    private final BooleanProperty pickedBlindVisibility = new SimpleBooleanProperty(false);
    private final BooleanProperty blindsVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty shopVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty rewardVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty handButtonVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty boosterOpeningVisibility = new SimpleBooleanProperty(true);
    //endregion

    //region POINTS VAR
    private final BooleanProperty pointsReached = new SimpleBooleanProperty(false);
    private final ObjectProperty<BigDecimal> scoredPoints = new SimpleObjectProperty<>(new BigDecimal(0));
    private final ObjectProperty<BigDecimal> scoreToReach = new SimpleObjectProperty<>(new BigDecimal(0));
    private final BigDecimal[] chipRequirement = new BigDecimal[]{
            BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800),
            BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000),
            BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};
    //endregion

    //region CONSTRUCTOR
    public GameModel() {
        scoredPoints.addListener((observable, oldValue, newValue) -> {
           pointsReachedProperty().set(newValue.compareTo(getScoreToReach()) > -1);
        });

        beanValue.addListener((observable, oldValue, newValue) -> {
            currentRound.setHands(runState.getMaxHands() +  getBeanValue());
        });
    }
    //endregion

    //region GETTER SETTER

    //region All Data Lists
    public List<PokerHand> getAllPokerHandList() {
        return allPokerHandList;
    }

    public List<Tag> getAllTagList() {
        return allTagList;
    }

    public List<Blind> getAllBlindsList() {
        return allBlindsList;
    }

    public List<Booster> getAllBoosterList() {
        return allBoosterList;
    }

    public List<Voucher> getAllVoucherList() {
        return allVoucherList;
    }

    public List<Tarot> getAllTarotList() {
        return allTarotList;
    }
    public Tarot getRandomTarot() {
        return allTarotList.get(rand.nextInt(allTarotList.size())).copy();
    }

    public List<Planet> getAllPlanetList() {
        return allPlanetList;
    }
    public Planet getRandomPlanet() {
        return allPlanetList.get(rand.nextInt(allPlanetList.size())).copy();
    }

    public List<Joker> getAllJokerList() {
        return allJokerList;
    }
    public Joker getRandomJoker() {
        return allJokerList.get(rand.nextInt(allJokerList.size())).copy();
    }

    public List<Spectral> getAllSpectralList() {
        return allSpectralList;
    }
    public Spectral getRandomSpectral() {
        return allSpectralList.get(rand.nextInt(allSpectralList.size())).copy();
    }

    public List<Edition> getAllEditionList() {
        return allEditionList;
    }
    public Edition getRandomEdition() {
        return allEditionList.get(rand.nextInt(allEditionList.size())).copy();
    }

    public List<Enhancement> getAllEnhancementList() {
        return allEnhancementList;
    }
    public Enhancement getRandomEnhancement() {
        return allEnhancementList.get(rand.nextInt(allEnhancementList.size())).copy();
    }

    public List<Seal> getAllSealList() {
        return allSealList;
    }
    public Seal getRandomSeal() {
        return allSealList.get(rand.nextInt(allSealList.size())).copy();
    }

    public List<PokerHand> getPokerHandList() {
        return pokerHandList;
    }

    public PokerHand getPokerHand(String handName) {
        return getAllPokerHandList().stream().filter(pokerHand -> pokerHand.getName().equals(handName)).findFirst().orElse(null);
    }

    public List<Sticker> getStickerList() {
        return stickerList;
    }

    //endregion

    //region Blinds
    public Blind getActiveBlind() {
        return activeBlind.get();
    }

    public ObjectProperty<Blind> activeBlindProperty() {
        return activeBlind;
    }

    public void setActiveBlind(Blind pickedBlind) {
        activeBlind.get().setBlind(pickedBlind);
    }
    //endregion

    //region Tag Queue
    public ObservableList<Tag> getTagQueue() {
        return tagQueue;
    }

    public Tag getNextTagFromQueue() {
        return tagQueue.get(0);
    }

    public void removeTagFromTagQueue(Tag tag) {
        tagQueue.remove(tag);
    }

    public void removeNextTagFromTagQueue() {
        tagQueue.removeFirst();
    }
    //endregion

    //region Played Cards
    public ObservableList<PlayingCard> getPlayedCards() {
        return playedCards;
    }
    //endregion

    //region Possible Poker Hands
    public ObservableList<PokerHand> getPossiblePokerHand() {
        return possiblePokerHand;
    }
    //endregion

    //region GAME SETTINGS VAR
    //Random Seed
    public Random getRand() {
        return rand;
    }

    public void setRand(Random random) {
        rand = random;
    }
    //endregion

    //region HAND POINTS VAR
    //Best Poker Hand
    public PokerHand getBestHand() {
        return bestHand.get();
    }

    public ObjectProperty<PokerHand> bestHandProperty() {
        return bestHand;
    }
    //endregion

    //region HOLDING HAND GS
    //Hand Cards
    public CardViewManager getHoldingHandViewManager() {
        return holdingHandViewManager;
    }

    //Selected Cards
    public ObservableList<PlayingCard> getSelectedCards() {
        return selectedCards;
    }

    public void addCardToSelectedCards(PlayingCard card) {
        selectedCards.add(card);
    }

    public void removeCardFromSelectedCards(PlayingCard card) {
        selectedCards.remove(card);
    }

    public void clearSelectedCards() {
        selectedCards.clear();
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
   //endregion

    //region ACTIVE JOKER GS
    public ObservableMap<Joker, IntegerProperty> getRocketJokers() {
        return rocketJokers.get();
    }

    public MapProperty<Joker, IntegerProperty> rocketJokersProperty() {
        return rocketJokers;
    }

    public CardViewManager getJokerManager() {
        return jokerManager;
    }

    public List<Joker> getActiveJokerList() {
    return jokerManager.getCardList(Joker.class);
    }    //endregion


    //region CONSUMABLES ON BOARD
    public CardViewManager getConsumableManager() {
        return consumableManager;
    }
    //endregion

    //region played Card View Manager
    public CardViewManager getPlayedCardsViewManager() {
        return playedCardsViewManager;
    }

    //endregion

    //region Background Run Variables


    public int getBeanValue() {
        return beanValue.get();
    }

    public IntegerProperty beanValueProperty() {
        return beanValue;
    }

    public void setBeanValue(int beanValue) {
        this.beanValue.set(beanValue);
    }

    public Card getLastConsumableUsed() {
        return lastConsumableUsed;
    }

    public void setLastConsumableUsed(Card lastConsumableUsed) {
        this.lastConsumableUsed = lastConsumableUsed;
    }

    //endregion

    //region UI VAR
    //Picked Blind Visibility
    public boolean isPickedBlindVisibility() {
        return pickedBlindVisibility.get();
    }

    public BooleanProperty pickedBlindVisibilityProperty() {
        return pickedBlindVisibility;
    }

    //Blinds Visibility
    public boolean getBlindsVisibility() {
        return blindsVisibility.get();
    }

    public BooleanProperty blindsVisibilityProperty() {
        return blindsVisibility;
    }

    public void setBlindsVisibility(boolean blindsVisibility) {
        this.blindsVisibility.set(blindsVisibility);
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


    public boolean isRewardVisibility() {
        return rewardVisibility.get();
    }

    public BooleanProperty rewardVisibilityProperty() {
        return rewardVisibility;
    }

    public void setRewardVisibility(boolean rewardVisibility) {
        this.rewardVisibility.set(rewardVisibility);
    }

    //Hand Buttons Visibility
    public boolean isHandButtonVisibility() {
        return handButtonVisibility.get();
    }

    public BooleanProperty handButtonVisibilityProperty() {
        return handButtonVisibility;
    }

    public void setHandButtonVisibility(Boolean value) {
        handButtonVisibility.set(value);
    }

    public boolean isBoosterOpeningVisibility() {
        return boosterOpeningVisibility.get();
    }

    public BooleanProperty boosterOpeningVisibilityProperty() {
        return boosterOpeningVisibility;
    }

    public void setBoosterOpeningVisibility(Boolean value) {
        boosterOpeningVisibility.set(value);
    }

    //endregion

    //region Scored Points
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
    //endregion

    //region Score To Reach
    public BigDecimal getScoreToReach() {
        return scoreToReach.get();
    }

    public ObjectProperty<BigDecimal> scoreToReachProperty() {
        return scoreToReach;
    }

    public void setScoreToReach(BigDecimal scoreToReach) {
        this.scoreToReach.set(scoreToReach);
    }
    //endregion

    //region Points Reached
    public boolean isPointsReached() {
        return pointsReached.get();
    }

    public BooleanProperty pointsReachedProperty() {
        return pointsReached;
    }
    //endregion

    //region Chip Requirement
    public BigDecimal[] getChipRequirement() {
        return chipRequirement;
    }

    public BigDecimal getChipRequirementByIndex(int index) {
        return getChipRequirement()[index];
    }
    //endregion


    //endregion

}
