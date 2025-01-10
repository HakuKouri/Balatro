package com.example.balatro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class BlindPickPanels {

    @FXML
    private Button btnSelectBlind;
    @FXML
    private Pane skipPane;

    @FXML
    private AnchorPane blindPanel;

    public void setButtonText(String text) {
        btnSelectBlind.setText(text);
    }

    public void setBossPanel(boolean isBoss) {
        try {
            if (isBoss) {
                skipPane.getChildren().add(FXMLLoader.load(getClass().getResource("bossPane.fxml")));
            } else {
                skipPane.getChildren().add(FXMLLoader.load(getClass().getResource("blindSkipPane.fxml")));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setActivity(boolean isActivity) {
        blindPanel.setDisable(isActivity);
    }


}
