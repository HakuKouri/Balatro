package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
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
    private final ObjectProperty<ProfileModel> shownProfile = new SimpleObjectProperty<>();
    //endregion

    //region Getter Setter
    public ProfileModel getShownProfile() {
        return shownProfile.get();
    }

    public ObjectProperty<ProfileModel> shownProfileProperty() {
        return shownProfile;
    }

    public void setShownProfile(ProfileModel shownProfile) {
        this.shownProfile.set(shownProfile);
    }

    //endregion

    public void initialize() {
        ProfileModel currentProfile = Balatro.getGameModel().getActiveProfile();

        progress_GridPane.visibleProperty().bind(currentProfile.activeProfileProperty());
        unlock_Button.visibleProperty().bind(currentProfile.activeProfileProperty());
        currentProfile_Button.textProperty().bind(Bindings.createStringBinding(() -> !currentProfile.isActiveProfile() ? "Create Profile" : ""));

        selectionIndicator_1.visibleProperty().bind(Bindings.createBooleanBinding(() -> currentProfile.getId() == 1, currentProfile.idProperty()));
        selectionIndicator_2.visibleProperty().bind(Bindings.createBooleanBinding(() -> currentProfile.getId() == 2, currentProfile.idProperty()));
        selectionIndicator_3.visibleProperty().bind(Bindings.createBooleanBinding(() -> currentProfile.getId() == 3, currentProfile.idProperty()));


        currentProfile.profileNameProperty().addListener((observable, oldValue, newValue) -> {
            profileName_TextField.setText(newValue);
        });

        profileName_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            currentProfile.setProfileName(newValue);
        });


    }

    //region Functions
    public void resetProfile(ActionEvent actionEvent) {

    }

    public void unlockAll(ActionEvent actionEvent) {

    }

    public void setProfile(ActionEvent actionEvent) {

    }

    public void closeMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
    }

    public void openProfile(ActionEvent actionEvent) {
        GameModel gameModel = Balatro.getGameModel();
        System.out.println("Opening Profile");;
        switch (((Button) actionEvent.getSource()).getText()) {
            case "1":
                gameModel.changeActiveProfile(gameModel.getProfiles().get(0));
                break;
            case "2":
                gameModel.changeActiveProfile(gameModel.getProfiles().get(1));
                break;
            case "3":
                gameModel.changeActiveProfile(gameModel.getProfiles().get(2));
                break;
        }
    }
    //endregion
}
