package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Blind;
import com.example.balatro.classes.Tag;
import com.example.balatro.models.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    }

    public List<BlindBoxPanelController> setBlindPanels() {
        List<BlindBoxPanelController> panelControllerList = new ArrayList<>();

        try {
            smallBlindPanel = loaderSmallBlind.load();
            smallBlindController = loaderSmallBlind.getController();

            bigBlindPanel = loaderBigBlind.load();
            bigBlindController = loaderBigBlind.getController();
            panelControllerList.add(bigBlindController);

            bossBlindPanel = loaderBossBlind.load();
            bossBlindController = loaderBossBlind.getController();
            panelControllerList.add(bossBlindController);

            blindBox.getChildren().add(smallBlindPanel);
            blindBox.getChildren().add(bigBlindPanel);
            blindBox.getChildren().add(bossBlindPanel);

            smallBlindController.setBossPanel(false);
            bigBlindController.setBossPanel(false);
            bossBlindController.setBossPanel(true);

            gameModel.getRunState().anteProperty().addListener((obs, oldAnte, newAnte) -> {
                smallBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getRunState().getAnte() - 1) * 3));
                smallBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getRunState().getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(smallBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
                smallBlindController.setTag(gameModel.getRunTags().isEmpty() ? new Tag() : gameModel.getRunTags().get((gameModel.getRunState().getAnte() - 1 ) * 2));

                bigBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getRunState().getAnte() - 1) * 3 + 1));
                bigBlindController.setMinScore(gameModel.getChipRequirement()[gameModel.getRunState().getAnte()].multiply(BigDecimal.valueOf(Double.parseDouble(bigBlindController.getBlind().getBlindScoreMultiplier().split("x")[0]))));
                bigBlindController.setTag(gameModel.getRunTags().isEmpty() ? new Tag() : gameModel.getRunTags().get((gameModel.getRunState().getAnte() -1 ) * 2 + 1));

                bossBlindController.blindProperty().get().setBlind(gameModel.getRunBlinds().isEmpty() ? new Blind() : gameModel.getRunBlinds().get((gameModel.getRunState().getAnte() - 1) * 3 + 2));
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

        return panelControllerList;
    }

    public void rerollBoss() {
        //TODO BOSS BLIND REROLL
    }
}


/*public class BlindBoxController {

    @FXML
    private BlindPickPanelsController smallBlind;
    @FXML
    private BlindPickPanelsController bigBlind;
    @FXML
    private BlindPickPanelsController bossBlind;

    private final GameModel gameModel = GameController.getGameModel();

    public void initialize() {
        System.out.println("BlindboxController");

//        smallBlind.blindProperty().bind(Bindings.createObjectBinding(() -> {
//            return gameModel.getRunBlinds().get((gameModel.getAnte()-1)*3);
//        }));
//        bigBlind.blindProperty().bind(Bindings.createObjectBinding(() -> {
//            return gameModel.getRunBlinds().get((gameModel.getAnte()-1)*3+1);
//        }));
//        bossBlind.blindProperty().bind(Bindings.createObjectBinding(() -> {
//            return gameModel.getRunBlinds().get((gameModel.getAnte()-1)*3+2);
//        }));
//        bossBlind.setBossPanel(true);
    }

}*/
