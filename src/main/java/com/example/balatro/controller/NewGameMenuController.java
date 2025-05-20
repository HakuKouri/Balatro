package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Deck;
import com.example.balatro.classes.GameSetup;
import com.example.balatro.classes.SqlHandler;
import com.example.balatro.classes.Stake;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.List;

public class NewGameMenuController
{
    //region FXML
    public GridPane gridMenu;
    public ColumnConstraints deckImageStackPaneColumn;
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
    //endregion

    private final Color white = new Color(1,1,1,1);
    private final Color dark = new Color(.2,.2,.2,1);
    private final Color grey =  new Color(.3,.3,.3,1);
    private final Color black = new Color(0,0,0,1);
    private final Color stakeGrey = new Color(.4,.4,.4,1);

    private final DoubleProperty width = new SimpleDoubleProperty(400);
    private final DoubleProperty height = new SimpleDoubleProperty(400);

    private final ObjectProperty<Image> deckImage = new SimpleObjectProperty<>();

    private final ObjectProperty<Deck> activeDeck = new SimpleObjectProperty<>();
    private List<Deck> deckList;
    private List<Stake> stakeList;
    private final IntegerProperty activeDeckIndex = new SimpleIntegerProperty(0);
    private final IntegerProperty activeStakeIndex = new SimpleIntegerProperty(0);

    //SET FIRST DECK AND FIRST STAKE IN SELECTION
    public void initialize() {
        deckList = SqlHandler.getAllDecks();
        stakeList = SqlHandler.getAllStakes();

        gridMenu.maxWidthProperty().bind(widthProperty());
        gridMenu.maxHeightProperty().bind(heightProperty());

        activeDeckIndex.addListener((observable, oldValue, newValue) -> {
            activeDeck.set(deckList.get(activeDeckIndex.get()));
        });

        activeStakeIndex.set(deckList.get(activeDeckIndex.get()).getStageCleared());

    }

    //region Getter Setter
    public int getActiveStakeIndex() {
        return activeStakeIndex.get();
    }

    public IntegerProperty activeStakeIndexProperty() {
        return activeStakeIndex;
    }

    public void setActiveStakeIndex(int activeStakeIndex) {
        this.activeStakeIndex.set(activeStakeIndex);
    }

    public int getActiveDeckIndex() {
        return activeDeckIndex.get();
    }

    public IntegerProperty activeDeckIndexProperty() {
        return activeDeckIndex;
    }

    public void setActiveDeckIndex(int activeDeckIndex) {
        this.activeDeckIndex.set(activeDeckIndex);
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    //endregion

    //region Functions
    public void setSize(double height) {
        setWidth(height * 0.88);
        setHeight(height * 0.88);
    }
    //endregion


    public void setDeck() {
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
        if(up) activeDeckIndex.set(getActiveDeckIndex() + 1);
        else activeDeckIndex.set(getActiveDeckIndex() - 1);

        if(getActiveDeckIndex() >= deckList.size()) {
            activeDeckIndex.set(0);
        } else if(getActiveDeckIndex() < 0) {
            activeDeckIndex.set(deckList.size()-1);
        }

        activeStakeIndex.set(deckList.get(getActiveDeckIndex()).getStageCleared());
        setDeck();
    }

    private void changeStake(boolean up) {
        if(up) activeStakeIndex.set(getActiveStakeIndex() + 1);
        else activeStakeIndex.set(getActiveStakeIndex() - 1);

        if(getActiveStakeIndex() > deckList.get(getActiveDeckIndex()).getStageCleared()) {
            activeStakeIndex.set(0);
        } else if(getActiveStakeIndex() < 0) {
            activeStakeIndex.set(deckList.get(getActiveDeckIndex()).getStageCleared());
        }

        setStake();
    }


    //UI FUNCTIONS
    //DECK
    private void setDeckName() {
        labelDeckName.setText(deckList.get(getActiveDeckIndex()).getDeckName());
    }

    private void loadDeckImage() {
        ImageView deckCover = new ImageView(new Image("file:"+deckList.get(getActiveDeckIndex()).getDeckCoverUrl()));
        deckCover.setFitWidth(stackPaneDeck.getWidth());
        deckCover.setPreserveRatio(true);
        stackPaneDeck.getChildren().add(deckCover);
        if(deckList.get(getActiveDeckIndex()).getStageCleared() > 0) {
            ImageView stakeChip = new ImageView(new Image("file:"+ stakeList.get(getActiveStakeIndex()).getStakeImageStickerUrl()));
            stakeChip.setFitHeight(120);
            stakeChip.setPreserveRatio(true);
            stackPaneDeck.getChildren().add(stakeChip);
        }
    }

    private void setDeckDescription() {
        labelDeckEffect.setText(deckList.get(getActiveDeckIndex()).getDeckDescription());
    }

    private void changeAvailableStakeLevel() {
        for(int i = 0; i < 7; i++) {
            if(i <= deckList.get(getActiveDeckIndex()).getStageCleared())
                ((Rectangle)boxStakeLevel.getChildren().get(7-i)).setWidth(20);
            else
                ((Rectangle)boxStakeLevel.getChildren().get(7-i)).setWidth(10);
        }
    }

    private void highlightActiveDeck() {
        resetSelection(selectedDeckDisplay);
        ((Circle)selectedDeckDisplay.getChildren().get(getActiveDeckIndex())).setStroke(white);
    }

    //STAKE
    private void setStakeName() { labelStakeName.setText(stakeList.get(getActiveStakeIndex()).getStakeName()); }

    private void setStakeEffect() { labelStakeEffect.setText(stakeList.get(getActiveStakeIndex()).getStakeEffect()); }

    private void setStakeChipImage() {imageStakeChip.setImage(new Image("file:"+ stakeList.get(getActiveStakeIndex()).getStakeImageChipUrl()));}

    private void highlightActiveStake() {
        resetSelection(selectedStakeDisplay);
        resetSelection();
        ((Circle)selectedStakeDisplay.getChildren().get(getActiveStakeIndex())).setStroke(white);
        ((Rectangle)boxStakeLevel.getChildren().get(7 - getActiveStakeIndex())).setStroke(white);
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
        gameSetup.setChosenDeck(deckList.get(getActiveDeckIndex()));
        gameSetup.setChosenStake(stakeList.get(getActiveStakeIndex()));

        try {
            Balatro.newGame(gameSetup);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getDeckImage() {
        return deckImage.get();
    }

    public ObjectProperty<Image> deckImageProperty() {
        return deckImage;
    }

    public void setDeckImage(Image deckImage) {
        this.deckImage.set(deckImage);
    }

    public Color getDark() {
        return dark;
    }
}
