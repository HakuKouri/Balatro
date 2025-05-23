package com.example.balatro.classes;

import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SqlHandler {
    private static final SqlHandler sqlHandler = new SqlHandler();
    private static Connection connection;

    private static final String DB_PATH = System.getProperty("user.dir") + "/database/balatrodb.db";
    private static final String DecksTableColumns = " (id, deckCover, deckName, deckDescription, unlockRequirement, stageCleared)";
    private static final String BlindsTableColumns = " (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn)";
    private static final String StakesTableColumns = " (id, stakeStickerUrl, stakeChipUrl, stakeName, stakeEffect, unlocks)";
    private static final String JokerCardsTableColumns = " (id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act)";
    private static final String TarotCardsTableColumns = " (id, tarotImage, tarotName, tarotDescription)";
    private static final String PlanetCardsTableColumns = " (id, planetImage, planetName, additions, pokerHand, handBaseScore, secret)";
    private static final String SpectralCardsTableColumns = " (id, spectralImage, spectralName, spectralEffect)";
    private static final String VoucherCardsTableColumns = " (id, baseVoucherImage, baseVoucherName, baseVoucherEffect, upgradeVoucherImage, upgradeVoucherName, upgradeVoucherEffect, upgradeVoucherUnlocked, note)";
    private static final String TagsTableColumns = " (id, tagIcon, tagName, tagBenefit, tagNote, minAnte)";
    private static final String EnhancementsTableColumns = " (id, appearance, enhancement, effect)";
    private static final String EditionsTableColumns = " (id, appearance, edition, effect)";
    private static final String SealsTableColumns = " (id, appearance, seal, effect)";
    private static final String LanguagesTableColumns = " (id, languageName, text, notes)";
    private static final String LangNameDetailsTableColumns = " (idLanguage, targetTable, targetId)";
    private static final String BoostersTableColumns = " (id, boosterImage, boosterName, boosterCost, boosterSize, boosterEffect)";
    private static final String PokerHandsTableColumns = " (pokerHandId, pokerHandName, pokerHandChips, pokerHandMulti)";

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
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS JokerCards " + JokerCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TarotCards " + TarotCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PlanetCards " + PlanetCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS SpectralCards " + SpectralCardsTableColumns + ";");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS VoucherCards " + VoucherCardsTableColumns + ";");
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
                    ps.setString(2, ((Joker) listItem).getCardFrontCoverUrl());
                    ps.setString(3, ((Joker) listItem).getName());
                    ps.setString(4, ((Joker) listItem).getDescription());
                    ps.setInt(5, ((Joker) listItem).getCost());
                    ps.setString(6, ((Joker) listItem).getRarity());
                    ps.setString(7, ((Joker) listItem).getUnlockRequirement());
                    ps.setString(8, ((Joker) listItem).getType());
                    ps.setString(9, ((Joker) listItem).getActTiming());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Deck.class) {
                    query += DecksTableColumns + " VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Deck) listItem).getDeckId());
                    ps.setString(2, ((Deck) listItem).getDeckCoverUrl());
                    ps.setString(3, ((Deck) listItem).getDeckName());
                    ps.setString(4, ((Deck) listItem).getDeckDescription());
                    ps.setString(5, ((Deck) listItem).getUnlockCondition());
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
                    ps.setString(5, ((Blind) listItem).getBlindMinimumAnte());
                    ps.setString(6, ((Blind) listItem).getBlindScoreMultiplier());
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
                    ps.setString(5, ((Stake) listItem).getStakeEffect());
                    ps.setString(6, ((Stake) listItem).getStakeUnlockCondition());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Tarot.class) {
                    //TarotCardsTableColumns = " (id, tarotImage, tarotName, tarotDescription)";
                    query += TarotCardsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Tarot) listItem).getCardId());
                    ps.setString(2, ((Tarot) listItem).getTarotImageUrl());
                    ps.setString(3, ((Tarot) listItem).getTarotName());
                    ps.setString(4, ((Tarot) listItem).getTarotDescription());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Planet.class) {
                    //PlanetCardsTableColumns = " (id, planetImage, planetName, additions, pokerHand, handBaseScore, secret)";
                    query += PlanetCardsTableColumns + "VALUES (?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Planet) listItem).getCardId());
                    ps.setString(2, ((Planet) listItem).getPlanetImageUrl());
                    ps.setString(3, ((Planet) listItem).getPlanetName());
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
                    ps.setString(4, ((Spectral) listItem).getSpectralEffect());
                    ps.executeUpdate();
                } else if (listItem.getClass() == Voucher.class) {
                    //VoucherCardsTableColumns = " (id, baseVoucherImage, baseVoucherName, baseVoucherEffect, upgradeVoucherImage, upgradeVoucherName, upgradeVoucherEffect, upgradeVoucherUnlocked, note)";
                    query += VoucherCardsTableColumns + "VALUES (?,?,?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Voucher) listItem).getCardId());
                    ps.setString(2, ((Voucher) listItem).getVoucherBaseImageUrl());
                    ps.setString(3, ((Voucher) listItem).getVoucherBaseName());
                    ps.setString(4, ((Voucher) listItem).getVoucherBaseEffect());
                    ps.setString(5, ((Voucher) listItem).getVoucherUpgradeImageUrl());
                    ps.setString(6, ((Voucher) listItem).getVoucherUpgradeName());
                    ps.setString(7, ((Voucher) listItem).getVoucherUpgradeEffect());
                    ps.setString(8, ((Voucher) listItem).getVoucherUpgradeUnlockCondition());
                    ps.setString(9, ((Voucher) listItem).getVoucherNotes());
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
                    ps.setInt(1, ((Enhancement) listItem).getId());
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
                    ps.setInt(1, ((Seal) listItem).getId());
                    ps.setString(2, ((Seal) listItem).getSealImageUrl());
                    ps.setString(3, ((Seal) listItem).getSealName());
                    ps.setString(4, ((Seal) listItem).getSealEffect());
                    ps.executeUpdate();
                }
                if (listItem.getClass() == Booster.class) {
                    //(id, boosterImage, boosterName, boosterCost, boosterSize, boosterEffect)
                    query += BoostersTableColumns + " VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Booster) listItem).getBoosterId());
                    ps.setString(2, ((Booster) listItem).getboosterImageUrl());
                    ps.setString(3, ((Booster) listItem).getBoosterName());
                    ps.setString(4, ((Booster) listItem).getBoosterEffect());
                    ps.setString(5, ((Booster) listItem).getBoosterSize());
                    ps.setString(6, ((Booster) listItem).getBoosterEffect());
                    ps.executeUpdate();
                }else if (listItem.getClass() == Language.class) {
                    //LanguagesTableColumns = " (id, languageName, text, notes)";
                }
            }
        } catch (SQLException e) {
            System.out.println(tableName);
            System.out.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static String getDeckName(int id) {
        try {
            Statement stmt = connection.createStatement();
            String statement = "SELECT deckName FROM Decks where id = " + id;
            ResultSet rs = stmt.executeQuery(statement);
            while (rs.next()) {
                return rs.getString("deckName");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static Deck getDeck(int id) {
        return new Deck();
    }

    public static List<Deck> getAllDecks() {
        List<Deck> deckList = new ArrayList<>();

        try {
            System.out.println(connection.isValid(2));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Decks";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Deck deck = new Deck();
                deck.setDeckId(rs.getInt(1));
                deck.setDeckCoverUrl(rs.getString(2));
                deck.setDeckName(rs.getString(3));
                deck.setDeckDescription(rs.getString(4));
                deck.setUnlockCondition(rs.getString(5));
                deck.setStageCleared(rs.getInt(6));
                deckList.add(deck);
            }

        } catch (SQLException e) {
            System.out.println("GetAllDecks");
            System.out.println(e.getMessage());
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException(e);
        }

        return deckList;
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
                stake.setStakeEffect(rs.getString(5));
                stake.setStakeUnlockCondition(rs.getString(6));

                stakeList.add(stake);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stakeList;
    }

    public static List<Blind> getAllBlinds() {
        List<Blind> blinds = new ArrayList<>();

        try
        {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Blinds";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Blind blind = new Blind();

                blind.setBlindId(rs.getInt(1));
                blind.setBlindImageUrl(rs.getString(2));
                blind.setBlindName(rs.getString(3));
                blind.setBlindDescription(rs.getString(4));
                blind.setBlindMinimumAnte(rs.getString(5));
                blind.setBlindScoreMultiplier(rs.getString(6));
                blind.setBlindReward(Integer.parseInt(rs.getString(7).substring(1)));
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

        try
        {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Boosters";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Booster booster = new Booster();

                booster.setBoosterId (rs.getInt(1));
                booster.setboosterImageUrl(rs.getString(2));
                booster.setBoosterName(rs.getString(3));
                booster.setBoosterSize(rs.getString(4));
                booster.setBoosterCost(rs.getString(5));
                booster.setBoosterEffect(rs.getString(6));

                boosters.add(booster);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return boosters;
    }

    public static List<Joker> getAllJokers() {
        List<Joker> jokers = new ArrayList<>();

        try
        {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM JokerCards";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Joker joker = new Joker();

                joker.setCardId(rs.getInt(1));
                joker.setJokerImageUrl(rs.getString(2));
                joker.setName(rs.getString(3));
                joker.setJokerEffect(rs.getString(4));
                joker.setCost(rs.getInt(5));
                joker.setRarity(rs.getString(6));
                joker.setUnlockRequirement(rs.getString(7));
                joker.setType(rs.getString(8));
                joker.setActTiming(rs.getString(9));

                jokers.add(joker);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jokers;
    }

    public static List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();

        try
        {
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

        try
        {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM VoucherCards";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Voucher voucher = new Voucher();

                voucher.setVoucherId(rs.getInt(1));
                voucher.setVoucherBaseImageUrl(rs.getString(2));
                voucher.setVoucherBaseName(rs.getString(3));
                voucher.setVoucherBaseEffect(rs.getString(4));
                voucher.setVoucherUpgradeImageUrl(rs.getString(5));
                voucher.setVoucherUpgradeName(rs.getString(6));
                voucher.setVoucherUpgradeEffect(rs.getString(6));
                voucher.setVoucherUpgradeUnlockCondition(rs.getString(7));
                voucher.setVoucherNotes(rs.getString(8));

                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vouchers;
    }

    public static List<Planet> getAllPlanets() {
        List<Planet> planets = new ArrayList<>();

        try
        {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM PlanetCards";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
                Planet planet = new Planet();

                planet.setPlanetId(rs.getInt(1));
                planet.setPlanetImageUrl(rs.getString(2));
                planet.setPlanetName(rs.getString(3));
                planet.setPlanetAddition(rs.getString(4));
                planet.setPlanetPokerHand(rs.getString(5));
                planet.setPlanetHandBaseScore(rs.getString(6));
                planet.setSecret(!Objects.equals(rs.getString(6), "0"));

                planets.add(planet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return planets;
    }

    public static List<PokerHand> getAllPokerHands() {
        List<PokerHand> pokerHands = new ArrayList<>();

        try
        {
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