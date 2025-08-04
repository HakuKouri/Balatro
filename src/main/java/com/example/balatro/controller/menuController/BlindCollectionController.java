package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.data.SqlHandler;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.util.MenuManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlindCollectionController {
    @FXML
    private ImageView blind_1_ImageView, blind_2_ImageView, blind_3_ImageView, blind_4_ImageView, blind_5_ImageView, blind_6_ImageView, blind_7_ImageView, blind_8_ImageView, blind_9_ImageView, blind_10_ImageView, blind_11_ImageView, blind_12_ImageView, blind_13_ImageView, blind_14_ImageView, blind_15_ImageView, blind_16_ImageView, blind_17_ImageView, blind_18_ImageView, blind_19_ImageView, blind_20_ImageView, blind_21_ImageView, blind_22_ImageView, blind_23_ImageView, blind_24_ImageView, blind_25_ImageView, blind_26_ImageView, blind_27_ImageView, blind_28_ImageView, blind_29_ImageView, blind_30_ImageView, blind_31_ImageView, blind_32_ImageView, blind_33_ImageView, blind_34_ImageView, blind_35_ImageView, blind_36_ImageView;


    public void initialize(){
        List<ImageView> viewList = Arrays.asList(blind_1_ImageView, blind_2_ImageView, blind_3_ImageView, blind_4_ImageView, blind_5_ImageView, blind_6_ImageView, blind_7_ImageView, blind_8_ImageView,blind_9_ImageView,blind_10_ImageView,blind_11_ImageView,blind_12_ImageView,blind_13_ImageView,blind_14_ImageView,blind_15_ImageView,blind_16_ImageView,blind_17_ImageView,blind_18_ImageView,blind_19_ImageView,blind_20_ImageView, blind_21_ImageView,blind_22_ImageView,blind_23_ImageView,blind_24_ImageView,blind_25_ImageView,blind_26_ImageView,blind_27_ImageView,blind_28_ImageView,blind_29_ImageView,blind_30_ImageView,blind_31_ImageView,blind_32_ImageView,blind_33_ImageView,blind_34_ImageView,blind_35_ImageView,blind_36_ImageView);
        List<Blind> blindList = Balatro.getGameModel().getAllBlindsList();

        for(int index = 0; index < viewList.size(); index++) {
            //TODO Set Blind Images on discovered Blinds
            viewList.get(index).setImage(new Image("file:src/main/resources/com/images/Blinds/unknown_blind.gif"));
            viewList.get(index).setPreserveRatio(true);
            viewList.get(index).setFitWidth(150);
        }
    }

    public void closeMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().openCollectionMenu();
    }
}
