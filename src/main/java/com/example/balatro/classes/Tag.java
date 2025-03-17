package com.example.balatro.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class Tag extends ImageView
{
    private int tagId;
    private StringProperty tagImageUrl = new SimpleStringProperty();
    private String tagName;
    private String tagBenefit;
    private String tagNotes;
    private String minAnte;

    //Constructor
    public Tag() {
        tagImageUrl.addListener((obs, oldVal, newVal) -> {
            this.setImage(new Image("file:" +newVal,true));
        });
        this.setFitHeight(100);
        this.setPreserveRatio(true);
    }

    //region GETTER SETTER
    public int getTagId()
    {
        return tagId;
    }

    public void setTagId(int id)
    {
        this.tagId = id;
    }

    public String getTagImageUrl() {
        return tagImageUrl.get();
    }

    public StringProperty tagImageUrlProperty() {
        return tagImageUrl;
    }

    public void setTagImageUrl(String tagImageUrl) {
        this.tagImageUrl.set(tagImageUrl);
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
    //endregion

    public static List<Tag> setTagList() {
        return SqlHandler.getAllTags();
    }
}
