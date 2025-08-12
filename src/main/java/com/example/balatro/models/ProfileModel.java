package com.example.balatro.models;

import com.example.balatro.Balatro;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.card.Joker;
import com.example.balatro.domain.deck.SelectableDeck;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.rules.Blind;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.math.BigInteger;


public class ProfileModel {
    //TODO Finish and DB

    //region Attributes
    private IntegerProperty id =  new SimpleIntegerProperty();
    private StringProperty profileName =  new SimpleStringProperty();
    private BigInteger bestHand;
    private int highestRound;
    private int highestAnte;
    private String mostPlayedHand;
    private int mostPlayedHandCount;
    private int mostMoney;
    private int bestWinStreak;
    private int wins;
    private final BooleanProperty activeProfile = new SimpleBooleanProperty(false);

    private double progress;
    private double collection;
    private double challenges;
    private double jokerStickers;
    private double deckStakeWins;

    private final ObservableMap<Integer, Integer> decks  = FXCollections.observableHashMap();

    //private final ObservableList<Integer> decks = FXCollections.observableArrayList();
    private final ObservableList<Integer> jokers = FXCollections.observableArrayList();
    private final ObservableList<Integer> unlockedJokers = FXCollections.observableArrayList();
    private final ObservableList<Integer> blinds = FXCollections.observableArrayList();
    private final ObservableList<Integer> tarots = FXCollections.observableArrayList();
    private final ObservableList<Integer> planets = FXCollections.observableArrayList();
    private final ObservableList<Integer> spectrals = FXCollections.observableArrayList();
    private final ObservableList<Integer> tags = FXCollections.observableArrayList();
    private final ObservableList<Integer> editions = FXCollections.observableArrayList();
    private final ObservableList<Integer> boosters = FXCollections.observableArrayList();
    private final ObservableList<Integer> vouchers = FXCollections.observableArrayList();
    private final ObservableList<Integer> unlockedVouchers = FXCollections.observableArrayList();

    //endregion

    //region Getter & Setter
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getProfileName() {
        return profileName.get();
    }

    public StringProperty profileNameProperty() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName.set(profileName);
    }

    public BigInteger getBestHand() {
        return bestHand;
    }

    public void setBestHand(BigInteger bestHand) {
        this.bestHand = bestHand;
    }

    public int getHighestRound() {
        return highestRound;
    }

    public void setHighestRound(int highestRound) {
        this.highestRound = highestRound;
    }

    public int getHighestAnte() {
        return highestAnte;
    }

    public void setHighestAnte(int highestAnte) {
        this.highestAnte = highestAnte;
    }

    public String getMostPlayedHand() {
        return mostPlayedHand;
    }

    public void setMostPlayedHand(String mostPlayedHand) {
        this.mostPlayedHand = mostPlayedHand;
    }

    public int getMostPlayedHandCount() {
        return mostPlayedHandCount;
    }

    public void setMostPlayedHandCount(int mostPlayedHandCount) {
        this.mostPlayedHandCount = mostPlayedHandCount;
    }

    public int getMostMoney() {
        return mostMoney;
    }

    public void setMostMoney(int mostMoney) {
        this.mostMoney = mostMoney;
    }

    public int getBestWinStreak() {
        return bestWinStreak;
    }

    public void setBestWinStreak(int bestWinStreak) {
        this.bestWinStreak = bestWinStreak;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public boolean isActiveProfile() {
        return activeProfile.get();
    }

    public BooleanProperty activeProfileProperty() {
        return activeProfile;
    }

    public void setActiveProfile(boolean activeProfile) {
        this.activeProfile.set(activeProfile);
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getCollection() {
        return collection;
    }

    public void setCollection(double collection) {
        this.collection = collection;
    }

    public double getChallenges() {
        return challenges;
    }

    public void setChallenges(double challenges) {
        this.challenges = challenges;
    }

    public double getJokerStickers() {
        return jokerStickers;
    }

    public void setJokerStickers(double jokerStickers) {
        this.jokerStickers = jokerStickers;
    }

    public double getDeckStakeWins() {
        return deckStakeWins;
    }

    public void setDeckStakeWins(double deckStakeWins) {
        this.deckStakeWins = deckStakeWins;
    }

    public ObservableMap<Integer, Integer> getDecks() {
        return decks;
    }

    //    public ObservableList<Integer> getDecks() {
//        return decks;
//    }

    public ObservableList<Integer> getJokers() {
        return jokers;
    }

    public ObservableList<Integer> getUnlockedJokers() {
        return unlockedJokers;
    }

    public ObservableList<Integer> getBlinds() {
        return blinds;
    }

    public ObservableList<Integer> getTarots() {
        return tarots;
    }

    public ObservableList<Integer> getPlanets() {
        return planets;
    }

    public ObservableList<Integer> getSpectrals() {
        return spectrals;
    }

    public ObservableList<Integer> getTags() {
        return tags;
    }

    public ObservableList<Integer> getEditions() {
        return editions;
    }

    public ObservableList<Integer> getBoosters() {
        return boosters;
    }

    public ObservableList<Integer> getVouchers() {
        return vouchers;
    }

    public ObservableList<Integer> getUnlockedVouchers() {
        return unlockedVouchers;
    }

    //endregion

    //region Constructor
    public  ProfileModel() {
        setId(0);
        getJokers().addListener((ListChangeListener<? super Integer>) change -> {
            while (change.next()) {
                if(change.wasUpdated()) {

                    System.out.println("Joker Changed");
                }
            }
        });
    }

    public ProfileModel(int id) {
        //jokers.setAll(Balatro.getGameModel().getAllJokerList().stream().filter(j -> SqlHandler.getJokerIdsOfProfile(this.getId()).contains(j.getCardId())).toList());
        //planets.setAll(Balatro.getGameModel().getAllPlanetList().stream().filter(p -> SqlHandler.getPlanetIdsOfProfile(this.getId()).contains( p.getCardId())).toList());
    }
    //endregion

    //region Functions
    public void setProfile(ProfileModel profile) {
//        id =  new SimpleIntegerProperty();
        setId(profile.getId());
//        profileName =  new SimpleStringProperty();
        setProfileName(profile.getProfileName());
//        bestHand;
        setBestHand(profile.getBestHand());
//        highestRound;
        setHighestRound(profile.getHighestRound());
//        highestAnte;
        setHighestAnte(profile.getHighestAnte());
//        mostPlayedHand;
        setMostPlayedHand(profile.getMostPlayedHand());
//        mostPlayedHandCount;
        setMostPlayedHandCount(profile.getMostPlayedHandCount());
//        mostMoney;
        setMostMoney(profile.getMostMoney());
//        bestWinStreak;
        setBestWinStreak(profile.getBestWinStreak());
//        wins;
        setWins(profile.getWins());
//        activeProfile = new SimpleBooleanProperty(false);
        setActiveProfile(profile.isActiveProfile());

//        progress;
        setProgress(profile.getProgress());
//        collection;
        setCollection(profile.getCollection());
//        challenges;
        setChallenges(profile.getChallenges());
//        jokerStickers;
        setJokerStickers(profile.getJokerStickers());
//        deckStakeWins;
        setDeckStakeWins(profile.getDeckStakeWins());

//
//        decks = FXCollections.observableArrayList();
        getDecks().putAll(profile.getDecks());
//        jokers = FXCollections.observableArrayList();
        getJokers().setAll(profile.getJokers());
//        unlockedJokers = FXCollections.observableArrayList();
        getUnlockedJokers().setAll(profile.getUnlockedJokers());
//        blinds = FXCollections.observableArrayList();
        getBlinds().setAll(profile.getBlinds());
//        tarots = FXCollections.observableArrayList();
        getTarots().setAll(profile.getTarots());
//        planets = FXCollections.observableArrayList();
        getPlanets().setAll(profile.getPlanets());
//        spectrals = FXCollections.observableArrayList();
        getSpectrals().setAll(profile.getSpectrals());
//        tags = FXCollections.observableArrayList();
        getTags().setAll(profile.getTags());
//        editions = FXCollections.observableArrayList();
        getEditions().setAll(profile.getEditions());
//        boosters = FXCollections.observableArrayList();
        getBoosters().setAll(profile.getBoosters());
//        vouchers = FXCollections.observableArrayList();
        getVouchers().setAll(profile.getVouchers());
//        unlockedVouchers = FXCollections.observableArrayList();
        getUnlockedVouchers().setAll(profile.getUnlockedVouchers());


    }
    //endregion
}
