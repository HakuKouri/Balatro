package com.example.balatro.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;

import java.io.IOException;

public class RewardPaneController {

    @FXML
    private AnchorPane reward_AnchorPane;
    @FXML
    private ColumnConstraints imageColumn;
    @FXML
    private ColumnConstraints countColumn;
    @FXML
    private ColumnConstraints effectColumn;
    @FXML
    private ColumnConstraints moneyColumn;
    @FXML
    private ImageView tagImage;
    @FXML
    private Label countLabel;
    @FXML
    private Label effectLabel;
    @FXML
    private Label moneyLabel;


    public void createPane(int count, String effect, int money, boolean tag) {

            if(count > 0) {
                countColumn.setPrefWidth(50);
                countLabel.setText(count + "");
            }
            else countColumn.setPrefWidth(0);

            tagImage.setVisible(tag);
            imageColumn.setMaxWidth(tag ? 50 : 0);

            effectLabel.setText(effect);
            moneyLabel.setText(money > 30 ? "$" + money : "$".repeat(money));

    }
}
