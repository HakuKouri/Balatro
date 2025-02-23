package com.example.balatro.controller;

import com.example.balatro.models.PointsScoredModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PointsScoredController {

    @FXML
    private Label pointsScoredLabel;
    @FXML
    private ImageView stakeImageView;

    public PointsScoredModel model = new PointsScoredModel();

    public void initialize() {
        /*System.out.println("🚀 initialize() gestartet!");

        if (stakeImageView == null) {
            System.out.println("❌ FEHLER: stakeImageView ist NULL!");
        } else {
            System.out.println("✅ stakeImageView ist NICHT null!");
        }

        System.out.println("📷 Aktuelles Bild beim Start: " + stakeChipImageUrl.get());
        */

        // 🌟 Teste: Setze das Bild manuell und prüfe, ob sich etwas ändert
        //stakeImageView.setImage(new Image(stakeChipImageUrl.get(), true));

        // 🌟 Teste: Falls die URL sich ändert, setze das Bild erneut
        model.stakeChipImageUrlProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("🔄 Image geändert auf: " + newVal);
            stakeImageView.setImage(new Image(newVal, true));
        });

        pointsScoredLabel.textProperty().bind(model.scoredPointsProperty().asString());

        /*
        File file = new File(stakeChipImageUrl.get().replace("file:", ""));
        System.out.println("📂 Datei existiert? " + file.exists() + " | Pfad: " + file.getAbsolutePath());
        */
    }

    public void setStakeImageView(String imageUrl) {
        // Pfad normalisieren
        model.setStakeChipImageUrl(imageUrl.replace("\\", "/"));

        // Direktes Setzen des Bildes zur Sicherheit
        stakeImageView.setImage(new Image(model.getStakeChipImageUrl(), true));
    }

    public void addPoints(long v) {
        model.setScoredPoints(model.getScoredPoints() + v );
    }

    public void clearPoints() {
        model.setScoredPoints(0);
    }
}
