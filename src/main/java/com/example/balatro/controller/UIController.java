package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Card;
import com.example.balatro.domain.card.PlayingCard;
import com.example.balatro.domain.game.checkHand;
import com.example.balatro.domain.rules.PokerHand;
import com.example.balatro.domain.util.CardViewManager;
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

    private static final IntegerProperty gameSpeed = new SimpleIntegerProperty();
    private static final List<Animation> animationList = new ArrayList<>();

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

                final double[] pressX = new double[1];
                final double[] pressY = new double[1];
                final Delta dragDelta = new Delta();

                //Mouse Pressed
                anchorPane.setOnMousePressed(e -> {
                    dragDelta.x = e.getSceneX() - anchorPane.getTranslateX();
                    dragDelta.y = e.getSceneY() - anchorPane.getTranslateY();

                    pressX[0] = e.getScreenX();
                    pressY[0] = e.getScreenY();

                    anchorPane.toFront();
                });

                //Mouse Drag
                anchorPane.setOnMouseDragged(e -> {
                    Pos alignment = stackPane.getAlignment();
                    double newX = e.getSceneX() - dragDelta.x;
                    double newY = e.getSceneY() - dragDelta.y;

                    double halfCardWidth = anchorPane.getWidth() / 2;
                    double halfCardHeight = anchorPane.getHeight() / 2;

                    double clampedX, clampedY;

                    if (alignment == Pos.CENTER || alignment == null) {
                        double halfWidth = stackPane.getWidth() / 2;
                        clampedX = Math.max(-halfWidth + halfCardWidth, Math.min(halfWidth - halfCardWidth, newX));
                    } else if (alignment == Pos.CENTER_LEFT) {
                        // Linksbündig: Start bei X = 0
                        double maxX = stackPane.getWidth() - anchorPane.getWidth();
                        clampedX = Math.max(0, Math.min(maxX, newX));
                    } else {
                        clampedX = newX;
                    }

                    double halfHeight = stackPane.getHeight() / 2;

                    double minY = -halfHeight + halfCardHeight;
                    double maxY = halfHeight - halfCardHeight;

                    clampedY = Math.max(minY, Math.min(maxY, newY));

                    anchorPane.setTranslateX(clampedX);
                    anchorPane.setTranslateY(clampedY);

                    snapAnchorPaneToNewIndex(stackPane,anchorPane);

                    moveCards(stackPane, anchorPane);// Visuelles Neulayout
                    anchorPane.toFront();
                });

                //Mouse Release
                anchorPane.setOnMouseReleased(e -> {
                    double dx = Math.abs(e.getScreenX() - pressX[0]);
                    double dy = Math.abs(e.getScreenY() - pressY[0]);
                    double dragDistance = Math.sqrt(dx * dx + dy * dy);

                    //Klick
                    if (dragDistance < 10) {
                        handleCardSelection(controller, Balatro.getGameModel());
                        anchorPane.toFront();
                    }
                    //Drag
                    else {
                        snapAnchorPaneToNewIndex(stackPane, anchorPane);
                        moveCards(stackPane);
                        map.keySet().forEach(otherController -> { if(otherController.isSelected()) map.get(otherController).toFront(); });
                    }
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
        List<Node> sortedChildren = stackPane.getChildren().stream()
                .filter(n -> n != anchorPane)
                .sorted(Comparator.comparingDouble(Node::getTranslateX))
                .toList();

        double draggedX = anchorPane.getTranslateX();
        int insertIndex = 0;

        for (int i = 0; i < sortedChildren.size(); i++) {
            if (draggedX > sortedChildren.get(i).getTranslateX()) {
                insertIndex = i + 1;
            }
        }

        List<Node> newOrder = new ArrayList<>(sortedChildren);
        newOrder.add(insertIndex, anchorPane);
        stackPane.getChildren().setAll(newOrder);
    }

    /**
     * Kleine Klasse zur Speicherung der relativen Drag-Position.
     */
    private static class Delta {
        double x, y;
    }

    public static void moveCards(StackPane stackPane) {
        moveCards(stackPane,new Card());
    }

    public static void moveCards(StackPane stackPane, Node exclude) {
        int count = stackPane.getChildren().size();
        if (count == 0) return;

        double cardWidth = 200;
        double margin = 10;
        double paneWidth = stackPane.getWidth();
        double spacing;

        if (count > 5) {
            stackPane.setAlignment(Pos.CENTER_LEFT);
            double availableSpace = paneWidth - (cardWidth * count);
            spacing = availableSpace / Math.max(1, count - 1); // Schutz vor Division durch 0
        } else {
            stackPane.setAlignment(Pos.CENTER);
            spacing = 20; // Wird bei ≤5 ignoriert
        }

        double centerIndex = (count - 1) / 2.0;

        for (int i = 0; i < count; i++) {
            Node node = stackPane.getChildren().get(i);
            if (node == exclude) continue;

            double translateX;
            if (count > 5) {
                translateX = margin + i * (cardWidth + spacing);
            } else {
                translateX = (i - centerIndex) * (cardWidth - count * 4);
            }

            node.setTranslateX(translateX);
        }
    }

    public static void handleCardSelection(CardViewController controller, GameModel gameModel) {
        Card card = controller.getCard();

        if (card instanceof PlayingCard playingCard) {
            if (controller.isSelected()) {
                controller.selectedProperty().set(false);
                gameModel.removeCardFromSelectedCards(playingCard);
            } else if (gameModel.getSelectedCards().size() < 5) {
                controller.selectedProperty().set(true);
                gameModel.addCardToSelectedCards(playingCard);
            }
            setHandInfo(Balatro.getGameModel(), checkHand.evaluateHands(gameModel, gameModel.getSelectedCards()));
        } else {
            // Nur eine andere Karte auswählbar (z. B. Joker, Consumable)
            gameModel.getHandCardViewManager().getViewMap().keySet()
                    .forEach(cvc -> cvc.selectedProperty().set(false));
            controller.selectedProperty().set(true);
        }
    }

    public static void setHandInfo(GameModel gameModel, List<PokerHand> hands) {
        if (hands.isEmpty()) {
            gameModel.getBestHand().setHand(new PokerHand());
            gameModel.getPossiblePokerHand().clear();
            return;
        }

        gameModel.getPossiblePokerHand().setAll(hands);

        PokerHand bestHand = hands.stream()
                .max(Comparator.comparingInt(h -> h.getChips() * h.getMulti()))
                .orElse(new PokerHand());

        gameModel.getBestHand().setHand(bestHand);
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

        if (Objects.equals(to, "middle")) {
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
        return new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        }));
    }

    public static void playAnimation(List<Animation> list) {
        if (list.isEmpty()) {
            return;
        }
        Animation first = list.removeFirst();
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

        Animation current = animationList.removeFirst();
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
