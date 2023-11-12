package com.gmail.visualbukkit.blocks;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class StatementSource extends Label {

    private final BlockFactory<StatementBlock> factory;

    public StatementSource(BlockFactory<StatementBlock> factory) {
        super(factory.getBlockDefinition().name());
        this.factory = factory;
        getStyleClass().add("statement-source");

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

        setOnDragDone(e -> {
            StatementConnector.hideCurrent();
            e.consume();
        });
    }

    public BlockFactory<StatementBlock> getFactory() {
        return factory;
    }
}
