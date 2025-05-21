package com.example.balatro.models;

import com.example.balatro.Balatro;
import javafx.beans.Observable;
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
        String newAppConfigXmlFile = rootPath;

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
            props.storeToXML(new FileOutputStream(newAppConfigXmlFile), "store to xml file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getSettings() {
        Properties props = new Properties();
        //Game
        props.getProperty("game speed");
        props.getProperty("hand button order");
        props.getProperty("screen shake");
        props.getProperty("display stake");
        props.getProperty("high contrast");
        props.getProperty("reduced motion");
        //Video
        props.getProperty("display monitor");
        props.getProperty("window mode");
        props.getProperty("resolution");
        props.getProperty("vsync");
        //Graphics
        props.getProperty("shadows");
        props.getProperty("pixel art smooting");
        props.getProperty("crt");
        props.getProperty("crt bloom");
        //Audio
        props.getProperty("master volume");
        props.getProperty("music volume");
        props.getProperty("game volume");

        return props;
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
