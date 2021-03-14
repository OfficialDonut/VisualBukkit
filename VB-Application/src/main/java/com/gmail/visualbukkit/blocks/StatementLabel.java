package com.gmail.visualbukkit.blocks;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

public class StatementLabel extends Label {

    private Statement statement;

    public StatementLabel(Statement statement) {
        super(statement.getTitle());
        this.statement = statement;

        getStyleClass().add("statement-label");

        setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null), e.getX(), e.getY());
                ClipboardContent content = new ClipboardContent();
                content.put(StatementConnector.POINT_DATA_FORMAT, new Point2D.Double(e.getX(), e.getY()));
                dragboard.setContent(content);
                e.consume();
            }
        });
    }

    public Statement getStatement() {
        return statement;
    }
}
