package com.example.balatro.classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WebHandler
{
    private static final String urlMain = "https://balatrogame.fandom.com/wiki/";

    public static void WebHandlerMain() {
    }
    public static void setupDb() {
        getBlindWithWebHandler();
        getDeckWithWebHandler();
        getEditionWithWebHandler();
        getEnhancementWithWebHandler();
        getJokerWithWebHandler();
        getPlanetWithWebHandler();
        getSpectralWithWebHandler();
        getStakeWithWebHandler();
        getTagWithWebHandler();
        getTarotWithWebHandler();
        getVoucherWithWebHandler();
        getSealWithWebHandler();
    }

    private static void getJokerWithWebHandler() {
        ArrayList<Joker> arrayList = new ArrayList<Joker>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Jokers").get().select("table").get(1).select("tbody > tr");
            for (Element row : rows)
            {
                Joker joker = new Joker();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    joker.setCardId(Integer.parseInt(cells.get(0).text()));
                    joker.setName(cells.get(1).childNode(0).attr("title"));
                    joker.setCardFrontCoverUrl("src\\main\\resources\\com\\images\\Jokers\\joker_" + joker.getCardId() + ".png");
                    if(joker.getCardId()== 70) joker.setCardFrontCoverUrl(joker.getCardFrontCoverUrl().replace(".png", ".gif"));
                    joker.setDescription(cells.get(2).text());
                    try {
                        joker.setCost(Integer.parseInt(cells.get(3).text().substring(1)));
                    } catch (Exception e) {
                        joker.setCost(20);
                    }
                    String rarityText = cells.get(4).childNode(1).attr("title");
                    joker.setRarity(rarityText.substring(9, rarityText.indexOf(" ")));
                    joker.setUnlockRequirement(cells.get(5).text());
                    joker.setType(cells.get(6).text());
                    joker.setActTiming(cells.get(7).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Joker: ");
                    System.out.println(e);
                }
                arrayList.add(joker);
            }
            SqlHandler.ListToSql(arrayList, "JokerCards");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    private static void getDeckWithWebHandler() {
        ArrayList<Deck> arrayList = new ArrayList<Deck>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Decks").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Deck deck = new Deck();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    deck.setId(arrayList.size()+1);
                    deck.setDeckCoverUrl("src\\main\\resources\\com\\images\\DeckBacks\\deckBack_" + deck.getId() + ".png");
                    deck.setName(cells.get(1).text());
                    deck.setDescription(cells.get(2).text());
                    deck.setUnlockCondition(cells.get(3).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!!");
                    System.out.println(e);
                }
                arrayList.add(deck);
            }
            SqlHandler.ListToSql(arrayList, "Decks");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    private static void getEditionWithWebHandler() {
        ArrayList<Edition> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Card_Modifiers").get().select("table").get(1).select("tbody > tr");
            for (Element row : rows)
            {
                Edition edition = new Edition();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    edition.setId(arrayList.size()+1);
                    edition.setEditionImageUrl("");
                    edition.setEditionName(cells.get(1).text());
                    edition.setEditionEffect(cells.get(2).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Editions: ");
                    System.out.println(e);
                }
                arrayList.add(edition);
            }
            SqlHandler.ListToSql(arrayList, "Editions");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    private static void getBlindWithWebHandler() {
        ArrayList<Blind> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Blinds_and_Antes").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Blind blind = new Blind();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    blind.setId(arrayList.size()+1);
                    blind.setBlindImageUrl("src\\main\\resources\\com\\images\\Blinds\\blind_" + blind.getId() + ".gif");
                    blind.setBlindName(cells.get(1).text());
                    blind.setBlindDescription(cells.get(2).text());
                    blind.setBlineMinimumAnte(cells.get(3).text());
                    blind.setBlindScoreMultiplier(cells.get(4).text());
                    blind.setBlindEarn(cells.get(5).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Blinds: ");
                    System.out.println(e);
                }
                arrayList.add(blind);
            }
            SqlHandler.ListToSql(arrayList, "Blinds");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getStakeWithWebHandler() {
        ArrayList<Stake> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Stakes").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Stake stake = new Stake();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    stake.setId(arrayList.size()+1);
                    stake.setStakeImageStickerUrl("src\\main\\resources\\com\\images\\Stickers_Seals\\difficult_" + stake.getId() + ".png");
                    stake.setStakeImageChipUrl("src\\main\\resources\\com\\images\\Stakechips\\stake_chip_" + stake.getId() + ".png");
                    stake.setStakeName(cells.get(2).text());
                    stake.setStakeEffect(cells.get(3).text());
                    stake.setStakeUnlockCondition(cells.get(4).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Stakes: ");
                    System.out.println(e);
                }
                arrayList.add(stake);
            }
            SqlHandler.ListToSql(arrayList, "Stakes");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getTarotWithWebHandler() {
        ArrayList<Tarot> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Tarot_Cards").get().select("table").get(1).select("tbody > tr");
            for (Element row : rows)
            {
                Tarot tarot = new Tarot();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    tarot.setCardId(arrayList.size()+1);
                    tarot.setTarotImageUrl("src\\main\\resources\\com\\images\\Tarots_Celestial_Spectral\\tarot_" + tarot.getCardId() + ".png");
                    tarot.setTarotName(cells.get(1).text());
                    tarot.setTarotDescription(cells.get(2).text());

                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Tarots: ");
                    System.out.println(e);
                }
                arrayList.add(tarot);
            }
            SqlHandler.ListToSql(arrayList, "TarotCards");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getPlanetWithWebHandler() {
        ArrayList<Planet> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Planet_Cards").get().select("table").get(1).select("tbody > tr");
            for (Element row : rows)
            {
                Planet planet = new Planet();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    planet.setCardId(arrayList.size()+1);
                    planet.setPlanetImageUrl("src\\main\\resources\\com\\images\\Tarots_Celestial_Spectral\\planet_" + planet.getCardId() + ".png");
                    planet.setPlanetName(cells.get(1).text());
                    planet.setPlanetAddition(cells.get(2).text());
                    planet.setPlanetPokerHand(cells.get(3).text());
                    planet.setPlanetHandBaseScore(cells.get(4).text());

                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Planets: ");
                    System.out.println(e);
                }
                arrayList.add(planet);
            }
            SqlHandler.ListToSql(arrayList, "PlanetCards");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getSpectralWithWebHandler() {
        ArrayList<Spectral> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Spectral_Cards").get().select("table").get(1).select("tbody > tr");
            for (Element row : rows)
            {
                Spectral spectral = new Spectral();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    spectral.setCardId(arrayList.size()+1);
                    spectral.setSpectralImageUrl("src\\main\\resources\\com\\images\\Tarots_Celestial_Spectral\\spectral_" + spectral.getCardId() + ".png");
                    spectral.setSpectralName(cells.get(1).text());
                    spectral.setSpectralEffect(cells.get(2).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Spectrals: ");
                    System.out.println(e);
                }
                arrayList.add(spectral);
            }
            SqlHandler.ListToSql(arrayList, "SpectralCards");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getVoucherWithWebHandler() {
        ArrayList<Voucher> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Vouchers").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Voucher voucher = new Voucher();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    voucher.setCardId(arrayList.size()+1);
                    voucher.setVoucherBaseImageUrl("src\\main\\resources\\com\\images\\Vouchers\\voucher_" + voucher.getCardId() + ".png");
                    voucher.setVoucherBaseName(cells.get(0).text());
                    voucher.setVoucherUpgradeImageUrl("src\\main\\resources\\com\\images\\Vouchers\\voucher_upgrade_" + voucher.getCardId() + ".png");
                    voucher.setVoucherUpgradeName(cells.get(2).text());
                    voucher.setVoucherBaseEffect(cells.get(1).text());
                    voucher.setVoucherUpgradeEffect(cells.get(3).text());
                    voucher.setVoucherUpgradeUnlockCondition(cells.get(4).text());
                    voucher.setVoucherNotes(cells.get(5).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Vouchers: ");
                    System.out.println(e);
                }
                arrayList.add(voucher);
            }
            SqlHandler.ListToSql(arrayList, "VoucherCards");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getTagWithWebHandler() {
        ArrayList<Tag> arrayList = new ArrayList<>();
        try
        {
            String prevRow = "";
            Elements rows = Jsoup.connect(urlMain + "Tags").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Tag tag = new Tag();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;

                if(cells.size() == 5)
                   prevRow = cells.get(3).text();
                try
                {
                    tag.setTagId(arrayList.size()+1);
                    tag.setTagImageUrl("src\\main\\resources\\com\\images\\tags\\tag_" + tag.getTagId() + ".png");
                    tag.setTagName(cells.get(1).text());
                    tag.setTagBenefit(cells.get(2).text());
                    if(cells.size() == 4)
                    {
                        tag.setTagNotes(prevRow);
                        tag.setMinAnte(cells.get(3).text());
                    }
                    else {
                        tag.setTagNotes(cells.get(3).text());
                        tag.setMinAnte(cells.get(4).text());
                    }
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Tags: ");
                    System.out.println(e);
                }
                arrayList.add(tag);
            }
            SqlHandler.ListToSql(arrayList, "Tags");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    private static void getEnhancementWithWebHandler() {
        ArrayList<Enhancement> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Card_Modifiers").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Enhancement enhancement = new Enhancement();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    enhancement.setId(arrayList.size()+1);
                    enhancement.setEnhancementImageUrl("src\\main\\resources\\com\\images\\Enhancers\\Enhancers" + enhancement.getId() + ".png");
                    enhancement.setEnhancementName(cells.get(1).text());
                    enhancement.setEnhancementEffect(cells.get(2).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Enhancements:");
                    System.out.println(e);
                }
                arrayList.add(enhancement );
            }
            SqlHandler.ListToSql(arrayList, "Enhancements");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    private static void getSealWithWebHandler() {
        ArrayList<Seal> arrayList = new ArrayList<>();
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Card_Modifiers").get().select("table").get(0).select("tbody > tr");
            for (Element row : rows)
            {
                Seal seal = new Seal();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    seal.setId(arrayList.size()+1);
                    seal.setSealImageUrl("src\\main\\resources\\com\\images\\Stickers_Seals\\stamp_" + seal.getId() + ".png");
                    seal.setSealName(cells.get(1).text());
                    seal.setSealEffect(cells.get(2).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Seals:");
                    System.out.println(e);
                }
                arrayList.add(seal);
            }
            SqlHandler.ListToSql(arrayList, "Seals");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
    public static void getBoosterWithWebHandler() {
        ArrayList<Booster> arrayList = new ArrayList<>();
        String boosterName = "";
        try
        {
            Elements rows = Jsoup.connect(urlMain + "Booster_Packs").get().select("table").get(0).select("tbody > tr");

            for (Element row : rows)
            {
                if(!row.select("th").text().isEmpty()) {
                    boosterName = row.select("th").text();
                }
                Booster booster = new Booster();

                Elements cells = row.select("td");
                if(cells.size() == 0) continue;
                try
                {
                    booster.setBoosterId(arrayList.size()+1);
                    booster.setboosterImageUrl("src\\main\\resources\\com\\images\\BoosterPacks\\booster_" + booster.getId() + ".png");
                    booster.setBoosterCost(cells.get(1).text());
                    booster.setBoosterSize(cells.get(2).text());
                    booster.setBoosterEffect(cells.get(3).text());
                }
                catch (Exception e) {
                    System.out.println("Fehler aufgetreten!! Booster:");
                    System.out.println(e);
                }
                arrayList.add(booster);
            }
            SqlHandler.ListToSql(arrayList, "Boosters");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
}

//region Old Code

//String imageUrl =Jsoup.connect(urlMain+joker.getName()).get().select("a.image image-thumbnail").attr("src");
//String imageUrl = Jsoup.connect(urlMain+joker.getName()).get().select("figure a").attr("href");
//System.out.println(imageUrl);
//joker.getName().toLowerCase().replace(" ", "_")
//BufferedImage bi = ImageIO.read(new URL(imageUrl));
//ImageIO.write(bi, "png", new File("src\\main\\resources\\com\\images\\cardCovers\\" + joker.getName().toLowerCase().replace(" ", "_") + ".png"));*/

//endregion