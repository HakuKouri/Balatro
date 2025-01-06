package com.example.balatro;

import com.example.balatro.classes.Deck;
import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.classes.Stake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class NewGameMenuController
{
    private Color white = new Color(1,1,1,1);
    private Color dark = new Color(.2,.2,.2,1);
    private Color grey =  new Color(.3,.3,.3,1);
    private Color black = new Color(0,0,0,1);

    @FXML
    private StackPane stackPaneDeck;
    @FXML
    private Label labelDeckName;
    @FXML
    private Label labelDeckEffect;
    @FXML
    private ImageView imageStakeChip;
    @FXML
    private Label labelStakeName;
    @FXML
    private Label labelStakeEffect;
    public HBox selectedStakeDisplay;
    public HBox selectedDeckDisplay;
    @FXML
    private VBox boxStakeLevel;
    @FXML
    private Button btnNewGame;

    private List<Deck> deckList;
    private List<Stake> stakeList;
    private int activeDeck = 0;
    private int activeStake = 0;

    public void initialize() {
        deckList = SqlHandler.getAllDecks();
        stakeList = SqlHandler.getAllStakes();

        setDeck(activeDeck);

        ((Rectangle)(boxStakeLevel.getChildren().get(8))).setStroke(white);
    }

    private void setDeck(int deckIndex) {
        labelDeckName.setText(deckList.get(deckIndex).getName());
        ImageView deckCover = new ImageView(new Image("file:"+deckList.get(deckIndex).getDeckCoverUrl()));
        deckCover.setFitHeight(120);
        deckCover.setPreserveRatio(true);
        stackPaneDeck.getChildren().add(deckCover);
        if(deckList.get(deckIndex).getStageCleared() > 0) {
            ImageView stakeChip = new ImageView(new Image("file:/com/images/Stickers_Seals/difficult_"+ deckList.get(deckIndex).getStageCleared() +".png"));
            stakeChip.setFitHeight(120);
            stakeChip.setPreserveRatio(true);
            stackPaneDeck.getChildren().add(stakeChip);
        }

        labelDeckEffect.setText(deckList.get(deckIndex).getDescription());
        changeAviableStakeLevel();
        setStake(deckList.get(deckIndex).getStageCleared());

        setSelectedDeck(activeDeck);
    }

    private void setSelectedDeck(int index) {
        ((Circle)selectedDeckDisplay.getChildren().get(index)).setStroke(white);
    }

    private void setStake(int stakeIndex) {
        activeStake = stakeIndex;

        imageStakeChip.setImage(new Image("file:"+ stakeList.get(activeStake).getStakeImageChipUrl()));
        labelStakeEffect.setText(stakeList.get(activeStake).getStakeEffect());
        labelStakeName.setText(stakeList.get(activeStake).getStakeName());

        setSelectedStake(deckList.get(activeDeck).getStageCleared());
    }

    private void setSelectedStake(int index) {
        ((Circle)selectedStakeDisplay.getChildren().get(index)).setStroke(white);
    }

    private void changeDeck(boolean up) {
        if(up) activeDeck++;
        else activeDeck--;

        if(activeDeck >= deckList.size()) {
            activeDeck = 0;
        } else if(activeDeck < 0) {
            activeDeck = deckList.size()-1;
        }

        changeIndexDisplay(selectedDeckDisplay, up);

        resetSelection(selectedDeckDisplay);
        setDeck(activeDeck);
    }

    private void changeAviableStakeLevel() {
        for(int i = 0; i < 8; i++) {
            if(i <= deckList.get(activeDeck).getStageCleared())
                ((Rectangle)boxStakeLevel.getChildren().get(8-i)).setWidth(20);
            else
                ((Rectangle)boxStakeLevel.getChildren().get(8-i)).setWidth(10);
        }
    }

    private void changeStake(boolean up) {
        if(up) activeStake++;
        else activeStake--;

        if(activeStake > deckList.get(activeDeck).getStageCleared()) {
            activeStake = 0;
        } else if(activeStake < 0) {
            activeStake = deckList.get(activeDeck).getStageCleared();
        }

        changeIndexDisplay(selectedStakeDisplay, up);

        resetSelection(selectedStakeDisplay);
        setStake(activeStake);
    }

    private void changeIndexDisplay(HBox box, boolean up) {
        int lastIndex = 0;
        int index = 0;
        int modifier;

        if(up) modifier = -1;
        else modifier = 1;

        if(Objects.equals(box.getId(), "selectedDeckDisplay")) {
            index = activeDeck;
            lastIndex = activeDeck + modifier;
            if(lastIndex < 0) lastIndex = deckList.size()-1;
            else if (lastIndex >= deckList.size()) lastIndex = 0;

        } else {
            index = activeStake;
            lastIndex = activeStake + modifier;
            if(lastIndex < 0) lastIndex = deckList.get(activeDeck).getStageCleared();
            else if(lastIndex > deckList.get(activeDeck).getStageCleared()) lastIndex = 0;
        }

        ((Circle)box.getChildren().get(lastIndex)).setStroke(black);
        ((Circle)box.getChildren().get(index)).setStroke(white);

    }

    private void resetSelection(HBox box) {
        for (Node circle: box.getChildren()) {
            ((Circle)circle).setStroke(black);
        }
    }


    //FXML FUNCTIONS
    public void nextDeck() { changeDeck(true); }
    public void prevDeck() { changeDeck(false); }

    public void nextStake() { changeStake(true); }
    public void prevStake() { changeStake(false); }


    public void stakeLevel_onclick(MouseEvent mouseEvent) {
        Rectangle source = (Rectangle) mouseEvent.getSource();
        for (Node rec : boxStakeLevel.getChildren()) {
            if(rec == source && source.getWidth() == 20) {
                ((Rectangle)rec).setStroke(white);
            } else
                ((Rectangle)rec).setStroke(grey);
        }
    }

    public void startNewGame(ActionEvent actionEvent) {
        GameSetup gameSetup = new GameSetup();
        gameSetup.setChosenDeck(deckList.get(activeDeck));
        gameSetup.setChosenStake(stakeList.get(activeStake));

        try {
            Balatro.newGame(gameSetup);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
