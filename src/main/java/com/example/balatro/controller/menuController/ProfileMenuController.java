package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.models.GameModel;
import com.example.balatro.models.ProfileModel;
import com.example.balatro.domain.util.MenuManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Polygon;

import java.util.List;

public class ProfileMenuController {
    //region FXML
    @FXML
    private Polygon selectionIndicator_1, selectionIndicator_2, selectionIndicator_3;
    @FXML
    private ProgressBar totalProgress_Progressbar, collections_Progressbar, challenges_Progressbar, jokerSticker_Progressbar, deckStake_Progressbar;
    @FXML
    private Label totalProgress_Label, collections_Label, challenges_Label, jokerSticker_Label, deckStake_Label, winCount_Label;
    @FXML
    private Button currentProfile_Button, unlock_Button;
    @FXML
    private TextField profileName_TextField;
    @FXML
    private GridPane progress_GridPane;
    //endregion

    //region Attributes
    //TODO SHOWN PROFILE Change
    private final ObjectProperty<ProfileModel> shownProfile = new SimpleObjectProperty<>(new ProfileModel());
    //endregion

    //region Getter Setter
    public ProfileModel getShownProfile() {
        return shownProfile.get();
    }

    public ObjectProperty<ProfileModel> shownProfileProperty() {
        return shownProfile;
    }

    public void setShownProfile(ProfileModel shownProfile) {
        getShownProfile().setProfile(shownProfile);
    }

    //endregion

    public void initialize() {
        GameModel gameModel = Balatro.getGameModel();
        if(gameModel.getActiveProfile().getId() > 0)
            setShownProfile(gameModel.getActiveProfile());
        else
            setShownProfile(gameModel.getProfiles().getFirst());

        progress_GridPane.visibleProperty().bind(getShownProfile().activeProfileProperty());
        unlock_Button.visibleProperty().bind(getShownProfile().activeProfileProperty());
        currentProfile_Button.textProperty().bind(Bindings.createStringBinding(() -> !getShownProfile().isActiveProfile() ? "Create Profile" : getShownProfile().equals(Balatro.getGameModel().getActiveProfile()) ? "Current Profile" : "Load Profile"));

        selectionIndicator_1.visibleProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == 1, getShownProfile().idProperty()));
        selectionIndicator_2.visibleProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == 2, getShownProfile().idProperty()));
        selectionIndicator_3.visibleProperty().bind(Bindings.createBooleanBinding(() -> getShownProfile().getId() == 3, getShownProfile().idProperty()));

        getShownProfile().profileNameProperty().addListener((observable, oldValue, newValue) -> {
            profileName_TextField.setText(newValue);
        });

        profileName_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            getShownProfile().setProfileName(newValue);
        });


    }

    //region Functions
    public void resetProfile(ActionEvent actionEvent) {
        //TODO include second Click to active
        SqlHandler.resetProfile(getShownProfile());
    }

    public void unlockAll(ActionEvent actionEvent) {
        GameModel model = Balatro.getGameModel();
        model.getAllBlindsList().stream()
                .filter(blind -> !getShownProfile().getBlinds().contains(blind))
                .toList()
                .forEach(blind -> SqlHandler.discoverForProfile("ProfileBlindDiscoveryDetails", getShownProfile(), blind.getBlindId()));
        model.getAllBoosterList().stream()
                .filter(booster -> !getShownProfile().getBoosters().contains(booster))
                .toList()
                .forEach(booster -> SqlHandler.discoverForProfile("ProfileBoosterDiscoveryDetails", getShownProfile(), booster.getCardId()));
        model.getAllDecksList().stream()
                .filter(deck -> !getShownProfile().getDecks().contains(deck))
                .toList()
                .forEach(deck -> SqlHandler.discoverForProfile("ProfileDeckUnlockedDetails", getShownProfile(), deck.getDeckId()));
        model.getAllEditionList().stream()
                .filter(edition -> !getShownProfile().getEditions().contains(edition))
                .toList()
                .forEach(edition -> SqlHandler.discoverForProfile("ProfileEditionDiscoveryDetails", getShownProfile(), edition.getId()));
        model.getAllJokerList().stream()
                .filter(joker -> !getShownProfile().getJokers().contains(joker))
                .toList()
                .forEach(joker -> SqlHandler.discoverForProfile("ProfileJokerDiscoveryDetails", getShownProfile(), joker.getCardId()));
        model.getAllJokerList().stream()
                .filter(joker -> !joker.isUnlocked())
                .filter(joker -> !getShownProfile().getUnlockedJokers().contains(joker))
                .toList()
                .forEach(joker -> SqlHandler.discoverForProfile("ProfileJokerUnlockedDetails", getShownProfile(), joker.getCardId()));
        model.getAllPlanetList().stream()
                .filter(planet -> !getShownProfile().getPlanets().contains(planet))
                .toList()
                .forEach(planet -> SqlHandler.discoverForProfile("ProfilePlanetDiscoveryDetails", getShownProfile(), planet.getCardId()));
        model.getAllSpectralList().stream()
                .filter(spectral -> !getShownProfile().getSpectrals().contains(spectral))
                .toList()
                .forEach(spectral -> SqlHandler.discoverForProfile("ProfileSpectralDiscoveryDetails", getShownProfile(), spectral.getCardId()));
        model.getAllTagList().stream()
                .filter(tag -> !getShownProfile().getTags().contains(tag))
                .toList()
                .forEach(tag -> SqlHandler.discoverForProfile("ProfileTagDiscoveryDetails", getShownProfile(), tag.getTagId()));
        model.getAllTarotList().stream()
                .filter(tarot -> !getShownProfile().getTarots().contains(tarot))
                .toList()
                .forEach(tarot -> SqlHandler.discoverForProfile("ProfileTarotDiscoveryDetails", getShownProfile(), tarot.getCardId()));
        model.getAllVoucherList().stream()
                .filter(voucher -> !getShownProfile().getVouchers().contains(voucher))
                .toList()
                .forEach(voucher -> SqlHandler.discoverForProfile("ProfileVoucherDiscoveryDetails", getShownProfile(), voucher.getCardId()));
        model.getAllVoucherList().stream()
                .filter(voucher -> !voucher.isAvailable())
                .toList()
                .forEach(voucher -> SqlHandler.discoverForProfile("ProfileVoucherUnlockedDetails", getShownProfile(), voucher.getCardId()));
        model.getProfiles().get(getShownProfile().getId() - 1).setProfile(SqlHandler.getProfileModelById(getShownProfile().getId()));
    }

    public void setProfile(ActionEvent actionEvent) {
        if(((Button) actionEvent.getSource()).getText().equals("Create Profile")) {
            SqlHandler.createProfile(getShownProfile());
            setShownProfile(SqlHandler.getProfileModelById(getShownProfile().getId()));
        } else  if(((Button) actionEvent.getSource()).getText().equals("Load Profile")) {
            Balatro.getGameModel().changeActiveProfile(getShownProfile());
        }
    }

    public void closeMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
    }

    public void openProfile(ActionEvent actionEvent) {
        GameModel gameModel = Balatro.getGameModel();
        System.out.println("Opening Profile");;
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
    //endregion
}
