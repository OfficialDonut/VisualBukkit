package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.ui.LanguageManager;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class StatementLabel extends Label {

    private Statement statement;

    public StatementLabel(Statement statement) {
        super(statement.getTitle());
        this.statement = statement;
        getStyleClass().add("statement-label");
        setTooltip(new Tooltip(statement.getDescription() != null ? statement.getDescription() : LanguageManager.get("tooltip.no_description")));

        setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null), -1, -1);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                e.consume();
            }
        });
    }

    public Statement.Block createBlock() {
        return statement.createBlock();
    }

    public Statement getStatement() {
        return statement;
    }
}
