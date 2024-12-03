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
    public static void getJokerWithWebHanlder() {
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
                    joker.setId(Integer.parseInt(cells.get(0).text()));
                    joker.setName(cells.get(1).childNode(0).attr("title"));
                    joker.setCardFrontCoverUrl("src\\main\\resources\\com\\images\\Jokers\\joker_" + joker.getId() + ".png");
                    if(joker.getId()== 70) joker.setCardFrontCoverUrl(joker.getCardFrontCoverUrl().replace(".png", ".gif"));
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
                    System.out.println("Fehleraufgetreten!!");
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
    public static void getDeckWithWebHanlder() {
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
                    deck.setDeckCoverUrl("src\\main\\resources\\com\\images\\Deck Backs\\deckBack_" + deck.getId() + ".png");
                    deck.setName(cells.get(1).childNode(0).attr("title"));
                    deck.setDescription(cells.get(2).text());
                    deck.setUnlockCondition(cells.get(3).text());
                }
                catch (Exception e) {
                    System.out.println("Fehleraufgetreten!!");
                    System.out.println(e);
                }
                arrayList.add(deck);
            }
            SqlHandler.ListToSql(arrayList, "JokerCards");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void getEditionWithWebHanlder() {
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
                    edition.setCardImageUrl("src\\main\\resources\\com\\images\\Deck Backs\\deckBack_" + edition.getId() + ".png");
                    edition.setName(cells.get(1).childNode(0).attr("title"));
                    edition.setEffect(cells.get(2).text());

                }
                catch (Exception e) {
                    System.out.println("Fehleraufgetreten!!");
                    System.out.println(e);
                }
                arrayList.add(edition);
            }
            SqlHandler.ListToSql(arrayList, "JokerCards");
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