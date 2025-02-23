package com.example.balatro.models;

import com.example.balatro.classes.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameModel {
    static final int maxAnte = 8;
    private ObservableList<Tag> tagStack = FXCollections.observableArrayList();

    public int getMaxAnte() {
        return maxAnte;
    }

    public ObservableList<Tag> getTagStack() {
        return tagStack;
    }

    public void addTagToStack(Tag tag) {
        tagStack.add(tag);
    }

    public void removeTagFromStack(Tag tag) {
        tagStack.remove(tag);
    }
}
