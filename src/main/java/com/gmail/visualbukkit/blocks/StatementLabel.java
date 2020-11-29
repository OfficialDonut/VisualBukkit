package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkit;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class StatementLabel extends Label {

    private StatementDefinition<?> statement;

    public StatementLabel(StatementDefinition<?> statement) {
        super(statement.getName());
        this.statement = statement;

        setBackground(new Background(new BackgroundFill(statement.getBlockColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(3));
        setTextFill(Color.BLACK);

        setOnDragDetected(e -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            dragboard.setDragView(snapshot(snapshotParameters, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            dragboard.setContent(content);
            e.consume();
        });

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().inspect(statement);
                e.consume();
            }
        });
    }

    public StatementDefinition<?> getStatement() {
        return statement;
    }
}
