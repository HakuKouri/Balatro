package com.example.balatro.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlHandler
{
    private static final SqlHandler sqlHandler = new SqlHandler();
    private static Connection connection;

    private static final String DB_PATH = System.getProperty("user.dir")+"/database/balatroDb.db";
    private static final String DecksTableColumns = " (id, deckCover, deckName, deckDescription, unlockRequirement, stageCleared)";
    private static final String BlindsTableColumns = " (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn)";
    private static final String StakesTableColumns = " (id, stakeIcon, stakeName, stakeEffect, unlocks)";
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

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e)
        {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    private SqlHandler(){
    }
    public static SqlHandler getInstance(){
        return sqlHandler;
    }
    private void initDBConnection() {
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
        try{
            Statement stmt = connection.createStatement();
            for (T listItem : list)
            {
                String query = "INSERT OR REPLACE INTO " + tableName + " ";

                if(listItem.getClass() == Joker.class) {
                    //(id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act)
                    query += JokerCardsTableColumns + " VALUES (?,?,?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Joker) listItem).getId());
                    ps.setString(2, ((Joker) listItem).getCardFrontCoverUrl());
                    ps.setString(3, ((Joker) listItem).getName());
                    ps.setString(4, ((Joker) listItem).getDescription());
                    ps.setInt(5, ((Joker) listItem).getCost());
                    ps.setString(6, ((Joker) listItem).getRarity());
                    ps.setString(7, ((Joker) listItem).getUnlockRequirement());
                    ps.setString(8, ((Joker) listItem).getType());
                    ps.setString(9, ((Joker) listItem).getActTiming());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Deck.class) {
                    query += DecksTableColumns + " VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, ((Deck) listItem).getId());
                    ps.setString(2, ((Deck) listItem).getDeckCoverUrl());
                    ps.setString(3, ((Deck) listItem).getName());
                    ps.setString(4, ((Deck) listItem).getDescription());
                    ps.setString(5, ((Deck) listItem).getUnlockCondition());
                    ps.setInt(6, 0);
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Blind.class) {
                    //BlindsTableColumns = " (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn)";
                    query += BlindsTableColumns + "VALUES (?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Blind) listItem).getId());
                    ps.setString(2,((Blind) listItem).getBlindImageUrl());
                    ps.setString(3,((Blind) listItem).getBlindName());
                    ps.setString(4,((Blind) listItem).getBlindDescription());
                    ps.setString(5,((Blind) listItem).getBlineMinimumAnte());
                    ps.setString(6,((Blind) listItem).getBlindScoreMultiplier());
                    ps.setString(7,((Blind) listItem).getBlindEarn());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Stake.class) {
                    //StakesTableColumns = " (id, stakeIcon, stakeName, stakeEffect, unlocks)";
                    query += StakesTableColumns + "VALUES (?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Stake) listItem).getId());
                    ps.setString(2,((Stake) listItem).getStakeImageUrl());
                    ps.setString(3,((Stake) listItem).getStakeName());
                    ps.setString(4,((Stake) listItem).getStakeEffect());
                    ps.setString(5,((Stake) listItem).getStakeUnlockCondition());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Tarot.class) {
                    //TarotCardsTableColumns = " (id, tarotImage, tarotName, tarotDescription)";
                    query += TarotCardsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Tarot) listItem).getId());
                    ps.setString(2,((Tarot) listItem).getTarotImageUrl());
                    ps.setString(3,((Tarot) listItem).getTarotName());
                    ps.setString(4,((Tarot) listItem).getTarotDescription());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Planet.class) {
                    //PlanetCardsTableColumns = " (id, planetImage, planetName, additions, pokerHand, handBaseScore, secret)";
                    query += PlanetCardsTableColumns + "VALUES (?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Planet) listItem).getId());
                    ps.setString(2,((Planet) listItem).getPlanetImageUrl());
                    ps.setString(3,((Planet) listItem).getPlanetName());
                    ps.setString(4,((Planet) listItem).getPlanetAddition());
                    ps.setString(5,((Planet) listItem).getPlanetPokerHand());
                    ps.setString(6,((Planet) listItem).getPlanetHandBaseScore());
                    ps.setBoolean(7, ((Planet) listItem).getId() >= 10);
                    ps.executeUpdate();


                }
                else if (listItem.getClass() == Spectral.class) {
                    //SpectralCardsTableColumns = " (id, spectralImage, spectralName, spectralEffect)";
                    query += SpectralCardsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Spectral) listItem).getId());
                    ps.setString(2,((Spectral) listItem).getSpectralImageUrl());
                    ps.setString(3,((Spectral) listItem).getSpectralName());
                    ps.setString(4,((Spectral) listItem).getSpectralEffect());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Voucher.class) {
                    //VoucherCardsTableColumns = " (id, baseVoucherImage, baseVoucherName, baseVoucherEffect, upgradeVoucherImage, upgradeVoucherName, upgradeVoucherEffect, upgradeVoucherUnlocked, note)";
                    query += VoucherCardsTableColumns + "VALUES (?,?,?,?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Voucher) listItem).getId());
                    ps.setString(2,((Voucher) listItem).getVoucherBaseImageUrl());
                    ps.setString(3,((Voucher) listItem).getVoucherBaseName());
                    ps.setString(4,((Voucher) listItem).getVoucherBaseEffect());
                    ps.setString(5,((Voucher) listItem).getVoucherUpgradeImageUrl());
                    ps.setString(6,((Voucher) listItem).getVoucherUpgradeName());
                    ps.setString(7,((Voucher) listItem).getVoucherUpgradeEffect());
                    ps.setString(8,((Voucher) listItem).getVoucherUpgradeUnlockCondition());
                    ps.setString(9,((Voucher) listItem).getVoucherNotes());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Tag.class) {
                    //TagsTableColumns = " (id, tagIcon, tagName, tagBenefit, tagNote, minAnte)";
                    query += TagsTableColumns + "VALUES (?,?,?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Tag) listItem).getId());
                    ps.setString(2,((Tag) listItem).getTagImageUrl());
                    ps.setString(3,((Tag) listItem).getTagName());
                    ps.setString(4,((Tag) listItem).getTagBenefit());
                    ps.setString(5,((Tag) listItem).getTagNotes());
                    ps.setString(6,((Tag) listItem).getMinAnte());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Enhancement.class) {
                    //EnhancementsTableColumns = " (id, appearance, enhancement, effect)";
                    query += EnhancementsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Enhancement) listItem).getId());
                    ps.setString(2,((Enhancement) listItem).getEnhancementImageUrl());
                    ps.setString(3,((Enhancement) listItem).getEnhancementName());
                    ps.setString(4,((Enhancement) listItem).getEnhancementEffect());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Edition.class) {
                    //EditionsTableColumns = " (id, appearance, edition, effect)";
                    query += EditionsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Edition) listItem).getId());
                    ps.setString(2,((Edition) listItem).getEditionImageUrl());
                    ps.setString(3,((Edition) listItem).getEditionName());
                    ps.setString(4,((Edition) listItem).getEditionEffect());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Seal.class) {
                    //SealsTableColumns = " (id, appearance, seal, effect)";
                    query += SealsTableColumns + "VALUES (?,?,?,?);";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1,((Seal) listItem).getId());
                    ps.setString(2,((Seal) listItem).getSealImageUrl());
                    ps.setString(3,((Seal) listItem).getSealName());
                    ps.setString(4,((Seal) listItem).getSealEffect());
                    ps.executeUpdate();
                }
                else if (listItem.getClass() == Language.class) {
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
        try
        {
            Statement stmt = connection.createStatement();
            String statement = "SELECT deckName FROM Decks where id = " + id;
            ResultSet rs = stmt.executeQuery(statement);
            while (rs.next()) {
                return rs.getString("deckName");
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return "";
    }

    public static Deck getDeck(int id)
    {
        return new Deck();
    }

    public static List<Deck> getAllDecks()
    {
        List<Deck> deckList = new ArrayList<>();

        try
        {
            Statement statement = connection.createStatement();
            String statementString = "SELECT * FROM Decks";
            ResultSet rs = statement.executeQuery(statementString);

            while (rs.next()) {
               Deck deck = new Deck();
               deck.setId(rs.getInt(1));
               deck.setDeckCoverUrl(rs.getString(2));
               deck.setName(rs.getString(3));
               deck.setDescription(rs.getString(4));
               deck.setUnlockCondition(rs.getString(5));
               deck.setStageCleared(rs.getInt(6));
               deckList.add(deck);
            }

        } catch (SQLException e)
        {
            System.out.println("GetAllDecks");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


        return deckList;
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