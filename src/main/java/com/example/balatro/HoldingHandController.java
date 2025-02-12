package com.example.balatro;

import com.example.balatro.classes.PlayingCard;
import com.example.balatro.models.HoldingHandModel;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import java.util.Collection;
import java.util.List;

public class HoldingHandController {

    @FXML
    private StackPane HoldingHand;

    HoldingHandModel model = new HoldingHandModel();


    public List<PlayingCard> getSelectedCards() {
        return model.getSelectedCards();
    }

    public List<PlayingCard> getHandCards() {
        return model.getHandCards();
    }
}
