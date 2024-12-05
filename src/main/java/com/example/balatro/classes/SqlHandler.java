package com.example.balatro.classes;

import java.sql.*;
import java.util.ArrayList;

public class SqlHandler
{
    private static final SqlHandler sqlHandler = new SqlHandler();
    private static Connection connection;

    private static final String DB_PATH = System.getProperty("user.dir")+"/database/balatrodb.db";
    private static final String DecksTableColumns = " (id, deckCover, deckName, deckDesription, unlockRequirement)";
    private static final String BlindsTableColumns = " (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn)";
    private static final String StakesTableColumns = " (id, stakeIcon, stakeName, stakeEffect, unlocks)";
    private static final String JokerCardsTableColumns = " (id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act)";
    private static final String TarotCardsTableColumns = " (id, tarotImage, tarotName, tarotDescription)";
    private static final String PlanetCardsTableColumns = " (id, planetImage, planetName, additions, pokerHand, handBaseScore)";
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

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Decks (id, deckCover, deckName, deckDesription, unlockRequirement);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Blinds (id, blindIcon, blindName, blindDescription, minimumAnte, minimumScore, earn);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Stakes (id, stakeIcon, stakeName, stakeEffect, unlocks);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS JokerCards (id, jokerImage, jokerName, jokerEffect, cost, rarity, unlockRequirement, jokerType, act);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TarotCards (id, tarotImage, tarotName, tarotDescription);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PlanetCards (id, planetImage, planetName, additions, pokerHand, handBaseScore);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS SpectralCards (id, spectralImage, spectralName, spectralEffect);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS VoucherCards (id, baseVoucherImage, baseVoucherName, baseVoucherEffect, upgradeVoucherImage, upgradeVoucherName, upgradeVoucherEffect, upgradeVoucherUnlocked, note);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Tags (id, tagIcon, tagName, tagBenefit, tagNote, minAnte);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Enhancements (id, appearance, enhancement, effect);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Editions (id, appearance, edition, effect);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Seals(id, appearance, seal, effect);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Languages(id, languageName, text, notes);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LangNameDetails(idLanguage, targetTable, targetId);");

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
                } else if (listItem.equals(Deck.class)) {
                    //columnsString = DecksTableColumns;
                } else if (listItem.equals(Blind.class)) {
                    //columnsString = BlindsTableColumns;
                } else if (listItem.equals(Stake.class)) {
                    //columnsString = StakesTableColumns;
                } else if (listItem.equals(Tarot.class)) {
                    //columnsString = TarotCardsTableColumns;
                } else if (listItem.equals(Planet.class)) {
                    //columnsString = PlanetCardsTableColumns;
                } else if (listItem.equals(Spectral.class)) {
                    //columnsString = SpectralCardsTableColumns;
                } else if (listItem.equals(Voucher.class)) {
                   // columnsString = VoucherCardsTableColumns;
                } else if (listItem.equals(Tag.class)) {
                    //columnsString = TagsTableColumns;
                } else if (listItem.equals(Enhancement.class)) {
                    //columnsString = EnhancementsTableColumns;
                } else if (listItem.equals(Edition.class)) {
                    //columnsString = EditionsTableColumns;
                } else if (listItem.equals(Seal.class)) {
                    //columnsString = SealsTableColumns;
                } else if (listItem.equals(Language.class)) {
                    //columnsString = LanguagesTableColumns;
                }
            }

        } catch (SQLException e) {
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

}
