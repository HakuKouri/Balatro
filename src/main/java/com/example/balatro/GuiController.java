package com.example.balatro;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiController
{
    private static GuiController instance;
    private Stage primaryStage;

    public static synchronized GuiController getInstance() {
        if(instance == null) instance = new GuiController();
        return instance;
    }

    public Stage getPrimaryStage() { return primaryStage; }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public void openTitleGui() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("title-screen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280,720);

            primaryStage.setTitle("Balatro");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
