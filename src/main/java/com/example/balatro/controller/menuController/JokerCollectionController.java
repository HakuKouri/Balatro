package com.example.balatro.controller.menuController;

import com.example.balatro.Balatro;
import com.example.balatro.domain.card.Joker;
import com.example.balatro.domain.util.MenuManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;

public class JokerCollectionController {

    //region FXML
    @FXML
    private ImageView image_1, image_2, image_3, image_4, image_5, image_6, image_7, image_8, image_9, image_10, image_11, image_12, image_13, image_14, image_15;
    @FXML
    private Label page_Label;
    //endregion

    //region Attributes
    private final ObservableList<Joker> jokers = FXCollections.observableArrayList();
    private final IntegerProperty pageNumber = new SimpleIntegerProperty(0);
    private final String pageString = "Page %d/10";
    //endregion


    public void initialize(){
        //TODO Placerholder for Unknown and Locked Joker
        jokers.setAll(Balatro.getGameModel().getAllJokerList().subList(0,15));
        List<ImageView> imageList = Arrays.asList(image_1, image_2, image_3, image_4, image_5, image_6, image_7, image_8, image_9, image_10, image_11, image_12, image_13, image_14, image_15);

        for (int index = 0; index < imageList.size(); index++) {
            int finalIndex = index;
            imageList.get(index).imageProperty().bind(Bindings.createObjectBinding(() -> jokers.get(finalIndex).getImage(), jokers));
        }

        pageNumber.addListener((observable, oldValue, newValue) -> {
            page_Label.setText(String.format(pageString, newValue.intValue() + 1));
            int startIndex = newValue.intValue() * 15;
            int endIndex = newValue.intValue() * 15 + 15;

            jokers.setAll(Balatro.getGameModel().getAllJokerList().subList(startIndex, endIndex));
        });
    }

    //region Functions
    public void prevPage(ActionEvent actionEvent) {
        System.out.println("Previous Page");
        if(pageNumber.get() == 0) {
            pageNumber.set(9);
        } else  {
            pageNumber.set(pageNumber.get() - 1);
        }
    }

    public void nextPage(ActionEvent actionEvent) {
        System.out.println("Next Page");
        if(pageNumber.get() == 9) {
            pageNumber.set(0);
        } else  {
            pageNumber.set(pageNumber.get() + 1);
        }
    }

    public void closeMenu(ActionEvent actionEvent) {
        MenuManager.getInstance().closeMenu();
    }

    //endregion
}
