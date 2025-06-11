package com.example.balatro.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.Map;
import java.util.Objects;

public class UIController {

    public static void addCardClickEvent(StackPane stackPane, Map<AnchorPane,CardViewController> map) {
        stackPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();
            //System.out.println("Clicked: " + source);

            if (source instanceof ImageView) {
                System.out.println("ImageView clicked");
                AnchorPane cardPane = (AnchorPane) source.getParent().getParent().getParent();
                System.out.println("CardPane Height: " + cardPane.getHeight());
                System.out.println("CardPane Width: " + cardPane.getWidth());
                CardViewController controller = map.get(cardPane);
                System.out.println("Image Height: " + ((ImageView) source).getFitHeight());
                System.out.println("Image Width: " + ((ImageView) source).getFitWidth());

                controller.setSelected(!controller.isSelected());
            } else {
                Node currentNode = source;
                if (source.getParent() instanceof Label) {
                    System.out.println("LabeledText clicked");
                    currentNode = source.getParent();
                }

                if (currentNode instanceof Label) {
                    System.out.println("Label clicked");
                    if (Objects.equals(currentNode.getId(), "buyLabel")) {
                        System.out.println("BuyLabel clicked");
                        AnchorPane cardPane = (AnchorPane) currentNode.getParent().getParent().getParent();
                        CardViewController controller = map.get(cardPane);
                        GameController.getInstance().buyItem(cardPane, controller);
                    }
                }
            }
        });
    }

    public static void moveCards(StackPane stackPane) {
        double cardWidth = 200;
        System.out.println("Joker Space Width: " + ((AnchorPane)stackPane.getParent()).getWidth());
        double lastPos = stackPane.getWidth() - cardWidth - 10;

        int cards = stackPane.getChildren().size();
        double pos = 0;
        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                stackPane.setAlignment(Pos.CENTER_LEFT);
                pos = i * lastPos / (cards - 1);
            } else {
                stackPane.setAlignment(Pos.CENTER);
                if(cards%2==0) {
                    pos = cardWidth/2 + i * cardWidth - cards/2*cardWidth + i * 5;
                } else {
                    pos = i * cardWidth - cards/2*cardWidth + i * 5;
                }
            }
            stackPane.getChildren().get(i).setTranslateX(pos);
        }
    }
}
