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
            Elements tables = doc.select("table").;
            for (Element table : tables) {
                System.out.println(String.format("%s\n", table.attr("class")));
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
