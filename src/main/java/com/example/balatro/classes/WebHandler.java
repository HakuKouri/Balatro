package com.example.balatro.classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WebHandler
{
    private static final String urlMain = "https://balatrogame.fandom.com/wiki/";

    public static void getTable() {
        try
        {
            ArrayList<Joker> jokerArrayList = new ArrayList<Joker>();
            Elements rows = Jsoup.connect(urlMain + "Jokers").get().select("table").get(1).select("tr");
            for (Element row : rows)
            {
                Joker joker = new Joker();
                Elements cells = row.select("td");
                try
                {
                    joker.setId(Integer.parseInt(cells.get(0).text()));
                    joker.setName(cells.get(1).childNode(0).attr("title"));

                    //String imageUrl =Jsoup.connect(urlMain+joker.getName()).get().select("a.image image-thumbnail").attr("src");
                    String imageUrl = Jsoup.connect(urlMain+joker.getName()).get().select("figure a").attr("href");
                    System.out.println(imageUrl);

                    //joker.getName().toLowerCase().replace(" ", "_")
                    BufferedImage bi = ImageIO.read(new URL(imageUrl));
                    ImageIO.write(bi, "png", new File("src\\main\\resources\\com\\images\\cardCovers\\" + joker.getName().toLowerCase().replace(" ", "_") + ".png"));

                }
                catch (Exception e) {
                    System.out.println("Fehleraufgetreten!!");
                    System.out.println(e);
                }


                //jokerArrayList
            }

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
