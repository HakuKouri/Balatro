package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.ProfileModel;
import com.example.balatro.domain.util.MenuManager;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.transform.Source;

public class ProfileMenuController {
    //region FXML
    @FXML
    private Polygon selectionIndicator_1, selectionIndicator_2, selectionIndicator_3;
    @FXML
    private ProgressBar totalProgress_Progressbar, collections_Progressbar, challenges_Progressbar, jokerSticker_Progressbar, deckStake_Progressbar;
    @FXML
    private Label totalProgress_Label, collections_Label, challenges_Label, jokerSticker_Label, deckStake_Label, winCount_Label;
    @FXML
    private Button currentProfile_Button, reset_delete_Button, unlock_Button;
    @FXML
    private TextField profileName_TextField;
    @FXML
    private GridPane progress_GridPane;
    //endregion

    //region Attributes
    //TODO SHOWN PROFILE Change
    private final ObjectProperty<ProfileModel> shownProfile = new SimpleObjectProperty<>(new ProfileModel());

    private boolean resetPressed = false;
    private boolean deletePressed = false;
    private boolean unlockPressed = false;
    //endregion

    //region Getter Setter
    public ProfileModel getShownProfile() {
        return shownProfile.get();
    }
    //endregion

    public void initialize() {
        setBinds();

        setShownProfile(Balatro.getGameModel().getProfiles().stream().filter(p -> p.isActiveProfile()).findFirst().get());
    }

    //region Functions
    public void setShownProfile(ProfileModel newProfile) {
        getShownProfile().setProfile(newProfile);

        resetPressed = false;
        deletePressed = false;
        unlockPressed = false;
    }

    private void setBinds() {
        GameModel gameModel = Balatro.getGameModel();

        //Indicator
        selectionIndicator_1.visibleProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == 1, getShownProfile().idProperty()));
        selectionIndicator_2.visibleProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == 2, getShownProfile().idProperty()));
        selectionIndicator_3.visibleProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == 3, getShownProfile().idProperty()));

        //Progress
        progress_GridPane.visibleProperty().bind(getShownProfile().activeProfileProperty());

        profileName_TextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                getProfileById(getShownProfile().getId()).setProfileName(profileName_TextField.getText());
            }
        });

        currentProfile_Button.textProperty().bind(Bindings.createStringBinding(() -> !getShownProfile().isActiveProfile()
                ? "Create Profile"
                : getShownProfile().getId() == gameModel.getActiveProfile().getId()
                ? "Current Profile"
                : "Load Profile"
                , getShownProfile().activeProfileProperty(),getShownProfile().idProperty(), gameModel.getActiveProfile().idProperty()));

        currentProfile_Button.disableProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == gameModel.getActiveProfile().getId(), getShownProfile().idProperty(), gameModel.getActiveProfile().idProperty()));
        reset_delete_Button.textProperty().bind(Bindings.createStringBinding(() -> getShownProfile().getId() == gameModel.getActiveProfile().getId() ? "Delete Profile" : "Reset Profile" , getShownProfile().idProperty(), gameModel.getActiveProfile().idProperty()));
        unlock_Button.visibleProperty().bind(getShownProfile().activeProfileProperty());

        Platform.runLater(() -> {
            Bindings.bindBidirectional(profileName_TextField.textProperty(), getShownProfile().profileNameProperty());
        });
    }

    public void resetProfile(ActionEvent actionEvent) {
        //TODO include second Click to active
        if (reset_delete_Button.getText().equals("Delete Profile")) {
            if (deletePressed) {
                SqlHandler.deleteProfile(getShownProfile());
            } else
                deletePressed = true;
        } else {
            if (resetPressed) {
                SqlHandler.resetProfile(getShownProfile());
            } else
                resetPressed = true;
        }
    }

    public void unlockAll(ActionEvent actionEvent) {
        if (!unlockPressed) {
            unlockPressed = true;
            return;
        }
        GameModel model = Balatro.getGameModel();
        model.getAllBlindsList().stream()
                .filter(blind -> !getShownProfile().getBlinds().contains(blind.getBlindId()))
                .toList()
                .forEach(blind -> SqlHandler.discoverAllBlindsForProfile(getShownProfile(), blind.getBlindId()));
        model.getAllBoosterList().stream()
                .filter(booster -> !getShownProfile().getBoosters().contains(booster.getCardId()))
                .toList()
                .forEach(booster -> SqlHandler.discoverAllBoosterForProfile(getShownProfile(), booster.getCardId()));
        model.getAllDecksList().stream()
                .filter(deck -> !getShownProfile().getDecks().containsKey(deck.getDeckId()))
                .toList()
                .forEach(deck -> SqlHandler.discoverAllDeckForProfile(getShownProfile(), deck.getDeckId()));
        model.getAllEditionList().stream()
                .filter(edition -> !getShownProfile().getEditions().contains(edition.getId()))
                .toList()
                .forEach(edition -> SqlHandler.discoverAllEditionForProfile(getShownProfile(), edition.getId()));
        model.getAllJokerList().stream()
                .filter(joker -> !getShownProfile().getJokers().contains(joker.getCardId()))
                .toList()
                .forEach(joker -> SqlHandler.discoverAllJokersForProfile(getShownProfile(), joker.getCardId()));
        model.getAllJokerList().stream()
                .filter(joker -> !joker.isUnlocked())
                .filter(joker -> !getShownProfile().getUnlockedJokers().contains(joker.getCardId()))
                .toList()
                .forEach(joker -> SqlHandler.unlockAllJokersForProfile(getShownProfile(), joker.getCardId()));
        model.getAllPlanetList().stream()
                .filter(planet -> !getShownProfile().getPlanets().contains(planet.getCardId()))
                .toList()
                .forEach(planet -> SqlHandler.discoverAllPlanetForProfile(getShownProfile(), planet.getCardId()));
        model.getAllSpectralList().stream()
                .filter(spectral -> !getShownProfile().getSpectrals().contains(spectral.getCardId()))
                .toList()
                .forEach(spectral -> SqlHandler.discoverAllSpectralForProfile(getShownProfile(), spectral.getCardId()));
        model.getAllTagList().stream()
                .filter(tag -> !getShownProfile().getTags().contains(tag.getTagId()))
                .toList()
                .forEach(tag -> SqlHandler.discoverAllTagForProfile(getShownProfile(), tag.getTagId()));
        model.getAllTarotList().stream()
                .filter(tarot -> !getShownProfile().getTarots().contains(tarot.getCardId()))
                .toList()
                .forEach(tarot -> SqlHandler.discoverAllTagForProfile(getShownProfile(), tarot.getCardId()));
        model.getAllVoucherList().stream()
                .filter(voucher -> !getShownProfile().getVouchers().contains(voucher.getCardId()))
                .toList()
                .forEach(voucher -> SqlHandler.discoverAllVoucherForProfile(getShownProfile(), voucher.getCardId()));
        model.getAllVoucherList().stream()
                .filter(voucher -> !voucher.isAvailable())
                .toList()
                .forEach(voucher -> SqlHandler.unlockAllVoucherForProfile(getShownProfile(), voucher.getCardId()));
        model.getProfiles().get(getShownProfile().getId() - 1).setProfile(SqlHandler.getProfileModelById(getShownProfile().getId()));
    }

    public void setProfile(ActionEvent actionEvent) {

        if (currentProfile_Button.getText().equals("Create Profile")) {
            System.out.println("Profile created");
            SqlHandler.createProfile(getShownProfile());
            getProfileById(getShownProfile().getId()).setProfile(SqlHandler.getProfileModelById(getShownProfile().getId()));
            setShownProfile(SqlHandler.getProfileModelById(getShownProfile().getId()));
        } else if (currentProfile_Button.getText().equals("Load Profile")) {
            System.out.println("Profile loaded");
            Balatro.getGameModel().changeActiveProfile(getShownProfile());
        }
    }

    public void closeMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
    }

    public void openProfile(ActionEvent actionEvent) {
        GameModel gameModel = Balatro.getGameModel();
        System.out.println("Opening Profile");

        switch (((Button) actionEvent.getSource()).getText()) {
            case "1":
                setShownProfile(gameModel.getProfiles().get(0));
                break;
            case "2":
                setShownProfile(gameModel.getProfiles().get(1));
                break;
            case "3":
                setShownProfile(gameModel.getProfiles().get(2));
                break;
        }
    }

    private ProfileModel getProfileById(int id)  {
        return Balatro.getGameModel().getProfiles().stream().filter(p -> p.getId() == id).findFirst().get() ;
    }
    //endregion
}
