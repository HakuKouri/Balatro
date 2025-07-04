package com.example.balatro.domain.card;

public class Enhancement
{
    private int id;
    private String enhancementImageUrl;
    private String enhancementName;
    private String enhancementEffect;

    public Enhancement() {
        setId(-1);
        setEnhancementImageUrl("");
        setEnhancementName("");
        setEnhancementEffect("");
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getEnhancementImageUrl()
    {
        return enhancementImageUrl;
    }

    public void setEnhancementImageUrl(String enhancementImageUrl)
    {
        this.enhancementImageUrl = enhancementImageUrl;
    }

    public String getEnhancementName()
    {
        return enhancementName;
    }

    public void setEnhancementName(String enhancementName)
    {
        this.enhancementName = enhancementName;
    }

    public String getEnhancementEffect()
    {
        return enhancementEffect;
    }

    public void setEnhancementEffect(String enhancementEffect)
    {
        this.enhancementEffect = enhancementEffect;
    }
}
