package com.example.balatro;

import com.example.balatro.classes.SqlHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class NewGameMenuController
{
    @FXML
    private StackPane stackPaneDeck;
    @FXML
    private Label labelDeckName;


    public void initialize() {
        labelDeckName.setText(SqlHandler.getDeckName(1));
        System.out.println(labelDeckName.getText());
        ImageView deckCover = new ImageView(new Image(NewGameMenuController.class.getResourceAsStream("/com/images/Deck_Backs/deckBack_1.png")));
        deckCover.setFitHeight(120);
        deckCover.setPreserveRatio(true);
        ImageView stakeChip = new ImageView(new Image(NewGameMenuController.class.getResourceAsStream("/com/images/Stickers_Seals/difficult_1.png")));
        stakeChip.setFitHeight(120);
        stakeChip.setPreserveRatio(true);
        stackPaneDeck.getChildren().addAll(deckCover,stakeChip);

    }
}
