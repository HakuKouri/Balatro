package com.example.balatro.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;

public class RewardPaneController {

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

    public void setValues(int count, String effect, int money, boolean tag) {
        if(count > 0) {
            countColumn.setPrefWidth(50);
            countLabel.setText(count + "");
        }
        else countColumn.setPrefWidth(0);

        System.out.println("MAX WIDTH BEFORE: " + imageColumn.getMaxWidth());
        tagImage.setVisible(tag);
        imageColumn.setMaxWidth(tag ? 50 : 0);
        System.out.println("MAX WIDTH AFTER: " + imageColumn.getMaxWidth());

        effectLabel.setText(effect);
        moneyLabel.setText(money > 30 ? "$" + money : "$".repeat(money));
    }
}
