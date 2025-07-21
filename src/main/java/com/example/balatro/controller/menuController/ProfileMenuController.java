package com.example.balatro.controller.menuController;

import com.example.balatro.domain.game.Profile;
import com.example.balatro.domain.util.MenuManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Polygon;

public class ProfileMenuController {
    //region FXML
    @FXML
    private Polygon selectionIndicator_1, selectionIndicator_2, selectionIndicator_3;
    @FXML
    private ProgressBar totalProgress_Progressbar, collections_Progressbar, challenges_Progressbar, jokerSticker_Progressbar, deckStake_Progressbar;
    @FXML
    private Label totalProgress_Label, collections_Label, challenges_Label, jokerSticker_Label, deckStake_Label, profileName_Label, winCount_Label;
    @FXML
    private Button currentProfile_Button;
    //endregion

    //region Attributes
    private ObjectProperty<Profile> currentProfile = new SimpleObjectProperty<>(new Profile());
    //endregion

    //region Getter Setter
    public Profile getCurrentProfile() {
        return currentProfile.get();
    }

    public ObjectProperty<Profile> currentProfileProperty() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile.set(currentProfile);
    }

    //endregion

    public void initialize() {

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

    }
    //endregion
}
