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

    opens com.example.balatro to javafx.fxml;
    exports com.example.balatro;
    exports com.example.balatro.controller;
    opens com.example.balatro.controller to javafx.fxml;
}