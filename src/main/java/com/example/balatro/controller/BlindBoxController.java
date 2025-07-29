package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.util.FxmlUtil;
import com.example.balatro.models.GameModel;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlindBoxController {

    //region FXML
    @FXML private AnchorPane blindBox_AnchorPane;
    @FXML private HBox blindBox;
    //endregion

    //region Attributes
    private GameModel gameModel;
    private static final IntegerProperty activeBlind = new SimpleIntegerProperty(-1);

    private final Map<String, Pair<BlindBoxPanelController,AnchorPane>> controllerMap = new HashMap<>();
    private final List<String> mapOrder = List.of("small", "big", "boss");
    //endregion

    //region Getter & Setter
    public int getActiveBlind() {
        return activeBlind.get();
    }

    public IntegerProperty activeBlindProperty() {
        return activeBlind;
    }

    public void setActiveBlind(int index) {
        activeBlind.set(index);
    }

    //endregion

    public void initialize() {
        gameModel = Balatro.getGameModel();
        fxmlLoad();
        bindUi();

        Platform.runLater(() -> {
            controllerMap.get("small").getKey().setBlind(gameModel.getAllBlindsList().get(0));
            controllerMap.get("big").getKey().setBlind(gameModel.getAllBlindsList().get(1));
            controllerMap.get("boss").getKey().setBlind(getNewBoss());
        });
    }

    private void fxmlLoad() {
        for (int i = 0; i <= 2; i++) {
            controllerMap.put(mapOrder.get(i), FxmlUtil.loadWithPane("/com/example/balatro/blind-Box-Panel.fxml"));
            blindBox.getChildren().add(controllerMap.get(mapOrder.get(i)).getValue());
            controllerMap.get(mapOrder.get(i)).getKey().setBossPanel(i == 2);
        }

        Platform.runLater(() -> {
            setPanelSize(controllerMap.get("small").getValue());
            setPanelSize(controllerMap.get("big").getValue());
            setPanelSize(controllerMap.get("boss").getValue());
        });
    }

    private void bindUi() {
        gameModel.getRunState().anteProperty().addListener((obs, oldAnte, newAnte) -> {
            for (int i = 0; i <= 2; i++) {
                controllerMap.get(mapOrder.get(i)).getKey().setScoreToReach(gameModel.getChipRequirement()[gameModel.getRunState().getAnte()]);

                if (newAnte.intValue() > oldAnte.intValue() ) {
                    if(i == 2)
                        controllerMap.get("boss").getKey().setBlind(getNewBoss());
                    if(i < 2)
                        controllerMap.get(mapOrder.get(i)).getKey().setTag(getNewTag());
                }
            }
        });

        activeBlindProperty().addListener((obs, oldValue, newValue) -> {
            for (int i = 0; i <= 2; i++) {
                controllerMap.get(mapOrder.get(i)).getKey().setNextBlind(false);
            }
            controllerMap.get(mapOrder.get(newValue.intValue())).getKey().setNextBlind(true);
        });
    }

    private void setPanelSize(AnchorPane pane) {
        pane.setPrefHeight(blindBox.heightProperty().get());
        pane.setPrefWidth(Balatro.getSettings().getWindowWidth() * .16);
    }

    public void rerollBoss() {
        controllerMap.get("boss").getKey().setBlind(getNewBoss());
    }

    private Blind getNewBoss() {
        List<Blind> blindList = gameModel.getAllBlindsList().stream().filter(b -> b.getBlindId() > 2 && !gameModel.getRunState().getBossBlindsBeaten().contains(b)).toList();

        if (gameModel.getRunState().getAnte() % 8 == 0) {
            blindList = blindList.stream().filter(b -> b.getBlindMinimumAnte() == 8).toList();
        }

        return blindList.get(gameModel.getRand().nextInt(blindList.size()));
    }

    private Tag getNewTag() {
        List<Tag> tagList = gameModel.getAllTagList().stream().filter(t -> Integer.parseInt(t.getMinAnte()) <= gameModel.getRunState().getAnte()).toList();
        return tagList.get(gameModel.getRand().nextInt(tagList.size()));
    }

    public static void nextBlind() {
        if (activeBlind.get() + 1 > 2) {
            activeBlind.set(0);
        } else {
            activeBlind.set(activeBlind.get() + 1);
        }
    }


}

