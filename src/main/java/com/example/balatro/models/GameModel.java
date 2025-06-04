package com.example.balatro.models;

import com.example.balatro.classes.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

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
    private final List<PokerHand> allPokerHandList = SqlHandler.getAllPokerHands();
    private List<Spectral> allSpectralList = SqlHandler.getAllSpectrals();
    //endregion

    //region Run Lists
    private final ObservableList<PlayingCard> deckFull = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> deckToPlay = FXCollections.observableArrayList();
    private final ObservableList<Blind> runBlinds = FXCollections.observableArrayList();
    private final List<Tag> runTags = new ArrayList<>();
    private final ObservableList<Tag> tagQueue = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> handCards = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> selectedCards = FXCollections.observableArrayList();
    private final ObservableList<PlayingCard> playedCards = FXCollections.observableArrayList();
    private final ObservableList<PokerHand> possiblePokerHand = FXCollections.observableArrayList();
    private final MapProperty<Joker, IntegerProperty> rocketJokers = new SimpleMapProperty<>();
    //endregion

    //region GAME SETTINGS VAR
    private Random rand;
    private final ObjectProperty<Deck> chosenDeck = new SimpleObjectProperty<>(new Deck());
    private final ObjectProperty<Stake> chosenStake = new SimpleObjectProperty<>(new Stake());
    private final ObjectProperty<Blind> activeBlind = new SimpleObjectProperty<>(new Blind());
    private final IntegerProperty maxJokers = new SimpleIntegerProperty(5);
    private final IntegerProperty maxConsumables = new SimpleIntegerProperty(2);
    //endregion

    //region HAND POINTS VAR
    private final ObjectProperty<PokerHand> bestHand = new SimpleObjectProperty<>(new PokerHand());
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

    //region ACTIVE JOKERS
    private final ObservableList<Joker> activeJokerObList = FXCollections.observableArrayList();
    //endregion

    //region Collected Consumables
    private final ObservableList<Card> consumableList = FXCollections.observableArrayList();
    //endregion

    //region RUN INFO VAR
    private final IntegerProperty maxHands = new SimpleIntegerProperty(4);
    private final IntegerProperty hands = new SimpleIntegerProperty(4);
    private final IntegerProperty maxDiscards = new SimpleIntegerProperty(3);
    private final IntegerProperty discards = new SimpleIntegerProperty(3);
    private final IntegerProperty money = new SimpleIntegerProperty(3);
    private final IntegerProperty ante = new SimpleIntegerProperty(0);
    private final IntegerProperty round = new SimpleIntegerProperty(-1);
    private final IntegerProperty handsPlayed = new SimpleIntegerProperty(0);
    private final IntegerProperty handsDiscarded = new SimpleIntegerProperty(0);
    private final IntegerProperty maxInterest = new SimpleIntegerProperty(5);
    //endregion

    //region UI VAR
    private final BooleanProperty pickedBlindVisibility = new SimpleBooleanProperty(false);
    private final BooleanProperty blindsVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty shopVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty rewardVisibility = new SimpleBooleanProperty(true);
    private final BooleanProperty handButtonVisibility = new SimpleBooleanProperty(false);
    //endregion

    //region POINTS VAR
    private final BooleanProperty pointsReached = new SimpleBooleanProperty(false);
    private final ObjectProperty<BigDecimal> scoredPoints = new SimpleObjectProperty<>(new BigDecimal(0));
    private final ObjectProperty<BigDecimal> scoreToReach = new SimpleObjectProperty<>(new BigDecimal(0));
    private final BigDecimal[] chipRequirement = new BigDecimal[]{
            BigDecimal.valueOf(100), BigDecimal.valueOf(300), BigDecimal.valueOf(800),
            BigDecimal.valueOf(2000), BigDecimal.valueOf(5000), BigDecimal.valueOf(11000),
            BigDecimal.valueOf(20000), BigDecimal.valueOf(35000), BigDecimal.valueOf(50000)};
    public ObjectProperty<BigDecimal> pointsScoredProperty = new SimpleObjectProperty<>(new BigDecimal(0));
    //endregion

    //region Voucher Flags
    private final BooleanProperty TarotMerchantVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty TarotTycoonVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty PlanetMerchantVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty PlanetTycoonVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty MagicTrickVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty IllusionVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty HoneVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty GlowUpVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty ClearanceSaleVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty LiquidationVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty RerollSurplusVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty RerollGlutVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty CrystalBallVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty OmenGlobeVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty TelescopeVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty ObservatoryVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty GrabberVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty NachoTongVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty WastefulVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty RecyclomancyVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty SeedMoneyVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty MoneyTreeVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty BlankVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty AntimatterVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty HieroglyphVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty PetroglyphVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty DirectorsCutVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty RetconVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty PaintBrushVoucher = new SimpleBooleanProperty(false);
    private final BooleanProperty PaletteVoucher = new SimpleBooleanProperty(false);
    //endregion

    //endregion

    //region CONSTRUCTOR
    public GameModel() {
        scoredPoints.addListener((observable, oldValue, newValue) -> {
//            System.out.println("points reached old: " + oldValue);
//            System.out.println("points reached new: " + newValue);
//            System.out.println("Compare result: " + newValue.compareTo(getScoreToReach()));
            pointsReachedProperty().set(newValue.compareTo(getScoreToReach()) > -1);
        });
    }
    //endregion

    //region GETTER SETTER

    //region All Data Lists
    public List<PokerHand> getAllHandList() {
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

    public List<Planet> getAllPlanetList() {
        return allPlanetList;
    }

    public List<PokerHand> getAllPokerHandList() {
        return allPokerHandList;
    }

    public List<Joker> getAllJokerList() {
        return allJokerList;
    }

    public List<Spectral> getAllSpectralList() {
        return allSpectralList;
    }
    //endregion

    //region Full Deck


    public ObservableList<PlayingCard> getDeckFull() {
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


    public ObservableList<PlayingCard> getDeckToPlay() {
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
    public Blind getActiveBlind() {
        return activeBlind.get();
    }

    public ObjectProperty<Blind> activeBlindProperty() {
        return activeBlind;
    }

    public void setActiveBlind(Blind pickedBlind) {
        activeBlind.get().setBlind(pickedBlind);
    }


    public int getMaxJokers() {
        return maxJokers.get();
    }

    public IntegerProperty maxJokersProperty() {
        return maxJokers;
    }

    public void setMaxJokers(int maxJokers) {
        this.maxJokers.set(maxJokers);
    }

    public int getMaxConsumables() {
        return maxConsumables.get();
    }

    public IntegerProperty maxConsumablesProperty() {
        return maxConsumables;
    }

    public void setMaxConsumables(int maxConsumables) {
        this.maxConsumables.set(maxConsumables);
    }

    public ObservableList<Blind> getRunBlinds() {
        return runBlinds;
    }

    public void setRunBlinds(List<Blind> blindList) {
        runBlinds.clear();
        runBlinds.addAll(blindList);
    }
    //endregion

    //region Tags for the Run
    public List<Tag> getRunTags() {
        return runTags;
    }

    public void setRunTags(List<Tag> tags) {
        runTags.clear();
        runTags.addAll(tags);
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
        tagQueue.remove(0);
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

    public void addHandToPossibleList(PokerHand pokerHand) {
        possiblePokerHand.add(pokerHand);
    }

    public void removeHandFromPossible(PokerHand pokerHand) {
        possiblePokerHand.remove(pokerHand);
    }
    //endregion

    //region GAME SETTINGS VAR
    //Chosen Deck
    public Deck getChosenDeck() {
        return chosenDeck.get();
    }

    public ObjectProperty<Deck> chosenDeckProperty() {
        return chosenDeck;
    }

    public void setChosenDeck(Deck chosenDeck) {
        this.chosenDeck.get().setDeck(chosenDeck);
    }

    //Chosen Stake
    public Stake getChosenStake() {
        return chosenStake.get();
    }

    public ObjectProperty<Stake> chosenStakeProperty() {
        return chosenStake;
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
    public PokerHand getBestHand() {
        return bestHand.get();
    }

    public ObjectProperty<PokerHand> bestHandProperty() {
        return bestHand;
    }


    //endregion

    //region Poker Hand
    //Poker Hand Name
    public String getHandName() {
        return handName.get();
    }

    public StringProperty handNameProperty() {
        return handName;
    }

    //Poker Hand Level
    public int getHandLevel() {
        return handLevel.get();
    }

    public IntegerProperty handLevelProperty() {
        return handLevel;
    }

    //Poker Hand Chips
    public int getHandChips() {
        return handChips.get();
    }

    public IntegerProperty handChipsProperty() {
        return handChips;
    }

    //Poker Hand Multiplyer
    public double getHandMultiplier() {
        return handMultiplier.get();
    }

    public DoubleProperty handMultiplierProperty() {
        return handMultiplier;
    }

    //endregion

    //region HOLDING HAND GS
    //Handcards
    public ObservableList<PlayingCard> getHandCards() {
        return handCards;
    }

    public void addCardToHandCards(PlayingCard card) {
        handCards.add(card);
    }

    public void addManyCardsToHandCards(List<PlayingCard> cards) {
        handCards.addAll(cards);
    }

    public void removeCardFromHandCards(PlayingCard card) {
        handCards.remove(card);
    }

    public void removeManyCardsFromHandCards(List<PlayingCard> cards) {
        handCards.removeAll(cards);
    }

    public void clearHandCards() {
        handCards.clear();
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

    public void incrementSelectedCardCounter() {
        selectedCardCounter.set(selectedCardCounter.get() + 1);
    }

    public void decrementSelectedCardCounter() {
        selectedCardCounter.set(selectedCardCounter.get() - 1);
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
    public int getHandSize() {
        return handSize.get();
    }

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

    //region ACTIVE JOKER GS
    public ObservableList<Joker> getActiveJokerObList() {
        return activeJokerObList;
    }

    public ObservableMap<Joker, IntegerProperty> getRocketJokers() {
        return rocketJokers.get();
    }

    public MapProperty<Joker, IntegerProperty> rocketJokersProperty() {
        return rocketJokers;
    }

    //endregion


    //region CONSUMABLES ON BOARD
    public ObservableList<Card> getConsumableList() {
        return consumableList;
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

    public void decrementHands() {
        setHands(getHands() - 1);
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

    public void decrementDiscards() {
        setDiscards(getDiscards() - 1);
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

    public int getMaxInterest() {
        return maxInterest.get();
    }

    public IntegerProperty maxInterestProperty() {
        return maxInterest;
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

    public void toggleBlindVisibity() {
        setBlindsVisibility(!getBlindsVisibility());
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

    public void addToMultiplier(int i) {
        handMultiplier.add(i);
    }
    //endregion

    //region Voucher Flags
    public boolean isTarotMerchantVoucher() {
        return TarotMerchantVoucher.get();
    }

    public BooleanProperty tarotMerchantVoucherProperty() {
        return TarotMerchantVoucher;
    }

    public boolean isTarotTycoonVoucher() {
        return TarotTycoonVoucher.get();
    }

    public BooleanProperty tarotTycoonVoucherProperty() {
        return TarotTycoonVoucher;
    }

    public boolean isPlanetMerchantVoucher() {
        return PlanetMerchantVoucher.get();
    }

    public BooleanProperty planetMerchantVoucherProperty() {
        return PlanetMerchantVoucher;
    }

    public boolean isPlanetTycoonVoucher() {
        return PlanetTycoonVoucher.get();
    }

    public BooleanProperty planetTycoonVoucherProperty() {
        return PlanetTycoonVoucher;
    }

    public boolean isMagicTrickVoucher() {
        return MagicTrickVoucher.get();
    }

    public BooleanProperty magicTrickVoucherProperty() {
        return MagicTrickVoucher;
    }

    public boolean isIllusionVoucher() {
        return IllusionVoucher.get();
    }

    public BooleanProperty illusionVoucherProperty() {
        return IllusionVoucher;
    }

    public boolean isHoneVoucher() {
        return HoneVoucher.get();
    }

    public BooleanProperty honeVoucherProperty() {
        return HoneVoucher;
    }

    public boolean isGlowUpVoucher() {
        return GlowUpVoucher.get();
    }

    public BooleanProperty glowUpVoucherProperty() {
        return GlowUpVoucher;
    }

    public boolean isClearanceSaleVoucher() {
        return ClearanceSaleVoucher.get();
    }

    public BooleanProperty clearanceSaleVoucherProperty() {
        return ClearanceSaleVoucher;
    }

    public boolean isLiquidationVoucher() {
        return LiquidationVoucher.get();
    }

    public BooleanProperty liquidationVoucherProperty() {
        return LiquidationVoucher;
    }

    public boolean isRerollSurplusVoucher() {
        return RerollSurplusVoucher.get();
    }

    public BooleanProperty rerollSurplusVoucherProperty() {
        return RerollSurplusVoucher;
    }

    public boolean isRerollGlutVoucher() {
        return RerollGlutVoucher.get();
    }

    public BooleanProperty rerollGlutVoucherProperty() {
        return RerollGlutVoucher;
    }

    public boolean isCrystalBallVoucher() {
        return CrystalBallVoucher.get();
    }

    public BooleanProperty crystalBallVoucherProperty() {
        return CrystalBallVoucher;
    }

    public boolean isOmenGlobeVoucher() {
        return OmenGlobeVoucher.get();
    }

    public BooleanProperty omenGlobeVoucherProperty() {
        return OmenGlobeVoucher;
    }

    public boolean isTelescopeVoucher() {
        return TelescopeVoucher.get();
    }

    public BooleanProperty telescopeVoucherProperty() {
        return TelescopeVoucher;
    }

    public boolean isObservatoryVoucher() {
        return ObservatoryVoucher.get();
    }

    public BooleanProperty observatoryVoucherProperty() {
        return ObservatoryVoucher;
    }

    public boolean isGrabberVoucher() {
        return GrabberVoucher.get();
    }

    public BooleanProperty grabberVoucherProperty() {
        return GrabberVoucher;
    }

    public boolean isNachoTongVoucher() {
        return NachoTongVoucher.get();
    }

    public BooleanProperty nachoTongVoucherProperty() {
        return NachoTongVoucher;
    }

    public boolean isWastefulVoucher() {
        return WastefulVoucher.get();
    }

    public BooleanProperty wastefulVoucherProperty() {
        return WastefulVoucher;
    }

    public boolean isRecyclomancyVoucher() {
        return RecyclomancyVoucher.get();
    }

    public BooleanProperty recyclomancyVoucherProperty() {
        return RecyclomancyVoucher;
    }

    public boolean isSeedMoneyVoucher() {
        return SeedMoneyVoucher.get();
    }

    public BooleanProperty seedMoneyVoucherProperty() {
        return SeedMoneyVoucher;
    }

    public boolean isMoneyTreeVoucher() {
        return MoneyTreeVoucher.get();
    }

    public BooleanProperty moneyTreeVoucherProperty() {
        return MoneyTreeVoucher;
    }

    public boolean isBlankVoucher() {
        return BlankVoucher.get();
    }

    public BooleanProperty blankVoucherProperty() {
        return BlankVoucher;
    }

    public boolean isAntimatterVoucher() {
        return AntimatterVoucher.get();
    }

    public BooleanProperty antimatterVoucherProperty() {
        return AntimatterVoucher;
    }

    public boolean isHieroglyphVoucher() {
        return HieroglyphVoucher.get();
    }

    public BooleanProperty hieroglyphVoucherProperty() {
        return HieroglyphVoucher;
    }

    public boolean isPetroglyphVoucher() {
        return PetroglyphVoucher.get();
    }

    public BooleanProperty petroglyphVoucherProperty() {
        return PetroglyphVoucher;
    }

    public boolean isDirectorsCutVoucher() {
        return DirectorsCutVoucher.get();
    }

    public BooleanProperty directorsCutVoucherProperty() {
        return DirectorsCutVoucher;
    }

    public boolean isRetconVoucher() {
        return RetconVoucher.get();
    }

    public BooleanProperty retconVoucherProperty() {
        return RetconVoucher;
    }

    public boolean isPaintBrushVoucher() {
        return PaintBrushVoucher.get();
    }

    public BooleanProperty paintBrushVoucherProperty() {
        return PaintBrushVoucher;
    }

    public boolean isPaletteVoucher() {
        return PaletteVoucher.get();
    }

    public BooleanProperty paletteVoucherProperty() {
        return PaletteVoucher;
    }

    //endregion
    //endregion

}
