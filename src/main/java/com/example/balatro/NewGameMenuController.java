package com.example.balatro;

import com.example.balatro.classes.Deck;
import com.example.balatro.classes.SqlHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class NewGameMenuController
{
    @FXML
    private StackPane stackPaneDeck;
    @FXML
    private Label labelDeckName;

    private List<Deck> deckList;


    public void initialize() {
        deckList = SqlHandler.getAllDecks();


        labelDeckName.setText(deckList.get(0).getName());
        ImageView deckCover = new ImageView(new Image(NewGameMenuController.class.getResourceAsStream(deckList.get(0).getDeckCoverUrl())));
        deckCover.setFitHeight(120);
        deckCover.setPreserveRatio(true);
        stackPaneDeck.getChildren().add(deckCover);
        if(deckList.get(0).getStageCleared() > 0) {
            ImageView stakeChip = new ImageView(new Image(NewGameMenuController.class.getResourceAsStream("/com/images/Stickers_Seals/difficult_"+ deckList.get(0).getStageCleared() +".png")));
            stakeChip.setFitHeight(120);
            stakeChip.setPreserveRatio(true);
            stackPaneDeck.getChildren().add(stakeChip);
        }



    }
}
