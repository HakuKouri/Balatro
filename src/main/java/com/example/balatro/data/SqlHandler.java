package com.example.balatro.data;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Joker;
import com.example.balatro.enums.SpectralEffect;
import com.example.balatro.enums.TarotEffect;
import com.example.balatro.models.ProfileModel;
import com.example.balatro.models.settings.Language;
import com.example.balatro.domain.rules.Stake;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.card.*;
import com.example.balatro.domain.deck.SelectableDeck;
import com.example.balatro.domain.effects.JokerEffectTrigger;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.enums.JokerTrigger;
import javafx.scene.paint.Color;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlHandler {

    //region Attributes
    private static final SqlHandler sqlHandler = new SqlHandler();
    private static Connection connection;

    private static final String DB_PATH = System.getProperty("user.dir") + "/database/balatrodb.db";
    private static final String DecksTableColumns = " (id, deckCover, deckName, deckDescription, unlockRequirement, stageCleared)";
    private static final String BlindsTableColumns = " (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn)";
    private static final String StakesTableColumns = " (id, stakeStickerUrl, stakeChipUrl, stakeName, stakeEffect, unlocks)";
    private static final String JokerCardsTableColumns = " (id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act, triggers, effect_keys, params)";
    private static final String TarotCardsTableColumns = " (id, tarotImage, tarotName, tarotDescription)";
    private static final String PlanetCardsTableColumns = " (id, planetImage, planetName, additions, chips, multiplier, pokerHand, handBaseScore, secret)";
    private static final String SpectralCardsTableColumns = " (id, spectralImage, spectralName, spectralEffect)";
    private static final String VoucherCardsTableColumns = " (id, voucherImageUrl, voucherName, voucherEffect, upgradeFrom, upgradeVoucherUnlocked, note)";
    private static final String TagsTableColumns = " (id, tagIcon, tagName, tagBenefit, tagNote, minAnte)";
    private static final String EnhancementsTableColumns = " (id, appearance, enhancement, effect)";
    private static final String EditionsTableColumns = " (id, appearance, edition, effect)";
    private static final String SealsTableColumns = " (id, appearance, seal, effect)";
    private static final String LanguagesTableColumns = " (id, languageName, text, notes)";
    private static final String LangNameDetailsTableColumns = " (idLanguage, targetTable, targetId)";
    private static final String BoostersTableColumns = " (id, boosterImage, boosterName, boosterCost, boosterSize, boosterEffect, cardDrawn, cardsToChoose)";
    private static final String StickerTableColumns = " (id, stickerImage, stickerName, stickerEffect)";
    private static final String profileTableColumns = " (id, profilName, bestHand, highestRound, highestAnte, bestWinStreak, activeProfile)";
//endregion

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    public static SqlHandler getInstance() {
        return sqlHandler;
    }

    //region Setup
    private static void initDBConnection() {
        try {
            if (connection != null)
                return;

            java.io.File dbFolder = new java.io.File(System.getProperty("user.dir") + "/database");
            if (!dbFolder.exists()) {
                dbFolder.mkdirs();
            }
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
                System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleDB() {
        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Decks " + DecksTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Blinds " + BlindsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Stakes " + StakesTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Jokers " + JokerCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Tarots " + TarotCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Planets " + PlanetCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Spectrals " + SpectralCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Vouchers " + VoucherCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Tags " + TagsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Enhancements " + EnhancementsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Editions " + EditionsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Seals " + SealsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Languages " + LanguagesTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LangNameDetails " + LangNameDetailsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Boosters " + BoostersTableColumns + ";");
        } catch (SQLException e) {
            System.err.println("Couldn't handle DB-Query");
            e.printStackTrace();
        }
    }

    public static void main() {
        SqlHandler dbc = SqlHandler.getInstance();
        dbc.initDBConnection();
        dbc.handleDB();
    }
    //endregion

    //List to Sql
    public static <T> void ListToSql(ArrayList<T> list, String tableName) {
        System.out.println("Im Sql Handler!");
        try {
            Statement stmt = connection.createStatement();
            for (T listItem : list) {
                String query = "INSERT OR REPLACE INTO " + tableName + " ";

                if (listItem.getClass() == Joker.class) {
                    //(id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act)
                    query += JokerCardsTableColumns + " VALUES (?,?,?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Joker) listItem).getCardId());
                    ps.setString(2, ((Joker) listItem).getCardImageUrl());
                    ps.setString(3, ((Joker) listItem).getCardName());
                    ps.setString(4, ((Joker) listItem).getCardDescription());
                    ps.setInt(5, ((Joker) listItem).getCardCost());
                    ps.setString(6, ((Joker) listItem).getRarity());
                    ps.setString(7, ((Joker) listItem).getUnlockRequirement());
                    ps.setString(8, ((Joker) listItem).getJokerType());
                    ps.setString(9, ((Joker) listItem).getActTiming());
                    ps.executeUpdate();
                } else if (listItem.getClass() == SelectableDeck.class) {
                    query += DecksTableColumns + " VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((SelectableDeck) listItem).getDeckId());
                    ps.setString(2, ((SelectableDeck) listItem).getDeckCoverUrl());
                    ps.setString(3, ((SelectableDeck) listItem).getDeckName());
                    ps.setString(4, ((SelectableDeck) listItem).getDeckDescription());
                    ps.setString(5, ((SelectableDeck) listItem).getUnlockCondition());
                    ps.setInt(6, 0);
                    ps.executeUpdate();
                } else if (listItem.getClass() == Blind.class) {
                    //BlindsTableColumns = " (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn)";
                    query += BlindsTableColumns + "VALUES (?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Blind) listItem).getBlindId());
                    ps.setString(2, ((Blind) listItem).getBlindImageUrl());
                    ps.setString(3, ((Blind) listItem).getBlindName());
                    ps.setString(4, ((Blind) listItem).getBlindDescription());
                    ps.setInt(5, ((Blind) listItem).getBlindMinimumAnte());
                    ps.setDouble(6, ((Blind) listItem).getBlindScoreMultiplier());
                    ps.setString(7, String.valueOf(((Blind) listItem).getBlindReward()));
                    ps.executeUpdate();
                } else if (listItem.getClass() == Stake.class) {
                    //StakesTableColumns = " (id, stakeIcon, stakeName, stakeEffect, unlocks)";
                    query += StakesTableColumns + "VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Stake) listItem).getStakeId());
                    ps.setString(2, ((Stake) listItem).getStakeImageStickerUrl());
                    ps.setString(3, ((Stake) listItem).getStakeImageChipUrl());
                    ps.setString(4, ((Stake) listItem).getStakeName());
                    ps.setString(5, ((Stake) listItem).getStakeDescription());
                    ps.setString(6, ((Stake) listItem).getStakeUnlockCondition());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Tarot.class) {
                    //TarotCardsTableColumns = " (id, tarotImage, tarotName, tarotDescription)";
                    query += TarotCardsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Tarot) listItem).getCardId());
                    ps.setString(2, ((Tarot) listItem).getCardImageUrl());
                    ps.setString(3, ((Tarot) listItem).getCardName());
                    ps.setString(4, ((Tarot) listItem).getTarotDescription());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Planet.class) {
                    //PlanetCardsTableColumns = " (id, planetImage, planetName, additions, pokerHand, handBaseScore, secret)";
                    query += PlanetCardsTableColumns + "VALUES (?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Planet) listItem).getCardId());
                    ps.setString(2, ((Planet) listItem).getCardImageUrl());
                    ps.setString(3, ((Planet) listItem).getCardName());
                    ps.setString(4, ((Planet) listItem).getPlanetAddition());
                    ps.setString(5, ((Planet) listItem).getPlanetPokerHand());
                    ps.setString(6, ((Planet) listItem).getPlanetHandBaseScore());
                    ps.setBoolean(7, ((Planet) listItem).getCardId() >= 10);
                    ps.executeUpdate();


                } else if (listItem.getClass() == Spectral.class) {
                    //SpectralCardsTableColumns = " (id, spectralImage, spectralName, spectralEffect)";
                    query += SpectralCardsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Spectral) listItem).getCardId());
                    ps.setString(2, ((Spectral) listItem).getSpectralImageUrl());
                    ps.setString(3, ((Spectral) listItem).getSpectralName());
                    ps.setString(4, ((Spectral) listItem).getSpectralDescription());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Voucher.class) {
                    //VoucherCardsTableColumns = "(id, voucherImageUrl, voucherName, voucherEffect, upgradeFrom, upgradeVoucherUnlocked, note)";
                    query += VoucherCardsTableColumns + "VALUES (?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Voucher) listItem).getCardId());
                    ps.setString(2, ((Voucher) listItem).getCardImageUrl());
                    ps.setString(3, ((Voucher) listItem).getCardName());
                    ps.setString(4, ((Voucher) listItem).getVoucherEffect());
                    ps.setString(5, ((Voucher) listItem).getVoucherUpgradeFrom());
                    ps.setString(6, ((Voucher) listItem).getVoucherUnlockCondition());
                    ps.setString(7, ((Voucher) listItem).getVoucherNotes());

                    ps.executeUpdate();
                } else if (listItem.getClass() == Tag.class) {
                    //TagsTableColumns = " (id, tagIcon, tagName, tagBenefit, tagNote, minAnte)";
                    query += TagsTableColumns + "VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Tag) listItem).getTagId());
                    ps.setString(2, ((Tag) listItem).getTagImageUrl());
                    ps.setString(3, ((Tag) listItem).getTagName());
                    ps.setString(4, ((Tag) listItem).getTagBenefit());
                    ps.setString(5, ((Tag) listItem).getTagNotes());
                    ps.setString(6, ((Tag) listItem).getMinAnte());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Enhancement.class) {
                    //EnhancementsTableColumns = " (id, appearance, enhancement, effect)";
                    query += EnhancementsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Enhancement) listItem).getEnhancementId());
                    ps.setString(2, ((Enhancement) listItem).getEnhancementImageUrl());
                    ps.setString(3, ((Enhancement) listItem).getEnhancementName());
                    ps.setString(4, ((Enhancement) listItem).getEnhancementEffect());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Edition.class) {
                    //EditionsTableColumns = " (id, appearance, edition, effect)";
                    query += EditionsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Edition) listItem).getId());
                    ps.setString(2, ((Edition) listItem).getEditionImageUrl());
                    ps.setString(3, ((Edition) listItem).getEditionName());
                    ps.setString(4, ((Edition) listItem).getEditionEffect());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Seal.class) {
                    //SealsTableColumns = " (id, appearance, seal, effect)";
                    query += SealsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Seal) listItem).getSealId());
                    ps.setString(2, ((Seal) listItem).getSealImageUrl());
                    ps.setString(3, ((Seal) listItem).getSealName());
                    ps.setString(4, ((Seal) listItem).getSealEffect());
                    ps.executeUpdate();
                }
                if (listItem.getClass() == Booster.class) {
                    //(id, boosterImage, boosterName, boosterCost, boosterSize, boosterEffect)
                    query += BoostersTableColumns + " VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Booster) listItem).getCardId());
                    ps.setString(2, ((Booster) listItem).getCardImageUrl());
                    ps.setString(3, ((Booster) listItem).getCardName());
                    ps.setString(4, ((Booster) listItem).getBoosterEffect());
                    ps.setString(5, ((Booster) listItem).getBoosterSize());
                    ps.setString(6, ((Booster) listItem).getBoosterEffect());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Language.class) {
                    //LanguagesTableColumns = " (id, languageName, text, notes)";
                }
            }
        } catch (SQLException e) {
            System.out.println(tableName);
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    //region Getter Game Data
    public static List<SelectableDeck> getAllDecks() {
        List<SelectableDeck> selectableDeckList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Decks";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                SelectableDeck selectableDeck = new SelectableDeck();
                selectableDeck.setDeckId(rs.getInt(1));
                selectableDeck.setDeckCoverUrl(rs.getString(2));
                selectableDeck.setDeckName(rs.getString(3));
                selectableDeck.setDeckDescription(rs.getString(4));
                selectableDeck.setUnlockCondition(rs.getString(5));
                selectableDeck.setStageCleared(rs.getInt(6));
                selectableDeckList.add(selectableDeck);
            }

        } catch (SQLException e) {
            System.out.println("GetAllDecks");
            System.out.println(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException(e);
        }

        return selectableDeckList;
    }

    public static List<Stake> getAllStakes() {
        List<Stake> stakeList = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            String statementString = "SELECT * FROM Stakes";
            ResultSet rs = stmt.executeQuery(statementString);

            while (rs.next()) {
                Stake stake = new Stake();
                stake.setStakeId(rs.getInt(1));
                stake.setStakeImageStickerUrl(rs.getString(2));
                stake.setStakeImageChipUrl(rs.getString(3));
                stake.setStakeName(rs.getString(4));
                stake.setStakeDescription(rs.getString(5));
                stake.setStakeUnlockCondition(rs.getString(6));
                stake.setStakeColorString(rs.getString(7));

                stakeList.add(stake);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stakeList;
    }

    public static List<Blind> getAllBlinds() {
        List<Blind> blinds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Blinds";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Blind blind = new Blind();

                blind.setBlindId(rs.getInt(1));
                blind.setBlindImageUrl(rs.getString(2));
                blind.setBlindName(rs.getString(3));
                blind.setBlindDescription(rs.getString(4));
                blind.setBlindMinimumAnte(rs.getInt(5));
                blind.setBlindScoreMultiplier(rs.getDouble(6));
                blind.setBlindReward(java.lang.Integer.parseInt(rs.getString(7).substring(1)));
                blind.setColorScheme(Color.valueOf(rs.getString(8)));

                blinds.add(blind);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return blinds;
    }

    public static List<Booster> getAllBooster() {
        List<Booster> boosters = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, boosterImage, boosterName, boosterCost, boosterSize, boosterEffect, cardDrawn, cardsToChoose)
            String statementString = "SELECT * FROM Boosters";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Booster booster = new Booster();

                booster.setCardId(rs.getInt(1));
                booster.setCardImageUrl(rs.getString(2));
                booster.setCardName(rs.getString(3));
                booster.setCardCost(rs.getInt(4));
                booster.setCardType("Booster");
                booster.setBoosterSize(rs.getString(5));
                booster.setBoosterEffect(rs.getString(6));
                booster.setBoosterSizeValue(rs.getInt(7));
                booster.setBoosterChoiceValue(rs.getInt(8));

                boosters.add(booster);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return boosters;
    }

    public static List<Joker> getAllJokers() {
        List<Joker> jokers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act, triggers, effect_keys, params)
            String statementString = "SELECT * FROM Jokers";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Joker joker = new Joker();

                joker.setCardType("Joker");
                joker.setCardId(rs.getInt(1));
                joker.setCardImageUrl(rs.getString(2));
                joker.setCardName(rs.getString(3));
                joker.setCardDescription(rs.getString(4));
                joker.setCardCost(rs.getInt(5));
                joker.setRarity(rs.getString(6));
                joker.setUnlockRequirement(rs.getString(7));
                joker.setJokerType(rs.getString(8));
                joker.setActTiming(rs.getString(9));
                joker.setTriggers(getTrigger(rs.getString(10), rs.getString(11)));
                joker.setParams(rs.getString(12));

                jokers.add(joker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jokers;
    }

    public static List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Tags";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Tag tag = new Tag();

                tag.setTagId(rs.getInt(1));
                tag.setTagImageUrl(rs.getString(2));
                tag.setTagName(rs.getString(3));
                tag.setTagBenefit(rs.getString(4));
                tag.setTagNotes(rs.getString(5));
                tag.setMinAnte(rs.getString(6));

                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tags;
    }

    public static List<Voucher> getAllVoucher() {
        List<Voucher> vouchers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, voucherImageUrl, voucherName, voucherEffect, upgradeFrom, upgradeVoucherUnlocked, note)
            String statementString = "SELECT * FROM Vouchers";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Voucher voucher = new Voucher();

                voucher.setCardId(rs.getInt(1));
                voucher.setCardImageUrl(rs.getString(2));
                voucher.setCardName(rs.getString(3));
                voucher.setCardCost(10);
                voucher.setCardType("Voucher");
                voucher.setVoucherEffect(rs.getString(4));
                voucher.setVoucherUpgradeFrom(rs.getString(5));
                voucher.setVoucherUnlockCondition(rs.getString(6));
                voucher.setVoucherNotes(rs.getString(7));
                voucher.availableProperty().set(rs.getBoolean(8));

                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vouchers;
    }

    public static List<Planet> getAllPlanets() {
        List<Planet> planets = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //
            String statementString = "SELECT * FROM Planets";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Planet planet = new Planet();

                planet.setCardType("Planet");
                planet.setCardId(rs.getInt(1));
                planet.setCardImageUrl(rs.getString(2));
                planet.setCardName(rs.getString(3));
                planet.setCardCost(3);
                planet.setPlanetAddition(rs.getString(4));
                planet.planetChipsProperty().set(rs.getInt(5));
                planet.planetMultiplierProperty().set(rs.getInt(6));
                planet.setPlanetPokerHand(rs.getString(7));
                planet.setPlanetHandBaseScore(rs.getString(8));
                planet.setSecret(rs.getBoolean(9));

                planets.add(planet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return planets;
    }

    public static List<Tarot> getAllTarots() {
        //(id, tarotImage, tarotName, tarotDescription)
        List<Tarot> tarots = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Tarots";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Tarot tarot = new Tarot();

                tarot.setCardType("Tarot");
                tarot.setCardId(rs.getInt(1));
                tarot.setCardCost(3);
                tarot.setCardImageUrl(rs.getString(2));
                tarot.setCardName(rs.getString(3));
                tarot.setTarotDescription(rs.getString(4));
                tarot.setTarotEffect(TarotEffect.valueOf(rs.getString(5)));

                tarots.add(tarot);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tarots;
    }

    public static List<Spectral> getAllSpectrals() {
        List<Spectral> spectrals = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, spectralImage, spectralName, spectralEffect)
            String statementString = "SELECT * FROM Spectrals";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Spectral spectral = new Spectral();

                spectral.setCardType("Spectral");
                spectral.setCardId(rs.getInt(1));
                spectral.setCardImageUrl(rs.getString(2));
                spectral.setCardName(rs.getString(3));
                spectral.setCardCost(3);
                spectral.setCardDescription(rs.getString(4));
                spectral.setEffect(SpectralEffect.valueOf(rs.getString(5)));

                spectrals.add(spectral);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return spectrals;
    }

    public static List<Edition> getAllEditions() {
        List<Edition> editions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, appearance, edition, effect)
            String statementString = "SELECT * FROM Editions";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Edition edition = new Edition();

                edition.setId(rs.getInt(1));
                edition.setEditionImageUrl(rs.getString(2));
                edition.setEditionName(rs.getString(3));
                edition.setEditionEffect(rs.getString(4));

                editions.add(edition);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return editions;

    }

    public static List<Enhancement> getAllEnhancements() {
        List<Enhancement> enhancements = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, appearance, enhancement, effect)
            String statementString = "SELECT * FROM Enhancements";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Enhancement enhancement = new Enhancement();

                enhancement.setEnhancementId(rs.getInt(1));
                enhancement.setEnhancementImageUrl(rs.getString(2));
                enhancement.setEnhancementName(rs.getString(3));
                enhancement.setEnhancementEffect(rs.getString(4));

                enhancements.add(enhancement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return enhancements;

    }

    public static List<Seal> getAllSeals() {
       // (id, appearance, seal, effect)
        List<Seal> seals = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, appearance, enhancement, effect)
            String statementString = "SELECT * FROM Seals";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Seal seal = new Seal();

                seal.setSealID(rs.getInt(1));
                seal.setSealImageUrl(rs.getString(2));
                seal.setSealName(rs.getString(3));
                seal.setSealEffect(rs.getString(4));

                seals.add(seal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return seals;

    }

    public static List<PokerHand> getAllPokerHands() {
        List<PokerHand> pokerHands = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM PokerHands";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                PokerHand pokerHand = new PokerHand();

                pokerHand.setId(rs.getInt(1));
                pokerHand.setName(rs.getString(2));
                pokerHand.setChips(rs.getInt(3));
                pokerHand.setMulti(rs.getInt(4));
                pokerHand.setLevel(1);
                pokerHand.setPlayed(0);

                pokerHands.add(pokerHand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pokerHands;
    }

    public static List<Sticker> getAllStickers() {
        List<Sticker> stickers = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            //(id, stickerImage, stickerName, stickerEffect)
            String statementString = "SELECT * FROM Sticker";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Sticker sticker = new Sticker();

                sticker.setStickerId(rs.getInt(1));
                sticker.setStickerImageUrl(rs.getString(2));
                sticker.setStickerName(rs.getString(3));
                sticker.setStickerDescription(rs.getString(4));


                stickers.add(sticker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stickers;
    }
    //endregion

    //region Get Profile Infos
    public static List<ProfileModel> getAllProfileModels() {
        List<ProfileModel> profileModels = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Profiles";
            //(id, profilName, bestHand, highestRound, highestAnte, bestWinStreak, wins, activeProfile)
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                ProfileModel profileModel = new ProfileModel();

                profileModel.setId(rs.getInt(1));
                profileModel.setProfileName(rs.getString(2));
                profileModel.setBestHand(BigInteger.valueOf(rs.getInt(3)));
                profileModel.setHighestRound(rs.getInt(4));
                profileModel.setHighestAnte(rs.getInt(5));
                profileModel.setBestWinStreak(rs.getInt(6));
                profileModel.setWins(rs.getInt(7));
                profileModel.setActiveProfile(rs.getBoolean(8));

                profileModel.getBlinds().setAll(getBlindIdsOfProfile(profileModel.getId()));
                profileModel.getBoosters().setAll(getBoosterIdsOfProfile(profileModel.getId()));
                profileModel.getDecks().setAll(getDeckIdsOfProfile(profileModel.getId()));
                profileModel.getEditions().setAll(getEditionIdsOfProfile(profileModel.getId()));
                profileModel.getJokers().setAll(getJokerIdsOfProfile(profileModel.getId()));
                profileModel.getUnlockedJokers().setAll(getUnlockedJokerIdsOfProfile(profileModel.getId()));
                profileModel.getPlanets().setAll(getPlanetIdsOfProfile(profileModel.getId()));
                profileModel.getSpectrals().setAll(getSpectralIdsOfProfile(profileModel.getId()));
                profileModel.getTags().setAll(getTagIdsOfProfile(profileModel.getId()));
                profileModel.getTarots().setAll(getTagIdsOfProfile(profileModel.getId()));
                profileModel.getVouchers().setAll(getVoucherIdsOfProfile(profileModel.getId()));
                profileModel.getUnlockedVouchers().setAll(getUnlockedVoucherIdsOfProfile(profileModel.getId()));

                profileModels.add(profileModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return profileModels;
    }

    public static ProfileModel getProfileModelById(int id) {
        ProfileModel profileModel = new ProfileModel();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Profiles WHERE id = " + id;
            //(id, profilName, bestHand, highestRound, highestAnte, bestWinStreak)
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                profileModel.setId(rs.getInt(1));
                profileModel.setProfileName(rs.getString(2));
                profileModel.setBestHand(BigInteger.valueOf(rs.getInt(3)));
                profileModel.setHighestRound(rs.getInt(4));
                profileModel.setHighestAnte(rs.getInt(5));
                profileModel.setBestWinStreak(rs.getInt(6));
                profileModel.setActiveProfile(rs.getBoolean(7));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return profileModel;
    }

    public static List<Integer> getBlindIdsOfProfile(int profileId) {
        List<Integer> blindIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT blindId FROM ProfileBlindDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                blindIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return blindIds;
    }

    public static List<Integer> getBoosterIdsOfProfile(int profileId) {
        List<Integer> boosterIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT boosterId FROM ProfileBoosterDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                boosterIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return boosterIds;
    }

    public static List<Integer> getDeckIdsOfProfile(int profileId) {
        List<Integer> deckIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT deckId FROM ProfileDeckUnlockedDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                deckIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deckIds;
    }

    public static List<Integer> getEditionIdsOfProfile(int profileId) {
        List<Integer> editionIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT editionId FROM ProfileEditionDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                editionIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return editionIds;
    }

    public static List<Integer> getJokerIdsOfProfile(int profileId) {
        List<Integer> jokerIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT jokerId FROM ProfileJokerDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                jokerIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jokerIds;
    }

    public static List<Integer> getUnlockedJokerIdsOfProfile(int profileId) {
        List<Integer> unlockedJokerIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT jokerId FROM ProfileJokerUnlockedDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                unlockedJokerIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return unlockedJokerIds;
    }

    public static List<Integer> getPlanetIdsOfProfile(int profileId) {
        List<Integer> planetIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT planetId FROM ProfilePlanetDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                planetIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return planetIds;
    }

    public static List<Integer> getSpectralIdsOfProfile(int profileId) {
        List<Integer> spectralIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT spectralId FROM ProfileSpectralDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                spectralIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return spectralIds;
    }

    public static List<Integer> getTagIdsOfProfile(int profileId) {
        List<Integer> tagIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT tagId FROM ProfileTagDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                tagIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tagIds;
    }

    public static List<Integer> getVoucherIdsOfProfile(int profileId) {
        List<Integer> voucherIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT voucherId FROM ProfileVoucherDiscoveryDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                voucherIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return voucherIds;
    }

    public static List<Integer> getUnlockedVoucherIdsOfProfile(int profileId) {
        List<Integer> unlockedVoucherIds = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT voucherId FROM ProfileVoucherUnlockedDetails WHERE profileId = " + profileId;

            ResultSet rs = statement.executeQuery(statementString);
            while (rs.next()) {
                unlockedVoucherIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return unlockedVoucherIds;
    }

    public static int getBeatenStakeOfDeck(int profileId,int deckId) {
        String statement = "SELECT beatenStake FROM ProfileDeckUnlockedDetails WHERE profileId = " + profileId + " AND deckId = " + deckId;
        try {
            return connection.createStatement().executeQuery(statement).getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region Profile Management
    public static void createProfile(ProfileModel profile) {
        try {
            String statementString = "UPDATE Profiles SET profileName = ?, activeProfile = 1 WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(statementString);
            preparedStatement.setString(1, profile.getProfileName());
            preparedStatement.setInt(2,profile.getId());
            preparedStatement.executeUpdate();

            Balatro.getGameModel().getProfiles().get(profile.getId() - 1).setProfile(getProfileModelById(profile.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetProfile(ProfileModel profile) {
        try {
            String resetProfileStatement = "UPDATE Profiles SET bestHand = 0, highestRound = 0, highestAnte = 0, bestWinStreak = 0 WHERE id = " + profile.getId();
            connection.createStatement().executeUpdate(resetProfileStatement);

            String resetBlindDetails = "DELETE FROM ProfileBlindDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetBlindDetails);

            String resetBoosterDetails = "DELETE FROM ProfileBoosterDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetBoosterDetails);

            String resetDeckDetails = "DELETE FROM ProfileDeckUnlockedDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetDeckDetails);

            String resetEditionDetails = "DELETE FROM ProfileEditionDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetEditionDetails);

            String resetJokerDetails = "DELETE FROM ProfileJokerDiscoveryDetails WHERE profileId = " + profile.getId() + " AND jokerId != 1";
            connection.createStatement().executeUpdate(resetJokerDetails);

            String resetJokerUnlockedDetails = "DELETE FROM ProfileJokerUnlockedDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetJokerUnlockedDetails);

            String resetPlanetDetails = "DELETE FROM ProfilePlanetDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetPlanetDetails);

            String resetSpectralDetails = "DELETE FROM ProfileSpectralDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetSpectralDetails);

            String resetTagDetails = "DELETE FROM ProfileTagDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetTagDetails);

            String resetVoucherDetails = "DELETE FROM ProfileVoucherDiscoveryDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetVoucherDetails);

            String resetVoucherUnlockDetails = "DELETE FROM ProfileVoucherUnlockedDetails WHERE profileId = " + profile.getId();
            connection.createStatement().executeUpdate(resetVoucherUnlockDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteProfile(ProfileModel profile) {
        resetProfile(profile);

        try {
            String statementString = "UPDATE Profiles SET profileName = NULL, activeProfile = 0 WHERE id = " + profile.getId();
            connection.createStatement().executeUpdate(statementString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region Discover and Unlock
    public static void discoverAllBlindsForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileBlindDiscoveryDetails VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllBoosterForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileBoosterDiscoveryDetails VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllDeckForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileDeckUnlockedDetails VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3,0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllEditionForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileEditionDiscoveryDetails VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllJokersForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileJokerDiscoveryDetails VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3,0);
            preparedStatement.setInt(4,0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unlockAllJokersForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileJokerUnlockedDetails VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllPlanetForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfilePlanetDiscoveryDetails VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3,0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllSpectralForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileSpectralDiscoveryDetails VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3,0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllTagForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileTagDiscoveryDetails VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void discoverAllVoucherForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileVoucherDiscoveryDetails VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3,0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unlockAllVoucherForProfile(ProfileModel profile, int id) {
        try {
            String statement = "INSERT INTO ProfileVoucherUnlockedDetails VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion


    private static List<JokerEffectTrigger> getTrigger(String triggers, String effect_keys) {
        String[] triggerParts = triggers.split(",");
        String[] effectParts = effect_keys.split(",");

        List<JokerEffectTrigger> jokerTriggers = new ArrayList<>();

        for (int i = 0; i < triggerParts.length; i++) {
            String triggerName = triggerParts[i].trim();
            JokerTrigger triggerEnum = JokerTrigger.valueOf(triggerName);  // String zu Enum

            String effectKeysConcat = effectParts.length > i ? effectParts[i].trim() : "";

            List<String> effectKeys = new ArrayList<>();
            if (!effectKeysConcat.isEmpty()) {
                effectKeys = Arrays.asList(effectKeysConcat.split("\\|"));
            }

            jokerTriggers.add(new JokerEffectTrigger(triggerEnum, effectKeys));
        }

        return jokerTriggers;
    }

}


//region Example
            /*stmt.executeUpdate("DROP TABLE IF EXISTS books;");
            stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);");
            stmt.execute("INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', " + Date.valueOf("2001-05-06") + ", '1234', '5.67')");

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");

            ps.setString(1, "Willi Winzig");
            ps.setString(2, "Willi's Wille");
            ps.setDate(3, Date.valueOf("2011-05-16"));
            ps.setInt(4, 432);
            ps.setDouble(5, 32.95);
            ps.addBatch();

            ps.setString(1, "Anton Antonius");
            ps.setString(2, "Anton's Alarm");
            ps.setDate(3, Date.valueOf("2009-10-01"));
            ps.setInt(4, 123);
            ps.setDouble(5, 98.76);
            ps.addBatch();

            connection.setAutoCommit(false);
            ps.executeBatch();
            connection.setAutoCommit(true);

            ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
            while (rs.next()) {
                System.out.println("Autor = " + rs.getString("author"));
                System.out.println("Titel = " + rs.getString("title"));
                System.out.println("Erscheinungsdatum = "
                        + rs.getDate("publication"));
                System.out.println("Seiten = " + rs.getInt("pages"));
                System.out.println("Preis = " + rs.getDouble("price"));
            }
            rs.close();*/

//endregion