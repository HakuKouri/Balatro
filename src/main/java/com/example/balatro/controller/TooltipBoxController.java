package com.example.balatro.controller;

import com.example.balatro.domain.card.*;
import com.example.balatro.domain.card.Joker;
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
    @FXML
    private AnchorPane name_AnchorPane, playingCardName_AnchorPane, effect_AnchorPane,
            rarity_AnchorPane, cardtype_AnchorPane, enhancement_AnchorPane, edition_AnchorPane, seal_AnchorPane,
            eternal_AnchorPane, perish_AnchorPane, rental_AnchorPane;
    //region FXML
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label cardName_Label, cardEffect_Label, cardValue_Label, cardSuit_Label,
            cardRarity_Label, cardType_Label, cardEnhancement_Label, cardEdition_Label, cardSeal_Label,
            cardSticker_Eternal_Label, cardSticker_Perish_Label, cardSticker_Rental_Label;
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


    public void initialize() {
        rootPane.setVisible(false);
        hideAllExtraLabels();
        hideAllExtraToolTips();
    }

    private void hideAllExtraLabels() {
        hide(name_AnchorPane);
        hide(playingCardName_AnchorPane);
        hide(rarity_AnchorPane);
        hide(cardtype_AnchorPane);
        hide(enhancement_AnchorPane);
        hide(edition_AnchorPane);
        hide(seal_AnchorPane);
        hide(eternal_AnchorPane);
        hide(perish_AnchorPane);
        hide(rental_AnchorPane);

    }

    private void hideAllExtraToolTips() {
        hide(enhancementBox);
        hide(editionBox);
        hide(sealBox);
        hide(stickerEternalBox);
        hide(stickerPerishableBox);
        hide(stickerRentalBox);
    }

    // --- Hauptanzeige ---
    public void showForCard(Node anchor, Card card) {
        hideAllExtraLabels();
        hideAllExtraToolTips();

        if(card instanceof PlayingCard playingCard)
            showForPlayingCard(anchor, playingCard);
        else if (card instanceof Joker joker) {
            showForJoker(anchor, joker);
        } else
            showForConsumable(anchor, card);
    }

    public void showForPlayingCard(Node anchor, PlayingCard card) {
        String effectString = "";

        cardValue_Label.setText(String.valueOf(card.getValue()));
        cardSuit_Label.setText(String.valueOf(card.getSuit().toString()));
        show(playingCardName_AnchorPane);

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
        cardName_Label.setText(joker.getCardName());
        show(name_AnchorPane);
        cardEffect_Label.setText(joker.getCardDescription());
        cardRarity_Label.setText(joker.getRarity());
        show(rarity_AnchorPane);
        cardRarity_Label.getStyleClass().setAll("rarity " + joker.getRarity().toLowerCase());

        if (joker.getEdition().getId() > 0 ) {
            setEdition(joker.getEdition());
        }

        if (joker.getStickers().size() > 0) {
            setSticker(joker.getStickers());
        }

        setPosition(anchor, TooltipPosition.AUTO_JOKER);
        show();
    }

    public void showForConsumable(Node anchor, Card  card) {
        cardName_Label.setText(card.getCardName());
        show(name_AnchorPane);
        cardEffect_Label.setText(card.getCardDescription());

        cardType_Label.setText(card.getCardType());
        show(cardtype_AnchorPane);

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
                rootPane.setLayoutX(anchorBounds.getMinX() - tooltipWidth / 2);
                rootPane.setLayoutY(anchorBounds.getMinY() - tooltipHeight - 10);
            }
            case BELOW_ANCHOR -> {
                rootPane.setLayoutX(anchorBounds.getMinX() - tooltipWidth / 2);
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
        if (!overlayPane.getChildren().contains(rootPane)) {
            overlayPane.getChildren().add(rootPane);
        }
    }

    // Setzt Texte für die Tool Tips und zeigt sie an
    private void setEnhancement(Enhancement enhancement) {
        cardEnhancement_Label.setText(enhancement.getEnhancementName());
        show(enhancement_AnchorPane);
        enhancementName.setText(enhancement.getEnhancementName());
        enhancementEffect.setText(enhancement.getEnhancementEffect());
        show(enhancementBox);
    }

    private void setEdition(Edition edition) {
        cardEdition_Label.setText(edition.getEditionName());
        show(edition_AnchorPane);
        editionName.setText(edition.getEditionName());
        editionEffect.setText(edition.getEditionEffect());
        show(editionBox);
    }

    void setSeal(Seal seal) {
        cardSeal_Label.setText(seal.getSealName());
        show(seal_AnchorPane);
        sealName.setText(seal.getSealName());
        sealEffect.setText(seal.getSealEffect());
        show(sealBox);
    }

    private void hide(Node node) {
        node.setManaged(false);
        node.setVisible(false);
    }

    private void show(Node node) {
        node.setManaged(true);
        node.setVisible(true);
    }

    void setSticker(ObservableSet<Sticker> sticker) {
        for (int i = 0; i < sticker.size(); i++) {
            switch (((Sticker)sticker.toArray()[i]).getStickerId()) {
                case 10: show(stickerEternalBox);
                    show(eternal_AnchorPane);
                    break;
                case 11: show(stickerPerishableBox);
                    show(perish_AnchorPane);
                    break;
                case 12: show(stickerRentalBox);
                    show(rental_AnchorPane);
                    break;
            }
        }
    }
}


