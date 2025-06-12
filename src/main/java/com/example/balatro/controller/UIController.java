package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Card;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UIController {

    private static IntegerProperty gameSpeed = new SimpleIntegerProperty();
    private static List<Animation> animationList = new ArrayList<>();

    public static void setupUiController() {
        System.out.println("UI gamespeed: " + Balatro.getSettings().getGameSpeed());
        gameSpeed.bind( Balatro.getSettings().gameSpeedProperty());
    }

    public static void addCardClickEvent(StackPane stackPane, Map<CardViewController, AnchorPane> map) {
        stackPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();
            //System.out.println("Clicked: " + source);

            if (source instanceof ImageView) {
                System.out.println("ImageView clicked");
                AnchorPane cardPane = (AnchorPane) source.getParent().getParent().getParent();
                System.out.println("CardPane Height: " + cardPane.getHeight());
                System.out.println("CardPane Width: " + cardPane.getWidth());
                CardViewController controller = map.keySet().stream().filter(cardViewController -> map.get(cardViewController).equals(stackPane)).findFirst().get();
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
                        CardViewController controller = map.keySet().stream().filter(cardViewController -> map.get(cardViewController).equals(cardPane)).findFirst().get();
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

    public static Timeline timeline(Node card) {
        System.out.println("Game Speed: " + gameSpeed.get());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), new KeyValue(card.rotateProperty(), 0)),
                new KeyFrame(Duration.millis((double) 4 /gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 8 /gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 12 /gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 16 /gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 20 /gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 24 /gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 28 /gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 32 /gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 36 /gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 40 /gameSpeed.get()), new KeyValue(card.rotateProperty(), 0)));

        timeline.setCycleCount(3);
        timeline.setDelay(Duration.seconds(0.2));

        return timeline;
    }

    public static void playAnimation(List<Animation> list) {
        if (list.isEmpty()) {
            return;
        }
        Animation first = list.remove(0);
        wrapAnimation(first, () -> playAnimation(list)).play();
    }

    public static void addToAnimationList(Animation animation) {
        animationList.add(animation);
    }

    public static Animation wrapAnimation(Animation animation, Runnable after) {
        EventHandler<ActionEvent> originalHandler = animation.getOnFinished();

        animation.setOnFinished(event -> {
            if (originalHandler != null) {
                originalHandler.handle(event);
            }
            after.run();
        });

        return animation;
    }

    public static void add(Animation animation) {
        animationList.add(animation);
    }

    public static void playAnimations(Runnable onComplete) {
        if (animationList.isEmpty()) {
            onComplete.run();
            return;
        }

        Animation current = animationList.remove(0);
        EventHandler<ActionEvent> originalHandler = current.getOnFinished();

        current.setOnFinished(event -> {
            if (originalHandler != null) originalHandler.handle(event);
            playAnimations(onComplete); // rekursiv weiter
        });

        current.play();
    }

    public static void clear() {
        animationList.clear();
    }
}
