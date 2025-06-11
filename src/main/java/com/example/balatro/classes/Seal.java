package com.example.balatro.classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Seal
{
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty sealImageUrl = new SimpleStringProperty("");
    private final StringProperty sealName = new SimpleStringProperty("");
    private final StringProperty sealEffect = new SimpleStringProperty("");

    //Getter Setter
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSealImageUrl() {
        return sealImageUrl.get();
    }

    public StringProperty sealImageUrlProperty() {
        return sealImageUrl;
    }

    public void setSealImageUrl(String sealImageUrl) {
        this.sealImageUrl.set(sealImageUrl);
    }

    public String getSealName() {
        return sealName.get();
    }

    public StringProperty sealNameProperty() {
        return sealName;
    }
    public void setSealName(String sealName) {
        this.sealName.set(sealName);
    }


    public String getSealEffect() {
        return sealEffect.get();
    }

    public StringProperty sealEffectProperty() {
        return sealEffect;
    }


    public void setSealEffect(String sealEffect) {
        this.sealEffect.set(sealEffect);
    }

    //Functions
    public void setSeal(Seal seal) {

    }
}
