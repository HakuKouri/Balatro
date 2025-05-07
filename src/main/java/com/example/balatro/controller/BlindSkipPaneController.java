package com.example.balatro.controller;

import com.example.balatro.classes.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlindSkipPaneController {

    @FXML
    private Button btnSkipBlind;
    @FXML
    private ImageView ivSkipReward;

    private Tag tag;

    private BlindBoxPanelController blindPickPanelsController;

    public void setTag(Tag tag) {
        this.tag = tag;
        setIvSkipReward(tag.getTagImageUrl());
    }

    public Tag getTag() {
        return tag;
    }

    private void setIvSkipReward(String imageUrl) {
        ivSkipReward.setImage(new Image("file:" + imageUrl));
    }

    public void setBlindPickPanels(BlindBoxPanelController blindPickPanelsController) {
        this.blindPickPanelsController = blindPickPanelsController;
    }


    public void onClickSkip(ActionEvent actionEvent) {
        System.out.println("Skipped clicked");
        GameController.getInstance().skip(tag);
    }
}
