package com.example.balatro;

import com.example.balatro.classes.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BlindSkipPane {

    @FXML
    private Button btnSkipBlind;
    @FXML
    private ImageView ivSkipReward;

    private Tag tag;

    private BlindPickPanels blindPickPanels;

    public void setTag(Tag tag) {
        this.tag = tag;
        setIvSkipReward(tag.getTagImageUrl());
    }

    private void setIvSkipReward(String imageUrl) {
        ivSkipReward.setImage(new Image(imageUrl));
    }

    public void setBlindPickPanels(BlindPickPanels blindPickPanels) {
        this.blindPickPanels = blindPickPanels;
    }

}
