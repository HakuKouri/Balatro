package com.example.balatro.models;

import com.example.balatro.Balatro;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;

public class SettingsModel {

    private DoubleProperty windowWidth = new SimpleDoubleProperty(Balatro.getPrimaryStage().getWidth());
    private DoubleProperty windowHeight = new SimpleDoubleProperty(Balatro.getPrimaryStage().getHeight());

    //region Game Settings
    private final IntegerProperty gameSpeed = new SimpleIntegerProperty();
    private final BooleanProperty playDiscardOrder = new SimpleBooleanProperty(true);
    private final IntegerProperty screenShake = new SimpleIntegerProperty();
    private final BooleanProperty displayStakeDuringRun = new SimpleBooleanProperty(false);
    private final BooleanProperty highContrast = new SimpleBooleanProperty(false);
    private final BooleanProperty reduceMotion = new SimpleBooleanProperty(false);
    //endregion

    //region Video Settings
    private final ObservableList<Screen> screens = FXCollections.observableArrayList();
    private enum ScreenState {fullscreen, borderless, windowed, none};
    //TODO Screen Resolution
    //private final List<Rectangle2D> resolutions = new List(){new Rectangle2D()}
    private final BooleanProperty vsync = new SimpleBooleanProperty(true);
    //endregion

    //region Graphics
    private final BooleanProperty shadow = new SimpleBooleanProperty(true);
    private final BooleanProperty pixelArtSmoothing = new SimpleBooleanProperty(true);
    private final IntegerProperty crtEffect = new SimpleIntegerProperty(50);
    private final BooleanProperty crtBloom = new SimpleBooleanProperty(false);
    //endregion

    //region Audio
    private final IntegerProperty masterVolume = new SimpleIntegerProperty(50);
    private final IntegerProperty musicVolume = new SimpleIntegerProperty(50);
    private final IntegerProperty gameVolume = new SimpleIntegerProperty(50);
    //endregion

    public static ScreenState current;
    private static GraphicsEnvironment env =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static GraphicsDevice ev = env.getDefaultScreenDevice();


    //region Constructor
    public SettingsModel() {
        screens.addAll(Screen.getScreens());

    }
    //endregion

    //region Getter Setter
    public int getGameSpeed() {
        return gameSpeed.get();
    }

    public IntegerProperty gameSpeedProperty() {
        return gameSpeed;
    }

    public boolean isPlayDiscardOrder() {
        return playDiscardOrder.get();
    }

    public BooleanProperty playDiscardOrderProperty() {
        return playDiscardOrder;
    }

    public int getScreenShake() {
        return screenShake.get();
    }

    public IntegerProperty screenShakeProperty() {
        return screenShake;
    }

    public boolean isDisplayStakeDuringRun() {
        return displayStakeDuringRun.get();
    }

    public BooleanProperty displayStakeDuringRunProperty() {
        return displayStakeDuringRun;
    }

    public boolean isHighContrast() {
        return highContrast.get();
    }

    public BooleanProperty highContrastProperty() {
        return highContrast;
    }

    public boolean isReduceMotion() {
        return reduceMotion.get();
    }

    public BooleanProperty reduceMotionProperty() {
        return reduceMotion;
    }

    public ObservableList<Screen> getScreens() {
        return screens;
    }

    public boolean isVsync() {
        return vsync.get();
    }

    public BooleanProperty vsyncProperty() {
        return vsync;
    }

    public boolean isShadow() {
        return shadow.get();
    }

    public BooleanProperty shadowProperty() {
        return shadow;
    }

    public boolean isPixelArtSmoothing() {
        return pixelArtSmoothing.get();
    }

    public BooleanProperty pixelArtSmoothingProperty() {
        return pixelArtSmoothing;
    }

    public int getCrtEffect() {
        return crtEffect.get();
    }

    public IntegerProperty crtEffectProperty() {
        return crtEffect;
    }

    public boolean isCrtBloom() {
        return crtBloom.get();
    }

    public BooleanProperty crtBloomProperty() {
        return crtBloom;
    }

    public int getMasterVolume() {
        return masterVolume.get();
    }

    public IntegerProperty masterVolumeProperty() {
        return masterVolume;
    }

    public int getMusicVolume() {
        return musicVolume.get();
    }

    public IntegerProperty musicVolumeProperty() {
        return musicVolume;
    }

    public int getGameVolume() {
        return gameVolume.get();
    }

    public IntegerProperty gameVolumeProperty() {
        return gameVolume;
    }

    //endregion

    //region Function
    public void changeWindow(ScreenState applied, JFrame frame) {

        if (applied == ScreenState.fullscreen && current != ScreenState.fullscreen) {
            if (ev.isFullScreenSupported()) {
                ev.setFullScreenWindow(frame);
            }
            current =  ScreenState.fullscreen;
        }
        if (applied == ScreenState.borderless && current != ScreenState.borderless) {
            frame.setUndecorated(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            current =  ScreenState.borderless;
        }
        if (applied == ScreenState.windowed && current != ScreenState.windowed) {
            frame.setUndecorated(false);
            // you can choose to make the screen fit or not
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            current =  ScreenState.windowed;
        }
    }
    //endregion
}
