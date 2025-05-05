package com.example.balatro.controller;

import com.example.balatro.Balatro;
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

public class NewGameMenuController
{
    private Color white = new Color(1,1,1,1);
    private Color dark = new Color(.2,.2,.2,1);
    private Color grey =  new Color(.3,.3,.3,1);
    private Color black = new Color(0,0,0,1);
    private Color stakeGrey = new Color(.4,.4,.4,1);

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

    //SET FIRST DECK AND FIRST STAKE IN SELECTION
    public void initialize() {
        deckList = SqlHandler.getAllDecks();
        stakeList = SqlHandler.getAllStakes();

        activeStake = deckList.get(activeDeck).getStageCleared();
        setDeck();

    }

    private void setDeck() {
        setDeckName();
        loadDeckImage();
        setDeckDescription();
        changeAvailableStakeLevel();

        setStake();
        highlightActiveDeck();

    }

    private void setStake() {
        highlightActiveStake();
        setStakeName();
        setStakeEffect();
        setStakeChipImage();

    }

    private void changeDeck(boolean up) {
        if(up) activeDeck++;
        else activeDeck--;

        if(activeDeck >= deckList.size()) {
            activeDeck = 0;
        } else if(activeDeck < 0) {
            activeDeck = deckList.size()-1;
        }

        activeStake = deckList.get(activeDeck).getStageCleared();
        setDeck();
    }

    private void changeStake(boolean up) {
        if(up) activeStake++;
        else activeStake--;

        if(activeStake > deckList.get(activeDeck).getStageCleared()) {
            activeStake = 0;
        } else if(activeStake < 0) {
            activeStake = deckList.get(activeDeck).getStageCleared();
        }

        setStake();
    }


    //UI FUNCTIONS
    //DECK
    private void setDeckName() {
        labelDeckName.setText(deckList.get(activeDeck).getDeckName());
    }
    private void loadDeckImage() {
        ImageView deckCover = new ImageView(new Image("file:"+deckList.get(activeDeck).getDeckCoverUrl()));
        deckCover.setFitHeight(120);
        deckCover.setPreserveRatio(true);
        stackPaneDeck.getChildren().add(deckCover);
        if(deckList.get(activeDeck).getStageCleared() > 0) {
            ImageView stakeChip = new ImageView(new Image("file:"+ stakeList.get(activeStake).getStakeImageStickerUrl()));
            stakeChip.setFitHeight(120);
            stakeChip.setPreserveRatio(true);
            stackPaneDeck.getChildren().add(stakeChip);
        }
    }
    private void setDeckDescription() {
        labelDeckEffect.setText(deckList.get(activeDeck).getDeckDescription());
    }
    private void changeAvailableStakeLevel() {
        for(int i = 0; i < 7; i++) {
            if(i <= deckList.get(activeDeck).getStageCleared())
                ((Rectangle)boxStakeLevel.getChildren().get(7-i)).setWidth(20);
            else
                ((Rectangle)boxStakeLevel.getChildren().get(7-i)).setWidth(10);
        }
    }
    private void highlightActiveDeck() {
        resetSelection(selectedDeckDisplay);
        ((Circle)selectedDeckDisplay.getChildren().get(activeDeck)).setStroke(white);
    }


    //STAKE
    private void setStakeName() { labelStakeName.setText(stakeList.get(activeStake).getStakeName()); }
    private void setStakeEffect() { labelStakeEffect.setText(stakeList.get(activeStake).getStakeEffect()); }
    private void setStakeChipImage() {imageStakeChip.setImage(new Image("file:"+ stakeList.get(activeStake).getStakeImageChipUrl()));}
    private void highlightActiveStake() {
        resetSelection(selectedStakeDisplay);
        resetSelection();
        ((Circle)selectedStakeDisplay.getChildren().get(activeStake)).setStroke(white);
        ((Rectangle)boxStakeLevel.getChildren().get(7 - activeStake)).setStroke(white);
    }

    private void resetSelection(HBox box) {
        for (Node circle: box.getChildren()) {
            ((Circle)circle).setStroke(black);
        }
    }
    private void resetSelection() {
        for(Node rectangle: boxStakeLevel.getChildren()) {
            ((Rectangle)rectangle).setStroke(stakeGrey);
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
