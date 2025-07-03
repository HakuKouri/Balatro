module com.example.balatro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.jsoup;
    requires java.desktop;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;

    exports com.example.balatro;
    exports com.example.balatro.controller;
    exports com.example.balatro.classes;
    exports com.example.balatro.models;

    opens com.example.balatro to javafx.fxml;
    opens com.example.balatro.controller to javafx.fxml;
}