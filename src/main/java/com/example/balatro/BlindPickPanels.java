package com.example.balatro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class BlindPickPanels {

    @FXML
    private Button btnSelectBlind;
    @FXML
    private Pane skipPane;
    @FXML
    private Pane bossPane;
    @FXML
    private AnchorPane blindPanel;

    public void setButtonText(String text) {
        btnSelectBlind.setText(text);
    }

    public void setBossPanel(boolean isBoss) {
        if (isBoss) {
            skipPane.setStyle("visibility: hidden;");
        }
        else {
            bossPane.setVisible(false);
        }
    }

    public void setActivity(boolean isActivity) {
        blindPanel.setDisable(isActivity);
    }


}
