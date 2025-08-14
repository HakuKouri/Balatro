package com.example.balatro.domain.card;

public class Edition
{
    private int id;
    private String editionImageUrl;
    private String editionName;
    private String editionEffect;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getEditionImageUrl()
    {
        return editionImageUrl;
    }

    public void setEditionImageUrl(String editionImageUrl)
    {
        this.editionImageUrl = editionImageUrl;
    }

    public String getEditionName()
    {
        return editionName;
    }

    public void setEditionName(String editionName)
    {
        this.editionName = editionName;
    }

    public String getEditionEffect()
    {
        return editionEffect;
    }

    public void setEditionEffect(String editionEffect)
    {
        this.editionEffect = editionEffect;
    }

    public Edition copy() {
        Edition edition = new Edition();
        edition.setId(id);
        edition.setEditionImageUrl(editionImageUrl);
        edition.setEditionName(editionName);
        edition.setEditionEffect(editionEffect);
        return edition;
    }

    public void setEdition(Edition edition) {
        setId(edition.getId());
        setEditionImageUrl(edition.getEditionImageUrl());
        setEditionName(edition.getEditionName());
        setEditionEffect(edition.getEditionEffect());
    }
}
