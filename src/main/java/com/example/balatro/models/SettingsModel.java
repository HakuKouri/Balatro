package com.example.balatro.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsModel {

    private static final String SETTINGS_PATH = "settings.xml";

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
    private final StringProperty windowMode = new SimpleStringProperty("windowed");
    private final StringProperty resolution = new SimpleStringProperty();
    //TODO Screen Resolution
    //private final List<Rectangle2D> resolutions = new List(){new Rectangle2D()}
    private final BooleanProperty vsync = new SimpleBooleanProperty(true);


    private final DoubleProperty storedWindowWidth = new SimpleDoubleProperty(1280);
    private final DoubleProperty storedWindowHeight = new SimpleDoubleProperty(720);

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

    private static ScreenState current;
    private static final GraphicsEnvironment env =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice ev = env.getDefaultScreenDevice();


    //region Constructor
    public SettingsModel() {
        windowHeightProperty().addListener((obs, oldVal, newVal) -> {
            //System.out.println("Window height: " + newVal);
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


    public double getStoredWindowWidth() { return storedWindowWidth.get(); }
    public DoubleProperty storedWindowWidthProperty() { return storedWindowWidth; }
    public void setStoredWindowWidth(double width) { this.storedWindowWidth.set(width); }

    public double getStoredWindowHeight() { return storedWindowHeight.get(); }
    public DoubleProperty storedWindowHeightProperty() { return storedWindowHeight; }
    public void setStoredWindowHeight(double height) { this.storedWindowHeight.set(height); }

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
    public void loadFromFile(String path) {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(path) ) {
            props.loadFromXML(in);

            //Game
            gameSpeed.set(Integer.parseInt(props.getProperty("gameSpeed", "1")));
            playDiscardOrder.set(Boolean.parseBoolean(props.getProperty("playDiscardOrder", "true")));
            screenShake.set(Integer.parseInt(props.getProperty("screenShake", "50")));
            displayStakeDuringRun.set(Boolean.parseBoolean(props.getProperty("displayStakeDuringRun", "false")));
            highContrast.set(Boolean.parseBoolean(props.getProperty("highContrast", "false")));
            reduceMotion.set(Boolean.parseBoolean(props.getProperty("reduceMotion", "false")));
            //Screen
            storedWindowWidth.set(Double.parseDouble(props.getProperty("window width", "1280")));
            storedWindowHeight.set(Double.parseDouble(props.getProperty("window height", "720")));
            //Graphics
            shadow.set(Boolean.parseBoolean(props.getProperty("shadow", "true")));
            pixelArtSmoothing.set(Boolean.parseBoolean(props.getProperty("pixelArtSmoothing", "true")));
            crtBloom.set(Boolean.parseBoolean(props.getProperty("crtBloom", "true")));
            crtEffect.set(Integer.parseInt(props.getProperty("crtEffect", "30")));
            //Audio
            masterVolume.set(Integer.parseInt(props.getProperty("masterVolume", "100")));
            musicVolume.set(Integer.parseInt(props.getProperty("musicVolume", "100")));
            gameVolume.set(Integer.parseInt(props.getProperty("gameVolume", "100")));
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            System.out.println("Default used!");
        }
    }

    public void saveToFile(String path) {
        Properties props = new Properties();
        //Game
        props.setProperty("gameSpeed", String.valueOf(gameSpeed.get()));
        props.setProperty("playDiscardOrder", String.valueOf(playDiscardOrder.get()));
        props.setProperty("screenShake", String.valueOf(screenShake.get()));
        props.setProperty("displayStakeDuringRun", String.valueOf(displayStakeDuringRun.get()));
        props.setProperty("highContrast", String.valueOf(highContrast.get()));
        props.setProperty("reduceMotion", String.valueOf(reduceMotion.get()));
        //Screen
        props.setProperty("window width", String.valueOf(getStoredWindowWidth()));
        props.setProperty("window height", String.valueOf(getStoredWindowHeight()));
        //Graphics
        props.setProperty("shadow", String.valueOf(shadow.get()));
        props.setProperty("pixelArtSmoothing", String.valueOf(pixelArtSmoothing.get()));
        props.setProperty("crtBloom", String.valueOf(crtBloom.get()));
        props.setProperty("crtEffect", String.valueOf(crtEffect.get()));
        //Audio
        props.setProperty("masterVolume", String.valueOf(masterVolume.get()));
        props.setProperty("musicVolume", String.valueOf(musicVolume.get()));
        props.setProperty("gameVolume", String.valueOf(gameVolume.get()));

        try (FileOutputStream out = new FileOutputStream(path)) {
            props.storeToXML(out, "User settings");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void initOrCreate() {
        File file = new File(SETTINGS_PATH);
        if (!file.exists()) {
            saveToFile(SETTINGS_PATH); // Erstellt Default
        }
        loadFromFile(SETTINGS_PATH);
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

    private void changeWindow(ScreenState applied, JFrame frame) {

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
