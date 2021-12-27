package com.gmail.visualbukkit.blocks;

import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class PluginComponentSource extends BlockSource<PluginComponent> {

    public PluginComponentSource(PluginComponent component) {
        super(component);

        getStyleClass().add("plugin-component-source");

        setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                e.consume();
            }
        });
    }
}
