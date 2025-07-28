package com.example.balatro.controller;

import com.example.balatro.domain.rewards.Tag;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlindSkipPaneController {

    @FXML
    private Button skipBlind_Button;
    @FXML
    private ImageView tag_ImageView;

    private final ObjectProperty<Tag> tag = new SimpleObjectProperty<>(new Tag());
    private BlindBoxPanelController blindPanelController;

    public void initialize() {
        tag_ImageView.imageProperty().bind(tag.get().imageProperty());
    }

    //region Getter & Setter
    public void setBlindPanelController(BlindBoxPanelController controller) {
        blindPanelController = controller;
    }

    public Tag getTag() {
        return tag.get();
    }

    public void setTag(Tag newTag) {
        getTag().setTag(newTag);
    }


    //endregion

    public void onClickSkip(ActionEvent actionEvent) {
        //GameController.getInstance().skip(tag.get());
        blindPanelController.skip(getTag());
        //BlindBoxController.nextBlind();
    }
}
