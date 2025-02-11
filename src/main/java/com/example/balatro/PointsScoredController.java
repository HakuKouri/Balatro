package com.example.balatro;

import com.example.balatro.models.PointsScoredModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static javafx.beans.binding.Bindings.createObjectBinding;



public class PointsScoredController {

    @FXML
    private Label pointsScoredLabel;
    @FXML
    private ImageView stakeImageView;

    public PointsScoredModel model = new PointsScoredModel();
    private StringProperty stakeChipImageUrl = new SimpleStringProperty("");

    public void initialize() {
        System.out.println("Property value: " + stakeChipImageUrl.get());
        stakeChipImageUrl.addListener((obs, oldVal, newVal) -> {
            System.out.println("Image changed: " + newVal);
        });
        System.out.println("Property value: " + stakeChipImageUrl.get());

        pointsScoredLabel.textProperty().bind(model.scoredPointsProperty().asString());
        stakeImageView.imageProperty().bind(createObjectBinding(
                () -> new Image(stakeChipImageUrl.get().isEmpty() ?
                        "file:src/main/resources/com/images/Stickers_Seals/difficult_1.png" :
                        stakeChipImageUrl.get(),true),
                stakeChipImageUrl
                ));
        stakeImageView.setPreserveRatio(true);
        stakeImageView.setFitHeight(44);
    }

    public void setStakeImageView(String imageUrl) {
        System.out.println("Model saved Url: " + stakeChipImageUrl.get());
        System.out.println("Argument url: " + imageUrl);

        stakeChipImageUrl.set("file:" +imageUrl);
        System.out.println("Model saved Url after set: " + stakeChipImageUrl.get());
    }
}
