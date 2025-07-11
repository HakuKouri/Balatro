package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.models.GameModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BlindBoxController {
    private final GameModel gameModel = Balatro.getGameModel();

    public AnchorPane blindBox_AnchorPane;
    public HBox blindBox;

    private final FXMLLoader loaderSmallBlind = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-Box-Panel.fxml"));
    private final FXMLLoader loaderBigBlind = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-Box-Panel.fxml"));
    private final FXMLLoader loaderBossBlind = new FXMLLoader(getClass().getResource("/com/example/balatro/blind-Box-Panel.fxml"));

    private BlindBoxPanelController smallBlindController;
    private BlindBoxPanelController bigBlindController;
    private BlindBoxPanelController bossBlindController;

    private AnchorPane smallBlindPanel;
    private AnchorPane bigBlindPanel;
    private AnchorPane bossBlindPanel;

    public void initialize() {
        setBlindPanels();
    }

    private void setBlindPanels() {
        try {
            smallBlindPanel = loaderSmallBlind.load();
            smallBlindController = loaderSmallBlind.getController();

            bigBlindPanel = loaderBigBlind.load();
            bigBlindController = loaderBigBlind.getController();

            bossBlindPanel = loaderBossBlind.load();
            bossBlindController = loaderBossBlind.getController();

            blindBox.getChildren().add(smallBlindPanel);
            blindBox.getChildren().add(bigBlindPanel);
            blindBox.getChildren().add(bossBlindPanel);

            smallBlindController.setBossPanel(false);
            bigBlindController.setBossPanel(false);
            bossBlindController.setBossPanel(true);

            gameModel.getRunState().anteProperty().addListener((obs, oldAnte, newAnte) -> {
                smallBlindController.blindProperty().get().setBlind(gameModel.getAllBlindsList().get(0));
                smallBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getRunState().getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(smallBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
                smallBlindController.setTag(getNewTag());

                bigBlindController.blindProperty().get().setBlind(gameModel.getAllBlindsList().get(1));
                bigBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getRunState().getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bigBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
                bigBlindController.setTag(getNewTag());

                bossBlindController.getBlind().setBlind(getNewBoss());
                bossBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getRunState().getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bossBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
            });

            gameModel.getRunState().roundProperty().addListener((obs, oldValue, newValue) -> {
                smallBlindPanel.setDisable(newValue.intValue()%3 != 0);
                bigBlindPanel.setDisable(newValue.intValue()%3 != 1 && newValue.intValue() != 0);
                bossBlindPanel.setDisable(newValue.intValue()%3 != 2 && newValue.intValue() != 0);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        smallBlindPanel.setPrefWidth(Balatro.getSettings().getWindowWidth() * .16);
        smallBlindPanel.setPrefHeight(blindBox.getHeight());
        bigBlindPanel.setPrefWidth(Balatro.getSettings().getWindowWidth() * .16);
        bigBlindPanel.setPrefHeight(blindBox.getHeight());
        bossBlindPanel.setPrefWidth(Balatro.getSettings().getWindowWidth() * .16);
        bossBlindPanel.setPrefHeight(blindBox.getHeight());
    }

    public void rerollBoss() {

        bossBlindController.getBlind().setBlind(getNewBoss());
    }

    private Blind getNewBoss() {
        List<Blind> blindList  = gameModel.getAllBlindsList().stream().filter(b -> b.getBlindId() > 2 && !gameModel.getRunState().getBossBlindsBeaten().contains(b)).toList();

        if(gameModel.getRunState().getAnte() % 8 == 0) {
            blindList = blindList.stream().filter(b -> b.getBlindMinimumAnte() == 8).toList();
        }

        return blindList.get(gameModel.getRand().nextInt(blindList.size()));
    }

    private Tag getNewTag() {
        List<Tag> tagList = gameModel.getAllTagList().stream().filter(t -> Integer.parseInt(t.getMinAnte()) <= gameModel.getRunState().getAnte()).toList();
        return tagList.get(gameModel.getRand().nextInt(tagList.size()));
    }
}

