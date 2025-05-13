package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.classes.Blind;
import com.example.balatro.classes.Tag;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.math.BigDecimal;

public class BlindBoxPanelController {

    public Label effectText_label;

    @FXML
    private Button btnSelectBlind;
    @FXML
    private AnchorPane skipAnchorPane;
    @FXML
    private AnchorPane blindPanel;
    @FXML
    private Label lblBlindName;
    @FXML
    private ImageView imageViewBlindChip;
    @FXML
    private ImageView imageViewStakeImage;
    @FXML
    private Label lblMinScore;
    @FXML
    private Label lblEarn;

    private final ObjectProperty<Blind> blind = new SimpleObjectProperty<>(new Blind());

    //region Game
    private GameController gameController = GameController.getInstance();
    private final GameModel gameModel = Balatro.getGameModel();
    //endregion

    //region Loader
    private final FXMLLoader loaderSkipPane = new FXMLLoader(getClass().getResource("/com/example/balatro/blindSkipPane.fxml"));
    private final FXMLLoader loaderBossPane = new FXMLLoader(getClass().getResource("/com/example/balatro/bossPane.fxml"));
    //endregion

    private BlindSkipPaneController blindSkipController;
    private AnchorPane skipPane;
    private AnchorPane bossPane;

    private int activeModulo = -1;

    public void initialize() {
        lblBlindName.textProperty().bind(blind.get().blindNameProperty());
        imageViewBlindChip.imageProperty().bind(blind.get().imageProperty());
        effectText_label.textProperty().bind(blind.get().blindDescriptionProperty());

        imageViewStakeImage.imageProperty().bind(gameModel.getChosenStake().imageProperty());

        lblEarn.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, blind.get().getBlindReward())),
                blind.get().blindRewardProperty()
        ));
        effectText_label.setWrapText(true);
    }

    //GETTER SETTER

    public void setActiveModulo(int value) {
        activeModulo = value;
    }
    public Blind getBlind() {
        return blind.get();
    }

    public ObjectProperty<Blind> blindProperty() {
        return blind;
    }

    /*public void setBlind(Blind blind, Tag tag, int blindNumber) {
        this.blind.get().setBlind(blind);
        if (blindNumber == 1) {
            setButtonText("Select");

            setActivity(false);
            setEarn(3);
            try {
                skipPane = loaderSkipPane.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blindSkipController = loaderSkipPane.getController();
            skipAnchorPane.getChildren().add(skipPane);
            setTag(tag);
        } else if (blindNumber == 2) {
            setButtonText("Upcoming");

            setActivity(false);
            setEarn(4);
            try {
                skipPane = loaderSkipPane.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blindSkipController = loaderSkipPane.getController();
            skipAnchorPane.getChildren().add(skipPane);
            setTag(tag);
        } else if (blindNumber == 3) {
            setButtonText("Upcoming");

            setActivity(false);
            setEarn(5);
            try {
                bossPane = loaderBossPane.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            skipAnchorPane.getChildren().add(bossPane);
        }
    }

    public void setButtonText(String text) {
        btnSelectBlind.setText(text);
    }
    */

    public void setBossPanel(boolean isBoss) {
        try {
            if (isBoss) {
                skipAnchorPane.getChildren().add(loaderBossPane.load());
            } else {
                skipAnchorPane.getChildren().add(loaderSkipPane.load());
                blindSkipController = loaderSkipPane.getController();
                System.out.println("Tags empty: " + gameModel.getRunTags().isEmpty());
            //    setTag(gameModel.getRunTags().isEmpty() ?
            //            new Tag() :
            //            gameModel.getRunTags().get((gameModel.getAnte()-1)*2));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tag getTag() {
       return blindSkipController.getTag().get();
    }

    public void setActivity(boolean isActivity) {
        blindPanel.setDisable(isActivity);
    }

    public void setMinScore(BigDecimal score) {
        lblMinScore.setText(score.toString());
    }

    public void setEarn(int score) {
        lblEarn.setText("$".repeat(Math.max(0, score)) + "+");
    }

    public void play() {
       System.out.println("Blind: " + blind.get().getBlindName());

       gameModel.setActiveBlind(blind.get());
       gameModel.setRound(gameModel.getRound() + 1);
       gameModel.setHandButtonVisibility(true);
        System.out.println("blind box visibilty: " + gameModel.getBlindsVisibility());
       gameModel.setBlindsVisibility(false);
        System.out.println("blind box visibilty: " + gameModel.getBlindsVisibility());
       gameController.startRound(new BigDecimal(lblMinScore.getText()));
    }

    public void skip() {
        gameController.skip(blindSkipController.getTag().get());
    }

    public void setTag(Tag tag) {
        System.out.println("Set Tag");
        blindSkipController.setTag(tag);
    }

    public void setName(String test) {
        System.out.println(lblBlindName.textProperty().isBound());
        System.out.println(blind.get().getBlindName());
        blind.get().setBlindName(test);
        System.out.println(blind.get().getBlindName());
    }
}
