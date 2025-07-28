package com.example.balatro.controller;

import com.example.balatro.Balatro;
import com.example.balatro.domain.rules.Blind;
import com.example.balatro.domain.rewards.Tag;
import com.example.balatro.domain.util.FxmlUtil;
import com.example.balatro.models.GameModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.math.BigDecimal;

public class BlindBoxPanelController {

    //region FXML
    @FXML
    private AnchorPane blindPanel;
    @FXML
    private Label blindEffect_Label;
    @FXML
    private Button btnSelectBlind;
    @FXML
    private AnchorPane skipAnchorPane;
    @FXML
    private Label blindName_Label;
    @FXML
    private ImageView blindChip_ImageView;
    @FXML
    private ImageView stakeChip_ImageView;
    @FXML
    private Label scoreToReach_Label;
    @FXML
    private Label earn_Label;
    //endregion

    //region Attributes
    private final GameModel gameModel = Balatro.getGameModel();

    private final ObjectProperty<Blind> blind = new SimpleObjectProperty<>(new Blind());

    private BlindSkipPaneController blindSkipController;

    private BooleanProperty skipped = new SimpleBooleanProperty(false);
    private BooleanProperty nextBlind = new SimpleBooleanProperty(false);
    private BooleanProperty defeated = new SimpleBooleanProperty(false);
    private BooleanProperty boss = new SimpleBooleanProperty(false);

    private ObjectProperty<BigDecimal> scoreToReach = new SimpleObjectProperty<>(new BigDecimal(0));
    //endregion

    //region Getter & Setter
    public Blind getBlind () {
        return blind.get();
    }
    public void setBlind(Blind blind) {
        getBlind().setBlind(blind);
    }

    public boolean isSkipped() {
        return skipped.get();
    }

    public BooleanProperty skippedProperty() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped.set(skipped);
    }

    public boolean getNextBlind() {
        return nextBlind.get();
    }

    public BooleanProperty nextBlindProperty() {
        return nextBlind;
    }

    public void setNextBlind(boolean nextBlind) {
        this.nextBlind.set(nextBlind);
    }

    public boolean isDefeated() {
        return defeated.get();
    }

    public BooleanProperty defeatedProperty() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated.set(defeated);
    }

    public boolean isBoss() {
        return boss.get();
    }

    public BooleanProperty bossProperty() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss.set(boss);
    }

    public double getScoredMultiplier() {
        return getBlind().getBlindScoreMultiplier();
    }

    public BigDecimal getScoreToReach() {
        return scoreToReach.get();
    }

    public ObjectProperty<BigDecimal> scoreToReachProperty() {
        return scoreToReach;
    }

    //endregion

    public void initialize() {
        bindUi();

    }

    private void bindUi() {
        blindPanel.disableProperty().bind(nextBlindProperty());

        btnSelectBlind.textProperty().bind(Bindings.createStringBinding(() ->
                isSkipped() ? "Skipped" : isDefeated() ? "Defeated" : getNextBlind() ? "Select Blind" : "Upcoming..."
        , skippedProperty(), defeatedProperty(), nextBlindProperty()));

        blindName_Label.textProperty().bind(getBlind().blindNameProperty());
        blindChip_ImageView.imageProperty().bind(getBlind().imageProperty());
        blindEffect_Label.textProperty().bind(Bindings.createStringBinding(() ->
                isBoss() ? getBlind().getBlindDescription() : "",
                bossProperty()));
        blindEffect_Label.setWrapText(true);

        stakeChip_ImageView.imageProperty().bind(gameModel.getRunState().getChosenStake().imageProperty());
        scoreToReach_Label.textProperty().bind(Bindings.createStringBinding(() -> getScoreToReach().toString(), scoreToReachProperty()));

        earn_Label.textProperty().bind(Bindings.createStringBinding(
                () -> "$".repeat(Math.max(0, blind.get().getBlindReward())),
                blind.get().blindRewardProperty()
        ));
    }

    public void setBossPanel(boolean isBoss) {
            if (isBoss) {
                setBoss(true);
                skipAnchorPane.getChildren().add(FxmlUtil.loadWithPane("/com/example/balatro/bossPane.fxml").getValue());
            } else {
                Pair<BlindSkipPaneController, AnchorPane> skip = FxmlUtil.loadWithPane("/com/example/balatro/blindSkipPane.fxml");
                skipAnchorPane.getChildren().add(skip.getValue());
                blindSkipController = skip.getKey();
                blindSkipController.setBlindPanelController(this);
            }
    }

    public Tag getTag() {
       return blindSkipController.getTag();
    }

    public void setTag(Tag tag) {
        blindSkipController.setTag(tag);
    }

    public void setIfNextBlind(boolean isDisabled) {
        nextBlind.set(isDisabled);
    }

    public void setScoreToReach(BigDecimal score) {
        scoreToReach.set(score.multiply(BigDecimal.valueOf(getBlind().getBlindScoreMultiplier())));
    }

    public void setEarn(int score) {
        earn_Label.setText("$".repeat(Math.max(0, score)) + "+");
    }

    public void play() {
       gameModel.setActiveBlind(blind.get());
       gameModel.getRunState().setRound(gameModel.getRunState().getRound() + 1);
       gameModel.setHandButtonVisibility(true);
       gameModel.setBlindsVisibility(false);
       gameModel.pickedBlindVisibilityProperty().set(true);
       GameController.getInstance().startRound(new BigDecimal(scoreToReach_Label.getText()));
    }

    public void skip(Tag tag) {
        GameController.getInstance().skip(tag);
        BlindBoxController.nextBlind();
    }
}
