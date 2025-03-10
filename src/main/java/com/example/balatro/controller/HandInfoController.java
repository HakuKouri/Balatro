package com.example.balatro.controller;

import com.example.balatro.models.GameModel;
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

    private GameModel model = GameController.getInstance().gameModel;


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

    public void clearHandInfo() {
        model.setHandName("");
        model.setHandLevel(0);
        model.setHandChips(0);
        model.setHandMultiplier(0);
    }
}
