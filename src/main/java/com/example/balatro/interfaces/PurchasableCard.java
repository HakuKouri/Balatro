package com.example.balatro.interfaces;

import com.example.balatro.models.GameModel;
import javafx.scene.layout.AnchorPane;

public interface PurchasableCard {
    void onPurchase(GameModel model, AnchorPane pane);
}
