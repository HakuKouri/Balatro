package com.example.balatro.classes;

import javafx.scene.paint.Color;

public class Blind
{
    private int id;
    private String blindImageUrl;
    private String blindName;
    private String blindDescription;
    private String blineMinimumAnte;
    private String blindScoreMultiplier;
    private String blindEarn;
    private boolean blindSkipped;
    private Color colorScheme;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getBlindImageUrl()
    {
        return blindImageUrl;
    }

    public void setBlindImageUrl(String blindImageUrl)
    {
        this.blindImageUrl = blindImageUrl;
    }

    public String getBlindName()
    {
        return blindName;
    }

    public void setBlindName(String blindName)
    {
        this.blindName = blindName;
    }

    public String getBlindDescription()
    {
        return blindDescription;
    }

    public void setBlindDescription(String blindDescription)
    {
        this.blindDescription = blindDescription;
    }

    public String getBlineMinimumAnte()
    {
        return blineMinimumAnte;
    }

    public void setBlineMinimumAnte(String blineMinimumAnte)
    {
        this.blineMinimumAnte = blineMinimumAnte;
    }

    public String getBlindScoreMultiplier()
    {
        return blindScoreMultiplier;
    }

    public void setBlindScoreMultiplier(String blindScoreMultiplier) {
        this.blindScoreMultiplier = blindScoreMultiplier;
    }

    public String getBlindEarn()
    {
        return blindEarn;
    }

    public void setBlindEarn(String blindEarn)
    {
        this.blindEarn = blindEarn;
    }

    public boolean getBlindSkipped() {
        return blindSkipped;
    }

    public void setBlindSkipped(boolean blindSkipped) {
        this.blindSkipped = blindSkipped;
    }

    public Color getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String string) {
        this.colorScheme = Color.web(string);
    }
}
