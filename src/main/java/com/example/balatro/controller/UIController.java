package com.example.balatro.controller;

import com.example.balatro.Balatro;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
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

public class UIController {

    private static IntegerProperty gameSpeed = new SimpleIntegerProperty();
    private static List<Animation> animationList = new ArrayList<>();

    public static void setupUiController() {
        gameSpeed.bind( Balatro.getSettings().gameSpeedProperty());
    }

    public static void addCardClickEvent(StackPane stackPane, Map<CardViewController, AnchorPane> map) {
        stackPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            Node source = (Node) event.getTarget();
            AnchorPane anchorPane = null;
            CardViewController cardViewController = null;

            if (source instanceof ImageView) {
                anchorPane = (AnchorPane) source.getParent().getParent().getParent();
                cardViewController = CardViewController.getCardViewController(map,anchorPane);
                cardViewController.setSelected(!cardViewController.isSelected());
            } else {
                Node currentNode = source;
                if (source.getParent() instanceof Label) {
                    currentNode = source.getParent();
                }

                if (currentNode instanceof Label) {
                    anchorPane = (AnchorPane) currentNode.getParent().getParent().getParent();
                    cardViewController = CardViewController.getCardViewController(map,anchorPane);

                    if (currentNode.getId().equals("buyLabel")) {
                        System.out.println("BuyLabel clicked");
                        GameController.getInstance().buyItem(anchorPane, cardViewController);
                    } else if(currentNode.getId().equals("sellLabel")) {
                        System.out.println("SellLabel clicked");
                        GameController.getInstance().sellItem(anchorPane,cardViewController,map);
                    }
                }
            }
        });
    }

    public static void bindStackPane(ObservableMap<CardViewController, AnchorPane> map, StackPane stackPane) {
        map.addListener((MapChangeListener<? super CardViewController, ? super AnchorPane>) change -> {
            if (change.wasAdded()) {
                AnchorPane anchorPane = change.getValueAdded();
                CardViewController controller = change.getKey();
                anchorPane.translateYProperty().bind(Bindings.createDoubleBinding(() ->
                        controller.isSelected() ? -50.0 : 0.0  ,controller.selectedProperty()));
                stackPane.getChildren().addAll(anchorPane);
            }
            if (change.wasRemoved()) {
                stackPane.getChildren().removeAll(change.getValueRemoved());
            }
            moveCards(stackPane);
        });
    }

    public static void moveCards(StackPane stackPane) {
        int cards = stackPane.getChildren().size();
        if (cards == 0) return;

        if(cards > 5) stackPane.setAlignment(Pos.CENTER_LEFT);
        else          stackPane.setAlignment(Pos.CENTER);

        double cardWidth = 200;
        double paneWidth = stackPane.getWidth();
        double margin = 10;
        double availableSpace = paneWidth - cardWidth * cards;
        double spacing = availableSpace < 0 ? availableSpace / (cards - 1) : 20;
        System.out.println(spacing);
        double pos;
        double centerIndex = (cards - 1) / 2.0;

        for(int i = 0; i < cards; i++) {
            if(cards > 5) {
                pos = margin + i * (cardWidth + spacing);
            } else {
                pos = (i - centerIndex) * (cardWidth - cards * 4);
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
