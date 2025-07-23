package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.controller.TitleScreenController;
import com.example.balatro.domain.deck.SelectableDeck;
import com.example.balatro.domain.game.GameSetup;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.domain.rules.Stake;
import com.example.balatro.domain.util.MenuManager;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class NewGameMenuController
{

    //region FXML
    //Tabs
    @FXML
    private Polygon selectionIndicator_1, selectionIndicator_2, selectionIndicator_3;
    @FXML
    private TabPane tabPane;
    //Images
    @FXML
    private ColumnConstraints deckImage_Column, stakeImage_Column, continue_deckImage_Column, continue_stakeImage_Column;
    @FXML
    private RowConstraints deckImage_Row, continue_deckImage_Row;
    @FXML
    private StackPane deck_StackPane, continue_deck_StackPane;
    @FXML
    private ImageView deckCover_ImageView, stakeSticker_ImageView, stakeChip_ImageView,
            continue_deckCover_ImageView, continue_stakeSticker_ImageView, continue_stakeChip_ImageView;
    //Labels
    @FXML
    private Label deckName_Label, deckEffect_Label, stakeName_Label, stakeEffect_Label,
            continue_deckName_Label, continue_deckEffect_Label, continue_stakeName_Label, continue_stakeEffect_Label;;
    @FXML
    private HBox selectedStakeDisplay_HBox, selectedDeckDisplay_HBox;
    @FXML
    private VBox selectedStakeDisplay_VBox;

    //Seed
    @FXML
    private HBox seed_HBox;
    @FXML
    private CheckBox seed_CheckBox;
    //endregion

    //region Attributes
    private final ObjectProperty<SelectableDeck> activeDeck = new SimpleObjectProperty<>(new SelectableDeck());
    private final ObjectProperty<Stake> activeStake = new SimpleObjectProperty<>(new Stake());
    private final List<SelectableDeck> selectableDeckList = SqlHandler.getAllDecks();
    private final List<Stake> stakeList = SqlHandler.getAllStakes();
    private final IntegerProperty activeDeckIndex = new SimpleIntegerProperty(-1);
    private final IntegerProperty activeStakeIndex = new SimpleIntegerProperty(-1);
    //endregion


    //SET FIRST DECK AND FIRST STAKE IN SELECTION
    public void initialize() {
        System.out.println("Decks: " + selectableDeckList.size());
        for (int i = 0; i < selectableDeckList.size(); i++) {
            selectedDeckDisplay_HBox.getChildren().add(createCircle("selectedDeck" + i));
        };
        System.out.println("Stakes: " + stakeList.size());
        for (int i = stakeList.size(); i > 0; i--) {
            selectedStakeDisplay_VBox.getChildren().add(createRectangle("selectedStake" + i, i <= activeDeckProperty().get().getStageCleared()));
        };

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected: " + newValue.getId());
            selectionIndicator_1.setVisible(false);
            selectionIndicator_2.setVisible(false);
            selectionIndicator_3.setVisible(false);
            if(Objects.equals(newValue.getId(), "tab0")) {
                selectionIndicator_1.setVisible(true);
            }
            if(Objects.equals(newValue.getId(), "tab1")) {
                selectionIndicator_2.setVisible(true);
            }
            if(Objects.equals(newValue.getId(), "tab2")) {
                selectionIndicator_3.setVisible(true);
            }
        });

        seed_HBox.visibleProperty().bind(seed_CheckBox.selectedProperty());

        activeDeckIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!selectedStakeDisplay_HBox.getChildren().isEmpty())
                selectedDeckDisplay_HBox.getChildren().forEach(c -> c.getStyleClass().remove("active"));
            selectedDeckDisplay_HBox.getChildren().get((Integer) newValue ).getStyleClass().add("active");
            activeDeck.set(selectableDeckList.get((Integer) newValue));
        });

        activeDeckProperty().addListener((observable, oldValue, newValue) -> {
            deckCover_ImageView.setImage(newValue.getImage());
            System.out.println(deckCover_ImageView.getFitWidth());
            System.out.println(deck_StackPane.getWidth());
            deckName_Label.setText(newValue.getDeckName());
            deckEffect_Label.setText(newValue.getDeckDescription());
            if(newValue.getStageCleared() > 0) stakeSticker_ImageView.setImage(new Image("file:src/main/resources/com/images/Stickers_Seals/difficult_" + (activeDeck.get().getStageCleared()+1) + ".png"));
            else stakeSticker_ImageView.setImage(null);
            updateStakeDisplay(newValue);
            setActiveStakeIndex(newValue.getStageCleared());
        });

        activeStakeIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Stake geändert");
            selectedStakeDisplay_VBox.getChildren().forEach(c -> c.getStyleClass().remove("active"));
            selectedStakeDisplay_VBox.getChildren().get(selectedStakeDisplay_VBox.getChildren().size() - 1 - (Integer) newValue).getStyleClass().add("active");
            if(!selectedStakeDisplay_HBox.getChildren().isEmpty()) {
                selectedStakeDisplay_HBox.getChildren().forEach(c -> c.getStyleClass().remove("active"));
                selectedStakeDisplay_HBox.getChildren().get((Integer) newValue).getStyleClass().add("active");
            }
            stakeChip_ImageView.setImage(getActiveStake().getImage());
            stakeName_Label.setText(getActiveStake().getStakeName());
            stakeEffect_Label.setText(getActiveStake().getStakeEffect());
        });

        setActiveDeckIndex(0);

        deckCover_ImageView.setPreserveRatio(true);
        stakeSticker_ImageView.setPreserveRatio(true);
        deck_StackPane.prefWidthProperty().bind(deckImage_Column.prefWidthProperty());
        stakeChip_ImageView.setPreserveRatio(true);
        stakeChip_ImageView.fitWidthProperty().bind(stakeImage_Column.prefWidthProperty());
    }



    //region Getter Setter
    public SelectableDeck getActiveDeck() {
        return activeDeck.get();
    }

    public ObjectProperty<SelectableDeck> activeDeckProperty() {
        return activeDeck;
    }

    public Stake getActiveStake() {
        return activeStake.get();
    }

    public ObjectProperty<Stake> activeStakeProperty() {
        return activeStake;
    }

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
    //endregion

    //region Functions
    private void changeDeck(int value) {
        if(getActiveDeckIndex() == 0 && value == -1) setActiveDeckIndex(selectableDeckList.size() - 1);
         else if(getActiveDeckIndex() == selectableDeckList.size() - 1 && value == 1) setActiveDeckIndex(0);
         else setActiveDeckIndex(getActiveDeckIndex() + value);
    }

    private void changeStake(int value) {
        if(getActiveStakeIndex() == 0 && value == -1) setActiveStakeIndex(selectedStakeDisplay_HBox.getChildren().size() - 1);
        else if(getActiveStakeIndex() == selectedStakeDisplay_HBox.getChildren().size() - 1 && value == 1) setActiveStakeIndex(0);
        else setActiveStakeIndex(getActiveStakeIndex() + value);
    }


    //FXML FUNCTIONS
    public void openTab(ActionEvent actionEvent) {
        String text = ((Button) actionEvent.getSource()).getText();
        System.out.println(text + " Tab opened!");
        switch (text) {
            case "New Run": tabPane.getSelectionModel().select(0);
            break;
            case "Continue": tabPane.getSelectionModel().select(1);
            break;
            case "Challenges": tabPane.getSelectionModel().select(2);
        }
    }

    public void nextDeck() { changeDeck(1); }
    public void prevDeck() { changeDeck(-1); }

    public void nextStake() { changeStake(1); }
    public void prevStake() { changeStake(-1); }

    public void startNewGame(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
        GameSetup gameSetup = new GameSetup();
        gameSetup.setChosenDeck(selectableDeckList.get(getActiveDeckIndex()));
        gameSetup.setChosenStake(stakeList.get(getActiveStakeIndex()));

        try {
            Balatro.newGame(gameSetup);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //UI
    private void updateStakeDisplay(SelectableDeck newValue) {
        selectedStakeDisplay_HBox.getChildren().clear();
        for(int i = 0; i <= newValue.getStageCleared(); i++) {
            selectedStakeDisplay_HBox.getChildren().add(createCircle("selectedStake" + i));
        }
        selectedStakeDisplay_HBox.getChildren().get(getActiveDeck().getStageCleared()).getStyleClass().add("active");
    }

    private Circle createCircle(String id) {
        Circle circle = new Circle();
        circle.getStyleClass().add("circle");
        circle.setId(id);
        circle.setRadius(5);
        return circle;
    }

    private Rectangle createRectangle(String id, boolean available) {
        Rectangle rectangle = new Rectangle();
        rectangle.setId(id);
        rectangle.getStyleClass().add("rectangle");
        rectangle.setWidth(10);
        rectangle.setHeight(8);
        if(available) {
            rectangle.getStyleClass().add("available");
            rectangle.setWidth(20);
        }
        return rectangle;
    }



    //Close
    public void closeNewGameMenu(ActionEvent actionEvent) {
        TitleScreenController.getInstance().closeNewGameMenu();
    }

    //endregion
}
