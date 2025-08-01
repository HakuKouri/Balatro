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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;

public class JokerCollectionController {


    public ImageView image_1;
    public ImageView image_2;
    public ImageView image_3;
    public ImageView image_4;
    public ImageView image_5;
    public ImageView image_6;
    public ImageView image_7;
    public ImageView image_8;
    public ImageView image_9;
    public ImageView image_10;
    public ImageView image_11;
    public ImageView image_12;
    public ImageView image_13;
    public ImageView image_14;
    public ImageView image_15;
    public Label page_Label;


    private final ObservableList<Joker> jokers = FXCollections.observableArrayList();
    private final IntegerProperty pageNumber = new SimpleIntegerProperty(0);
    private final String pageString = "Page %d/10";

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
}
