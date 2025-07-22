package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.util.MenuManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;

public class OptionScreenController
{

    public Polygon selectionIndicator_1;
    public Polygon selectionIndicator_2;
    public Polygon selectionIndicator_3;
    public Polygon selectionIndicator_4;
    public TabPane option_TabPane;

    public void closeOptionScreen(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
    }

    public void openTab(ActionEvent actionEvent) {
        hideIndicator();
        String name = ((Button) actionEvent.getSource()).getText();

        switch (name) {
            case "Game": option_TabPane.getSelectionModel().select(0);
            selectionIndicator_1.setVisible(true);
            break;
            case "Video": option_TabPane.getSelectionModel().select(1);
            selectionIndicator_2.setVisible(true);
            break;
            case "Graphics": option_TabPane.getSelectionModel().select(2);
            selectionIndicator_3.setVisible(true);
            break;
            case "Audio": option_TabPane.getSelectionModel().select(3);
            selectionIndicator_4.setVisible(true);
            break;
        }
    }

    private void hideIndicator() {
        selectionIndicator_1.setVisible(false);
        selectionIndicator_2.setVisible(false);
        selectionIndicator_3.setVisible(false);
        selectionIndicator_4.setVisible(false);
    }
}
