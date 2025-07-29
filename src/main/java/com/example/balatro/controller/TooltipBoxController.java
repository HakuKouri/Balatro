package com.example.balatro.controller;

import com.example.balatro.domain.card.*;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class TooltipBoxController {

    public VBox mainTooltipBox;
    //region FXML
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane playingCardName_AnchorPane;
    @FXML
    private Label cardName_Label, cardEffect_Label, cardValue_Label, cardSuit_Label,
            cardRarity_Label, cardType_Label, cardEnhancement_Label, cardEdition_Label, cardSeal_Label, cardSticker_Label;
    @FXML
    private VBox enhancementBox, editionBox, sealBox,
            stickerEternalBox, stickerPerishableBox, stickerRentalBox;
    @FXML
    private Label enhancementName, editionName, sealName,
            stickerEternalName, stickerPerishableName, stickerRentalName;
    @FXML
    private Label enhancementEffect, editionEffect, sealEffect,
            stickerEternalEffect, stickerPerishableEffect, stickerRentalEffect;
    //endregion


    private AnchorPane overlayPane;

    public void initialize() {
        hideAllExtraLabels();
        hideAllExtraToolTips();
    }

    private void hideAllExtraLabels() {
        cardName_Label.setVisible(false);
        playingCardName_AnchorPane.setVisible(false);

        cardRarity_Label.setVisible(false);
        cardEnhancement_Label.setVisible(false);
        cardEdition_Label.setVisible(false);
        cardSeal_Label.setVisible(false);
    }

    private void hideAllExtraToolTips() {
        enhancementBox.setVisible(false);
        editionBox.setVisible(false);
        sealBox.setVisible(false);
        stickerEternalBox.setVisible(false);
        stickerPerishableBox.setVisible(false);
        stickerRentalBox.setVisible(false);
    }

    // --- Hauptanzeige ---
    public void showForCard(Node anchor, Card card) {
        if(card instanceof PlayingCard playingCard)
            showForPlayingCard(anchor, playingCard);
        else if (card instanceof Joker joker) {
            showForJoker(anchor, joker);
        } else
            showForConsumable(anchor, card);
    }

    public void showForPlayingCard(Node anchor, PlayingCard card) {
        String effectString = "";

        hideAllExtraLabels();
        hideAllExtraToolTips();

        cardValue_Label.setText(String.valueOf(card.getValue()));
        cardSuit_Label.setText(String.valueOf(card.getSuit().toString()));
        playingCardName_AnchorPane.setVisible(true);

        effectString += "+" + card.getValue() + " chips ";

        if (card.getEnhancement().getEnhancementId() != -1) {
            setEnhancement(card.getEnhancement());
            effectString += "\n" + card.getEnhancement().getEnhancementEffect();
        }

        if (card.getEdition().getId() != -1) {
            setEdition(card.getEdition());
        }

        if (card.getSeal().getSealId() != -1) {
            setSeal(card.getSeal());
        }

        cardEffect_Label.setText(effectString);
        setPosition(anchor, TooltipPosition.AUTO_HAND); // passt Position automatisch an
        show();
    }

    public void showForJoker(Node anchor, Joker joker) {
        hideAllExtraLabels();
        hideAllExtraToolTips();

        cardName_Label.setText(joker.getCardName());
        cardName_Label.setVisible(true);

        if (joker.getEdition().getId() != -1 ) {
            setEdition(joker.getEdition());
        }

        if (!joker.getStickers().isEmpty()) {
            setSticker(joker.getStickers());
        }

        setPosition(anchor, TooltipPosition.AUTO_JOKER);
        show();
    }

    public void showForConsumable(Node anchor, Card  card) {
        hideAllExtraLabels();
        hideAllExtraToolTips();

        cardName_Label.setText(card.getCardName());
        cardName_Label.setVisible(true);
        cardEffect_Label.setText(card.getCardDescription());
        cardEffect_Label.setVisible(true);

        cardType_Label.setText(card.getCardType());
        cardType_Label.setVisible(true);

        if (card.getEdition().getId() != -1 ) {
            setEdition(card.getEdition());
        }

        setPosition(anchor, TooltipPosition.AUTO_CONSUMABLE);
        show();
    }

    public void hide() {
        rootPane.setVisible(false);
    }

    private void show() {
        rootPane.setVisible(true);
        rootPane.toFront();
    }

    // --- Positionierungslogik ---
    public enum TooltipPosition {
        ABOVE_ANCHOR, BELOW_ANCHOR, LEFT_OF_ANCHOR,
        AUTO_HAND, AUTO_JOKER, AUTO_CONSUMABLE
    }

    private void setPosition(Node anchor, TooltipPosition position) {
        Bounds anchorBounds = anchor.localToScene(anchor.getBoundsInLocal());
        Point2D scenePos = anchor.localToScene(0, 0);
        double tooltipWidth = rootPane.getWidth();
        double tooltipHeight = rootPane.getHeight();

        switch (position) {
            case ABOVE_ANCHOR -> {
                rootPane.setLayoutX(anchorBounds.getMinX());
                rootPane.setLayoutY(anchorBounds.getMinY() - tooltipHeight - 10);
            }
            case BELOW_ANCHOR -> {
                rootPane.setLayoutX(anchorBounds.getMinX());
                rootPane.setLayoutY(anchorBounds.getMaxY() + 10);
            }
            case LEFT_OF_ANCHOR -> {
                rootPane.setLayoutX(anchorBounds.getMinX() - tooltipWidth - 10);
                rootPane.setLayoutY(anchorBounds.getMinY());
            }
            case AUTO_HAND -> setPosition(anchor, TooltipPosition.ABOVE_ANCHOR);
            case AUTO_JOKER, AUTO_CONSUMABLE -> setPosition(anchor, TooltipPosition.BELOW_ANCHOR);
        }
    }

    // Setzt den Overlay-Container (muss extern übergeben werden, z. B. root-AnchorPane)
    public void setOverlayPane(AnchorPane overlayPane) {
        this.overlayPane = overlayPane;
        if (!overlayPane.getChildren().contains(rootPane)) {
            overlayPane.getChildren().add(rootPane);
        }
    }

    // Setzt Texte für die Tool Tips und zeigt sie an
    private void setEnhancement(Enhancement enhancement) {
        cardEnhancement_Label.setText(enhancement.getEnhancementName());
        cardEnhancement_Label.setVisible(true);
        enhancementName.setText(enhancement.getEnhancementName());
        enhancementEffect.setText(enhancement.getEnhancementEffect());
        enhancementBox.setVisible(true);
    }

    private void setEdition(Edition edition) {
        cardEdition_Label.setText(edition.getEditionName());
        cardEdition_Label.setVisible(true);
        editionName.setText(edition.getEditionName());
        editionEffect.setText(edition.getEditionEffect());
        editionBox.setVisible(true);
    }

    void setSeal(Seal seal) {
        cardSeal_Label.setText(seal.getSealName());
        cardSeal_Label.setVisible(true);
        sealName.setText(seal.getSealName());
        sealEffect.setText(seal.getSealEffect());
        sealBox.setVisible(true);
    }

    void setSticker(ObservableSet<Sticker> sticker) {
        for (int i = 0; i < sticker.size(); i++) {
            switch (((Sticker)sticker.toArray()[i]).getStickerId()) {
                case 10: stickerEternalBox.setVisible(true);
                    break;
                case 11: stickerPerishableBox.setVisible(true);
                    break;
                case 12: stickerRentalBox.setVisible(true);
                    break;
            }
        }
    }
}


