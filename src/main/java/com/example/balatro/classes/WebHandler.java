package com.example.balatro.classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebHandler
{
    public static void getTable() {
        try
        {
            Document doc = Jsoup.connect("https://balatrogame.fandom.com/wiki/Jokers").get();
            System.out.println(doc.title());
            Elements tables = doc.select("table");
            Element table = tables.get(1);
            Elements rows = table.select("tr");
            for (Element row : rows)
            {
                Elements cells = row.select("td");
                for (Element cell : cells )
                {
                    System.out.println(cell);
                }

            }

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
