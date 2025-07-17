package com.example.balatro.domain.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.IOException;

public class FxmlUtil {
    public static <T> T loadController(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtil.class.getResource(fxmlPath));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    public static <T> Pair<T, AnchorPane> loadWithPane(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtil.class.getResource(fxmlPath));
        try {
            AnchorPane pane = loader.load();
            T controller = loader.getController();
            return new Pair<>(controller, pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
