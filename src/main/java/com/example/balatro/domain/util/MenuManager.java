package com.example.balatro.domain.util;

import com.example.balatro.Balatro;
import com.example.balatro.controller.OptionScreenController;
import com.example.balatro.controller.menuController.DeckOverviewController;
import com.example.balatro.controller.menuController.NewGameMenuController;
import com.example.balatro.models.GameModel;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

public class MenuManager {

    //region Attributes

    private static MenuManager instance;
    private final StackPane root;

    private AnchorPane currentOpenMenu;

    //Main Panes
    private AnchorPane profilePane, languagePane, menuPane, newGamePane, optionsPane, deckOverviewPane, runInfoPane, statsPane, cardStatsPane;

    //Collection Panes
    private AnchorPane collectionPane, jokerPane, deckPane, voucherPane, tarotPane, planetPane, spectralPane,
            enhancedPane, sealsPane, editionPane, boosterPane, tagPane, blindPane;


    //Gamemodel
    private static GameModel gameModel;

    //endregion


    //region Getter & Setter
    public AnchorPane getProfilePane() {
        return profilePane;
    }

    public void setProfilePane(AnchorPane profilePane) {
        this.profilePane = profilePane;
    }

    public AnchorPane getLanguagePane() {
        return languagePane;
    }

    public void setLanguagePane(AnchorPane languagePane) {
        this.languagePane = languagePane;
    }

    public AnchorPane getMenuPane() {
        return menuPane;
    }

    public void setMenuPane(AnchorPane menuPane) {
        this.menuPane = menuPane;
    }

    public AnchorPane getNewGamePane() {
        return newGamePane;
    }

    public void setNewGamePane(AnchorPane newGamePane) {
        this.newGamePane = newGamePane;
    }

    public AnchorPane getOptionsPane() {
        return optionsPane;
    }

    public void setOptionsPane(AnchorPane optionsPane) {
        this.optionsPane = optionsPane;
    }

    public AnchorPane getDeckOverviewPane() {
        return deckOverviewPane;
    }

    public void setDeckOverviewPane(AnchorPane deckOverviewPane) {
        this.deckOverviewPane = deckOverviewPane;
    }

    public AnchorPane getRunInfoPane() {
        return runInfoPane;
    }

    public void setRunInfoPane(AnchorPane runInfoPane) {
        this.runInfoPane = runInfoPane;
    }

    public AnchorPane getStatsPane() {
        return statsPane;
    }

    public void setStatsPane(AnchorPane statsPane) {
        this.statsPane = statsPane;
    }

    public AnchorPane getCardStatsPane() {
        return cardStatsPane;
    }

    public void setCardStatsPane(AnchorPane cardStatsPane) {
        this.cardStatsPane = cardStatsPane;
    }

    public AnchorPane getCollectionPane() {
        return collectionPane;
    }

    public void setCollectionPane(AnchorPane collectionPane) {
        this.collectionPane = collectionPane;
    }

    public AnchorPane getJokerPane() {
        return jokerPane;
    }

    public void setJokerPane(AnchorPane jokerPane) {
        this.jokerPane = jokerPane;
    }

    public AnchorPane getDeckPane() {
        return deckPane;
    }

    public void setDeckPane(AnchorPane deckPane) {
        this.deckPane = deckPane;
    }

    public AnchorPane getVoucherPane() {
        return voucherPane;
    }

    public void setVoucherPane(AnchorPane voucherPane) {
        this.voucherPane = voucherPane;
    }

    public AnchorPane getTarotPane() {
        return tarotPane;
    }

    public void setTarotPane(AnchorPane tarotPane) {
        this.tarotPane = tarotPane;
    }

    public AnchorPane getPlanetPane() {
        return planetPane;
    }

    public void setPlanetPane(AnchorPane planetPane) {
        this.planetPane = planetPane;
    }

    public AnchorPane getSpectralPane() {
        return spectralPane;
    }

    public void setSpectralPane(AnchorPane spectralPane) {
        this.spectralPane = spectralPane;
    }

    public AnchorPane getEnhancedPane() {
        return enhancedPane;
    }

    public void setEnhancedPane(AnchorPane enhancedPane) {
        this.enhancedPane = enhancedPane;
    }

    public AnchorPane getSealsPane() {
        return sealsPane;
    }

    public void setSealsPane(AnchorPane sealsPane) {
        this.sealsPane = sealsPane;
    }

    public AnchorPane getEditionPane() {
        return editionPane;
    }

    public void setEditionPane(AnchorPane editionPane) {
        this.editionPane = editionPane;
    }

    public AnchorPane getBoosterPane() {
        return boosterPane;
    }

    public void setBoosterPane(AnchorPane boosterPane) {
        this.boosterPane = boosterPane;
    }

    public AnchorPane getTagPane() {
        return tagPane;
    }

    public void setTagPane(AnchorPane tagPane) {
        this.tagPane = tagPane;
    }

    public AnchorPane getBlindPane() {
        return blindPane;
    }

    public void setBlindPane(AnchorPane blindPane) {
        this.blindPane = blindPane;
    }
    //endregion


    //region Constructor
    public MenuManager(StackPane root) {
        this.root = root;
    }
    //endregion

    //region Functions
    public static void init(GameModel model) {
        gameModel = model;
    }

    public static void setRootPane(StackPane rootPane) {
        instance = new MenuManager(rootPane);
    }

    public static MenuManager getInstance() {
        return instance;
    }

    private void openMenu(AnchorPane menu) {
        root.getChildren().clear();
        root.getChildren().add(menu);
        currentOpenMenu = menu;
        menu.toFront();
    }

    private void closeCurrentMenu() {
        if(currentOpenMenu != null) {
            root.getChildren().remove(currentOpenMenu);
            currentOpenMenu = null;
        }
    }

    public void openDeckOverview() {
        if(deckOverviewPane == null) {
            Pair<DeckOverviewController, AnchorPane> overview = FxmlUtil.loadWithPane("/com/example/balatro/menu/deck-overview.fxml");
            setDeckOverviewPane(overview.getValue());
        }
        openMenu(deckOverviewPane);
    }

    public void openOptionScreen() {
        if(optionsPane == null) {
            Pair<OptionScreenController, AnchorPane> option = FxmlUtil.loadWithPane("/com/example/balatro/menu/option-screen.fxml");
            setOptionsPane(option.getValue());
        }
        openMenu(optionsPane);
    }

    public void openNewGame() {
        if(newGamePane == null) {
            Pair<NewGameMenuController, AnchorPane> newGame = FxmlUtil.loadWithPane("/com/example/balatro/menu/newGameMenu-screen.fxml");
            setNewGamePane(newGame.getValue());
            newGamePane.setMaxWidth(root.getWidth() * .475);
            newGamePane.setMaxHeight(root.getHeight() * .844);
        }
        root.setVisible(true);
        openMenu(newGamePane);
    }

    public void closeMenu() {
        closeCurrentMenu();
        root.setVisible(false);
    }
    //endregion
}
