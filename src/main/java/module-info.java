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
    requires java.management;
    requires org.xerial.sqlitejdbc;

    exports com.example.balatro;
    exports com.example.balatro.controller;
    exports com.example.balatro.models;

    opens com.example.balatro to javafx.fxml;
    opens com.example.balatro.controller to javafx.fxml;
    exports com.example.balatro.enums;
    exports com.example.balatro.domain.card;
    exports com.example.balatro.domain.deck;
    exports com.example.balatro.domain.game;
    exports com.example.balatro.domain.effects;
    exports com.example.balatro.domain.util;
    exports com.example.balatro.domain.rules;
    exports com.example.balatro.data;
    exports com.example.balatro.data.web;
    exports com.example.balatro.domain.rewards;
    exports com.example.balatro.models.settings;
    exports com.example.balatro.controller.menuController;
    opens com.example.balatro.controller.menuController to javafx.fxml;
    opens com.example.balatro.domain.util to javafx.fxml;
}