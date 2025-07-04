package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.enums.SlideDirection;
import com.example.balatro.models.GameModel;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.*;

public class UIController {

    private static IntegerProperty gameSpeed = new SimpleIntegerProperty();
    private static List<Animation> animationList = new ArrayList<>();

    public static void setupUiController() {
        gameSpeed.bind(Balatro.getSettings().gameSpeedProperty());
    }

    public static void bindGameUi(GameController gameController) {

    }


    public static void bindHandInfo(Label name, Label level, Label chips, Label multi, GameModel model) {
        name.textProperty().bind(Bindings.createStringBinding(() -> {
            String best = model.getBestHand().getName();
            boolean isRoyal = "Straight Flush".equals(best) &&
                    model.getSelectedCards().stream().anyMatch(card -> "Ace".equals(card.getRank()));
            return isRoyal ? "Royal Flush" : best;
        }, model.getBestHand().nameProperty(), model.getPlayedCards()));

        level.textProperty().bind(
                Bindings.when(model.getBestHand().levelProperty().greaterThan(0))
                        .then(Bindings.concat("lv. ", model.getBestHand().levelProperty().asString()))
                        .otherwise("lv."));
        chips.textProperty().bind(Bindings.convert(model.getBestHand().chipsProperty()));
        multi.textProperty().bind(Bindings.convert(model.getBestHand().multiProperty()));
    }

    public static void bindRunInfo(Label hands, Label discards, Label money, Label ante, Label round, GameModel gameModel) {
        hands.textProperty().bind(Bindings.createStringBinding(() ->
                String.valueOf(gameModel.getCurrentRound().getHands()), gameModel.getCurrentRound().handsProperty()));
        discards.textProperty().bind(Bindings.createStringBinding(() ->
                String.valueOf(gameModel.getCurrentRound().getDiscards()), gameModel.getCurrentRound().discardsProperty()));
        money.textProperty().bind(Bindings.createStringBinding(() ->
                "$" + gameModel.getRunState().getMoney(), gameModel.getRunState().moneyProperty()));
        ante.textProperty().bind(Bindings.createStringBinding(() ->
                gameModel.getRunState().getAnte() + "/8", gameModel.getRunState().anteProperty()));
        round.textProperty().bind(Bindings.createStringBinding(() ->
                String.valueOf(gameModel.getRunState().getRound()), gameModel.getRunState().roundProperty()));
    }

    public static void bindBlindToBeatInfo(Label name, Label effect, ImageView blind, ImageView stake, Label score, Label reward, GameModel gameModel) {
        name.textProperty().bind(gameModel.activeBlindProperty().get().blindNameProperty());

        effect.textProperty().bind(Bindings.createStringBinding(() -> {
            return gameModel.activeBlindProperty().get().getBlindId() < 2 ? "" : gameModel.activeBlindProperty().get().getBlindDescription();
        }));

        blind.imageProperty().bind(gameModel.getActiveBlind().imageProperty());

        stake.imageProperty().bind(gameModel.getRunState().getChosenStake().imageProperty());

        score.textProperty().bind(Bindings.createStringBinding(() ->
                        String.valueOf(gameModel.getScoreToReach()),
                gameModel.scoreToReachProperty()
        ));

        reward.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, gameModel.getActiveBlind().getBlindReward())),
                gameModel.getActiveBlind().blindRewardProperty()
        ));
    }

    public static void bindScoredPointsInfo(ImageView stake, Label score, GameModel gameModel) {
        stake.imageProperty().bind(gameModel.getRunState().getChosenStake().imageProperty());

        score.textProperty().bind(
                Bindings.createStringBinding( () -> gameModel.getScoredPoints().toString(),
                        gameModel.scoredPointsProperty()));
    }


    //Sizing
    public static void configurePlaceHolder(AnchorPane anchorPane) {
        anchorPane.setPrefWidth(Balatro.getSettings().getWindowWidth() * .53);
        anchorPane.setPrefHeight(Balatro.getSettings().getWindowHeight() * .72);
        anchorPane.setLayoutX(Balatro.getSettings().getWindowWidth() * .26);
        anchorPane.setLayoutY(Balatro.getSettings().getWindowHeight() * .3);
    }

    /**
    * Diese Methode bindet MouseClick events an StackPanes,
    * um zu erkennen, welches Label geklickt worden sind
    */
    public static void addCardClickEvent(StackPane stackPane, Map<CardViewController, AnchorPane> map) {
        stackPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            Node source = (Node) event.getTarget();
            AnchorPane anchorPane = null;
            CardViewController cardViewController = null;

            Node currentNode = source;
            // Falls das Textfeld eines Labels geklickt wurde
            if (source.getParent() instanceof Label) {
                currentNode = source.getParent();
            }

            //Führe bestimmte Aktionen aus anhand des geklickten Labels
            if (currentNode instanceof Label) {
                anchorPane = getPaneById(currentNode, "card_AnchorPane");
                cardViewController = CardViewController.getCardViewController(map, anchorPane);

                switch (currentNode.getId()) {
                    case "buyLabel":
                        System.out.println("BuyLabel clicked");
                        GameController.getInstance().buyItem(anchorPane, cardViewController);
                        break;
                    case "buyUseSell_Label":
                        System.out.println("SellLabel clicked");
                        if (cardViewController.isInShop())
                            GameController.getInstance().buyAndUse(cardViewController);
                        else
                            GameController.getInstance().sellItem(anchorPane, cardViewController, map);
                        break;
                    case "use_Label":
                        System.out.println("UseLabel clicked");
                        GameController.getInstance().useCard(anchorPane, cardViewController, map);
                        break;
                }
            }
        });
    }

    /**
    * Diese Methode bindet visuelle Effekte an eine StackPane
    * z.B. selected oder Drag and Drop
    */
    public static void bindStackPane(ObservableMap<CardViewController, AnchorPane> map, StackPane stackPane) {
        map.addListener((MapChangeListener<? super CardViewController, ? super AnchorPane>) change -> {

            if (change.wasAdded()) {
                AnchorPane anchorPane = change.getValueAdded();
                CardViewController controller = change.getKey();

                // (1) Auswahl-Visualisierung
                controller.selectedProperty().addListener((observable, oldVal, isSelected) -> {
                    anchorPane.setTranslateY(isSelected ? -50.0 : 0.0);
                });

                // (2) Drag + Click Unterscheidung vorbereiten
                final double[] pressX = new double[1];
                final double[] pressY = new double[1];
                final Delta dragDelta = new Delta();

                // (3) Maus gedrückt
                anchorPane.setOnMousePressed(e -> {
                    dragDelta.x = e.getSceneX() - anchorPane.getTranslateX();
                    dragDelta.y = e.getSceneY() - anchorPane.getTranslateY();

                    pressX[0] = e.getScreenX();
                    pressY[0] = e.getScreenY();

                    anchorPane.toFront(); // Karte optisch nach vorne holen
                });

                // (4) Maus gezogen
                anchorPane.setOnMouseDragged(e -> {
                    anchorPane.setTranslateX(e.getSceneX() - dragDelta.x);
                    anchorPane.setTranslateY(e.getSceneY() - dragDelta.y);

                    // Während des Drags: Andere Karten visuell umsortieren
                    snapAnchorPaneToNewIndex(stackPane,anchorPane);

                    moveCards(stackPane, anchorPane);// Visuelles Neulayout
                    anchorPane.toFront();
                });

                // (5) Maus losgelassen – Click oder Drag?
                anchorPane.setOnMouseReleased(e -> {
                    double dx = Math.abs(e.getScreenX() - pressX[0]);
                    double dy = Math.abs(e.getScreenY() - pressY[0]);

                    // (5a) Click (kleine Bewegung)
                    if (dx < 100 && dy < 100) {
                        for(CardViewController cardViewController : map.keySet())
                            if(cardViewController != controller) cardViewController.selectedProperty().set(false);
                        controller.selectedProperty().set(!controller.isSelected());
                        anchorPane.toFront();
                        return;
                    }

                    // (5b) Drag & Snap
                    snapAnchorPaneToNewIndex(stackPane, anchorPane);
                    moveCards(stackPane); // visuelles Neulayout
                });

                // (6) Karte der StackPane hinzufügen
                if (!stackPane.getChildren().contains(anchorPane)) {
                    stackPane.getChildren().add(anchorPane);
                }
            }

            // (7) Karte wurde entfernt
            if (change.wasRemoved()) {
                stackPane.getChildren().remove(change.getValueRemoved());
            }

            // (8) Immer Layout aktualisieren
            moveCards(stackPane);
        });
    }

    public static void bindStackPane(ObservableList<PlayingCard> playingCardsList, StackPane stackPane) {
        playingCardsList.addListener((ListChangeListener<? super PlayingCard>) change -> {
            while (change.next()) {
                if(change.wasAdded()) {
                    stackPane.getChildren().addAll(change.getAddedSubList());
                }
                if(change.wasRemoved()) {
                    stackPane.getChildren().removeAll(change.getRemoved());
                }
            }
            moveCards(stackPane);
        });
    }

    /**
     * Diese Methode bestimmt, an welche Position im StackPane
     * die gezogene Karte eingefügt werden soll (Snapping-Logik).
     */
    private static void snapAnchorPaneToNewIndex(StackPane stackPane, AnchorPane anchorPane) {
        List<Node> children = new ArrayList<>(stackPane.getChildren());
        children.remove(anchorPane);

        double draggedX = anchorPane.getTranslateX();
        int insertIndex = 0;

        for (int i = 0; i < children.size(); i++) {
            if (draggedX > children.get(i).getTranslateX()) {
                insertIndex = i + 1;
            }
        }

        children.add(insertIndex, anchorPane);
        stackPane.getChildren().setAll(children);
    }

    /**
     * Kleine Klasse zur Speicherung der relativen Drag-Position.
     */
    private static class Delta {
        double x, y;
    }


    public static void moveCards(StackPane stackPane) {
        int cards = stackPane.getChildren().size();
        if (cards == 0) return;

        if (cards > 5) stackPane.setAlignment(Pos.CENTER_LEFT);
        else stackPane.setAlignment(Pos.CENTER);

        double cardWidth = 200;
        double paneWidth = stackPane.getWidth();
        double margin = 10;
        double availableSpace = paneWidth - cardWidth * cards;
        double spacing = availableSpace < 0 ? availableSpace / (cards - 1) : 20;
        double pos;
        double centerIndex = (cards - 1) / 2.0;

        for (int i = 0; i < cards; i++) {
            if (cards > 5) {
                pos = margin + i * (cardWidth + spacing);
            } else {
                pos = (i - centerIndex) * (cardWidth - cards * 4);
            }

            stackPane.getChildren().get(i).setTranslateX(pos);

            for (AnchorPane pane : Balatro.getGameModel().getActiveJokerMap().values()) {
                if(CardViewController.getCardViewController(Balatro.getGameModel().getActiveJokerMap(), pane).isSelected()) {
                    pane.setTranslateY(-50);
                } else pane.setTranslateY(0);
            }
        }
    }

    public static void moveCards(StackPane stackPane, Node exclude) {
        int cards = stackPane.getChildren().size();
        if (cards == 0) return;

        if (cards > 5) stackPane.setAlignment(Pos.CENTER_LEFT);
        else stackPane.setAlignment(Pos.CENTER);

        double cardWidth = 200;
        double paneWidth = stackPane.getWidth();
        double margin = 10;
        double availableSpace = paneWidth - cardWidth * cards;
        double spacing = availableSpace < 0 ? availableSpace / (cards - 1) : 20;
        double centerIndex = (cards - 1) / 2.0;

        for (int i = 0; i < cards; i++) {
            Node node = stackPane.getChildren().get(i);
            if (node == exclude) continue; // Ziehende Karte bleibt wo sie ist

            double pos;
            if (cards > 5) {
                pos = margin + i * (cardWidth + spacing);
            } else {
                pos = (i - centerIndex) * (cardWidth - cards * 4);
            }
            node.setTranslateX(pos);
        }
    }

    private static AnchorPane getPaneById(Node node, String id) {
        if (node.getId() != null && node.getId().equals(id)) {
            return (AnchorPane) node;
        }

        Node parent = node.getParent();
        if (parent == null) {
            return null;
        }
        return getPaneById(parent, id);
    }

    //region Animation
    public static Timeline cardWiggleTimeline(Node card) {
        System.out.println("Game Speed: " + gameSpeed.get());
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0), new KeyValue(card.rotateProperty(), 0)),
                new KeyFrame(Duration.millis((double) 4 / gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 8 / gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 12 / gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 16 / gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 20 / gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 24 / gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 28 / gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 32 / gameSpeed.get()), new KeyValue(card.rotateProperty(), 10)),
                new KeyFrame(Duration.millis((double) 36 / gameSpeed.get()), new KeyValue(card.rotateProperty(), -10)),
                new KeyFrame(Duration.millis((double) 40 / gameSpeed.get()), new KeyValue(card.rotateProperty(), 0)));

        timeline.setCycleCount(3);
        timeline.setDelay(Duration.seconds(0.2));

        return timeline;
    }

    public static TranslateTransition cardMoveToAnimation(Node card) {
        return cardMoveToAnimation(card, "", "");
    }

    public static TranslateTransition cardMoveToAnimation(Node card, String from, String to) {
        Scene scene = card.getScene();

        double targetX = scene.getWidth() + 300;
        double targetY = scene.getHeight() / 2;

        if (to == "middle") {
            targetX = scene.getWidth() / 2;
            targetY = scene.getHeight() / 3 * 2;
        }

        // Aktuelle Position der Karte relativ zur Szene
        Bounds bounds = card.localToScene(card.getBoundsInLocal());
        double currentX = bounds.getMinX() + bounds.getWidth() / 2;
        double currentY = bounds.getMinY() + bounds.getHeight() / 2;

        // Differenz berechnen
        double deltaX = targetX - currentX;
        double deltaY = targetY - currentY;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(.1), card);
        transition.setByX(deltaX);
        transition.setByY(deltaY);

        return transition;
    }

    public static Timeline delayTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        }));
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

    //endregion


    //UI
    public static void bindAnimatedVisibility(BooleanProperty visibilityProperty, Node node, SlideDirection direction) {
        visibilityProperty.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                node.setVisible(true);
                animateSlide(node, true, direction, null); // Einblenden ohne Callback
            } else {
                animateSlide(node, false, direction, () -> node.setVisible(false)); // Erst Animation, dann ausblenden
            }
        });
    }

    public static void animateSlide(Node node, boolean visible, SlideDirection direction, Runnable after) {
        double screenHeight = Balatro.getSettings().getWindowHeight();
        double toY = visible ? 0 : (direction == SlideDirection.DOWN ? screenHeight : -screenHeight * 0.5);

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), node);
        transition.setToY(toY);
        transition.setInterpolator(Interpolator.EASE_BOTH);

        if (after != null) {
            transition.setOnFinished(event -> after.run());
        }

        transition.play();
    }
}
