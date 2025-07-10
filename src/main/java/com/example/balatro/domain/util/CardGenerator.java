package com.example.balatro.domain.util;

import com.example.balatro.domain.card.*;
import com.example.balatro.enums.TarotEffect;
import com.example.balatro.models.GameModel;

import java.util.List;
import java.util.Objects;

public class CardGenerator {

    public static Joker getRandomJoker(GameModel gameModel) {
        Joker joker = new Joker();

        int jokerChance = gameModel.getRand().nextInt(100);

        if (jokerChance < 70) joker.setJoker(getJokerFilteredByRarity(gameModel, "Common"));
        else if (jokerChance < 95) joker.setJoker(getJokerFilteredByRarity(gameModel, "Uncommon"));
        else joker.setJoker(getJokerFilteredByRarity(gameModel, "Rare"));

        return joker;
    }

    public static Tarot getRandomTarot(GameModel gameModel) {
        Tarot tarot = new Tarot();

        int tarotChance = gameModel.getRand().nextInt(gameModel.getAllTarotList().size());

        tarot.setTarot(gameModel.getAllTarotList().get(tarotChance));
        System.out.println("Tarot: " + tarot.getCardName());
        return tarot;
    }

    public static Planet getRandomPlanet(GameModel gameModel) {
        Planet planet = new Planet();

        int planetChance = gameModel.getRand().nextInt(gameModel.getAllPlanetList().size());

        planet.setPlanet(gameModel.getAllPlanetList().get(planetChance));
        System.out.println("Planet: " + planet.getCardName());
        return planet;
    }

    public static Spectral getRandomSpectral(GameModel gameModel) {
        Spectral spectral = new Spectral();

        int spectralChance = gameModel.getRand().nextInt(gameModel.getAllSpectralList().size());

        spectral.setSpectral(gameModel.getAllSpectralList().get(spectralChance));
        System.out.println("Spectral: " + spectral.getSpectralName());
        return spectral;
    }

    public static PlayingCard getRandomPlayingCard(GameModel gameModel) {
        PlayingCard playingCard = new PlayingCard(gameModel.getRand().nextInt(13),gameModel.getRand().nextInt(4));

        System.out.println("PlayingCard: " + playingCard.getRank() + " of " + playingCard.getSuit());

        return playingCard;
    }

    public static Voucher getRandomVoucher(GameModel gameModel) {
        Voucher voucher = new Voucher();

        List<Voucher> availableVoucher = gameModel.getAllVoucherList().stream().filter(Voucher::isAvailable).toList();
        voucher.setVoucher(availableVoucher.get(gameModel.getRand().nextInt(availableVoucher.size())));
        return voucher;
    }

    public static Booster getRandomBooster(GameModel gameModel) {
        Booster booster = new Booster();

        int boosterChance = gameModel.getRand().nextInt(2242);

        if (boosterChance < 100) booster.setBooster(gameModel.getAllBoosterList().get(0));
        else if (boosterChance < 200) booster.setBooster(gameModel.getAllBoosterList().get(1));
        else if (boosterChance < 300) booster.setBooster(gameModel.getAllBoosterList().get(2));
        else if (boosterChance < 400) booster.setBooster(gameModel.getAllBoosterList().get(3));
        else if (boosterChance < 500) booster.setBooster(gameModel.getAllBoosterList().get(4));
        else if (boosterChance < 600) booster.setBooster(gameModel.getAllBoosterList().get(5));
        else if (boosterChance < 625) booster.setBooster(gameModel.getAllBoosterList().get(6));
        else if (boosterChance < 650) booster.setBooster(gameModel.getAllBoosterList().get(7));
        else if (boosterChance < 750) booster.setBooster(gameModel.getAllBoosterList().get(8));
        else if (boosterChance < 850) booster.setBooster(gameModel.getAllBoosterList().get(9));
        else if (boosterChance < 950) booster.setBooster(gameModel.getAllBoosterList().get(10));
        else if (boosterChance < 1050) booster.setBooster( gameModel.getAllBoosterList().get(11));
        else if (boosterChance < 1150) booster.setBooster( gameModel.getAllBoosterList().get(12));
        else if (boosterChance < 1250) booster.setBooster( gameModel.getAllBoosterList().get(13));
        else if (boosterChance < 1275) booster.setBooster( gameModel.getAllBoosterList().get(14));
        else if (boosterChance < 1300) booster.setBooster( gameModel.getAllBoosterList().get(15));
        else if (boosterChance < 1400) booster.setBooster( gameModel.getAllBoosterList().get(16));
        else if (boosterChance < 1500) booster.setBooster( gameModel.getAllBoosterList().get(17));
        else if (boosterChance < 1600) booster.setBooster( gameModel.getAllBoosterList().get(18));
        else if (boosterChance < 1700) booster.setBooster( gameModel.getAllBoosterList().get(19));
        else if (boosterChance < 1800) booster.setBooster( gameModel.getAllBoosterList().get(20));
        else if (boosterChance < 1900) booster.setBooster( gameModel.getAllBoosterList().get(21));
        else if (boosterChance < 1925) booster.setBooster( gameModel.getAllBoosterList().get(22));
        else if (boosterChance < 1950) booster.setBooster( gameModel.getAllBoosterList().get(23));
        else if (boosterChance < 2010) booster.setBooster( gameModel.getAllBoosterList().get(24));
        else if (boosterChance < 2070) booster.setBooster( gameModel.getAllBoosterList().get(25));
        else if (boosterChance < 2130) booster.setBooster( gameModel.getAllBoosterList().get(26));
        else if (boosterChance < 2145) booster.setBooster( gameModel.getAllBoosterList().get(27));
        else if (boosterChance < 2175) booster.setBooster( gameModel.getAllBoosterList().get(28));
        else if (boosterChance < 2205) booster.setBooster( gameModel.getAllBoosterList().get(29));
        else if (boosterChance < 2235) booster.setBooster( gameModel.getAllBoosterList().get(30));
        else booster.setBooster(gameModel.getAllBoosterList().get(31));

        System.out.println("Booster Name: " + booster.getCardName());
        System.out.println("Booster Size: " + booster.getBoosterSize());
        return booster;
    }

    private static Joker getJokerFilteredByRarity(GameModel gameModel, String rarity) {
        List<Joker> jokerList = gameModel.getAllJokerList().stream().filter(x -> Objects.equals(x.getRarity(), rarity)).toList();
        return jokerList.get(gameModel.getRand().nextInt(jokerList.size()));
    }

}
