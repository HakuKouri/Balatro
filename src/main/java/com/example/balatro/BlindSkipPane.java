package com.example.balatro;

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

    private BlindPickPanels blindPickPanels;

    public void setIvSkipReward(String imageUrl) {
        ivSkipReward.setImage(new Image(imageUrl));
    }

    public void setBlindPickPanels(BlindPickPanels blindPickPanels) {
        this.blindPickPanels = blindPickPanels;
    }
}
