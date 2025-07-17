package com.example.balatro.domain.card;

public class Enhancement
{
    private int enhancementId;
    private String enhancementImageUrl;
    private String enhancementName;
    private String enhancementEffect;

    public Enhancement() {
        setEnhancementId(-1);
        setEnhancementImageUrl("");
        setEnhancementName("");
        setEnhancementEffect("");
    }


    public int getEnhancementId()
    {
        return enhancementId;
    }

    public void setEnhancementId(int enhancementId)
    {
        this.enhancementId = enhancementId;
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

    public Enhancement copy() {
        Enhancement enhancement = new Enhancement();
        enhancement.setEnhancementId(enhancementId);
        enhancement.setEnhancementImageUrl(enhancementImageUrl);
        enhancement.setEnhancementName(enhancementName);
        enhancement.setEnhancementEffect(enhancementEffect);
        return enhancement;
    }
}
