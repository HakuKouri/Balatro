package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.models.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BlindBoxController {

    public HBox blindBox;

    private final GameModel gameModel = Balatro.getGameModel();

    private BlindBoxPanelController smallBlindController;
    private BlindBoxPanelController bigBlindController;
    private BlindBoxPanelController bossBlindController;



    public void testBlinds(String test) {
        smallBlindController.getBlind().setBlindName("testController");
        smallBlindController.setName(test);
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
