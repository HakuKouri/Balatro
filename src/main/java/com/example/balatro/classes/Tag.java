package com.example.balatro.classes;

public class Tag
{
    private int id;
    private String tagImageUrl;
    private String tagName;
    private String tagBenefit;
    private String tagNotes;
    private String minAnte;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTagImageUrl()
    {
        return tagImageUrl;
    }

    public void setTagImageUrl(String tagImageUrl)
    {
        this.tagImageUrl = tagImageUrl;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public String getTagBenefit()
    {
        return tagBenefit;
    }

    public void setTagBenefit(String tagBenefit)
    {
        this.tagBenefit = tagBenefit;
    }

    public String getTagNotes()
    {
        return tagNotes;
    }

    public void setTagNotes(String tagNotes)
    {
        this.tagNotes = tagNotes;
    }

    public String getMinAnte()
    {
        return minAnte;
    }

    public void setMinAnte(String minAnte)
    {
        this.minAnte = minAnte;
    }
}
