package com.example.balatro.models;

import com.example.balatro.domain.card.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.math.BigInteger;


public class ProfileModel {
    //TODO Finish and DB

    //region Attributes
    private final IntegerProperty id =  new SimpleIntegerProperty();
    private final StringProperty profileName =  new SimpleStringProperty();
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
    //endregion

    //region Functions
    public void setProfile(ProfileModel profile) {
        setId(profile.getId());
        setProfileName(profile.getProfileName());
        setBestHand(profile.getBestHand());
        setHighestRound(profile.getHighestRound());
        setHighestAnte(profile.getHighestAnte());
        setMostPlayedHand(profile.getMostPlayedHand());

        setMostPlayedHandCount(profile.getMostPlayedHandCount());
        setMostMoney(profile.getMostMoney());
        setBestWinStreak(profile.getBestWinStreak());
        setWins(profile.getWins());
        setActiveProfile(profile.isActiveProfile());

        setProgress(profile.getProgress());
        setCollection(profile.getCollection());
        setChallenges(profile.getChallenges());
        setJokerStickers(profile.getJokerStickers());
        setDeckStakeWins(profile.getDeckStakeWins());

        getDecks().putAll(profile.getDecks());
        getJokers().setAll(profile.getJokers());
        getUnlockedJokers().setAll(profile.getUnlockedJokers());
        getBlinds().setAll(profile.getBlinds());
        getTarots().setAll(profile.getTarots());
        getPlanets().setAll(profile.getPlanets());
        getSpectrals().setAll(profile.getSpectrals());
        getTags().setAll(profile.getTags());
        getEditions().setAll(profile.getEditions());
        getBoosters().setAll(profile.getBoosters());
        getVouchers().setAll(profile.getVouchers());
        getUnlockedVouchers().setAll(profile.getUnlockedVouchers());
    }
    //endregion
}
