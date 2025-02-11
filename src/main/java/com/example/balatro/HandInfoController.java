package com.example.balatro;

import com.example.balatro.models.HandInfoModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HandInfoController {

    @FXML
    private Label infoHand;
    @FXML
    private Label infoHandLevel;
    @FXML
    private Label infoHandChips;
    @FXML
    private Label infoHandMulti;

    private HandInfoModel model;

    public void initialize() {
        model = new HandInfoModel();
        infoHand.textProperty().bind(model.handNameProperty());
        infoHandLevel.textProperty().bind(model.handLevelProperty().asString());
        infoHandChips.textProperty().bind(model.handChipsProperty().asString());
        infoHandMulti.textProperty().bind(model.handMutliplierProperty().asString());
    }
}
