package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.controller.BlindPickPanelsController;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BlindBoxController implements Initializable {

    @FXML
    private AnchorPane smallBlind;

    private final GameModel gameModel = Balatro.getGameModel();

    private BlindPickPanelsController smallBlindController;
    private BlindPickPanelsController bigBlindController;
    private BlindPickPanelsController bossBlindController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loaderSmall = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
        FXMLLoader loaderBig = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
        FXMLLoader loaderBoss = new FXMLLoader(getClass().getResource("/com/example/balatro/blindPickPanels.fxml"));
        try {
            //loader.setRoot(smallBlind);
            //loader.setControllerFactory(param -> null); // deaktiviert neuen Controller
            System.out.println("Small Blind Load");
            loaderSmall.load();
            smallBlindController = loaderSmall.getController(); // Hier bekommst du den Controller
            System.out.println("Big Blind Load");
            loaderBig.load();
            bigBlindController = loaderBig.getController();
            System.out.println("Boss Blind Load");
            loaderBoss.load();
            bossBlindController = loaderBoss.getController();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setUpBlinds() {
//        smallBlindController.setBlind(
//                gameModel.getRunBlinds().get(0),
//                gameModel.getRunTags().get(0),
//                1);
//        smallBlindController.blindProperty().bind(Bindings.createObjectBinding(() -> {
//            System.out.println("Setup Blinds: " + gameModel.getRunBlinds().stream().findFirst().get().getBlindName());
//            return gameModel.getRunBlinds().get((gameModel.getAnte()-1)*3);
//        }));
        System.out.println("Small blind schould be: " + gameModel.getRunBlinds().get(0).getBlindName());


        //smallBlindController.setBlind(gameModel.getRunBlinds().get(0));
    }

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
