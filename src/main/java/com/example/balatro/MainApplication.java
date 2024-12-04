package com.example.balatro;


import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        System.out.println("test");
        GuiController.getInstance().setPrimaryStage(primaryStage);
        GuiController.getInstance().openTitleGui();
    }

    public static void main(String[] args) {launch(args);}
}
