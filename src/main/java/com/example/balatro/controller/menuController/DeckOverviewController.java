package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.controller.CardViewController;
import com.example.balatro.controller.UIController;
import com.example.balatro.domain.card.Card;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.deck.SelectableDeck;
import com.example.balatro.domain.util.CardViewManager;
import com.example.balatro.domain.util.MenuManager;
import com.example.balatro.enums.Suit;
import com.example.balatro.models.GameModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeckOverviewController {

    //region FXML
    //Deck Display
    @FXML private Label selectedDeckName_Label, selectedDeckEffect_Label;
    //Display Buttons
    @FXML private Button remaining_Button, full_Button;
    //Card Display
    @FXML private VBox cardView_VBox;
    //Card Rank Counts
    @FXML private Label aceCount_Label, kingCount_Label, queenCount_Label, jackCount_Label, tenthCount_Label,
            nineCount_Label, eightCount_Label, sevenCount_Label, sixCount_Label, fiveCount_Label,
            fourCount_Label, threeCount_Label, twoCount_Label;
    //Type Count
    @FXML private ImageView aces_ImageView, picture_ImageView, numbers_ImageView;
    @FXML private Label acesCount_Label, picturesCount_Label, numbersCount_Label;
    //Suit Count
    @FXML private ImageView spades_ImageView, hearts_ImageView, clubs_ImageView, diamonds_ImageView;
    @FXML private Label spadesCount_Label, heartsCount_Label, clubsCount_Label, diamondsCount_Label;
    //endregion


    //region Attributes
    private GameModel gameModel = Balatro.getGameModel();
    private final BooleanProperty fullDeckShown = new SimpleBooleanProperty(true);
    private final String uiAssetsUrl = "com/images/UIAssets/ui_assets.png";

    private final CardViewManager cardViewManager = new CardViewManager(false,false,false);
    //endregion


    //region Constructor

    //endregion


    //region Getter & Setter
    public boolean isFullDeckShown() {
        return fullDeckShown.get();
    }

    public BooleanProperty fullDeckShownProperty() {
        return fullDeckShown;
    }
    //endregion


    //region UI
    public void initialize() {
        bindUiAssets();

        fullDeckShownProperty().addListener((observable, oldValue, newValue) -> {
            update(gameModel);
        });

        for (PlayingCard card : gameModel.getRunState().getPlayingDeck().getFullDeck()) {
            card.setFitHeight(card.getFitHeight() * .685);
            cardViewManager.create(card);
            cardViewManager.getControllerByCard(card).setMaxWidth(Balatro.getSettings().getWindowWidth() *.05);
            System.out.println(card.getFitHeight());
        }

        update(gameModel);
    }

    private void bindUiAssets() {
        aces_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        aces_ImageView.setViewport(new Rectangle2D(32,0,32,32));

        picture_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        picture_ImageView.setViewport(new Rectangle2D(64,0,32,32));

        numbers_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        numbers_ImageView.setViewport(new Rectangle2D(96,0,32,32));

        spades_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        spades_ImageView.setViewport(new Rectangle2D(96,32,32,32));

        hearts_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        hearts_ImageView.setViewport(new Rectangle2D(0,32,32,32));

        clubs_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        clubs_ImageView.setViewport(new Rectangle2D(64,32,32,32));

        diamonds_ImageView.setImage(new Image("file:" + uiAssetsUrl));
        diamonds_ImageView.setViewport(new Rectangle2D(32,32,32,32));
    }

    private void setSelectedDeckInfos(SelectableDeck chosenDeck) {
        selectedDeckName_Label.setText(chosenDeck.getDeckName());
        selectedDeckEffect_Label.setText(chosenDeck.getDeckDescription());
    }
    //endregion


    //region Functions
    public void update(GameModel gameModel) {
        List<PlayingCard> cardsToCountList = isFullDeckShown() ? gameModel.getRunState().getPlayingDeck().getFullDeck() : gameModel.getRunState().getPlayingDeck().getPlayDeck();

        Map<Suit, Integer> suitCount = countSuits(cardsToCountList);
        heartsCount_Label.setText(String.valueOf(suitCount.getOrDefault(Suit.HEARTS, 0) + 1));
        spadesCount_Label.setText(String.valueOf(suitCount.getOrDefault(Suit.SPADES, 0) + 1));
        clubsCount_Label.setText(String.valueOf(suitCount.getOrDefault(Suit.CLUBS, 0) + 1));
        diamondsCount_Label.setText(String.valueOf(suitCount.getOrDefault(Suit.DIAMONDS, 0) + 1));

        Map<String, Integer> rankCount = countRanks(cardsToCountList);
        aceCount_Label.setText(String.valueOf(rankCount.getOrDefault("Ace", 0)));
        acesCount_Label.setText(String.valueOf(rankCount.getOrDefault("Ace", 0)));
        kingCount_Label.setText(String.valueOf(rankCount.getOrDefault("King", 0)));
        queenCount_Label.setText(String.valueOf(rankCount.getOrDefault("Queen", 0)));
        jackCount_Label.setText(String.valueOf(rankCount.getOrDefault("Jack", 0)));
        tenthCount_Label.setText(String.valueOf(rankCount.getOrDefault("Ten", 0)));
        nineCount_Label.setText(String.valueOf(rankCount.getOrDefault("Nine", 0)));
        eightCount_Label.setText(String.valueOf(rankCount.getOrDefault("Eight", 0)));
        sevenCount_Label.setText(String.valueOf(rankCount.getOrDefault("Seven", 0)));
        sixCount_Label.setText(String.valueOf(rankCount.getOrDefault("Six", 0)));
        fiveCount_Label.setText(String.valueOf(rankCount.getOrDefault("Five", 0)));
        fourCount_Label.setText(String.valueOf(rankCount.getOrDefault("Four", 0)));
        threeCount_Label.setText(String.valueOf(rankCount.getOrDefault("Three", 0)));
        twoCount_Label.setText(String.valueOf(rankCount.getOrDefault("Two", 0)));

        picturesCount_Label.setText(String.valueOf(rankCount.getOrDefault("King", 0)
                + rankCount.getOrDefault("Queen", 0)
                + rankCount.getOrDefault("Jack",0)));
        numbersCount_Label.setText(String.valueOf(cardsToCountList.size()
                - rankCount.getOrDefault("Ace", 0)
                - rankCount.getOrDefault("King", 0)
                - rankCount.getOrDefault("Queen", 0)
                - rankCount.getOrDefault("Jack",0)));

        if(suitCount.get(Suit.SPADES) != null) {
            createCardDisplay(Suit.SPADES);

        }
        if(suitCount.get(Suit.HEARTS) != null) {
            createCardDisplay(Suit.HEARTS);
        }
        if(suitCount.get(Suit.CLUBS) != null) {
            createCardDisplay(Suit.CLUBS);
        }
        if(suitCount.get(Suit.DIAMONDS) != null) {
            createCardDisplay(Suit.DIAMONDS);
        }

        setSelectedDeckInfos(gameModel.getRunState().getChosenDeck());
    }

    private void createCardDisplay(Suit suit) {
        StackPane stackPane = new StackPane();
        stackPane.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        stackPane.setAlignment(Pos.CENTER);

        List<PlayingCard> cardList = new java.util.ArrayList<>(cardViewManager.getCardList(PlayingCard.class)
                .stream()
                .filter(card -> card.getSuit().equals(suit))
                .toList());

        cardList.sort(Comparator.comparingInt(PlayingCard::getRankIndex).thenComparingInt(PlayingCard::getSuitIndex).reversed());

        for(PlayingCard card : cardList) {
            if(card.getSuit().equals(suit)) {
                stackPane.getChildren().add(cardViewManager.getView(card));
            }
        }

        cardView_VBox.getChildren().add(stackPane);

        Platform.runLater(() -> {
            UIController.moveCards(stackPane);
        });


    }

    private void setCount(Label label, int count) {
        label.setText(Integer.toString(count));
    }

    private Map<String, Integer> countRanks(List<PlayingCard> playingCardList){
        Map<String, Integer> rankCount = new HashMap<>();
        for (PlayingCard card : playingCardList) {
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        return rankCount;
    }

    private Map<Suit, Integer> countSuits(List<PlayingCard> playingCardList){
        Map<Suit, Integer> suitCount = new HashMap<>();
        for (PlayingCard card : playingCardList) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        return suitCount;
    }

    public void showCardsInDeck(ActionEvent actionEvent) {
        fullDeckShownProperty().set(false);
    }

    public void showFullDeck(ActionEvent actionEvent) {
        fullDeckShownProperty().set(true);
    }

    public void closeMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
    }

    //endregion


}
