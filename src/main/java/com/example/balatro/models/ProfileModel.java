package com.example.balatro.models;

import com.example.balatro.domain.card.*;
import com.example.balatro.domain.deck.SelectableDeck;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.rules.Blind;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.math.BigInteger;

public class ProfileModel {
    //TODO Finish and DB

    //region Attributes
    private int id;
    private String profileName;
    private BigInteger bestHand;
    private int highestRound;
    private int highestAnte;
    private String mostPlayedHand;
    private int mostPlayedHandCount;
    private int mostMoney;
    private String bestWinStreak;

    private double progress;
    private double collection;
    private double challenges;
    private double jokerStickers;
    private double deckStakeWins;

    private final ObservableList<SelectableDeck> decks = FXCollections.observableArrayList();
    private final ObservableList<Joker> jokers = FXCollections.observableArrayList();
    private final ObservableList<Joker> unlockedJokers = FXCollections.observableArrayList();
    private final ObservableList<Blind> blinds = FXCollections.observableArrayList();
    private final ObservableList<Tarot> tarots = FXCollections.observableArrayList();
    private final ObservableList<Planet> planets = FXCollections.observableArrayList();
    private final ObservableList<Spectral> spectrals = FXCollections.observableArrayList();
    private final ObservableList<Tag> tags = FXCollections.observableArrayList();
    private final ObservableList<Edition> editions = FXCollections.observableArrayList();
    private final ObservableList<Booster> boosters = FXCollections.observableArrayList();
    private final ObservableList<Voucher> vouchers = FXCollections.observableArrayList();
    private final ObservableList<Voucher> unlockedVouchers = FXCollections.observableArrayList();

    //endregion

    //region Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
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

    public String getBestWinStreak() {
        return bestWinStreak;
    }

    public void setBestWinStreak(String bestWinStreak) {
        this.bestWinStreak = bestWinStreak;
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

    public ObservableList<SelectableDeck> getDecks() {
        return decks;
    }

    public ObservableList<Joker> getJokers() {
        return jokers;
    }

    public ObservableList<Joker> getUnlockedJokers() {
        return unlockedJokers;
    }

    public ObservableList<Blind> getBlinds() {
        return blinds;
    }

    public ObservableList<Tarot> getTarots() {
        return tarots;
    }

    public ObservableList<Planet> getPlanets() {
        return planets;
    }

    public ObservableList<Spectral> getSpectrals() {
        return spectrals;
    }

    public ObservableList<Tag> getTags() {
        return tags;
    }

    public ObservableList<Edition> getEditions() {
        return editions;
    }

    public ObservableList<Booster> getBoosters() {
        return boosters;
    }

    public ObservableList<Voucher> getVouchers() {
        return vouchers;
    }

    public ObservableList<Voucher> getUnlockedVouchers() {
        return unlockedVouchers;
    }

    //endregion

    //region Constructor
    public  ProfileModel() {
        getJokers().addListener((ListChangeListener<? super Joker>)  change -> {
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
        setProgress(profile.getProgress());
        setCollection(profile.getCollection());
        setChallenges(profile.getChallenges());
        setJokerStickers(profile.getJokerStickers());
        setDeckStakeWins(profile.getDeckStakeWins());

        getDecks().setAll(profile.getDecks());
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
