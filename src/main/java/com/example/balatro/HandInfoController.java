package com.example.balatro;

import com.example.balatro.models.HandInfoModel;
import javafx.beans.binding.Bindings;
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

    private final HandInfoModel model = new HandInfoModel();

    public void initialize() {
        System.out.println("HandInfoController hier");

        infoHand.textProperty().bind(model.handNameProperty());
        infoHandLevel.textProperty().bind(Bindings.createStringBinding(
                () -> model.getHandLevel() == 0 ? "" : String.format("lvl: %d", model.getHandLevel())));
        infoHandChips.textProperty().bind(model.handChipsProperty().asString());
        infoHandMulti.textProperty().bind(model.handMultiplierProperty().asString());
    }


    public void setHandName(String name) {
        model.setHandName(name);
    }

    public void setHandLevel(int level) {
        model.setHandLevel(level);
    }

    public void setHandChips(int chips) {
        model.setHandChips(chips);
    }

    public void setHandMultiplier(int multiplier) {
        model.setHandMultiplier(multiplier);
    }

    public String getHandName() {
        return model.getHandName();
    }

    public void addChips(int value) {
        model.setHandChips(model.getHandChips() + value);
    }

    public double getHandMultiplier() {
        return model.getHandMultiplier();
    }

    public int getHandChips() {
        return model.getHandChips();
    }
}
