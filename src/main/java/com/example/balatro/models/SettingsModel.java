package com.example.balatro.models;

import com.example.balatro.Balatro;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsModel {

    private Screen screen;
    private final DoubleProperty windowWidth = new SimpleDoubleProperty();
    private final DoubleProperty windowHeight = new SimpleDoubleProperty();
    private final DoubleProperty cardHeight = new SimpleDoubleProperty(0);

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
    private final IntegerProperty screenIndex = new SimpleIntegerProperty();
    private enum ScreenState {fullscreen, borderless, windowed, none};
    private final StringProperty windowMode = new SimpleStringProperty();
    private final StringProperty resolution = new SimpleStringProperty();
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
        windowHeightProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Window height: " + newVal);
            cardHeightProperty().set((double)newVal * 0.20138);
        });
    }
    //endregion

    //region Getter Setter
    public double getWindowWidth() {
        return windowWidth.get();
    }

    public DoubleProperty windowWidthProperty() {
        return windowWidth;
    }

    public void setWindowWidth(double windowWidth) {
        this.windowWidth.set(windowWidth);
    }

    public double getWindowHeight() {
        return windowHeight.get();
    }

    public DoubleProperty windowHeightProperty() {
        return windowHeight;
    }

    public void setWindowHeight(double windowHeight) {
        this.windowHeight.set(windowHeight);
    }

    public double getCardHeight() {
        return cardHeight.get();
    }

    public DoubleProperty cardHeightProperty() {
        return cardHeight;
    }

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

    public int getScreen() {
        return screenIndex.get();
    }

    public IntegerProperty screenProperty() {
        return screenIndex;
    }

    public String getWindowMode() {
        return windowMode.get();
    }

    public StringProperty windowModeProperty() {
        return windowMode;
    }

    public String getResolution() {
        return resolution.get();
    }

    public StringProperty resolutionProperty() {
        return resolution;
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
    public void setSettings(String rootPath) {
        Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(rootPath));

            //Game
            gameSpeedProperty().set(Integer.parseInt(properties.getProperty("game speed")));
            playDiscardOrderProperty().set(Boolean.parseBoolean(properties.getProperty("hand button order")));
            screenShakeProperty().set(Integer.parseInt(properties.getProperty("screen shake")));
            displayStakeDuringRunProperty().set(Boolean.parseBoolean(properties.getProperty("display stake")));
            highContrastProperty().set(Boolean.parseBoolean(properties.getProperty("high contrast")));
            reduceMotionProperty().set(Boolean.parseBoolean(properties.getProperty("reduced motion")));
            //Video
            screenProperty().set(Integer.parseInt(properties.getProperty("display monitor")));
            windowModeProperty().set(properties.getProperty("window mode"));
            resolutionProperty().set(properties.getProperty("resolution"));
            vsyncProperty().set(Boolean.parseBoolean(properties.getProperty("vsync")));
            //Graphics
            shadowProperty().set(Boolean.parseBoolean(properties.getProperty("shadows")));
            pixelArtSmoothingProperty().set(Boolean.parseBoolean(properties.getProperty("pixel art smooting")));
            crtEffectProperty().set(Integer.parseInt(properties.getProperty("crt")));
            crtBloomProperty().set(Boolean.parseBoolean(properties.getProperty("crt bloom")));
            //Audio
            masterVolumeProperty().set(Integer.parseInt(properties.getProperty("master volume")));
            musicVolumeProperty().set(Integer.parseInt(properties.getProperty("music volume")));
            gameVolumeProperty().set(Integer.parseInt(properties.getProperty("game volume")));

            System.out.printf("Resolution: " + getResolution());
            setWindowWidth(Double.parseDouble(getResolution().split("x")[0]));
            setWindowHeight(Double.parseDouble(getResolution().split("x")[1]));

            System.out.println("Width: " + getWindowWidth() + " Height: " + getWindowHeight());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createSettingsFile(String rootPath) {
        Properties props = new Properties();
        //Game
        props.setProperty("game speed", "1");
        props.setProperty("hand button order", "true");
        props.setProperty("screen shake", "50");
        props.setProperty("display stake", "false");
        props.setProperty("high contrast", "false");
        props.setProperty("reduced motion", "false");
        //Video

        props.setProperty("display monitor", String.valueOf(Screen.getScreens().indexOf(Screen.getPrimary())));
        props.setProperty("window mode", "fullscreen");
        props.setProperty("resolution", Screen.getPrimary().getVisualBounds().getWidth() + "x" +  Screen.getPrimary().getVisualBounds().getHeight());
        props.setProperty("vsync", "true");
        //Graphics
        props.setProperty("shadows", "true");
        props.setProperty("pixel art smooting", "true");
        props.setProperty("crt", "50");
        props.setProperty("crt bloom", "false");
        //Audio
        props.setProperty("master volume", "50");
        props.setProperty("music volume", "50");
        props.setProperty("game volume", "50");

        try {
            props.storeToXML(new FileOutputStream(rootPath), "store to xml file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getSettings() {
        Properties props = new Properties();
        //Game
        props.setProperty("game speed", String.valueOf(getGameSpeed()));
        props.setProperty("hand button order", String.valueOf(isPlayDiscardOrder()));
        props.setProperty("screen shake", String.valueOf(getScreenShake()));
        props.setProperty("display stake", String.valueOf(isDisplayStakeDuringRun()));
        props.setProperty("high contrast", String.valueOf(isHighContrast()));
        props.setProperty("reduced motion", String.valueOf(isReduceMotion()));
        //Video
        props.setProperty("display monitor", String.valueOf(getScreen()));
        props.setProperty("window mode", getWindowMode());
        props.setProperty("resolution", getResolution());
        props.setProperty("vsync", String.valueOf(isVsync()));
        //Graphics
        props.setProperty("shadows", String.valueOf(isShadow()));
        props.setProperty("pixel art smooting", String.valueOf(isPixelArtSmoothing()));
        props.setProperty("crt", String.valueOf(getCrtEffect()));
        props.setProperty("crt bloom", String.valueOf(isCrtBloom()));
        //Audio
        props.setProperty("master volume", String.valueOf(getMasterVolume()));
        props.setProperty("music volume", String.valueOf(getMusicVolume()));
        props.setProperty("game volume", String.valueOf(getGameVolume()));

        return props;
    }

    public void updateSettings(String rootPath) {
        Properties props = getSettings();

        //Video
        props.setProperty("display monitor", String.valueOf(Screen.getScreens().indexOf(Screen.getPrimary())));
        props.setProperty("window mode", getSettings().getProperty("window mode"));
        props.setProperty("resolution", Screen.getPrimary().getVisualBounds().getWidth() + "x" +  Screen.getPrimary().getVisualBounds().getHeight());
        props.setProperty("vsync", getSettings().getProperty("vsync"));
        //Graphics
        props.setProperty("shadows", getSettings().getProperty("shadows"));
        props.setProperty("pixel art smooting", getSettings().getProperty("pixel art smooting"));
        props.setProperty("crt", getSettings().getProperty("crt"));
        props.setProperty("crt bloom", getSettings().getProperty("crt bloom"));
        //Audio
        props.setProperty("master volume", getSettings().getProperty("master volume"));
        props.setProperty("music volume", getSettings().getProperty("music volume"));
        props.setProperty("game volume", getSettings().getProperty("game volume"));

        try {
            props.storeToXML(new FileOutputStream(rootPath), "update xml file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
