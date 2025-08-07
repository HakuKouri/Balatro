package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.controller.TitleScreenController;
import com.example.balatro.domain.deck.SelectableDeck;
import com.example.balatro.domain.game.GameSetup;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.domain.rules.Stake;
import com.example.balatro.domain.util.MenuManager;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

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
    private AnchorPane deckImage_AnchorPane;
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
    private final IntegerProperty activeDeckIndex = new SimpleIntegerProperty(-1);
    private final ObjectProperty<SelectableDeck> activeDeck = new SimpleObjectProperty<>(new SelectableDeck());
    private final IntegerProperty activeStakeIndex = new SimpleIntegerProperty(-1);
    private final ObjectProperty<Stake> activeStake = new SimpleObjectProperty<>(new Stake());

    private final List<SelectableDeck> selectableDeckList = SqlHandler.getAllDecks();
    private final List<Stake> stakeList = SqlHandler.getAllStakes();
    //endregion

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
        this.activeStakeIndex.set(-1);
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

    //Initialize


    public void initialize() {

        bindIndicatorVisibility();
        bindUi();

        setListener();

        seed_HBox.visibleProperty().bind(seed_CheckBox.selectedProperty());

        setActiveDeckIndex(0);
        setActiveStakeIndex(0);

        Platform.runLater(() -> {
            deckCover_ImageView.setFitWidth(deckImage_AnchorPane.getWidth());
            deckCover_ImageView.setFitHeight(deckImage_AnchorPane.getHeight());
        });
    }


    //region Functions
    private void setListener() {
        activeDeckIndex.addListener((observable, oldValue, newValue) -> {
            getActiveDeck().setDeck(selectableDeckList.get((Integer) newValue));
            System.out.println("New Deck Id: " + getActiveDeck().getDeckId());
            displayActiveDeck();
            updateStakeDisplay(getActiveDeck());
            setActiveStakeIndex(getActiveDeck().getStageCleared());
        });

        activeDeck.addListener((observable, oldValue, newValue) -> {
            setActiveStakeIndex(newValue.getStageCleared() + 1);
            displayActiveStake();
        });

        activeStakeIndex.addListener((observable, oldValue, newValue) -> {
            if(getActiveStakeIndex() == -1 ) return;
            getActiveStake().setStake(stakeList.get((Integer) newValue));
            displayActiveStake();
        });
    }

    private void changeDeck(boolean up) {
        setActiveDeckIndex(up
                ? (getActiveDeckIndex() + 1 >= selectableDeckList.size() ? 0 : getActiveDeckIndex() + 1)
                : (getActiveDeckIndex() - 1 < 0 ? selectableDeckList.size() - 1 : getActiveDeckIndex() - 1));
    }

    private void changeStake(boolean up) {
        setActiveStakeIndex(up
                ? (getActiveStakeIndex() + 1 > getActiveDeck().getStageCleared() ? 0 : getActiveStakeIndex() + 1)
                : (getActiveStakeIndex() - 1 < 0 ? getActiveDeck().getStageCleared() : getActiveStakeIndex() - 1));
    }


    //UI
    private void bindUi() {
        //TODO Unlocked Decks

        getActiveDeck().deckCoverUrlProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Deck Name: " + getActiveDeck().getDeckName() + " Deck Id: " + getActiveDeck().getDeckId());

            deckCover_ImageView.setImage(
                    Balatro.getGameModel().getActiveProfile().getDecks().contains(getActiveDeck().getDeckId())
                    ? new Image("file:" + newValue)
                    : new Image("file:src/main/resources/com/images/DeckBacks/locked_deck.png"));
        });
//        deckCover_ImageView.imageProperty().bind(
//                Bindings.createObjectBinding(() -> {
//                    System.out.println("Deck Name: " + getActiveDeck().getDeckName() + " Deck Id: " + getActiveDeck().getDeckId());
//                        return Balatro.getGameModel().getActiveProfile().getDecks().contains(getActiveDeck().getDeckId())
//                                ? getActiveDeck().getImage()
//                                : new Image("file:src/main/resources/com/images/DeckBacks/locked_deck.png");
//                 }, getActiveDeck().imageProperty()));

        deckName_Label.textProperty().bind(
                Bindings.createStringBinding(() ->
                        Balatro.getGameModel().getActiveProfile().getDecks().contains(getActiveDeck().getDeckId())
                                ? getActiveDeck().getDeckName()
                                : "Locked"
                        , getActiveDeck().deckNameProperty()));

        deckEffect_Label.textProperty().bind(getActiveDeck().deckDescriptionProperty());
        stakeSticker_ImageView.imageProperty().bind(Bindings.createObjectBinding(() ->
             getActiveDeck().getStageCleared() > 0 ? new Image("file:src/main/resources/com/images/Stickers_Seals/difficult_" + (activeDeck.get().getStageCleared()+1) + ".png") : null
        ));

        stakeChip_ImageView.imageProperty().bind(getActiveStake().imageProperty());
        stakeName_Label.textProperty().bind(getActiveStake().stakeNameProperty());
        stakeEffect_Label.textProperty().bind(getActiveStake().stakeDescriptionProperty());
    }

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

    public void nextDeck() { changeDeck(true); }
    public void prevDeck() { changeDeck(false); }

    public void nextStake() { changeStake(true); }
    public void prevStake() { changeStake(false); }

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
    private void bindIndicatorVisibility() {
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
    }

    private void updateStakeDisplay(SelectableDeck newDeck) {
        List<Node> vBox = selectedStakeDisplay_VBox.getChildren();
        List<Node> hBox = selectedStakeDisplay_HBox.getChildren();

        hBox.forEach(node -> { node.setVisible(false); });
        vBox.forEach(node -> { node.setStyle(""); node.getStyleClass().setAll("stake-rect"); });

        int stageClearedIndex = newDeck.getStageCleared();
        int stakeListSize = stakeList.size();
        for(int i = 0; i <= stageClearedIndex; i++) {
            hBox.get(i).setVisible(true);
        }

        for(int i = 0 ; i < stakeListSize; i++) {
            if(i <= stageClearedIndex) {
                vBox.get(stakeListSize - 1 - i).getStyleClass().add("available");
                if(stageClearedIndex != i)
                    vBox.get(stakeListSize - 1 - i).setStyle("-fx-background-color: " + stakeList.get(i).getStakeColorString() + " ;");
            }
        }
    }

    private void displayActiveDeck() {
        selectedDeckDisplay_HBox.getChildren().forEach(c -> c.getStyleClass().remove("active"));
        selectedDeckDisplay_HBox.getChildren().get(getActiveDeckIndex()).getStyleClass().add("active");
    }

    private void displayActiveStake() {
        selectedStakeDisplay_VBox.getChildren().forEach(c -> c.getStyleClass().remove("active"));
        selectedStakeDisplay_VBox.getChildren().get(stakeList.size() - 1 - getActiveStakeIndex()).getStyleClass().add("active");
        selectedStakeDisplay_HBox.getChildren().forEach(c -> c.getStyleClass().remove("active"));
        selectedStakeDisplay_HBox.getChildren().get(getActiveStakeIndex()).getStyleClass().add("active");
    }

    //Close
    public void closeNewGameMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
        //TitleScreenController.getInstance().closeNewGameMenu();
    }

    //endregion
}
