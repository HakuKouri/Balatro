package com.example.balatro.controller.menuController;

import com.example.balatro.domain.util.MenuManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class CollectionsMenuController {
    public Button jokerCollection_Button;
    public Button deckCollection_Button;
    public Button voucherCollection_Button;
    public Button enhancedCollection_Button;
    public Button sealCollection_Button;
    public Button editionCollection_Button;
    public Button boosterCollection_Button;
    public Button tagCollection_Button;
    public Button blindCollection_Button;
    public Button tarotCollection_Button;
    public Button planetCollection_Button;
    public Button spectralCollection_Button;

    public void closeMenu(ActionEvent actionEvent) {
        //TODO
    }

    public void openCollectionsTypeMenu(ActionEvent actionEvent) {
        System.out.println("Button Pressed");
        String buttonId = ((Button)actionEvent.getSource()).getId();

        switch(buttonId) {
            case "jokerCollection_Button":
                System.out.println("jokerCollection_Button pressed");
                MenuManager.getInstance().openJokerCollection();
                break;
            case "deckCollection_Button":
                MenuManager.getInstance().openDeckCollection();
                break;
            case "voucherCollection_Button":
                MenuManager.getInstance().openVoucherCollection();
                break;
            case "enhancedCollection_Button":
                MenuManager.getInstance().openEnhancedCollection();
                break;
            case "sealCollection_Button":
                MenuManager.getInstance().openSealCollection();
                break;
            case "editionCollection_Button":
                MenuManager.getInstance().openEditionCollection();
                break;
            case "boosterCollection_Button":
                MenuManager.getInstance().openBoosterCollection();
                break;
            case "tagCollection_Button":
                MenuManager.getInstance().openTagCollection();
                break;
            case "blindCollection_Button":
                MenuManager.getInstance().openBlindCollection();
                break;
            case "tarotCollection_Button":
                MenuManager.getInstance().openTarotCollection();
                break;
            case "planetCollection_Button":
                MenuManager.getInstance().openPlanetCollection();
                break;
            case "spectralCollection_Button":
                MenuManager.getInstance().openSpectralCollection();
                break;
        }
    }
}
